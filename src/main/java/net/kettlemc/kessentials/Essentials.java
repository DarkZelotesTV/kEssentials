package net.kettlemc.kessentials;

import io.github.almightysatan.slams.Placeholder;
import io.github.almightysatan.slams.minimessage.AdventureMessage;
import net.kettlemc.kcommon.bukkit.ContentManager;
import net.kettlemc.kessentials.command.*;
import net.kettlemc.kessentials.command.tpa.TPACommand;
import net.kettlemc.kessentials.command.tpa.TPAcceptCommand;
import net.kettlemc.kessentials.command.tpa.TPDenyCommand;
import net.kettlemc.kessentials.command.tpa.TPListCommand;
import net.kettlemc.kessentials.config.Configuration;
import net.kettlemc.kessentials.config.Messages;
import net.kettlemc.kessentials.listener.JoinQuitListener;
import net.kettlemc.kessentials.loading.Loadable;
import net.kettlemc.klanguage.api.LanguageAPI;
import net.kettlemc.klanguage.bukkit.BukkitLanguageAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Essentials implements Loadable {

    public static final LanguageAPI<Player> LANGUAGE_API = BukkitLanguageAPI.of();
    private static Essentials instance;

    private final ContentManager contentManager;
    private final JavaPlugin plugin;
    private BukkitAudiences adventure;

    public Essentials(JavaPlugin plugin) {
        this.plugin = plugin;
        this.contentManager = new ContentManager(plugin);
    }

    public static Essentials instance() {
        return instance;
    }

    public BukkitAudiences adventure() {
        if (this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    @Override
    public void onEnable() {

        instance = this;

        this.plugin.getLogger().info("Loading adventure support...");
        this.adventure = BukkitAudiences.create(this.plugin);

        this.plugin.getLogger().info("Registering config...");
        if (!Configuration.load()) {
            this.plugin.getLogger().severe("Failed to load config!");
        }

        this.plugin.getLogger().info("Loading messages...");
        if (!Messages.load()) {
            this.plugin.getLogger().severe("Failed to load messages!");
        }

        this.plugin.getLogger().info("Registering commands and listeners...");
        TimeCommands timeCommands = new TimeCommands();
        TimeCommands.TIME_MAP.keySet().forEach(time -> contentManager.registerCommand(time, timeCommands));

        contentManager.registerCommand("tpa", new TPACommand());
        contentManager.registerCommand("tpaccept", new TPAcceptCommand());
        contentManager.registerCommand("tpdeny", new TPDenyCommand());
        contentManager.registerCommand("tplist", new TPListCommand());

        contentManager.registerCommand("gamemode", new GamemodeCommand());

        contentManager.registerCommand("suicide", new SuicideCommand());

        contentManager.registerCommand("f3d", new F3DCommand());

        contentManager.registerCommand("speed", new SpeedCommand());

        contentManager.registerCommand("fly", new FlyCommand());

        contentManager.registerCommand("chatclear", new ChatClearCommand());

        contentManager.registerCommand("enderchest", new EnderchestCommand());

        Configuration.DISABLED_COMMANDS.getValue().forEach(cmd -> Bukkit.getPluginCommand(cmd).setExecutor(new DisabledCommandExecutor()));

        contentManager.registerListener(new JoinQuitListener());
    }

    @Override
    public void onDisable() {
        this.plugin.getLogger().info("Disabling plugin...");
        Configuration.unload();
        instance = null;
    }

    public void sendMessage(CommandSender sender, AdventureMessage message) {
        Audience audience = Essentials.instance().adventure().sender(sender);
        if (sender instanceof Player) {
            Player player = (Player) sender;
            audience.sendMessage(Messages.PREFIX.value().append(message.value(LANGUAGE_API.getEntity(player))));
            return;
        }
        audience.sendMessage(Messages.PREFIX.value().append(message.value()));
    }

    public void sendMessage(CommandSender sender, AdventureMessage message, Placeholder... placeholders) {
        sendMessage(sender, message, null, null, placeholders);
    }

    public void sendMessage(CommandSender sender, AdventureMessage message, AdventureMessage hover, String command, Placeholder... placeholders) {
        Audience audience = Essentials.instance().adventure().sender(sender);
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Component component = message.value(LANGUAGE_API.getEntity(player), placeholders);
            if (hover != null) {
                component = component.hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(hover.value(LANGUAGE_API.getEntity(player), placeholders)));
            }
            if (command != null) {
                component = component.clickEvent(net.kyori.adventure.text.event.ClickEvent.runCommand(command));
            }
            audience.sendMessage(Messages.PREFIX.value().append(component));
            return;
        }
        audience.sendMessage(Messages.PREFIX.value().append(message.value(placeholders)));
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public boolean checkPermission(CommandSender sender, Command command, boolean other) {
        return (sender instanceof ConsoleCommandSender)
                || (sender.hasPermission(Configuration.PERMISSION_LAYOUT_OTHER.getValue().replace("%command%", command.getLabel())))
                || (!other && sender.hasPermission(Configuration.PERMISSION_LAYOUT.getValue().replace("%command%", command.getLabel())));
    }
}

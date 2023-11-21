package net.kettlemc.kessentials;

import net.kettlemc.kcommon.bukkit.ContentManager;
import net.kettlemc.kcommon.language.MessageManager;
import net.kettlemc.kessentials.command.*;
import net.kettlemc.kessentials.command.tpa.TPACommand;
import net.kettlemc.kessentials.command.tpa.TPAcceptCommand;
import net.kettlemc.kessentials.command.tpa.TPDenyCommand;
import net.kettlemc.kessentials.command.tpa.TPListCommand;
import net.kettlemc.kessentials.config.Configuration;
import net.kettlemc.kessentials.config.Messages;
import net.kettlemc.kessentials.listener.BlockListener;
import net.kettlemc.kessentials.listener.InventoryClickListener;
import net.kettlemc.kessentials.listener.JoinQuitListener;
import net.kettlemc.kessentials.listener.PlayerMoveListener;
import net.kettlemc.kessentials.loading.Loadable;
import net.kettlemc.klanguage.api.LanguageAPI;
import net.kettlemc.klanguage.bukkit.BukkitLanguageAPI;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
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
    private MessageManager messageManager;
    private final JavaPlugin plugin;
    private BukkitAudiences adventure;

    public Essentials(JavaPlugin plugin) {
        this.plugin = plugin;
        this.contentManager = new ContentManager(plugin);
    }

    public static Essentials instance() {
        return instance;
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

        messageManager = new MessageManager(Messages.PREFIX, LANGUAGE_API, adventure);

        this.plugin.getLogger().info("Registering commands and listeners...");

        // Register all the time commands with one instance of the TimeCommands class
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
        contentManager.registerCommand("teleportplayer", new TeleportPlayerCommand());
        contentManager.registerCommand("freeze", new FreezeCommand());
        contentManager.registerCommand("vanish", new VanishCommand());
        contentManager.registerCommand("heal", new HealCommand());
        contentManager.registerCommand("feed", new FeedCommand());
        contentManager.registerCommand("repair", new RepairCommand());
        contentManager.registerCommand("inventorysee", new InventorySeeCommand());
        contentManager.registerCommand("armorsee", new ArmorSeeCommand());

        // Disable all commands disabled in the config
        Configuration.DISABLED_COMMANDS.getValue().forEach(cmd -> Bukkit.getPluginCommand(cmd).setExecutor(new DisabledCommandExecutor()));

        contentManager.registerListener(new JoinQuitListener());
        contentManager.registerListener(new BlockListener());
        contentManager.registerListener(new PlayerMoveListener());
        contentManager.registerListener(new InventoryClickListener());
    }

    @Override
    public void onDisable() {
        this.plugin.getLogger().info("Disabling plugin...");
        Configuration.unload();
        instance = null;
    }

    /**
     * Returns the adventure instance
     *
     * @return The adventure instance
     */
    public BukkitAudiences adventure() {
        if (this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }


    /**
     * Returns the underlying plugin
     *
     * @return The underlying plugin
     */
    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * Checks if the sender has permission to run the command.
     * When other is true, it checks for the permission to run the command on other players.
     * When other is false, it checks for the permission to run the command on themselves.
     * When the sender is a console, it always returns true.
     * <p>
     * When the sender has the permission to run the command on other players, they also have the permission to run the command on themselves.
     *
     * @param sender  The sender to check
     * @param command The command to check
     * @param other   Whether to check for the permission to run the command on other players
     * @return True if the sender has permission to run the command, false otherwise
     */
    public boolean checkPermission(CommandSender sender, Command command, boolean other) {
        return (sender instanceof ConsoleCommandSender)
                || (sender.hasPermission(Configuration.PERMISSION_LAYOUT_OTHER.getValue().replace("%command%", command.getLabel())))
                || (!other && sender.hasPermission(Configuration.PERMISSION_LAYOUT.getValue().replace("%command%", command.getLabel())));
    }

    public MessageManager messages() {
        return this.messageManager;
    }
}

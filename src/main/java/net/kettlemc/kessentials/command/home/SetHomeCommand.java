package net.kettlemc.kessentials.command.home;

import net.kettlemc.kessentials.util.Placeholder;
import net.kettlemc.kessentials.util.BukkitUtil;
import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.config.Configuration;
import net.kettlemc.kessentials.config.Messages;
import net.kettlemc.kessentials.teleport.Warp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetHomeCommand implements CommandExecutor, TabCompleter {

    private static final List<String> BLOCKED_NAMES = Arrays.asList("list", "delete", "set", "remove", "add", "max");

    public static int maxHomes(Player player) {
        int homes = BukkitUtil.getPermissionAmount(player, Configuration.PERMISSION_HOME_LAYOUT.getValue());
        return homes == 0 ? Configuration.DEFAULT_MAX_HOMES.getValue() : homes;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            Essentials.instance().messages().sendMessage(sender, Messages.PLAYER_ONLY);
            return true;
        }

        if (args.length == 0) {
            Essentials.instance().messages().sendMessage(sender, Messages.HOME_SET_USAGE);
            return true;
        }

        Player player = (Player) sender;

        int maxHomes = maxHomes(player);
        if (Essentials.instance().homeHandler().getHomes(player.getUniqueId()).size() >= maxHomes) {
            Essentials.instance().messages().sendMessage(player, Messages.HOME_MAX_HOMES_REACHED, Placeholder.of("max", () -> String.valueOf(maxHomes)));
            return true;
        }

        if (Essentials.instance().homeHandler().getHome(player.getUniqueId(), args[0]) != null) {
            Essentials.instance().messages().sendMessage(player, Messages.HOME_ALREADY_EXISTS);
            return true;
        }

        if (BLOCKED_NAMES.contains(args[0].toLowerCase())) {
            Essentials.instance().messages().sendMessage(player, Messages.HOME_INVALID_NAME);
            return true;
        }

        Warp home = new Warp(player.getUniqueId(), args[0], player.getLocation());
        Essentials.instance().homeHandler().addHome(home);
        Essentials.instance().messages().sendMessage(player, Messages.HOME_SET, Placeholder.of("name", () -> home.name()));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }

}

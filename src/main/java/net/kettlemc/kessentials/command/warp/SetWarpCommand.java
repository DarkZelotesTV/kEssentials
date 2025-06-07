package net.kettlemc.kessentials.command.warp;

import net.kettlemc.kessentials.util.Placeholder;
import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.config.Configuration;
import net.kettlemc.kessentials.config.Messages;
import net.kettlemc.kessentials.teleport.Warp;
import net.kettlemc.kessentials.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetWarpCommand implements CommandExecutor, TabCompleter {

    private static final List<String> BLOCKED_NAMES = Arrays.asList("list", "delete", "set", "remove", "add");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!Essentials.instance().checkPermission(sender, command, false)) {
            Essentials.instance().messages().sendMessage(sender, Messages.NO_PERMISSION);
            return true;
        }

        if (!Essentials.instance().checkPermission(sender, command, true)) {
            Essentials.instance().messages().sendMessage(sender, Messages.NO_PERMISSION);
            return true;
        }

        if (!(sender instanceof Player)) {
            Essentials.instance().messages().sendMessage(sender, Messages.PLAYER_ONLY);
            return true;
        }

        if (args.length == 0) {
            Essentials.instance().messages().sendMessage(sender, Messages.WARP_SET_USAGE);
            return true;
        }

        Player player = (Player) sender;

        if (Essentials.instance().warpHandler().getWarp(args[0]) != null) {
            Essentials.instance().messages().sendMessage(player, Messages.WARP_ALREADY_EXISTS);
            return true;
        }

        if (BLOCKED_NAMES.contains(args[0].toLowerCase())) {
            Essentials.instance().messages().sendMessage(player, Messages.WARP_INVALID_NAME);
            return true;
        }

        Warp warp = new Warp(args[0], player.getLocation());
        Essentials.instance().warpHandler().addWarp(warp);
        Essentials.instance().messages().sendMessage(player, Messages.WARP_SET, Placeholder.of("name", () -> warp.name()));

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }

}

package net.kettlemc.kessentials.command.warp;

import io.github.almightysatan.slams.Placeholder;
import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.config.Configuration;
import net.kettlemc.kessentials.config.Messages;
import net.kettlemc.kessentials.teleport.Teleportation;
import net.kettlemc.kessentials.teleport.Warp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class WarpCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            Essentials.instance().messages().sendMessage(sender, Messages.PLAYER_ONLY);
            return true;
        }

        if (args.length == 0) {
            Essentials.instance().messages().sendMessage(sender, Messages.WARP_USAGE);
            return true;
        }

        Player player = (Player) sender;

        if (args[0].equalsIgnoreCase("list")) {
            Essentials.instance().messages().sendMessage(player, Messages.WARP_LIST_HEADER);
            Essentials.instance().warpHandler().getWarps().forEach(
                    warp -> Essentials.instance().messages().sendMessage(player, Messages.WARP_LIST_ENTRY,
                            Placeholder.of("name", (ctx, argument) -> warp.name()))
            );
            return true;
        }

        Warp warp = Essentials.instance().warpHandler().getWarp(args[0]);

        if (warp == null) {
            Essentials.instance().messages().sendMessage(player, Messages.WARP_NOT_FOUND);
            return true;
        }

        if (!Teleportation.schedule(Teleportation.prepare(player, warp.location(), Configuration.TELEPORT_COUNTDOWN_SECONDS.getValue()))) {
            Essentials.instance().messages().sendMessage(player, Messages.TELEPORT_ALREADY_SCHEDULED);
            return true;
        }

        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) return Collections.emptyList();

        if (args.length <= 1) {
            List<String> warps = Essentials.instance().warpHandler().getWarps().stream().map(Warp::name).collect(Collectors.toList());
            return StringUtil.copyPartialMatches(args.length == 0 ? "" : args[0], warps, warps.stream().map(String::toLowerCase).collect(Collectors.toList()));
        }

        return Collections.emptyList();
    }

}

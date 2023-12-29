package net.kettlemc.kessentials.command.home;

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

public class HomeCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            Essentials.instance().messages().sendMessage(sender, Messages.PLAYER_ONLY);
            return true;
        }

        if (args.length == 0) {
            Essentials.instance().messages().sendMessage(sender, Messages.HOME_USAGE);
            return true;
        }

        Player player = (Player) sender;

        if (args[0].equalsIgnoreCase("list")) {
            Essentials.instance().messages().sendMessage(player, Messages.HOME_LIST_HEADER);
            Essentials.instance().homeHandler().getHomes(player.getUniqueId()).forEach(
                    home -> Essentials.instance().messages().sendMessage(player, Messages.HOME_LIST_ENTRY, true, Messages.HOME_LIST_HOVER, "/home " + home.name(), Placeholder.of("name", (ctx, argument) -> home.name()))
            );
            return true;
        }

        Warp home = Essentials.instance().homeHandler().getHome(player.getUniqueId(), args[0]);

        if (home == null) {
            Essentials.instance().messages().sendMessage(player, Messages.HOME_NOT_FOUND);
            return true;
        }

        if (!Teleportation.schedule(Teleportation.prepare(player, home.location(), Configuration.TELEPORT_COUNTDOWN_SECONDS.getValue()))) {
            Essentials.instance().messages().sendMessage(player, Messages.TELEPORT_ALREADY_SCHEDULED);
            return true;
        }

        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!(sender instanceof Player)) return Collections.emptyList();

        if (args.length <= 1) {
            List<String> warps = Essentials.instance().homeHandler().getHomes(((Player) sender).getUniqueId()).stream().map(Warp::name).collect(Collectors.toList());
            return StringUtil.copyPartialMatches(args.length == 0 ? "" : args[0], warps, warps.stream().map(String::toLowerCase).collect(Collectors.toList()));
        }

        return Collections.emptyList();
    }

}

package net.kettlemc.kessentials.command.tpa;

import io.github.almightysatan.slams.Placeholder;
import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.config.Messages;
import net.kettlemc.kessentials.teleport.TeleportRequest;
import net.kettlemc.kessentials.teleport.Teleportation;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TPAcceptCommand implements CommandExecutor, TabCompleter {

    public void tpAccept(Player requester, Player target) {

        if (requester == null) {
            Essentials.instance().sendMessage(target, Messages.TPA_TPACCEPT_USAGE);
            return;
        }

        if (TeleportRequest.getRequestsFor(target).contains(requester)) {
            Essentials.instance().sendMessage(requester, Messages.TPA_REQUEST_ACCEPTED, Placeholder.of("target", (ctx, args) -> target.getName()));
            Essentials.instance().sendMessage(target, Messages.TPA_TPACCEPT, Placeholder.of("requester", (ctx, args) -> requester.getName()));
            Teleportation.schedule(Teleportation.prepare(requester, target.getLocation(), 5, () -> TeleportRequest.remove(target, requester)));


        } else {
            Essentials.instance().sendMessage(target, Messages.TPA_LIST_NO_REQUEST, Placeholder.of("requester", (ctx, args) -> requester.getName()));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            Essentials.instance().sendMessage(sender, Messages.PLAYER_ONLY);
            return true;
        }

        Player target = (Player) sender;

        if (args.length == 0) {
            Essentials.instance().sendMessage(target, Messages.TPA_TPACCEPT_USAGE);
            return true;
        }

        Player requester = Bukkit.getPlayer(args[0]);

        tpAccept(requester, target);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (args.length > 1) {
            return Collections.emptyList();
        }

        if (!(sender instanceof Player)) {
            return Collections.emptyList();
        }

        List<String> players = TeleportRequest.getRequestsFor((Player) sender).stream().map(Player::getName).collect(Collectors.toList());

        return args.length == 0 ? players : StringUtil.copyPartialMatches(args[0], players, new ArrayList<>());
    }
}

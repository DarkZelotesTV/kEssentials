package net.kettlemc.kessentials.command.tpa;

import net.kettlemc.kessentials.util.Placeholder;
import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.config.Messages;
import net.kettlemc.kessentials.teleport.TeleportRequest;
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

public class TPDenyCommand implements CommandExecutor, TabCompleter {

    public void tpDeny(Player requester, Player target) {

        if (requester == null) {
            Essentials.instance().messages().sendMessage(target, Messages.TPA_TPDENY_USAGE);
            return;
        }

        if (TeleportRequest.getRequestsFor(target).contains(requester)) {
            Essentials.instance().messages().sendMessage(requester, Messages.TPA_REQUEST_DENIED, Placeholder.of("target", () -> target.getName()));
            Essentials.instance().messages().sendMessage(target, Messages.TPA_TPDENY, Placeholder.of("requester", () -> requester.getName()));
            TeleportRequest.remove(target, requester);


        } else {
            Essentials.instance().messages().sendMessage(target, Messages.TPA_LIST_NO_REQUEST, Placeholder.of("requester", () -> requester.getName()));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            Essentials.instance().messages().sendMessage(sender, Messages.PLAYER_ONLY);
            return true;
        }

        Player target = (Player) sender;

        if (args.length == 0) {
            Essentials.instance().messages().sendMessage(target, Messages.TPA_TPDENY_USAGE);
            return true;
        }

        Player requester = Bukkit.getPlayer(args[0]);

        tpDeny(requester, target);

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

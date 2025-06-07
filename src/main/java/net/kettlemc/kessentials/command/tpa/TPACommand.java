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

import java.util.List;

public class TPACommand implements CommandExecutor, TabCompleter {


    public void tpa(Player requester, Player target) {

        if (target == null) {
            Essentials.instance().messages().sendMessage(requester, Messages.PLAYER_NOT_FOUND);
            return;
        }

        if (target == requester) {
            Essentials.instance().messages().sendMessage(requester, Messages.TPA_OTHER_ONLY);
            return;
        }

        if (TeleportRequest.request(requester, target)) {
            Essentials.instance().messages().sendMessage(requester, Messages.TPA_REQUEST_SENT,
                    Placeholder.of("target", () -> target.getName()));
            Essentials.instance().messages().sendMessage(target, Messages.TPA_REQUEST_RECEIVED,
                    Placeholder.of("requester", () -> requester.getName()));
            Essentials.instance().messages().sendMessage(target, Messages.TPA_REQUEST_ACCEPT,
                    Placeholder.of("requester", () -> requester.getName()));
            Essentials.instance().messages().sendMessage(target, Messages.TPA_REQUEST_DENY,
                    Placeholder.of("requester", () -> requester.getName()));

        } else {
            Essentials.instance().messages().sendMessage(requester, Messages.TPA_REQUEST_ALREADY_SENT, Placeholder.of("target", () -> target.getName()));
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            Essentials.instance().messages().sendMessage(sender, Messages.PLAYER_ONLY);
            return true;
        }

        Player requester = (Player) sender;

        if (args.length == 0) {
            Essentials.instance().messages().sendMessage(requester, Messages.TPA_REQUEST_USAGE);
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        tpa(requester, target);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

}

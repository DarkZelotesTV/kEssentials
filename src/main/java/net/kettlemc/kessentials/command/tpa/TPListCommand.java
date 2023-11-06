package net.kettlemc.kessentials.command.tpa;

import io.github.almightysatan.slams.Placeholder;
import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.config.Messages;
import net.kettlemc.kessentials.teleport.TeleportRequest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class TPListCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            Essentials.instance().sendMessage(sender, Messages.PLAYER_ONLY);
            return true;
        }

        Player target = (Player) sender;

        if (TeleportRequest.getRequestsFor(target).isEmpty()) {
            Essentials.instance().sendMessage(target, Messages.TPA_LIST_NO_REQUEST);
            return true;
        }

        Essentials.instance().sendMessage(target, Messages.TPA_LIST);

        TeleportRequest.getRequestsFor(target).forEach(
                requester -> Essentials.instance().sendMessage(target, Messages.TPA_LIST_ENTRY, Placeholder.of("requester", (ctx, args1) -> requester.getName()))
        );

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}

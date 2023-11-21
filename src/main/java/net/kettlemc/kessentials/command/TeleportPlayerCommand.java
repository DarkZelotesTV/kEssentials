package net.kettlemc.kessentials.command;

import io.github.almightysatan.slams.Placeholder;
import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.config.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class TeleportPlayerCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!Essentials.instance().checkPermission(sender, command, false)) {
            Essentials.instance().messages().sendMessage(sender, Messages.NO_PERMISSION);
            return true;
        }

        if (args.length == 1) {

            if (sender instanceof Player) {

                Player player = (Player) sender;

                Player target = Bukkit.getPlayer(args[0]);

                if (target == null) {
                    Essentials.instance().messages().sendMessage(sender, Messages.PLAYER_NOT_FOUND);
                    return true;
                }

                player.teleport(target.getLocation());
                Essentials.instance().messages().sendMessage(sender, Messages.TELEPORT_TELEPORTED, Placeholder.of("target", ((ctx, args1) -> target.getName())));
            } else {
                Essentials.instance().messages().sendMessage(sender, Messages.TELEPORT_USAGE);
            }

        } else if (args.length == 2) {

            if (!Essentials.instance().checkPermission(sender, command, true)) {
                Essentials.instance().messages().sendMessage(sender, Messages.NO_PERMISSION);
                return true;
            }

            Player player = Bukkit.getPlayer(args[0]);
            Player target = Bukkit.getPlayer(args[1]);
            if (target == null || player == null) {
                Essentials.instance().messages().sendMessage(sender, Messages.PLAYER_NOT_FOUND);
                return true;
            }

            player.teleport(target.getLocation());
            Essentials.instance().messages().sendMessage(sender, Messages.TELEPORT_TELEPORTED_OTHER, Placeholder.of("player", ((ctx, arguments) -> player.getName())), Placeholder.of("target", ((ctx, args1) -> target.getName())));
        } else {
            Essentials.instance().messages().sendMessage(sender, Messages.TELEPORT_USAGE);
        }

        return true;

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (!Essentials.instance().checkPermission(sender, command, false)) {
            return Collections.emptyList();
        }

        if (args.length >= 3) return Collections.emptyList();
        return null;
    }
}

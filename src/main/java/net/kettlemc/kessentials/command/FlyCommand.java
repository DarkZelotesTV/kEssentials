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
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FlyCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!Essentials.instance().checkPermission(sender, command, false)) {
            Essentials.instance().sendMessage(sender, Messages.NO_PERMISSION);
            return true;
        }

        if (args.length == 0) {

            if (sender instanceof Player) {
                fly(sender, (Player) sender);
            } else {
                Essentials.instance().sendMessage(sender, Messages.FLY_USAGE);
            }

        } else if (args.length == 1) {

            if (!Essentials.instance().checkPermission(sender, command, true)) {
                Essentials.instance().sendMessage(sender, Messages.NO_PERMISSION);
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                Essentials.instance().sendMessage(sender, Messages.PLAYER_NOT_FOUND);
                return true;
            }
            fly(sender, target);
        } else {
            Essentials.instance().sendMessage(sender, Messages.FLY_USAGE);
        }

        return true;
    }

    private void fly(CommandSender sender, Player target) {
        target.setAllowFlight(!target.getAllowFlight());
        Essentials.instance().sendMessage(target, target.getAllowFlight() ? Messages.FLY_ENABLED : Messages.FLY_DISABLED);
        if (sender != target) {
            Essentials.instance().sendMessage(sender, target.getAllowFlight() ? Messages.FLY_ENABLED_OTHER : Messages.FLY_DISABLED_OTHER, Placeholder.of("player", ((ctx, args) -> target.getName())));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length >= 2) return Collections.emptyList();
        return StringUtil.copyPartialMatches(args.length == 0 ? "" : args[0], Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()), new ArrayList<>());
    }
}

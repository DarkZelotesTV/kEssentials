package net.kettlemc.kessentials.command;

import net.kettlemc.kessentials.util.Placeholder;
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

public class HealCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!Essentials.instance().checkPermission(sender, command, false)) {
            Essentials.instance().messages().sendMessage(sender, Messages.NO_PERMISSION);
            return true;
        }

        if (args.length == 0) {

            if (sender instanceof Player) {
                heal(sender, (Player) sender);
            } else {
                Essentials.instance().messages().sendMessage(sender, Messages.HEAL_USAGE);
            }

        } else if (args.length == 1) {

            if (!Essentials.instance().checkPermission(sender, command, true)) {
                Essentials.instance().messages().sendMessage(sender, Messages.NO_PERMISSION);
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                Essentials.instance().messages().sendMessage(sender, Messages.PLAYER_NOT_FOUND);
                return true;
            }
            heal(sender, target);
        } else {
            Essentials.instance().messages().sendMessage(sender, Messages.HEAL_USAGE);
        }

        return true;

    }

    private void heal(CommandSender sender, Player target) {
        target.setHealth(target.getMaxHealth());
        target.setFoodLevel(Integer.MAX_VALUE);
        target.setFireTicks(0);
        target.setSaturation(Integer.MAX_VALUE);

        Essentials.instance().messages().sendMessage(target, Messages.HEAL_HEALED);

        if (sender != target) {
            Essentials.instance().messages().sendMessage(sender, Messages.HEAL_HEALED_OTHER, Placeholder.of("target", () -> target.getName()));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return args.length <= 1 ? null : Collections.emptyList();
    }
}

package net.kettlemc.kessentials.command;

import io.github.almightysatan.slams.Placeholder;
import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.config.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public class RepairCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!Essentials.instance().checkPermission(sender, command, false)) {
            Essentials.instance().messages().sendMessage(sender, Messages.NO_PERMISSION);
            return true;
        }

        if (args.length == 0) {

            if (sender instanceof Player) {
                repair(sender, (Player) sender);
            } else {
                Essentials.instance().messages().sendMessage(sender, Messages.REPAIR_USAGE);
            }

        } else if (args.length == 1) {

            if (!Essentials.instance().checkPermission(sender, command, true)) {
                Essentials.instance().messages().sendMessage(sender, Messages.REPAIR_USAGE);
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                Essentials.instance().messages().sendMessage(sender, Messages.PLAYER_NOT_FOUND);
                return true;
            }
            repair(sender, target);
        } else {
            Essentials.instance().messages().sendMessage(sender, Messages.REPAIR_USAGE);
        }

        return true;

    }

    private void repair(CommandSender sender, Player target) {
        ItemStack stack = target.getItemInHand();

        if (stack == null || stack.getType() == Material.AIR) {
            Essentials.instance().messages().sendMessage(sender, Messages.REPAIR_NO_ITEM_OTHER);
            Essentials.instance().messages().sendMessage(target, Messages.REPAIR_NO_ITEM);
            return;
        }

        short max = stack.getType().getMaxDurability();

        System.out.println(max);

        if (max == 0) {
            Essentials.instance().messages().sendMessage(target, Messages.REPAIR_NOT_REPAIRABLE);

            if (sender != target) {
                Essentials.instance().messages().sendMessage(sender, Messages.REPAIR_NOT_REPAIRABLE_OTHER, Placeholder.of("target", (ctx, args) -> target.getName()));
            }

            return;
        }

        stack.setDurability(max);

        Essentials.instance().messages().sendMessage(target, Messages.REPAIR_REPAIRED);

        if (sender != target) {
            Essentials.instance().messages().sendMessage(sender, Messages.REPAIR_REPAIRED_OTHER, Placeholder.of("target", (ctx, args) -> target.getName()));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return args.length <= 1 ? null : Collections.emptyList();
    }
}

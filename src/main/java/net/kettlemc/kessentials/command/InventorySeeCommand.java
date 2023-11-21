package net.kettlemc.kessentials.command;

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

public class InventorySeeCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!Essentials.instance().checkPermission(sender, command, true)) {
            Essentials.instance().messages().sendMessage(sender, Messages.NO_PERMISSION);
            return true;
        }

        if (!(sender instanceof Player)) {
            Essentials.instance().messages().sendMessage(sender, Messages.PLAYER_ONLY);
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            Essentials.instance().messages().sendMessage(sender, Messages.INVENTORY_USAGE);
            return true;
        }

        ((Player) sender).openInventory(target.getInventory());

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return args.length <= 1 ? null : Collections.emptyList();
    }
}

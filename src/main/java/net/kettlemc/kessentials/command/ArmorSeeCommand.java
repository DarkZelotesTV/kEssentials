package net.kettlemc.kessentials.command;

import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.config.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public class ArmorSeeCommand implements CommandExecutor, TabCompleter {

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

        Player target;
        if (args.length == 0) {
            if (sender instanceof Player) {
                target = (Player) sender;
            } else {
                Essentials.instance().messages().sendMessage(sender, Messages.ARMOR_USAGE);
                return true;
            }
        } else {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                Essentials.instance().messages().sendMessage(sender, Messages.ARMOR_USAGE);
                return true;
            }
        }

        Inventory inventory = Bukkit.createInventory(new ArmorInventoryHolder(target), InventoryType.HOPPER, target.getName() + "'s Armor");

        inventory.setItem(0, target.getInventory().getHelmet());
        inventory.setItem(1, target.getInventory().getChestplate());
        inventory.setItem(2, target.getInventory().getLeggings());
        inventory.setItem(3, target.getInventory().getBoots());
        inventory.setItem(4, target.getInventory().getItemInOffHand());

        ((Player) sender).openInventory(inventory);

        return true;
    }

    /**
     * Refreshes all open armor inventories of the given player.
     *
     * @param player The player whose armor should be displayed
     */
    public static void refreshArmorInventories(Player player) {
        for (Player viewer : Bukkit.getOnlinePlayers()) {
            Inventory open = viewer.getOpenInventory().getTopInventory();
            if (open.getHolder() instanceof ArmorInventoryHolder) {
                ArmorInventoryHolder holder = (ArmorInventoryHolder) open.getHolder();
                if (holder.player().equals(player)) {
                    open.setItem(0, player.getInventory().getHelmet());
                    open.setItem(1, player.getInventory().getChestplate());
                    open.setItem(2, player.getInventory().getLeggings());
                    open.setItem(3, player.getInventory().getBoots());
                    open.setItem(4, player.getInventory().getItemInOffHand());
                    viewer.updateInventory();
                }
            }
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return args.length <= 1 ? null : Collections.emptyList();

    }

    public static class ArmorInventoryHolder implements org.bukkit.inventory.InventoryHolder {

        private final Player player;

        public ArmorInventoryHolder(Player player) {
            this.player = player;
        }

        @Override
        public Inventory getInventory() {
            return null;
        }

        public Player player() {
            return player;
        }
    }

}

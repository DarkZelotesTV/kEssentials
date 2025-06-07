package net.kettlemc.kessentials.listener;

import net.kettlemc.kessentials.command.ArmorSeeCommand;
import net.kettlemc.kessentials.Essentials;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.Bukkit;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (event.getInventory() == null || event.getClickedInventory() == null || event.getSlot() < 0) {
            return;
        }

        if (event.getClickedInventory().getHolder() instanceof ArmorSeeCommand.ArmorInventoryHolder) {
            event.setCancelled(true);
            return;
        }

        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();

        // Check if the player clicked an armor or offhand slot in their own inventory
        if (event.getClickedInventory().getHolder() instanceof Player
                && ((Player) event.getClickedInventory().getHolder()).equals(player)) {

            if (event.getSlotType() == InventoryType.SlotType.ARMOR || event.getSlot() == 40) {
                Bukkit.getScheduler().runTask(Essentials.instance().getPlugin(), () ->
                        ArmorSeeCommand.refreshArmorInventories(player));
            }
        }

    }
}

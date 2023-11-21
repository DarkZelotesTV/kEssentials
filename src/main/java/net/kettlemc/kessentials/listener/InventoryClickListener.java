package net.kettlemc.kessentials.listener;

import net.kettlemc.kessentials.command.ArmorSeeCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {

        if (event.getInventory() == null || event.getClickedInventory() == null || event.getSlot() < 0) {
            return;
        }

        if (event.getClickedInventory().getHolder() instanceof ArmorSeeCommand.ArmorInventoryHolder) {
            ArmorSeeCommand.ArmorInventoryHolder holder = (ArmorSeeCommand.ArmorInventoryHolder) event.getClickedInventory().getHolder();
            event.setCancelled(true);
            // TODO: Update the open armor inventories if a player updates their armor

        }

    }
}

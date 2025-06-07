package net.kettlemc.kessentials.listener;

import net.kettlemc.kessentials.command.ArmorSeeCommand;
import net.kettlemc.kessentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

/**
 * Updates opened armorsee inventories when the player's armor changes outside of inventory clicks.
 */
public class ArmorChangeListener implements Listener {

    @EventHandler
    public void onSwapHand(PlayerSwapHandItemsEvent event) {
        Bukkit.getScheduler().runTask(Essentials.instance().getPlugin(), () ->
                ArmorSeeCommand.refreshArmorInventories(event.getPlayer()));
    }

    @EventHandler
    public void onItemBreak(PlayerItemBreakEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTask(Essentials.instance().getPlugin(), () ->
                ArmorSeeCommand.refreshArmorInventories(player));
    }
}

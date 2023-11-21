package net.kettlemc.kessentials.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import static net.kettlemc.kessentials.command.FreezeCommand.FROZEN_PLAYERS;

public class BlockListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (FROZEN_PLAYERS.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (FROZEN_PLAYERS.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }
}

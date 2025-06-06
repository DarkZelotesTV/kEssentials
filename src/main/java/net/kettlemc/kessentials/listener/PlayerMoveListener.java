package net.kettlemc.kessentials.listener;

import net.kettlemc.kessentials.command.FreezeCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (FreezeCommand.isFrozen(event.getPlayer())) {
            event.setCancelled(true);
            return;
        }

        if (event.getFrom().getY() != event.getTo().getY() || event.getFrom().getX() != event.getTo().getX() || event.getFrom().getZ() != event.getTo().getZ()) {
            event.setCancelled(true);
        }
    }
}

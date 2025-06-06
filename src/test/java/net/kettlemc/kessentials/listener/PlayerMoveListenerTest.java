package net.kettlemc.kessentials.listener;

import net.kettlemc.kessentials.command.FreezeCommand;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerMoveListenerTest {

    @Test
    public void frozenPlayersCannotMove() {
        Player player = Mockito.mock(Player.class);
        World world = Mockito.mock(World.class);
        Plugin plugin = Mockito.mock(Plugin.class);

        Mockito.when(player.hasMetadata("kettlemc.essentials.frozen")).thenReturn(true);
        Mockito.when(player.getMetadata("kettlemc.essentials.frozen"))
                .thenReturn(Collections.singletonList(new FixedMetadataValue(plugin, true)));

        Location from = new Location(world, 0, 0, 0);
        Location to = new Location(world, 1, 0, 0);
        PlayerMoveEvent event = new PlayerMoveEvent(player, from, to);

        new PlayerMoveListener().onMove(event);

        assertTrue(event.isCancelled(), "Frozen players should not be able to move");
    }
}


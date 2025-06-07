package net.kettlemc.kessentials.listener;

import net.kettlemc.kessentials.util.Placeholder;
import net.kettlemc.kessentials.util.Message;
import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.command.FreezeCommand;
import net.kettlemc.kessentials.command.VanishCommand;
import net.kettlemc.kessentials.config.Configuration;
import net.kettlemc.kessentials.config.Messages;
import net.kettlemc.kessentials.util.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static net.kettlemc.kessentials.command.FreezeCommand.*;
import static net.kettlemc.kessentials.command.VanishCommand.*;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Essentials.instance().homeHandler().loadHomes(event.getPlayer().getUniqueId());

        event.setJoinMessage(Util.translate(Messages.JOIN_MESSAGE, Placeholder.of("player", () -> event.getPlayer().getName())));

        // Hide vanished players from the player and disable the join message if the player is vanished
        hideVanished(event.getPlayer());
        if (isVanished(event.getPlayer())) {
            event.setJoinMessage("");
            hideFromAllPlayers(event.getPlayer());
        }

        Configuration.ON_JOIN_COMMANDS.getValue().forEach(command ->
                Essentials.instance().getPlugin().getServer().dispatchCommand(
                        Essentials.instance().getPlugin().getServer().getConsoleSender(),
                        command.replace("%player%", event.getPlayer().getName())
                )
        );

        // Set the player's speed to the frozen speed if they are frozen
        if (FreezeCommand.isFrozen(event.getPlayer())) {
            event.getPlayer().setWalkSpeed(FROZEN_SPEED);
            event.getPlayer().setFlySpeed(FROZEN_SPEED);
        } else {
            event.getPlayer().setWalkSpeed(DEFAULT_WALK_SPEED);
            event.getPlayer().setFlySpeed(DEFAULT_FLY_SPEED);
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(Util.translate(Messages.QUIT_MESSAGE, Placeholder.of("player", () -> event.getPlayer().getName())));

        // Disable the quit message if the player is vanished
        if (VanishCommand.isVanished(event.getPlayer())) {
            event.setQuitMessage("");
        }

        Essentials.instance().homeHandler().saveHomes(event.getPlayer().getUniqueId(), true);
    }

}

package net.kettlemc.kessentials.listener;

import io.github.almightysatan.slams.Placeholder;
import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.config.Messages;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage("");
        Bukkit.getOnlinePlayers().forEach(player -> Essentials.instance().sendMessage(player, Messages.JOIN_MESSAGE, Placeholder.of("player", (ctx, args) -> event.getPlayer().getName())));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage("");
        Bukkit.getOnlinePlayers().forEach(player -> Essentials.instance().sendMessage(player, Messages.QUIT_MESSAGE, Placeholder.of("player", (ctx, args) -> event.getPlayer().getName())));
    }

}

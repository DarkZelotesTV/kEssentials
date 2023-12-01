package net.kettlemc.kessentials.discord.listener.bukkit;

import io.github.almightysatan.slams.Placeholder;
import net.dv8tion.jda.api.utils.MarkdownSanitizer;
import net.kettlemc.kcommon.language.AdventureUtil;
import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.config.DiscordConfiguration;
import net.kettlemc.kessentials.config.Messages;
import net.kettlemc.kessentials.util.Util;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DiscordJoinQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        String channel = Util.stripEmojis(Essentials.instance().getDiscordBot().getMessageChannelById(DiscordConfiguration.DISCORD_CHANNEL_ID.getValue()).getName()).trim();

        Essentials.instance().messages().sendMessage(event.getPlayer(), Messages.DISCORD_WELCOME_MESSAGE, Placeholder.of("channel", (ctx, args) -> channel));

        String message = (DiscordConfiguration.DISCORD_DISABLE_FORMATTING.getValue() ? MarkdownSanitizer.escape(Util.stripColor(event.getJoinMessage()), true) : Util.stripColor(event.getJoinMessage()));
        Essentials.instance().getDiscordBot().sendMessage(AdventureUtil.componentToLegacy(Messages.DISCORD_JOIN.value(Placeholder.of("message", (ctx, args) -> message))));
        Essentials.instance().getDiscordBot().updateStatus();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        String message = (DiscordConfiguration.DISCORD_DISABLE_FORMATTING.getValue() ? MarkdownSanitizer.escape(Util.stripColor(event.getQuitMessage()), true) : Util.stripColor(event.getQuitMessage()));
        Essentials.instance().getDiscordBot().sendMessage(AdventureUtil.componentToLegacy(Messages.DISCORD_QUIT.value(Placeholder.of("message", (ctx, args) -> message))));
        Essentials.instance().getDiscordBot().updateStatus();
    }

}

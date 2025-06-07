package net.kettlemc.kessentials.discord.listener.bukkit;

import net.kettlemc.kessentials.util.Placeholder;
import net.dv8tion.jda.api.utils.MarkdownSanitizer;
import net.kettlemc.kessentials.util.BukkitUtil;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.config.DiscordConfiguration;
import net.kettlemc.kessentials.config.Messages;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class DiscordAsyncChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        // Don't send the message if it's cancelled (for example, if the player is muted)
        if (event.isCancelled())
            return;

        // Get the player's prefix
        String prefix = BukkitUtil.stripColor(BukkitUtil.getLuckPermsPrefix(event.getPlayer()));
        if (prefix.isEmpty()) {
            prefix = DiscordConfiguration.DEFAULT_RANK.getValue();
        }

        // Get the player's name
        String name = (DiscordConfiguration.DISCORD_DISABLE_FORMATTING.getValue() ? MarkdownSanitizer.escape(event.getPlayer().getDisplayName(), true) : event.getPlayer().getDisplayName());

        // Get the player's message
        String chatMessage = (DiscordConfiguration.DISCORD_DISABLE_FORMATTING.getValue() ? MarkdownSanitizer.escape(BukkitUtil.stripColor(event.getMessage()), true) : BukkitUtil.stripColor(event.getMessage()));

        // Format and send the message
        String finalPrefix = prefix;
        String message = LegacyComponentSerializer.legacySection().serialize(Messages.DISCORD_CHAT_FORMAT.value(
                Placeholder.of("rank", () -> finalPrefix),
                Placeholder.of("player", () -> name),
                Placeholder.of("message", () -> chatMessage)
        ));
        Essentials.instance().getDiscordBot().sendMessage(message);
    }

}

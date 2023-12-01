package net.kettlemc.kessentials.discord.listener;

import io.github.almightysatan.slams.Placeholder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.config.DiscordConfiguration;
import net.kettlemc.kessentials.config.Messages;
import net.kettlemc.kessentials.util.Util;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.Objects;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.isFromType(ChannelType.PRIVATE) || event.getAuthor().isBot() || event.getChannel().getIdLong() != DiscordConfiguration.DISCORD_CHANNEL_ID.getValue())
            return;

        List<Role> roles = Objects.requireNonNull(event.getGuild().getMember(event.getAuthor())).getRoles();

        String rank = roles.isEmpty() ? DiscordConfiguration.DEFAULT_RANK.getValue() : Util.stripEmojis(roles.get(0).getName()).trim();

        Bukkit.getOnlinePlayers().forEach(player -> Essentials.instance().messages().sendMessage(player, Messages.DISCORD_CHAT_FORMAT_MINECRAFT,
                Placeholder.of("name", (ctx, value) -> event.getAuthor().getName()),
                Placeholder.of("message", (ctx, value) -> event.getMessage().getContentDisplay()),
                Placeholder.of("rank", (ctx, value) -> rank))
        );
        Essentials.instance().messages().sendMessage(Bukkit.getConsoleSender(), Messages.DISCORD_CHAT_FORMAT_MINECRAFT,
                Placeholder.of("name", (ctx, value) -> event.getAuthor().getName()),
                Placeholder.of("message", (ctx, value) -> event.getMessage().getContentDisplay()),
                Placeholder.of("rank", (ctx, value) -> rank)
        );


    }
}

package net.kettlemc.kessentials.discord.listener;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kettlemc.kessentials.config.DiscordConfiguration;
import net.kettlemc.kessentials.config.Messages;
import net.kettlemc.kessentials.discord.DiscordBot;
import net.kettlemc.kessentials.Essentials;
import org.jetbrains.annotations.NotNull;

public class ReadyListener extends ListenerAdapter {

    private final DiscordBot bot;

    public ReadyListener(DiscordBot bot) {
        this.bot = bot;
        Essentials.instance().getPlugin().getLogger().info(
                bot.getJDA().getSelfUser().getName() + " ReadyListener created.");
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        Essentials.instance().getPlugin().getLogger().info(
                "Discord bot " + bot.getJDA().getSelfUser().getName() + " is ready.");
        this.bot.sendMessage(
                DiscordConfiguration.DISCORD_CHANNEL_ID.getValue(),
                LegacyComponentSerializer.legacySection().serialize(Messages.DISCORD_STARTUP.value()));
    }


}

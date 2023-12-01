package net.kettlemc.kessentials.discord.listener;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.kettlemc.kcommon.language.AdventureUtil;
import net.kettlemc.kessentials.config.DiscordConfiguration;
import net.kettlemc.kessentials.config.Messages;
import net.kettlemc.kessentials.discord.DiscordBot;
import org.jetbrains.annotations.NotNull;

public class ReadyListener extends ListenerAdapter {

    private final DiscordBot bot;

    public ReadyListener(DiscordBot bot) {
        System.out.println("ReadyListener created.");
        this.bot = bot;
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        System.out.println("Discord bot is ready.");
        this.bot.sendMessage(DiscordConfiguration.DISCORD_CHANNEL_ID.getValue(), AdventureUtil.componentToLegacy(Messages.DISCORD_STARTUP.value()));
    }


}

package net.kettlemc.kessentials.discord.command.commands;

import net.kettlemc.kessentials.util.Placeholder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.api.utils.MarkdownSanitizer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kettlemc.kessentials.config.DiscordConfiguration;
import net.kettlemc.kessentials.config.Messages;
import net.kettlemc.kessentials.discord.command.SlashCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ListSlashCommand extends SlashCommand {

    public ListSlashCommand() {
        super("list");
    }

    @Override
    public void onExecute(SlashCommandInteractionEvent event, Member member, MessageChannel channel) {
        int count = 0;
        StringBuilder players = new StringBuilder();

        for (Player player : Bukkit.getOnlinePlayers()) {
            players.append(player.getName());
            players.append(" ");
            count++;
        }

        String playerList = DiscordConfiguration.DISCORD_DISABLE_FORMATTING.getValue() ? MarkdownSanitizer.escape(players.toString(), true) : players.toString();

        if (count != 0) {
            int finalCount = count;
            event.reply(
                    LegacyComponentSerializer.legacySection().serialize(Messages.DISCORD_ONLINE_LIST.value(
                            Placeholder.of("list", () -> playerList),
                            Placeholder.of("amount", () -> String.valueOf(finalCount))
                    ))
            ).queue();
        } else {
            event.reply(LegacyComponentSerializer.legacySection().serialize(Messages.DISCORD_NO_PLAYERS.value())).queue();
        }
    }

    @Override
    public void construct(CommandListUpdateAction commands) {
        commands.addCommands(Commands.slash("list", "Lists all players on the server"));
    }

}

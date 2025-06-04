package net.kettlemc.kessentials.discord.command.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.data.ClanDAO;
import net.kettlemc.kessentials.discord.command.SlashCommand;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Lists clans ordered by member count.
 */
public class TopTeamsSlashCommand extends SlashCommand {

    public TopTeamsSlashCommand() {
        super("topteams");
    }

    @Override
    public void onExecute(SlashCommandInteractionEvent event, Member member, MessageChannel channel) {
        ClanDAO dao = Essentials.instance().clanDAO();
        try {
            List<Map.Entry<String, Integer>> list = dao.getTopClans(5);
            StringBuilder sb = new StringBuilder();
            int rank = 1;
            for (Map.Entry<String, Integer> entry : list) {
                sb.append("#").append(rank++).append(" ")
                        .append(entry.getKey()).append(" - ")
                        .append(entry.getValue()).append(" members\n");
            }
            if (sb.length() == 0) sb.append("No data");
            event.reply(sb.toString()).queue();
        } catch (SQLException e) {
            event.reply("Failed to query data.").setEphemeral(true).queue();
        }
    }

    @Override
    public void construct(CommandListUpdateAction commands) {
        commands.addCommands(Commands.slash("topteams", "Lists clans with most members"));
    }
}


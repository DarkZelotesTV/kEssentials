package net.kettlemc.kessentials.discord.command.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.data.PlayerDataDAO;
import net.kettlemc.kessentials.discord.command.SlashCommand;
import org.bukkit.Bukkit;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Shows players with the most kills.
 */
public class TopKillsSlashCommand extends SlashCommand {

    public TopKillsSlashCommand() {
        super("topkills");
    }

    @Override
    public void onExecute(SlashCommandEvent event, Member member, MessageChannel channel) {
        PlayerDataDAO dao = Essentials.instance().playerDataDAO();
        try {
            List<Map.Entry<UUID, Integer>> list = dao.getTopKills(5);
            StringBuilder sb = new StringBuilder();
            int rank = 1;
            for (Map.Entry<UUID, Integer> entry : list) {
                String name = Bukkit.getOfflinePlayer(entry.getKey()).getName();
                sb.append("#").append(rank++).append(" ")
                        .append(name == null ? entry.getKey() : name)
                        .append(" - ")
                        .append(entry.getValue()).append(" kills\n");
            }
            if (sb.length() == 0) sb.append("No data");
            event.reply(sb.toString()).queue();
        } catch (SQLException e) {
            event.reply("Failed to query data.").setEphemeral(true).queue();
        }
    }

    @Override
    public void construct(CommandListUpdateAction commands) {
        commands.addCommands(Commands.slash("topkills", "Lists players with the most kills"));
    }
}


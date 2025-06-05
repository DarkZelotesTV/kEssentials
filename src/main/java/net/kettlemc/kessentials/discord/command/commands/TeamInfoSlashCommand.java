package net.kettlemc.kessentials.discord.command.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.data.ClanDAO;
import net.kettlemc.kessentials.discord.command.SlashCommand;
import org.bukkit.Bukkit;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Slash command to show information about a clan.
 */
public class TeamInfoSlashCommand extends SlashCommand {

    public TeamInfoSlashCommand() {
        super("teaminfo");
    }

    @Override
    public void onExecute(SlashCommandInteractionEvent event, Member member, MessageChannel channel) {
        OptionMapping nameOption = event.getOption("name");
        String name = nameOption != null ? nameOption.getAsString() : null;
        if (name == null || name.isEmpty()) {
            event.reply("Please provide a clan name.").setEphemeral(true).queue();
            return;
        }
        ClanDAO dao = Essentials.instance().clanDAO();
        try {
            List<UUID> members = dao.getMembers(name);
            if (members.isEmpty()) {
                event.reply("Clan not found.").setEphemeral(true).queue();
                return;
            }
            StringBuilder sb = new StringBuilder("Members (" + members.size() + "): ");
            for (UUID uuid : members) {
                String playerName = Bukkit.getOfflinePlayer(uuid).getName();
                if (playerName != null) sb.append(playerName).append(" ");
            }
            event.reply(sb.toString().trim()).queue();
        } catch (SQLException e) {
            event.reply("Failed to query clan info.").setEphemeral(true).queue();
        }
    }

    @Override
    public void construct(CommandListUpdateAction commands) {
        OptionData option = new OptionData(OptionType.STRING, "name", "Clan name", true);
        commands.addCommands(Commands.slash("teaminfo", "Displays clan members").addOptions(option));
    }
}


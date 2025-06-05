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
import net.kettlemc.kessentials.data.PlayerDataDAO;
import net.kettlemc.kessentials.discord.command.SlashCommand;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.SQLException;
import java.util.UUID;

public class PlayerInfoSlashCommand extends SlashCommand {

    public PlayerInfoSlashCommand() {
        super("playerinfo");
    }

    @Override
    public void onExecute(SlashCommandInteractionEvent event, Member member, MessageChannel channel) {
        OptionMapping nameOption = event.getOption("name");
        String name = nameOption != null ? nameOption.getAsString() : null;
        if (name == null || name.isEmpty()) {
            event.reply("Please provide a player name.").setEphemeral(true).queue();
            return;
        }
        OfflinePlayer offline = Bukkit.getOfflinePlayer(name);
        UUID uuid = offline.getUniqueId();
        PlayerDataDAO dao = Essentials.instance().playerDataDAO();
        try {
            int kills = dao.getKills(uuid);
            int deaths = dao.getDeaths(uuid);
            event.reply(name + " - Kills: " + kills + ", Deaths: " + deaths).queue();
        } catch (SQLException e) {
            event.reply("Failed to query player info.").setEphemeral(true).queue();
        }
    }

    @Override
    public void construct(CommandListUpdateAction commands) {
        OptionData nameOption = new OptionData(OptionType.STRING, "name", "Minecraft player name", true);
        commands.addCommands(Commands.slash("playerinfo", "Shows kills and deaths for a player").addOptions(nameOption));
    }
}

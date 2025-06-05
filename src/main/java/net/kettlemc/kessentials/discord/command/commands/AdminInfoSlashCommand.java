package net.kettlemc.kessentials.discord.command.commands;

import net.dv8tion.jda.api.Permission;
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
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.UUID;

public class AdminInfoSlashCommand extends SlashCommand {

    public AdminInfoSlashCommand() {
        super("admininfo");
    }

    @Override
    public void onExecute(SlashCommandInteractionEvent event, Member member, MessageChannel channel) {
        if (!member.hasPermission(Permission.ADMINISTRATOR)) {
            event.reply("You do not have permission to use this command.").setEphemeral(true).queue();
            return;
        }
        OptionMapping nameOpt = event.getOption("name");
        String name = nameOpt != null ? nameOpt.getAsString() : null;
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
            String position = "Offline";
            Player online = Bukkit.getPlayerExact(name);
            if (online != null) {
                position = online.getWorld().getName() + " " + online.getLocation().getBlockX() + "," + online.getLocation().getBlockY() + "," + online.getLocation().getBlockZ();
            }
            event.reply(name + " - Kills: " + kills + ", Deaths: " + deaths + ", Position: " + position).queue();
        } catch (SQLException e) {
            event.reply("Failed to query player info.").setEphemeral(true).queue();
        }
    }

    @Override
    public void construct(CommandListUpdateAction commands) {
        OptionData nameOption = new OptionData(OptionType.STRING, "name", "Minecraft player name", true);
        commands.addCommands(Commands.slash("admininfo", "Shows extended info about a player").addOptions(nameOption));
    }
}

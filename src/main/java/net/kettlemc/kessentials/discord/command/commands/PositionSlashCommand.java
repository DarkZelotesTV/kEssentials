package net.kettlemc.kessentials.discord.command.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.kettlemc.kessentials.discord.command.SlashCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Shows the current position of an online player. Admin only.
 */
public class PositionSlashCommand extends SlashCommand {

    public PositionSlashCommand() {
        super("position");
    }

    @Override
    public void onExecute(SlashCommandEvent event, Member member, MessageChannel channel) {
        if (!member.hasPermission(Permission.ADMINISTRATOR)) {
            event.reply("You do not have permission to use this command.").setEphemeral(true).queue();
            return;
        }
        OptionMapping nameOption = event.getOption("name");
        String name = nameOption != null ? nameOption.getAsString() : null;
        if (name == null || name.isEmpty()) {
            event.reply("Please provide a player name.").setEphemeral(true).queue();
            return;
        }
        Player player = Bukkit.getPlayerExact(name);
        if (player == null) {
            event.reply("Player not online.").queue();
            return;
        }
        String pos = player.getWorld().getName() + " " + player.getLocation().getBlockX() + "," + player.getLocation().getBlockY() + "," + player.getLocation().getBlockZ();
        event.reply(name + " - " + pos).queue();
    }

    @Override
    public void construct(CommandListUpdateAction commands) {
        OptionData nameOption = new OptionData(OptionType.STRING, "name", "Player name", true);
        commands.addCommands(Commands.slash("position", "Shows player coordinates").addOptions(nameOption));
    }
}


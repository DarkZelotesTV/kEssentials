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
import net.kettlemc.kessentials.data.LinkDAO;
import net.kettlemc.kessentials.discord.command.SlashCommand;

import java.sql.SQLException;

/**
 * Links a Minecraft account with a Discord user using a code.
 */
public class VerifySlashCommand extends SlashCommand {

    public VerifySlashCommand() {
        super("verify");
    }

    @Override
    public void onExecute(SlashCommandInteractionEvent event, Member member, MessageChannel channel) {
        OptionMapping codeOpt = event.getOption("code");
        String code = codeOpt != null ? codeOpt.getAsString() : null;
        if (code == null || code.isEmpty()) {
            event.reply("Please provide your verification code.").setEphemeral(true).queue();
            return;
        }
        LinkDAO dao = Essentials.instance().linkDAO();
        try {
            if (dao.verifyCode(code, member.getId())) {
                event.reply("Your account has been linked.").queue();
            } else {
                event.reply("Invalid or already used code.").setEphemeral(true).queue();
            }
        } catch (SQLException e) {
            event.reply("Failed to verify code.").setEphemeral(true).queue();
        }
    }

    @Override
    public void construct(CommandListUpdateAction commands) {
        OptionData codeOption = new OptionData(OptionType.STRING, "code", "Verification code", true);
        commands.addCommands(Commands.slash("verify", "Links your Minecraft account").addOptions(codeOption));
    }
}


package net.kettlemc.kessentials.command;

import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.config.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DisabledCommandExecutor implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Essentials.instance().messages().sendMessage(sender, Messages.COMMAND_DISABLED);
        return true;
    }
}

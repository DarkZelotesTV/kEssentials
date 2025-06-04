package net.kettlemc.kessentials.command;

import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.config.DiscordConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collections;
import java.util.List;

/**
 * Reloads the Discord configuration file.
 */
public class BotReloadCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!Essentials.instance().checkPermission(sender, command, false)) {
            sender.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }
        if (DiscordConfiguration.load()) {
            sender.sendMessage(ChatColor.GREEN + "Discord config reloaded.");
        } else {
            sender.sendMessage(ChatColor.RED + "Failed to reload config.");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}


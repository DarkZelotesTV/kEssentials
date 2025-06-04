package net.kettlemc.kessentials.command;

import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.discord.DiscordBot;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Provides simple bot subcommands such as sending messages to Discord.
 */
public class BotCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!Essentials.instance().checkPermission(sender, command, false)) {
            sender.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }
        if (args.length < 1 || !args[0].equalsIgnoreCase("say")) {
            sender.sendMessage(ChatColor.RED + "Usage: /bot say <text>");
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /bot say <text>");
            return true;
        }
        DiscordBot bot = Essentials.instance().getDiscordBot();
        if (bot == null) {
            sender.sendMessage(ChatColor.RED + "Bot is disabled.");
            return true;
        }
        String message = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        bot.sendMessage(message);
        sender.sendMessage(ChatColor.GREEN + "Message sent to Discord.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}


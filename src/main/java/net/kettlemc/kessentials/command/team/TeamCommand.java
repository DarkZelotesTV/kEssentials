package net.kettlemc.kessentials.command.team;

import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.data.ClanDAO;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Simple team command to create, join or leave clans.
 */
public class TeamCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Players only.");
            return true;
        }
        Player player = (Player) sender;
        ClanDAO dao = Essentials.instance().clanDAO();

        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /team <create|join|leave> [name]");
            return true;
        }

        String sub = args[0].toLowerCase();
        try {
            switch (sub) {
                case "create":
                    if (args.length < 2) {
                        sender.sendMessage(ChatColor.RED + "Usage: /team create <name>");
                        break;
                    }
                    String name = args[1];
                    if (dao.clanExists(name)) {
                        sender.sendMessage(ChatColor.RED + "Clan already exists.");
                        break;
                    }
                    dao.createClan(name, player.getUniqueId());
                    sender.sendMessage(ChatColor.GREEN + "Clan created: " + name);
                    break;
                case "join":
                    if (args.length < 2) {
                        sender.sendMessage(ChatColor.RED + "Usage: /team join <name>");
                        break;
                    }
                    if (!dao.clanExists(args[1])) {
                        sender.sendMessage(ChatColor.RED + "Clan does not exist.");
                        break;
                    }
                    dao.joinClan(player.getUniqueId(), args[1]);
                    sender.sendMessage(ChatColor.GREEN + "Joined clan " + args[1]);
                    break;
                case "leave":
                    dao.leaveClan(player.getUniqueId());
                    sender.sendMessage(ChatColor.GREEN + "Left your clan.");
                    break;
                default:
                    sender.sendMessage(ChatColor.RED + "Usage: /team <create|join|leave> [name]");
                    break;
            }
            // Update scoreboard after any change
            Essentials.instance().scoreboardHandler().updateScoreboard(player);
        } catch (SQLException e) {
            sender.sendMessage(ChatColor.RED + "Database error.");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], List.of("create", "join", "leave"), new ArrayList<>());
        } else if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
            // Suggest existing clans
            ClanDAO dao = Essentials.instance().clanDAO();
            List<String> names = new ArrayList<>();
            try {
                // This query gets all clan names
                names = dao.getAllClans();
            } catch (SQLException ignored) {
            }
            return StringUtil.copyPartialMatches(args[1], names, new ArrayList<>());
        }
        return Collections.emptyList();
    }
}

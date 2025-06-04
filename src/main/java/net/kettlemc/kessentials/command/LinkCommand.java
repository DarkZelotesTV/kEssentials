package net.kettlemc.kessentials.command;

import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.data.LinkDAO;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Generates a verification code so players can link their Discord account.
 */
public class LinkCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Players only.");
            return true;
        }
        Player player = (Player) sender;
        LinkDAO dao = Essentials.instance().linkDAO();
        try {
            if (dao.isLinked(player.getUniqueId())) {
                player.sendMessage(ChatColor.GREEN + "Your account is already linked.");
                return true;
            }
            String code = dao.createCode(player.getUniqueId());
            player.sendMessage(ChatColor.YELLOW + "Use !verify " + code + " in Discord to link your account.");
        } catch (SQLException e) {
            player.sendMessage(ChatColor.RED + "Failed to generate link code.");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return Collections.emptyList();
    }
}


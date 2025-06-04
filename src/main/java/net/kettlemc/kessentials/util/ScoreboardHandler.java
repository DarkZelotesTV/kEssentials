package net.kettlemc.kessentials.util;

import net.kettlemc.kessentials.data.PlayerDataDAO;
import net.kettlemc.kessentials.data.ClanDAO;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.sql.SQLException;

/**
 * Simple scoreboard handler to display kills and deaths for each player.
 */
public class ScoreboardHandler implements Listener {

    private final PlayerDataDAO dao;
    private final ClanDAO clanDAO;
    private final ScoreboardManager manager = Bukkit.getScoreboardManager();

    public ScoreboardHandler(PlayerDataDAO dao, ClanDAO clanDAO) {
        this.dao = dao;
        this.clanDAO = clanDAO;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        updateScoreboard(event.getPlayer());
    }

    public void updateScoreboard(Player player) {
        try {
            int kills = dao.getKills(player.getUniqueId());
            int deaths = dao.getDeaths(player.getUniqueId());
            String clan = clanDAO.getClan(player.getUniqueId());

            Scoreboard board = manager.getNewScoreboard();
            Objective obj = board.registerNewObjective("stats", "dummy", ChatColor.GREEN + "Stats");
            obj.setDisplaySlot(DisplaySlot.SIDEBAR);

            Score killScore = obj.getScore(ChatColor.YELLOW + "Kills: ");
            killScore.setScore(kills);
            Score deathScore = obj.getScore(ChatColor.RED + "Deaths: ");
            deathScore.setScore(deaths);
            if (clan != null) {
                Score clanScore = obj.getScore(ChatColor.AQUA + "Clan: " + clan);
                clanScore.setScore(0);
            }

            player.setScoreboard(board);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

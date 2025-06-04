package net.kettlemc.kessentials.listener;

import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.data.PlayerDataDAO;
import net.kettlemc.kessentials.util.ScoreboardHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.sql.SQLException;

/**
 * Captures kill events, updates the database and notifies Discord.
 */
public class KillListener implements Listener {

    private final PlayerDataDAO dao;
    private final ScoreboardHandler scoreboardHandler;

    public KillListener(PlayerDataDAO dao, ScoreboardHandler scoreboardHandler) {
        this.dao = dao;
        this.scoreboardHandler = scoreboardHandler;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();
        try {
            dao.incrementDeaths(victim.getUniqueId());
            if (killer != null) {
                dao.incrementKills(killer.getUniqueId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        scoreboardHandler.updateScoreboard(victim);
        if (killer != null) {
            scoreboardHandler.updateScoreboard(killer);
            // Send killfeed embed to Discord with the killer's skin
            if (Essentials.instance().getDiscordBot() != null) {
                String body = killer.getName() + " -> " + victim.getName();
                String avatar = "https://minotar.net/avatar/" + killer.getName() + ".png";
                Essentials.instance().getDiscordBot().sendEmbed(killer.getName(), body, avatar);
            }
        }
    }
}

package net.kettlemc.kessentials.listener;

import net.kettlemc.kessentials.config.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class CommandListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage().startsWith("/") ? event.getMessage().substring(1) : event.getMessage();
        if (Configuration.ON_COMMAND_COMMANDS.getValue().containsKey(command)) {
            List<String> playerCommands = Configuration.ON_COMMAND_COMMANDS.getValue().get(command).get("player");
            List<String> consoleCommands = Configuration.ON_COMMAND_COMMANDS.getValue().get(command).get("console");
            if (playerCommands != null) {
                for (String playerCommand : playerCommands) {
                    event.getPlayer().performCommand(playerCommand);
                }
            }
            if (consoleCommands != null) {
                for (String consoleCommand : consoleCommands) {
                    event.getPlayer().getServer().dispatchCommand(event.getPlayer().getServer().getConsoleSender(), consoleCommand);
                }
            }
        }

    }

}

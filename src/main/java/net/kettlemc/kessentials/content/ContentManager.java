package net.kettlemc.kessentials.content;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Simple helper for registering commands and listeners.
 */
public class ContentManager {

    private final JavaPlugin plugin;

    public ContentManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerCommand(String name, CommandExecutor executor) {
        PluginCommand command = plugin.getCommand(name);
        if (command == null) {
            plugin.getLogger().warning("Command '" + name + "' is not defined in plugin.yml");
            return;
        }
        command.setExecutor(executor);
        if (executor instanceof TabCompleter) {
            command.setTabCompleter((TabCompleter) executor);
        }
    }

    public void registerListener(Listener listener) {
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }
}

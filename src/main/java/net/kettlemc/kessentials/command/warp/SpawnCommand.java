package net.kettlemc.kessentials.command.warp;

import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.config.Configuration;
import net.kettlemc.kessentials.config.Messages;
import net.kettlemc.kessentials.teleport.Teleportation;
import net.kettlemc.kessentials.teleport.Warp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!Essentials.instance().checkPermission(sender, command, false)) {
            Essentials.instance().messages().sendMessage(sender, Messages.NO_PERMISSION);
            return true;
        }

        if (!(sender instanceof Player)) {
            Essentials.instance().messages().sendMessage(sender, Messages.PLAYER_ONLY);
            return true;
        }

        Player player = (Player) sender;
        Warp spawn = Essentials.instance().warpHandler().getWarp("spawn");

        if (spawn == null) {
            Essentials.instance().messages().sendMessage(player, Messages.WARP_NOT_FOUND);
            return true;
        }

        if (!Teleportation.schedule(Teleportation.prepare(player, spawn.location(), Configuration.TELEPORT_COUNTDOWN_SECONDS.getValue()))) {
            Essentials.instance().messages().sendMessage(player, Messages.TELEPORT_ALREADY_SCHEDULED);
            return true;
        }

        return true;
    }
}

package net.kettlemc.kessentials.command;

import io.github.almightysatan.slams.Placeholder;
import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.config.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.*;

public class FreezeCommand implements CommandExecutor, TabCompleter {

    public static final float DEFAULT_WALK_SPEED = 0.2f;
    public static final float DEFAULT_FLY_SPEED = 0.1f;
    public static final float FROZEN_SPEED = 0f;
    public static final Set<UUID> FROZEN_PLAYERS = new HashSet<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!Essentials.instance().checkPermission(sender, command, false)) {
            Essentials.instance().messages().sendMessage(sender, Messages.NO_PERMISSION);
            return true;
        }

        if (args.length == 0) {

            if (sender instanceof Player) {
                toggleFreeze(sender, (Player) sender);
            } else {
                Essentials.instance().messages().sendMessage(sender, Messages.FREEZE_USAGE);
            }

        } else if (args.length == 1) {

            if (!Essentials.instance().checkPermission(sender, command, true)) {
                Essentials.instance().messages().sendMessage(sender, Messages.NO_PERMISSION);
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                Essentials.instance().messages().sendMessage(sender, Messages.PLAYER_NOT_FOUND);
                return true;
            }
            toggleFreeze(sender, target);
        } else {
            Essentials.instance().messages().sendMessage(sender, Messages.FREEZE_USAGE);
        }

        return true;
    }

    private void toggleFreeze(CommandSender sender, Player target) {

        if (FROZEN_PLAYERS.contains(target.getUniqueId())) {
            FROZEN_PLAYERS.remove(target.getUniqueId());
            Essentials.instance().messages().sendMessage(target, Messages.FREEZE_UNFROZEN);
            target.setWalkSpeed(DEFAULT_WALK_SPEED);
            target.setFlySpeed(DEFAULT_FLY_SPEED);
        } else {
            FROZEN_PLAYERS.add(target.getUniqueId());
            Essentials.instance().messages().sendMessage(target, Messages.FREEZE_FROZEN);
            target.setWalkSpeed(FROZEN_SPEED);
            target.setFlySpeed(FROZEN_SPEED);
        }

        if (sender != target) {
            Essentials.instance().messages().sendMessage(sender, FROZEN_PLAYERS.contains(target.getUniqueId()) ? Messages.FREEZE_FROZEN_OTHER : Messages.FREEZE_UNFROZEN_OTHER, Placeholder.of("target", ((ctx, args) -> target.getName())));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length <= 1) {
            return null;
        }
        return Collections.emptyList();
    }

}

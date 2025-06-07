package net.kettlemc.kessentials.command;

import net.kettlemc.kessentials.util.Placeholder;
import net.kettlemc.kessentials.util.BukkitUtil;
import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.config.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SpeedCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!Essentials.instance().checkPermission(sender, command, false)) {
            Essentials.instance().messages().sendMessage(sender, Messages.NO_PERMISSION);
            return true;
        }

        if (args.length == 1) {

            if (sender instanceof Player) {
                speed(sender, (Player) sender, args[0]);
            } else {
                Essentials.instance().messages().sendMessage(sender, Messages.SPEED_USAGE);
            }

        } else if (args.length == 2) {

            if (!Essentials.instance().checkPermission(sender, command, true)) {
                Essentials.instance().messages().sendMessage(sender, Messages.NO_PERMISSION);
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                Essentials.instance().messages().sendMessage(sender, Messages.PLAYER_NOT_FOUND);
                return true;
            }
            speed(sender, target, args[0]);
        } else {
            Essentials.instance().messages().sendMessage(sender, Messages.SPEED_USAGE);
        }

        return true;
    }

    private void speed(CommandSender sender, Player target, String arg) {
        if (!BukkitUtil.isInteger(arg)) {
            Essentials.instance().messages().sendMessage(sender, Messages.SPEED_USAGE);
            return;
        }

        int speed = Integer.parseInt(arg);

        if (speed < 1 || speed > 10) {
            Essentials.instance().messages().sendMessage(sender, Messages.SPEED_INVALID);
            return;
        }

        if (target.isFlying()) {
            target.setFlySpeed(getRealMoveSpeed(speed, true));
            Essentials.instance().messages().sendMessage(target, Messages.SPEED_SET_FLYING, Placeholder.of("speed", () -> String.valueOf(speed)));
            if (sender != target) {
                Essentials.instance().messages().sendMessage(sender, Messages.SPEED_SET_FLYING_OTHER, Placeholder.of("speed", () -> String.valueOf(speed)), Placeholder.of("player", () -> target.getName()));
            }
        } else {
            target.setWalkSpeed(getRealMoveSpeed(speed, false));
            Essentials.instance().messages().sendMessage(target, Messages.SPEED_SET_WALKING, Placeholder.of("speed", () -> String.valueOf(speed)));
            if (sender != target) {
                Essentials.instance().messages().sendMessage(sender, Messages.SPEED_SET_WALKING_OTHER, Placeholder.of("speed", () -> String.valueOf(speed)), Placeholder.of("player", () -> target.getName()));
            }
        }
    }

    private float getRealMoveSpeed(final float userSpeed, final boolean isFly) {
        float defaultSpeed = isFly ? 0.1f : 0.2f;
        float maxSpeed = 1f;

        if (userSpeed < 1f) {
            return defaultSpeed * userSpeed;
        } else {
            float ratio = ((userSpeed - 1) / 9) * (maxSpeed - defaultSpeed);
            return ratio + defaultSpeed;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (!Essentials.instance().checkPermission(sender, command, false)) {
            return Collections.emptyList();
        }

        if (args.length >= 3) return Collections.emptyList();
        if (args.length == 2) return StringUtil.copyPartialMatches(args[1], Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()), new ArrayList<>());
        return null;
    }
}

package net.kettlemc.kessentials.command;

import io.github.almightysatan.slams.Placeholder;
import net.kettlemc.kcommon.java.NumberUtil;
import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.config.Messages;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GamemodeCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!Essentials.instance().checkPermission(sender, command, false)) {
            Essentials.instance().messages().sendMessage(sender, Messages.NO_PERMISSION);
            return true;
        }

        if (args.length == 1) {

            if (sender instanceof Player) {
                gamemode(sender, (Player) sender, args[0]);
            } else {
                Essentials.instance().messages().sendMessage(sender, Messages.GAMEMODE_USAGE);
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
            gamemode(sender, target, args[0]);
        } else {
            Essentials.instance().messages().sendMessage(sender, Messages.GAMEMODE_USAGE);
        }

        return true;
    }

    private void gamemode(CommandSender sender, Player target, String arg) {
        GameMode gameMode = getGamemode(arg);
        if (gameMode == null) {
            Essentials.instance().messages().sendMessage(sender, Messages.GAMEMODE_INVALID);
            return;
        }

        target.setGameMode(gameMode);

        Essentials.instance().messages().sendMessage(target, Messages.GAMEMODE_SET, Placeholder.of("gamemode", (ctx, args) -> gameMode.name()));

        if (sender != target) {
            Essentials.instance().messages().sendMessage(sender, Messages.GAMEMODE_SET_OTHER, Placeholder.of("gamemode", (ctx, args) -> gameMode.name()), Placeholder.of("target", (ctx, args) -> target.getName()));
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length <= 1) {
            return StringUtil.copyPartialMatches(args.length == 0 ? "" : args[0], Arrays.stream(GameMode.values()).map(GameMode::name).collect(Collectors.toList()), new ArrayList<>());
        } else if (args.length == 2) {
            return StringUtil.copyPartialMatches(args[1], Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()), new ArrayList<>());
        } else {
            return Collections.emptyList();
        }
    }

    private GameMode getGamemode(String gm) {
        try {
            return GameMode.valueOf(gm.toUpperCase());
        } catch (Exception e) {
            return NumberUtil.isInteger(gm) ? GameMode.getByValue(Integer.parseInt(gm)) : null;
        }
    }

}

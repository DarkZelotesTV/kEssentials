package net.kettlemc.kessentials.command;

import net.kettlemc.kessentials.util.Message;
import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.config.Messages;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;
import java.util.stream.Collectors;


public class TimeCommands implements CommandExecutor, TabCompleter {

    public static final Map<String, Integer> TIME_MAP = new HashMap<>();
    public static final Map<String, Message> MESSAGE_MAP = new HashMap<>();

    static {
        TIME_MAP.put("morning", 23500);
        TIME_MAP.put("day", 500);
        TIME_MAP.put("midday", 6000);
        TIME_MAP.put("evening", 12500);
        TIME_MAP.put("night", 14000);

        MESSAGE_MAP.put("morning", Messages.MORNING);
        MESSAGE_MAP.put("day", Messages.DAY);
        MESSAGE_MAP.put("midday", Messages.MIDDAY);
        MESSAGE_MAP.put("evening", Messages.EVENING);
        MESSAGE_MAP.put("night", Messages.NIGHT);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!Essentials.instance().checkPermission(sender, command, false)) {
            Essentials.instance().messages().sendMessage(sender, Messages.NO_PERMISSION);
            return true;
        }

        World world;
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                Essentials.instance().messages().sendMessage(sender, Messages.TIME_USAGE);
                return true;
            }
            world = ((Player) sender).getWorld();
        } else {
            world = Bukkit.getWorld(args[0]);
        }

        if (world == null) {
            Essentials.instance().messages().sendMessage(sender, Messages.TIME_USAGE);
            return true;
        }

        if (TIME_MAP.containsKey(command.getName().toLowerCase()) && MESSAGE_MAP.containsKey(command.getName().toLowerCase())) {
            setTime(sender, world, TIME_MAP.get(command.getName().toLowerCase()), MESSAGE_MAP.get(command.getName().toLowerCase()));
            return true;
        }

        return true;
    }

    /**
     * Sets the time of the world to the given time
     *
     * @param sender         The sender of the command
     * @param world          The world to set the time of
     * @param time           The time to set the world to
     * @param successMessage The message to send to the sender if the time was set successfully
     */
    private void setTime(CommandSender sender, World world, int time, Message successMessage) {

        if (sender instanceof ConsoleCommandSender && world == null) {
            Essentials.instance().messages().sendMessage(sender, Messages.TIME_USAGE);
            return;
        }

        Player player = (Player) sender;

        if (world == null) {
            world = player.getWorld();
        }

        world.setTime(time);
        Essentials.instance().messages().sendMessage(sender, successMessage);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length >= 2) return Collections.emptyList();
        return StringUtil.copyPartialMatches(args.length == 1 ? args[0] : "", Bukkit.getWorlds().stream().map(World::getName).collect(Collectors.toList()), new ArrayList<>());
    }
}

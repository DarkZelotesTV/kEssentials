package net.kettlemc.kessentials.util;

import net.kettlemc.kessentials.util.Placeholder;
import net.kettlemc.kessentials.util.Message;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;

import java.time.LocalDateTime;

public class Util {

    public static final String CLEARLAGG_PLUGIN_NAME = "ClearLag";

    public static boolean luckPermsInstalled() {
        return Bukkit.getPluginManager().getPlugin("LuckPerms") != null;
    }


    /**
     * Parse a time string in the format of "HH:mm" to a LocalTime object
     *
     * @param time The time string
     * @return The LocalTime object or 00:00 if the parsing failed
     */
    public static LocalDateTime parseTime(String time) {
        String[] split = time.split(":");
        try {
            LocalDateTime now = LocalDateTime.now();
            return now.withHour(Integer.parseInt(split[0])).withMinute(Integer.parseInt(split[1])).withSecond(0);
        } catch (Exception e) {
            return LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        }
    }


    public static String translate(Message message, Placeholder... placeholders) {
        return LegacyComponentSerializer.legacySection().serialize(message.value(placeholders));
    }

}

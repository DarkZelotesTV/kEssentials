package net.kettlemc.kessentials.util;

import io.github.almightysatan.slams.Placeholder;
import io.github.almightysatan.slams.minimessage.AdventureMessage;
import net.kettlemc.kcommon.language.AdventureUtil;
import net.kettlemc.kessentials.Essentials;
import org.bukkit.Bukkit;

import java.time.LocalDateTime;
import java.util.UUID;

public class Util {

    public static final String CLEARLAGG_PLUGIN_NAME = "ClearLag";

    public static boolean luckPermsInstalled() {
        return Bukkit.getPluginManager().getPlugin("LuckPerms") != null;
    }

    public static boolean miniMessagesAvailable() {
        try {
            Class.forName("net.kyori.adventure.text.minimessage.MiniMessage");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
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


    public static String translate(UUID uuid, AdventureMessage message, Placeholder... placeholders) {
        return AdventureUtil.componentToLegacy(message.value(Essentials.LANGUAGE_API.getEntity(uuid), placeholders));
    }

}

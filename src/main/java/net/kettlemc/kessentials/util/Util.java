package net.kettlemc.kessentials.util;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.regex.Pattern;

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

}

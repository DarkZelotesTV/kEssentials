package net.kettlemc.kessentials.util;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.regex.Pattern;

public class Util {

    public static final String CLEARLAGG_PLUGIN_NAME = "ClearLag";

    // Inspired by Spigot's Chatcolor
    private static final Pattern COLOR_PATTERN = Pattern.compile("(?i)" + "(&|§)" + "[0-9A-FK-OR]");

    public static String stripColor(final String input) {
        if (input == null) {
            return null;
        }

        return COLOR_PATTERN.matcher(input).replaceAll("");
    }

    public static String stripEmojis(String input) {
        return input.replaceAll("[^\\p{L}\\p{M}\\p{N}\\p{P}\\p{Z}\\p{Cf}\\p{Cs}\\s]", "");
    }

    /**
     * Used to retrieve numerical values from permissions (e.g. plugin.amount.1)
     *
     * @param player         The player to check
     * @param permissionBase The base permission (e.g. plugin.amount)
     * @return The numerical value of the permission, 0 if not found, -1 if *
     */
    public static int getPermissionAmount(Player player, String permissionBase) {
        for (PermissionAttachmentInfo permission : player.getEffectivePermissions()) {
            String permString = permission.getPermission();
            if (permString.startsWith(permissionBase.endsWith(".") ? permissionBase : permissionBase + ".")) {
                String[] amount = permString.split("\\.");

                if (amount[amount.length - 1].equalsIgnoreCase("*")) {
                    return -1;
                }

                try {
                    return Integer.parseInt(amount[amount.length - 1]);
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        }
        return 0;
    }

}

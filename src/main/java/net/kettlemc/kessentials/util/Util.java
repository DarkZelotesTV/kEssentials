package net.kettlemc.kessentials.util;

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

}

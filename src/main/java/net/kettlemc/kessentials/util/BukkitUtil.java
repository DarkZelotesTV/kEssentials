package net.kettlemc.kessentials.util;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.regex.Pattern;

/** Utility replacements for some features previously provided by kCommon. */
public final class BukkitUtil {

    private static final Pattern EMOJI_PATTERN = Pattern.compile("[^A-Za-z0-9 ]");

    private BukkitUtil() {}

    public static boolean isInteger(String value) {
        if (value == null) return false;
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static int getPermissionAmount(Player player, String permissionLayout) {
        if (player == null) return 0;
        for (PermissionAttachmentInfo info : player.getEffectivePermissions()) {
            String perm = info.getPermission();
            if (!perm.startsWith(permissionLayout)) continue;
            String suffix = perm.substring(permissionLayout.length());
            if (suffix.startsWith(".")) suffix = suffix.substring(1);
            if (isInteger(suffix)) {
                return Integer.parseInt(suffix);
            }
        }
        return 0;
    }

    public static String stripColor(String input) {
        return input == null ? "" : ChatColor.stripColor(input);
    }

    public static String stripEmojis(String input) {
        if (input == null) return "";
        return EMOJI_PATTERN.matcher(input).replaceAll("");
    }

    public static void saveResource(Class<?> clazz, String resource, Path target) throws IOException {
        if (Files.notExists(target.getParent())) {
            Files.createDirectories(target.getParent());
        }
        try (InputStream in = clazz.getClassLoader().getResourceAsStream(resource)) {
            if (in == null) {
                throw new IOException("Resource not found: " + resource);
            }
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public static String getLuckPermsPrefix(Player player) {
        if (Bukkit.getPluginManager().getPlugin("LuckPerms") == null) {
            return "";
        }
        try {
            LuckPerms lp = LuckPermsProvider.get();
            User user = lp.getPlayerAdapter(Player.class).getUser(player);
            CachedMetaData meta = user.getCachedData().getMetaData();
            String prefix = meta.getPrefix();
            return prefix == null ? "" : prefix;
        } catch (Exception e) {
            return "";
        }
    }
}

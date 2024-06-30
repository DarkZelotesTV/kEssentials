package net.kettlemc.kessentials.config;

import io.github.almightysatan.jaskl.Config;
import io.github.almightysatan.jaskl.Type;
import io.github.almightysatan.jaskl.entries.IntegerConfigEntry;
import io.github.almightysatan.jaskl.entries.ListConfigEntry;
import io.github.almightysatan.jaskl.entries.StringConfigEntry;
import io.github.almightysatan.jaskl.hocon.HoconConfig;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

public class Configuration {

    public static final Path CONFIG_PATH = Paths.get("plugins", "kEssentials");
    private static final Config CONFIG = HoconConfig.of(CONFIG_PATH.resolve("kessentials.conf").toFile());

    public static final StringConfigEntry PERMISSION_LAYOUT = StringConfigEntry.of(CONFIG, "kessentials.settings.permission-prefix", "system.%command%");
    public static final StringConfigEntry PERMISSION_LAYOUT_OTHER = StringConfigEntry.of(CONFIG, "kessentials.settings.permission-layout-other", "system.%command%.other");
    public static final StringConfigEntry PERMISSION_HOME_LAYOUT = StringConfigEntry.of(CONFIG, "kessentials.settings.permission-home-layout", "system.home.amount");

    public static final ListConfigEntry<String> DISABLED_COMMANDS = ListConfigEntry.of(CONFIG, "kessentials.settings.disabled-commands", Collections.emptyList(), Type.STRING);

    public static final ListConfigEntry<String> RESTART_TIMES = ListConfigEntry.of(CONFIG, "kessentials.settings.restart.times", Collections.singletonList("02:00"), Type.STRING);
    public static final ListConfigEntry<Long> RESTART_TIMES_WARNING = ListConfigEntry.of(CONFIG, "kessentials.settings.restart.warning-times", Arrays.asList(
            20L, // 1 second
            2 * 20L, // 2 seconds
            3 * 20L, // 3 seconds
            4 * 20L, // 4 seconds
            5 * 20L, // 5 seconds
            10 * 20L, // 10 seconds
            30 * 20L, // 30 seconds
            60 * 20L, // 1 minute
            5 * 60 * 20L, // 5 minutes
            10 * 60 * 20L, // 10 minutes
            30 * 60 * 20L, // 30 minutes
            60 * 60 * 20L // 1 hour
    ), Type.LONG);

    public static final StringConfigEntry DEFAULT_LANGUAGE = StringConfigEntry.of(CONFIG, "kessentials.settings.default-language", Locale.GERMAN.toLanguageTag());

    public static final IntegerConfigEntry AUTO_SAVE_INTERVAL_SECONDS = IntegerConfigEntry.of(CONFIG, "kessentials.settings.auto-save-interval-seconds", 120);

    public static final IntegerConfigEntry TELEPORT_COUNTDOWN_SECONDS = IntegerConfigEntry.of(CONFIG, "kessentials.settings.teleport-countdown-seconds", 5);

    public static final IntegerConfigEntry DEFAULT_MAX_HOMES = IntegerConfigEntry.of(CONFIG, "kessentials.settings.default-max-homes", 1);

    private Configuration() {
    }

    public static boolean load() {
        try {
            CONFIG.load();
            CONFIG.write();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static void unload() {
        CONFIG.close();
    }

    public static boolean write() {
        try {
            CONFIG.write();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}

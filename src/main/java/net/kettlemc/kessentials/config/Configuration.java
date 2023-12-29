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
import java.util.Collections;
import java.util.Locale;

public class Configuration {

    public static final Path CONFIG_PATH = Paths.get("plugins", "kEssentials");
    private static final Config CONFIG = HoconConfig.of(CONFIG_PATH.resolve("kessentials.conf").toFile());

    public static final StringConfigEntry PERMISSION_LAYOUT = StringConfigEntry.of(CONFIG, "kessentials.settings.permission-prefix", "system.%command%");
    public static final StringConfigEntry PERMISSION_LAYOUT_OTHER = StringConfigEntry.of(CONFIG, "kessentials.settings.permission-layout-other", "system.%command%.other");
    public static final StringConfigEntry PERMISSION_HOME_LAYOUT = StringConfigEntry.of(CONFIG, "kessentials.settings.permission-home-layout", "system.home.amount");

    public static final ListConfigEntry<String> DISABLED_COMMANDS = ListConfigEntry.of(CONFIG, "kessentials.settings.disabled-commands", Collections.emptyList(), Type.STRING);

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

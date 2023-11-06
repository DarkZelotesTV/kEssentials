package net.kettlemc.kessentials.config;

import io.github.almightysatan.jaskl.Config;
import io.github.almightysatan.jaskl.Type;
import io.github.almightysatan.jaskl.entries.ListConfigEntry;
import io.github.almightysatan.jaskl.entries.StringConfigEntry;
import io.github.almightysatan.jaskl.hocon.HoconConfig;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Locale;

public class Configuration {

    private static final Path CONFIG_PATH = Paths.get("plugins", "kEssentials", "kessentials.conf");
    private static final Config CONFIG = HoconConfig.of(CONFIG_PATH.toFile(), "Config for kEssentials");

    public static final StringConfigEntry PERMISSION_LAYOUT = StringConfigEntry.of(CONFIG, "kessentials.settings.permission-prefix", "The default permission layout", "system.%s");
    public static final StringConfigEntry PERMISSION_LAYOUT_OTHER = StringConfigEntry.of(CONFIG, "kessentials.settings.permission-layout-other", "The default permission layout for targeting other players", "system.%s.other");

    public static final ListConfigEntry<String> DISABLED_COMMANDS = ListConfigEntry.of(CONFIG, "kessentials.settings.disabled-commands", "The disabled commands", Collections.emptyList(), Type.STRING);

    public static final StringConfigEntry DEFAULT_LANGUAGE = StringConfigEntry.of(CONFIG, "kessentials.settings.default-language", "The default language", Locale.GERMAN.toLanguageTag());

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

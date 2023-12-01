package net.kettlemc.kessentials.config;

import io.github.almightysatan.jaskl.Config;
import io.github.almightysatan.jaskl.entries.BooleanConfigEntry;
import io.github.almightysatan.jaskl.entries.IntegerConfigEntry;
import io.github.almightysatan.jaskl.entries.LongConfigEntry;
import io.github.almightysatan.jaskl.entries.StringConfigEntry;
import io.github.almightysatan.jaskl.hocon.HoconConfig;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DiscordConfiguration {

    public static final Path CONFIG_PATH = Paths.get("plugins", "kEssentials", "discord.conf");
    private static final Config CONFIG = HoconConfig.of(CONFIG_PATH.toFile(), "Config for the discord discord of kEssentials");

    // Discord discord
    public static StringConfigEntry DISCORD_TOKEN = StringConfigEntry.of(CONFIG, "discord.discord.token", "YOUR TOKEN");
    public static LongConfigEntry DISCORD_CHANNEL_ID = LongConfigEntry.of(CONFIG, "discord.discord.channel-id", 1L);
    public static BooleanConfigEntry DISCORD_ALLOW_EVERY_CHANNEL_FOR_COMMANDS = BooleanConfigEntry.of(CONFIG, "discord.discord.commands-in-every-channel", false);
    public static StringConfigEntry DISCORD_BOT_STATUS = StringConfigEntry.of(CONFIG, "discord.discord.status", " mit <online> players");

    // Chat messages on discord
    public static BooleanConfigEntry DISCORD_DISABLE_FORMATTING = BooleanConfigEntry.of(CONFIG, "discord.discord.disable-formatting", true);
    public static BooleanConfigEntry DISCORD_EMBED_ENABLED = BooleanConfigEntry.of(CONFIG, "discord.discord.embed.enabled", false);
    public static BooleanConfigEntry DISCORD_EMBED_SHOW_SKIN = BooleanConfigEntry.of(CONFIG, "discord.discord.embed.show-skin", true);
    public static StringConfigEntry DEFAULT_RANK = StringConfigEntry.of(CONFIG, "discord.default-rank", "Player");

    // Clearlagg support
    public static IntegerConfigEntry MIN_CLEARLAGG_ITEMS = IntegerConfigEntry.of(CONFIG, "discord.clearlagg.min-items", 100);

    private DiscordConfiguration() {
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

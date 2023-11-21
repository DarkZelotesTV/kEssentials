package net.kettlemc.kessentials.config;

import io.github.almightysatan.slams.Slams;
import io.github.almightysatan.slams.minimessage.AdventureMessage;
import io.github.almightysatan.slams.parser.JacksonParser;
import net.kettlemc.kcommon.java.FileUtil;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Objects;

public class Messages {

    private static final Path LANGUAGE_PATH = Paths.get("plugins", "kEssentials", "languages");
    private static final Slams LANGUAGE_MANAGER = Slams.create(getDefaultLocale());

    public static final AdventureMessage PREFIX = AdventureMessage.of("messages.common.prefix", LANGUAGE_MANAGER);
    public static final AdventureMessage NO_PERMISSION = AdventureMessage.of("messages.common.no-permission", LANGUAGE_MANAGER);
    public static final AdventureMessage PLAYER_ONLY = AdventureMessage.of("messages.common.player-only", LANGUAGE_MANAGER);
    public static final AdventureMessage CONSOLE_ONLY = AdventureMessage.of("messages.common.console-only", LANGUAGE_MANAGER);
    public static final AdventureMessage PLAYER_NOT_FOUND = AdventureMessage.of("messages.common.player-not-found", LANGUAGE_MANAGER);
    public static final AdventureMessage COMMAND_DISABLED = AdventureMessage.of("messages.common.command-disabled", LANGUAGE_MANAGER);

    public static final AdventureMessage JOIN_MESSAGE = AdventureMessage.of("messages.common.join", LANGUAGE_MANAGER);
    public static final AdventureMessage QUIT_MESSAGE = AdventureMessage.of("messages.common.quit", LANGUAGE_MANAGER);

    public static final AdventureMessage TELEPORTATION_CANCELLED = AdventureMessage.of("messages.teleport.teleportation-cancelled", LANGUAGE_MANAGER);
    public static final AdventureMessage TELEPORTATION_SUCCESSFUL = AdventureMessage.of("messages.teleport.teleportation-successful", LANGUAGE_MANAGER);
    public static final AdventureMessage TELEPORTATION_COOLDOWN = AdventureMessage.of("messages.teleport.teleportation-cooldown", LANGUAGE_MANAGER);

    public static final AdventureMessage GAMEMODE_SET = AdventureMessage.of("messages.gamemode.set", LANGUAGE_MANAGER);
    public static final AdventureMessage GAMEMODE_SET_OTHER = AdventureMessage.of("messages.gamemode.set-other", LANGUAGE_MANAGER);
    public static final AdventureMessage GAMEMODE_INVALID = AdventureMessage.of("messages.gamemode.invalid", LANGUAGE_MANAGER);
    public static final AdventureMessage GAMEMODE_USAGE = AdventureMessage.of("messages.gamemode.usage", LANGUAGE_MANAGER);

    public static final AdventureMessage FLY_ENABLED = AdventureMessage.of("messages.fly.enabled", LANGUAGE_MANAGER);
    public static final AdventureMessage FLY_ENABLED_OTHER = AdventureMessage.of("messages.fly.enabled-other", LANGUAGE_MANAGER);
    public static final AdventureMessage FLY_DISABLED = AdventureMessage.of("messages.fly.disabled", LANGUAGE_MANAGER);
    public static final AdventureMessage FLY_DISABLED_OTHER = AdventureMessage.of("messages.fly.disabled-other", LANGUAGE_MANAGER);
    public static final AdventureMessage FLY_USAGE = AdventureMessage.of("messages.fly.usage", LANGUAGE_MANAGER);

    public static final AdventureMessage SPEED_SET_WALKING = AdventureMessage.of("messages.speed.set.walking", LANGUAGE_MANAGER);
    public static final AdventureMessage SPEED_SET_FLYING = AdventureMessage.of("messages.speed.set.flying", LANGUAGE_MANAGER);
    public static final AdventureMessage SPEED_SET_WALKING_OTHER = AdventureMessage.of("messages.speed.set-other.walking", LANGUAGE_MANAGER);
    public static final AdventureMessage SPEED_SET_FLYING_OTHER = AdventureMessage.of("messages.speed.set-other.flying", LANGUAGE_MANAGER);
    public static final AdventureMessage SPEED_INVALID = AdventureMessage.of("messages.speed.invalid", LANGUAGE_MANAGER);
    public static final AdventureMessage SPEED_USAGE = AdventureMessage.of("messages.speed.usage", LANGUAGE_MANAGER);

    public static final AdventureMessage TELEPORT_TELEPORTED = AdventureMessage.of("messages.teleport.teleported", LANGUAGE_MANAGER);
    public static final AdventureMessage TELEPORT_TELEPORTED_OTHER = AdventureMessage.of("messages.teleport.teleported-other", LANGUAGE_MANAGER);
    public static final AdventureMessage TELEPORT_USAGE = AdventureMessage.of("messages.teleport.usage", LANGUAGE_MANAGER);

    public static final AdventureMessage FREEZE_FROZEN = AdventureMessage.of("messages.freeze.frozen", LANGUAGE_MANAGER);
    public static final AdventureMessage FREEZE_FROZEN_OTHER = AdventureMessage.of("messages.freeze.frozen-other", LANGUAGE_MANAGER);
    public static final AdventureMessage FREEZE_UNFROZEN = AdventureMessage.of("messages.freeze.unfrozen", LANGUAGE_MANAGER);
    public static final AdventureMessage FREEZE_UNFROZEN_OTHER = AdventureMessage.of("messages.freeze.unfrozen-other", LANGUAGE_MANAGER);
    public static final AdventureMessage FREEZE_USAGE = AdventureMessage.of("messages.freeze.usage", LANGUAGE_MANAGER);

    public static final AdventureMessage VANISH_VANISHED = AdventureMessage.of("messages.vanish.vanished", LANGUAGE_MANAGER);
    public static final AdventureMessage VANISH_VANISHED_OTHER = AdventureMessage.of("messages.vanish.vanished-other", LANGUAGE_MANAGER);
    public static final AdventureMessage VANISH_UNVANISHED = AdventureMessage.of("messages.vanish.unvanished", LANGUAGE_MANAGER);
    public static final AdventureMessage VANISH_UNVANISHED_OTHER = AdventureMessage.of("messages.vanish.unvanished-other", LANGUAGE_MANAGER);
    public static final AdventureMessage VANISH_USAGE = AdventureMessage.of("messages.vanish.usage", LANGUAGE_MANAGER);

    public static final AdventureMessage HEAL_HEALED = AdventureMessage.of("messages.heal.healed", LANGUAGE_MANAGER);
    public static final AdventureMessage HEAL_HEALED_OTHER = AdventureMessage.of("messages.heal.healed-other", LANGUAGE_MANAGER);
    public static final AdventureMessage HEAL_USAGE = AdventureMessage.of("messages.heal.usage", LANGUAGE_MANAGER);

    public static final AdventureMessage FEED_FED = AdventureMessage.of("messages.feed.fed", LANGUAGE_MANAGER);
    public static final AdventureMessage FEED_FED_OTHER = AdventureMessage.of("messages.feed.fed-other", LANGUAGE_MANAGER);
    public static final AdventureMessage FEED_USAGE = AdventureMessage.of("messages.feed.usage", LANGUAGE_MANAGER);

    public static final AdventureMessage REPAIR_REPAIRED = AdventureMessage.of("messages.repair.repaired", LANGUAGE_MANAGER);
    public static final AdventureMessage REPAIR_REPAIRED_OTHER = AdventureMessage.of("messages.repair.repaired-other", LANGUAGE_MANAGER);
    public static final AdventureMessage REPAIR_USAGE = AdventureMessage.of("messages.repair.usage", LANGUAGE_MANAGER);
    public static final AdventureMessage REPAIR_NOT_REPAIRABLE = AdventureMessage.of("messages.repair.not-repairable", LANGUAGE_MANAGER);
    public static final AdventureMessage REPAIR_NOT_REPAIRABLE_OTHER = AdventureMessage.of("messages.repair.not-repairable-other", LANGUAGE_MANAGER);
    public static final AdventureMessage REPAIR_NO_ITEM = AdventureMessage.of("messages.repair.no-item", LANGUAGE_MANAGER);
    public static final AdventureMessage REPAIR_NO_ITEM_OTHER = AdventureMessage.of("messages.repair.no-item-other", LANGUAGE_MANAGER);

    public static final AdventureMessage INVENTORY_USAGE = AdventureMessage.of("messages.inventory.usage", LANGUAGE_MANAGER);

    public static final AdventureMessage ARMOR_USAGE = AdventureMessage.of("messages.armor.usage", LANGUAGE_MANAGER);

    public static final AdventureMessage SUN = AdventureMessage.of("messages.weather.sun", LANGUAGE_MANAGER);
    public static final AdventureMessage RAIN = AdventureMessage.of("messages.weather.rain", LANGUAGE_MANAGER);
    public static final AdventureMessage THUNDER = AdventureMessage.of("messages.weather.thunder", LANGUAGE_MANAGER);
    public static final AdventureMessage WEATHER_USAGE = AdventureMessage.of("messages.weather.usage", LANGUAGE_MANAGER);

    public static final AdventureMessage MORNING = AdventureMessage.of("messages.time.morning", LANGUAGE_MANAGER);
    public static final AdventureMessage DAY = AdventureMessage.of("messages.time.day", LANGUAGE_MANAGER);
    public static final AdventureMessage MIDDAY = AdventureMessage.of("messages.time.midday", LANGUAGE_MANAGER);
    public static final AdventureMessage EVENING = AdventureMessage.of("messages.time.evening", LANGUAGE_MANAGER);
    public static final AdventureMessage NIGHT = AdventureMessage.of("messages.time.night", LANGUAGE_MANAGER);
    public static final AdventureMessage TIME_USAGE = AdventureMessage.of("messages.time.usage", LANGUAGE_MANAGER);

    public static final AdventureMessage CHATCLEAR_CLEARED = AdventureMessage.of("messages.chat.cleared", LANGUAGE_MANAGER);

    public static final AdventureMessage SUICIDE = AdventureMessage.of("messages.suicide.killed", LANGUAGE_MANAGER);

    public static final AdventureMessage TPA_OTHER_ONLY = AdventureMessage.of("messages.tpa.tpa.other-only", LANGUAGE_MANAGER);

    public static final AdventureMessage TPA_LIST = AdventureMessage.of("messages.tpa.list.header", LANGUAGE_MANAGER);
    public static final AdventureMessage TPA_LIST_NO_REQUEST = AdventureMessage.of("messages.tpa.list.no-request", LANGUAGE_MANAGER);
    public static final AdventureMessage TPA_LIST_ENTRY = AdventureMessage.of("messages.tpa.list.entry", LANGUAGE_MANAGER);

    public static final AdventureMessage TPA_REQUEST_SENT = AdventureMessage.of("messages.tpa.tpa.sent", LANGUAGE_MANAGER);
    public static final AdventureMessage TPA_REQUEST_RECEIVED = AdventureMessage.of("messages.tpa.tpa.received", LANGUAGE_MANAGER);
    public static final AdventureMessage TPA_REQUEST_ACCEPT = AdventureMessage.of("messages.tpa.tpa.accept", LANGUAGE_MANAGER);
    public static final AdventureMessage TPA_REQUEST_DENY = AdventureMessage.of("messages.tpa.tpa.deny", LANGUAGE_MANAGER);
    public static final AdventureMessage TPA_REQUEST_ACCEPT_HOVER = AdventureMessage.of("messages.tpa.tpa.accept-hover", LANGUAGE_MANAGER);
    public static final AdventureMessage TPA_REQUEST_DENY_HOVER = AdventureMessage.of("messages.tpa.tpa.deny-hover", LANGUAGE_MANAGER);
    public static final AdventureMessage TPA_REQUEST_ALREADY_SENT = AdventureMessage.of("messages.tpa.tpa.already-sent", LANGUAGE_MANAGER);
    public static final AdventureMessage TPA_REQUEST_DENIED = AdventureMessage.of("messages.tpa.tpa.denied", LANGUAGE_MANAGER);
    public static final AdventureMessage TPA_REQUEST_ACCEPTED = AdventureMessage.of("messages.tpa.tpa.accepted", LANGUAGE_MANAGER);
    public static final AdventureMessage TPA_REQUEST_USAGE = AdventureMessage.of("messages.tpa.tpa.usage", LANGUAGE_MANAGER);

    public static final AdventureMessage TPA_TPACCEPT = AdventureMessage.of("messages.tpa.tpaccept.tpaccept", LANGUAGE_MANAGER);
    public static final AdventureMessage TPA_TPACCEPT_USAGE = AdventureMessage.of("messages.tpa.tpaccept.usage", LANGUAGE_MANAGER);

    public static final AdventureMessage TPA_TPDENY = AdventureMessage.of("messages.tpa.tpdeny.tpdeny", LANGUAGE_MANAGER);
    public static final AdventureMessage TPA_TPDENY_USAGE = AdventureMessage.of("messages.tpa.tpdeny.usage", LANGUAGE_MANAGER);


    private static String getDefaultLocale() {
        return Locale.forLanguageTag(Configuration.DEFAULT_LANGUAGE.getValue()).toLanguageTag();
    }

    public static boolean load() {
        try {
            FileUtil.saveResourceAsFile(Messages.class, "lang/de.json", LANGUAGE_PATH.resolve("de.json"));
            FileUtil.saveResourceAsFile(Messages.class, "lang/en.json", LANGUAGE_PATH.resolve("en.json"));
            loadFromFilesInDirectory(LANGUAGE_PATH.toFile());
            return true;
        } catch (IOException | URISyntaxException e) {
            return false;
        }

    }

    private static void loadFromFilesInDirectory(File directory) throws IOException, URISyntaxException {
        if (!directory.isDirectory()) return;
        for (File file : Objects.requireNonNull(LANGUAGE_PATH.toFile().listFiles())) {
            if (file.isDirectory()) loadFromFilesInDirectory(file);
            else if (file.getName().endsWith(".json"))
                LANGUAGE_MANAGER.load(file.getName().replace(".json", ""), JacksonParser.createJsonParser(file));
        }
    }

}

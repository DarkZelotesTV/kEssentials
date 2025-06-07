package net.kettlemc.kessentials.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.kettlemc.kessentials.util.BukkitUtil;
import net.kettlemc.kessentials.util.Message;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Map;

public class Messages {

    private static final Path LANGUAGE_PATH = Paths.get("plugins", "kEssentials", "languages");
    private static Map<String, Object> LANGUAGE_DATA;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static final Message PREFIX = Message.of("messages.common.prefix");
    public static final Message NO_PERMISSION = Message.of("messages.common.no-permission");
    public static final Message PLAYER_ONLY = Message.of("messages.common.player-only");
    public static final Message CONSOLE_ONLY = Message.of("messages.common.console-only");
    public static final Message PLAYER_NOT_FOUND = Message.of("messages.common.player-not-found");
    public static final Message COMMAND_DISABLED = Message.of("messages.common.command-disabled");

    public static final Message RESTART = Message.of("messages.common.restart");
    public static final Message TIMEUNIT_SECOND = Message.of("messages.common.timeunit.second");
    public static final Message TIMEUNIT_SECONDS = Message.of("messages.common.timeunit.seconds");
    public static final Message TIMEUNIT_MINUTE = Message.of("messages.common.timeunit.minute");
    public static final Message TIMEUNIT_MINUTES = Message.of("messages.common.timeunit.minutes");
    public static final Message TIMEUNIT_HOUR = Message.of("messages.common.timeunit.hour");
    public static final Message TIMEUNIT_HOURS = Message.of("messages.common.timeunit.hours");

    public static final Message JOIN_MESSAGE = Message.of("messages.common.join");
    public static final Message QUIT_MESSAGE = Message.of("messages.common.quit");

    public static final Message TELEPORTATION_CANCELLED = Message.of("messages.teleport.teleportation-cancelled");
    public static final Message TELEPORTATION_SUCCESSFUL = Message.of("messages.teleport.teleportation-successful");
    public static final Message TELEPORTATION_COOLDOWN = Message.of("messages.teleport.teleportation-cooldown");

    public static final Message GAMEMODE_SET = Message.of("messages.gamemode.set");
    public static final Message GAMEMODE_SET_OTHER = Message.of("messages.gamemode.set-other");
    public static final Message GAMEMODE_INVALID = Message.of("messages.gamemode.invalid");
    public static final Message GAMEMODE_USAGE = Message.of("messages.gamemode.usage");

    public static final Message FLY_ENABLED = Message.of("messages.fly.enabled");
    public static final Message FLY_ENABLED_OTHER = Message.of("messages.fly.enabled-other");
    public static final Message FLY_DISABLED = Message.of("messages.fly.disabled");
    public static final Message FLY_DISABLED_OTHER = Message.of("messages.fly.disabled-other");
    public static final Message FLY_USAGE = Message.of("messages.fly.usage");

    public static final Message SPEED_SET_WALKING = Message.of("messages.speed.set.walking");
    public static final Message SPEED_SET_FLYING = Message.of("messages.speed.set.flying");
    public static final Message SPEED_SET_WALKING_OTHER = Message.of("messages.speed.set-other.walking");
    public static final Message SPEED_SET_FLYING_OTHER = Message.of("messages.speed.set-other.flying");
    public static final Message SPEED_INVALID = Message.of("messages.speed.invalid");
    public static final Message SPEED_USAGE = Message.of("messages.speed.usage");

    public static final Message TELEPORT_TELEPORTED = Message.of("messages.teleport.teleported");
    public static final Message TELEPORT_TELEPORTED_OTHER = Message.of("messages.teleport.teleported-other");
    public static final Message TELEPORT_USAGE = Message.of("messages.teleport.usage");
    public static final Message TELEPORT_ALREADY_SCHEDULED = Message.of("messages.teleport.already-scheduled");

    public static final Message FREEZE_FROZEN = Message.of("messages.freeze.frozen");
    public static final Message FREEZE_FROZEN_OTHER = Message.of("messages.freeze.frozen-other");
    public static final Message FREEZE_UNFROZEN = Message.of("messages.freeze.unfrozen");
    public static final Message FREEZE_UNFROZEN_OTHER = Message.of("messages.freeze.unfrozen-other");
    public static final Message FREEZE_USAGE = Message.of("messages.freeze.usage");

    public static final Message VANISH_VANISHED = Message.of("messages.vanish.vanished");
    public static final Message VANISH_VANISHED_OTHER = Message.of("messages.vanish.vanished-other");
    public static final Message VANISH_UNVANISHED = Message.of("messages.vanish.unvanished");
    public static final Message VANISH_UNVANISHED_OTHER = Message.of("messages.vanish.unvanished-other");
    public static final Message VANISH_USAGE = Message.of("messages.vanish.usage");

    public static final Message HEAL_HEALED = Message.of("messages.heal.healed");
    public static final Message HEAL_HEALED_OTHER = Message.of("messages.heal.healed-other");
    public static final Message HEAL_USAGE = Message.of("messages.heal.usage");

    public static final Message FEED_FED = Message.of("messages.feed.fed");
    public static final Message FEED_FED_OTHER = Message.of("messages.feed.fed-other");
    public static final Message FEED_USAGE = Message.of("messages.feed.usage");

    public static final Message REPAIR_REPAIRED = Message.of("messages.repair.repaired");
    public static final Message REPAIR_REPAIRED_OTHER = Message.of("messages.repair.repaired-other");
    public static final Message REPAIR_USAGE = Message.of("messages.repair.usage");
    public static final Message REPAIR_NOT_REPAIRABLE = Message.of("messages.repair.not-repairable");
    public static final Message REPAIR_NOT_REPAIRABLE_OTHER = Message.of("messages.repair.not-repairable-other");
    public static final Message REPAIR_NO_ITEM = Message.of("messages.repair.no-item");
    public static final Message REPAIR_NO_ITEM_OTHER = Message.of("messages.repair.no-item-other");

    public static final Message INVENTORY_USAGE = Message.of("messages.inventory.usage");

    public static final Message ARMOR_USAGE = Message.of("messages.armor.usage");

    public static final Message SUN = Message.of("messages.weather.sun");
    public static final Message RAIN = Message.of("messages.weather.rain");
    public static final Message THUNDER = Message.of("messages.weather.thunder");
    public static final Message WEATHER_USAGE = Message.of("messages.weather.usage");

    public static final Message MORNING = Message.of("messages.time.morning");
    public static final Message DAY = Message.of("messages.time.day");
    public static final Message MIDDAY = Message.of("messages.time.midday");
    public static final Message EVENING = Message.of("messages.time.evening");
    public static final Message NIGHT = Message.of("messages.time.night");
    public static final Message TIME_USAGE = Message.of("messages.time.usage");

    public static final Message CHATCLEAR_CLEARED = Message.of("messages.chat.cleared");

    public static final Message SUICIDE = Message.of("messages.suicide.killed");

    public static final Message TPA_OTHER_ONLY = Message.of("messages.tpa.tpa.other-only");

    public static final Message TPA_LIST = Message.of("messages.tpa.list.header");
    public static final Message TPA_LIST_NO_REQUEST = Message.of("messages.tpa.list.no-request");
    public static final Message TPA_LIST_ENTRY = Message.of("messages.tpa.list.entry");

    public static final Message TPA_REQUEST_SENT = Message.of("messages.tpa.tpa.sent");
    public static final Message TPA_REQUEST_RECEIVED = Message.of("messages.tpa.tpa.received");
    public static final Message TPA_REQUEST_ACCEPT = Message.of("messages.tpa.tpa.accept");
    public static final Message TPA_REQUEST_DENY = Message.of("messages.tpa.tpa.deny");
    public static final Message TPA_REQUEST_ACCEPT_HOVER = Message.of("messages.tpa.tpa.accept-hover");
    public static final Message TPA_REQUEST_DENY_HOVER = Message.of("messages.tpa.tpa.deny-hover");
    public static final Message TPA_REQUEST_ALREADY_SENT = Message.of("messages.tpa.tpa.already-sent");
    public static final Message TPA_REQUEST_DENIED = Message.of("messages.tpa.tpa.denied");
    public static final Message TPA_REQUEST_ACCEPTED = Message.of("messages.tpa.tpa.accepted");
    public static final Message TPA_REQUEST_USAGE = Message.of("messages.tpa.tpa.usage");

    public static final Message TPA_TPACCEPT = Message.of("messages.tpa.tpaccept.tpaccept");
    public static final Message TPA_TPACCEPT_USAGE = Message.of("messages.tpa.tpaccept.usage");

    public static final Message TPA_TPDENY = Message.of("messages.tpa.tpdeny.tpdeny");
    public static final Message TPA_TPDENY_USAGE = Message.of("messages.tpa.tpdeny.usage");

    public static final Message DISCORD_CHAT_FORMAT = Message.of("messages.discord.chat-format");
    public static final Message DISCORD_NO_PERMISSION = Message.of("messages.discord.no-permission");
    public static final Message DISCORD_JOIN = Message.of("messages.discord.join");
    public static final Message DISCORD_QUIT = Message.of("messages.discord.quit");
    public static final Message DISCORD_STARTUP = Message.of("messages.discord.startup");
    public static final Message DISCORD_SHUTDOWN = Message.of("messages.discord.shutdown");
    public static final Message DISCORD_CLEARLAGG = Message.of("messages.discord.clearlagg");
    public static final Message DISCORD_ONLINE_LIST = Message.of("messages.discord.online.list");
    public static final Message DISCORD_NO_PLAYERS = Message.of("messages.discord.online.no-players");
    public static final Message DISCORD_RESTART = Message.of("messages.discord.restart.countdown");
    public static final Message DISCORD_RESTART_INSTANT = Message.of("messages.discord.restart.instant");
    public static final Message DISCORD_CHAT_FORMAT_MINECRAFT = Message.of("messages.discord.minecraft.chat-format");
    public static final Message DISCORD_RESTART_MINECRAFT = Message.of("messages.discord.minecraft.restart.countdown");
    public static final Message DISCORD_WELCOME_MESSAGE = Message.of("messages.discord.minecraft.welcome");

    public static final Message HOME_SET_USAGE = Message.of("messages.home.set.usage");
    public static final Message HOME_INVALID_NAME = Message.of("messages.home.set.invalid-name");
    public static final Message HOME_MAX_HOMES_REACHED = Message.of("messages.home.set.max");
    public static final Message HOME_ALREADY_EXISTS = Message.of("messages.home.set.already-exists");
    public static final Message HOME_SET = Message.of("messages.home.set.set");

    public static final Message HOME_LIST_HEADER = Message.of("messages.home.list.header");
    public static final Message HOME_LIST_ENTRY = Message.of("messages.home.list.entry");
    public static final Message HOME_LIST_HOVER = Message.of("messages.home.list.hover");
    public static final Message HOME_NOT_FOUND = Message.of("messages.home.not-found");
    public static final Message HOME_USAGE = Message.of("messages.home.usage");

    public static final Message HOME_DELETE_USAGE = Message.of("messages.home.delete.usage");
    public static final Message HOME_DOES_NOT_EXIST = Message.of("messages.home.delete.doesnt-exist");
    public static final Message HOME_DELETED = Message.of("messages.home.delete.deleted");

    public static final Message WARP_SET_USAGE = Message.of("messages.warp.set.usage");
    public static final Message WARP_INVALID_NAME = Message.of("messages.warp.set.invalid-name");
    public static final Message WARP_ALREADY_EXISTS = Message.of("messages.warp.set.already-exists");
    public static final Message WARP_SET = Message.of("messages.warp.set.set");

    public static final Message WARP_LIST_HEADER = Message.of("messages.warp.list.header");
    public static final Message WARP_LIST_ENTRY = Message.of("messages.warp.list.entry");
    public static final Message WARP_LIST_HOVER = Message.of("messages.warp.list.hover");
    public static final Message WARP_NOT_FOUND = Message.of("messages.warp.not-found");
    public static final Message WARP_USAGE = Message.of("messages.warp.usage");

    public static final Message WARP_DELETE_USAGE = Message.of("messages.warp.delete.usage");
    public static final Message WARP_DOES_NOT_EXIST = Message.of("messages.warp.delete.doesnt-exist");
    public static final Message WARP_DELETED = Message.of("messages.warp.delete.deleted");

    public static final Message RELOAD_SUCCESS = Message.of("messages.reload.success");
    public static final Message RELOAD_FAIL = Message.of("messages.reload.fail");

    public static final Message MATERIAL = Message.of("messages.material");

    public static final Message ENCHANTINGTABLE_USAGE = Message.of("messages.gui.enchantingtable.usage");

    private static String getDefaultLocale() {
        return Locale.forLanguageTag(Configuration.DEFAULT_LANGUAGE.getValue()).toLanguageTag();
    }

    public static String get(String path) {
        String[] parts = path.split("\\.");
        Object current = LANGUAGE_DATA;
        for (String part : parts) {
            if (!(current instanceof Map)) return null;
            current = ((Map<?, ?>) current).get(part);
            if (current == null) return null;
        }
        return current.toString();
    }

    public static boolean load() {
        try {
            BukkitUtil.saveResource(Messages.class, "lang/de.json", LANGUAGE_PATH.resolve("de.json"));
            BukkitUtil.saveResource(Messages.class, "lang/en.json", LANGUAGE_PATH.resolve("en.json"));
            Path langFile = LANGUAGE_PATH.resolve(getDefaultLocale() + ".json");
            if (Files.notExists(langFile)) {
                langFile = LANGUAGE_PATH.resolve("en.json");
            }
            LANGUAGE_DATA = MAPPER.readValue(langFile.toFile(), Map.class);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean reload() {
        return load();
    }
}

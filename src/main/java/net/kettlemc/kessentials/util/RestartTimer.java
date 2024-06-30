package net.kettlemc.kessentials.util;

import io.github.almightysatan.slams.Context;
import io.github.almightysatan.slams.Placeholder;
import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.config.Configuration;
import net.kettlemc.kessentials.config.Messages;
import net.kettlemc.klanguage.common.LanguageEntity;
import org.bukkit.Bukkit;

import java.time.LocalTime;
import java.util.UUID;

public class RestartTimer {

    public static void scheduleRestart() {
        // Get the restart time closest to the current time in the future
        LocalTime now = LocalTime.now();
        LocalTime closest = null;
        for (String time : Configuration.RESTART_TIMES.getValue()) {
            LocalTime parsed = Util.parseTime(time);
            if (parsed.isBefore(now)) {
                parsed = parsed.plusHours(24);
            }
            if (closest == null || parsed.isBefore(closest)) {
                closest = parsed;
            }
        }

        if (closest == null) {
            Essentials.instance().getPlugin().getLogger().warning("No valid restart times found in the configuration.");
            return;
        }

        // Schedule the restart
        long delay = (closest.toSecondOfDay() - now.toSecondOfDay()) * 20L;
        Essentials.instance().getPlugin().getLogger().info("Scheduled restart at " + closest + " in " + delay + " ticks.");
        Bukkit.getScheduler().runTaskLater(Essentials.instance().getPlugin(), Bukkit::shutdown, delay);

        for (long time : Configuration.RESTART_TIMES_WARNING.getValue()) {
            if (delay >= time) {
                Essentials.instance().getPlugin().getLogger().info("Scheduled restart warning at " + (delay - time) + " ticks before restart.");
                Bukkit.getScheduler().runTaskLaterAsynchronously(Essentials.instance().getPlugin(), () -> {
                    Placeholder timeValue = Placeholder.of("time", (ctx, args) -> timeMessage(ctx, delay));
                    Essentials.instance().messages().broadcastMessage(Messages.RESTART, timeValue);
                }, delay - time);
                break;
            }
        }
    }


    /**
     * Returns a string representation of the time unit for the given amount of ticks.
     * <p>
     * The format is as follows: X hours X minutes X seconds (the timeunits are replaced with the translated versions)
     * If the amount of ticks doesn't fill a time unit, it will be omitted.
     * <p>
     * Example: 3740 ticks will return "3 minutes 7 seconds"
     * <p>
     * Example: 7200 ticks will return "6 minutes"
     *
     * @param player The player to send the message to
     * @param ticks  The amount of ticks
     * @return The message
     */
    public static String timeMessage(Context player, long ticks) {

        LanguageEntity entity = (LanguageEntity) player;

        long seconds = ticks / 20;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        seconds %= 60;
        minutes %= 60;

        UUID uuid = UUID.fromString(entity.uuid());

        StringBuilder message = new StringBuilder();

        if (hours == 1) {
            message.append(hours).append(" ").append(Util.translate(uuid, Messages.TIMEUNIT_HOUR));
        } else if (hours > 1) {
            message.append(hours).append(" ").append(Util.translate(uuid, Messages.TIMEUNIT_HOURS));
        }

        if (minutes == 1) {
            if (message.length() > 0) {
                message.append(" ");
            }
            message.append(minutes).append(" ").append(Util.translate(uuid, Messages.TIMEUNIT_MINUTE));
        } else if (minutes > 1) {
            if (message.length() > 0) {
                message.append(" ");
            }
            message.append(minutes).append(" ").append(Util.translate(uuid, Messages.TIMEUNIT_MINUTES));
        }

        if (seconds == 1) {
            if (message.length() > 0) {
                message.append(" ");
            }
            message.append(seconds).append(" ").append(Util.translate(uuid, Messages.TIMEUNIT_SECOND));
        } else if (seconds > 1) {
            if (message.length() > 0) {
                message.append(" ");
            }
            message.append(seconds).append(" ").append(Util.translate(uuid, Messages.TIMEUNIT_SECONDS));
        }

        return message.toString();
    }

}

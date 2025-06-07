package net.kettlemc.kessentials.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;

public class Message {
    private final String path;

    private Message(String path) {
        this.path = path;
    }

    public static Message of(String path) {
        return new Message(path);
    }

    public Component value(Placeholder... placeholders) {
        String raw = Messages.get(path);
        if (raw == null) {
            raw = path;
        }
        if (placeholders != null) {
            for (Placeholder p : placeholders) {
                raw = raw.replace("<" + p.key() + ">", p.value());
            }
        }
        raw = ChatColor.translateAlternateColorCodes('&', raw);
        return LegacyComponentSerializer.legacySection().deserialize(raw);
    }
}

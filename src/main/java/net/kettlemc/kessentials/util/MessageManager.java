package net.kettlemc.kessentials.util;

import io.github.almightysatan.slams.Placeholder;
import io.github.almightysatan.slams.minimessage.AdventureMessage;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageManager {

    private final AdventureMessage prefix;
    private final BukkitAudiences adventure;

    public MessageManager(AdventureMessage prefix, BukkitAudiences adventure) {
        this.prefix = prefix;
        this.adventure = adventure;
    }

    private Component build(boolean usePrefix, AdventureMessage message, Placeholder... placeholders) {
        Component content = message.value(placeholders);
        if (usePrefix && prefix != null) {
            return prefix.value().append(Component.space()).append(content);
        }
        return content;
    }

    public void sendMessage(CommandSender sender, AdventureMessage message, Placeholder... placeholders) {
        sendMessage(sender, true, message, placeholders);
    }

    public void sendMessage(CommandSender sender, boolean prefix, AdventureMessage message, Placeholder... placeholders) {
        Audience audience = (sender instanceof Player) ? adventure.player((Player) sender) : adventure.sender(sender);
        audience.sendMessage(build(prefix, message, placeholders));
    }

    public void broadcastMessage(AdventureMessage message, Placeholder... placeholders) {
        broadcastMessage(message, true, placeholders);
    }

    public void broadcastMessage(AdventureMessage message, boolean prefix, Placeholder... placeholders) {
        adventure.all().sendMessage(build(prefix, message, placeholders));
    }
}

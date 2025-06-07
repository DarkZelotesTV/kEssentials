package net.kettlemc.kessentials.util;

import net.kettlemc.kessentials.util.Placeholder;
import net.kettlemc.kessentials.util.Message;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageManager {

    private final Message prefix;
    private final BukkitAudiences adventure;

    public MessageManager(Message prefix, BukkitAudiences adventure) {
        this.prefix = prefix;
        this.adventure = adventure;
    }

    private Component build(boolean usePrefix, Message message, Placeholder... placeholders) {
        Component content = message.value(placeholders);
        if (usePrefix && prefix != null) {
            return prefix.value().append(Component.space()).append(content);
        }
        return content;
    }

    public void sendMessage(CommandSender sender, Message message, Placeholder... placeholders) {
        sendMessage(sender, true, message, placeholders);
    }

    public void sendMessage(CommandSender sender, boolean prefix, Message message, Placeholder... placeholders) {
        Audience audience = (sender instanceof Player) ? adventure.player((Player) sender) : adventure.sender(sender);
        audience.sendMessage(build(prefix, message, placeholders));
    }

    public void broadcastMessage(Message message, Placeholder... placeholders) {
        broadcastMessage(message, true, placeholders);
    }

    public void broadcastMessage(Message message, boolean prefix, Placeholder... placeholders) {
        adventure.all().sendMessage(build(prefix, message, placeholders));
    }
}

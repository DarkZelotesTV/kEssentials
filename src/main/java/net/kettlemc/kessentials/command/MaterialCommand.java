package net.kettlemc.kessentials.command;

import io.github.almightysatan.slams.Placeholder;
import net.kettlemc.kessentials.Essentials;
import net.kettlemc.kessentials.config.Messages;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;

public class MaterialCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!Essentials.instance().checkPermission(sender, command, false)) {
            Essentials.instance().messages().sendMessage(sender, Messages.NO_PERMISSION);
            return true;
        }

        if (!(sender instanceof Player)) {
            Essentials.instance().messages().sendMessage(sender, Messages.PLAYER_ONLY);
            return true;
        }

        Player player = (Player) sender;

        ItemStack itemStack = player.getItemInHand();
        Material material = itemStack.getType();
        int damage = itemStack.getDurability();

        Essentials.instance().messages().sendMessage(player, Messages.MATERIAL, Placeholder.of("material", (p, ctx) -> material.name()), Placeholder.of("damage", (p, ctx) -> String.valueOf(damage)));

        return true;


    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return args.length <= 1 ? null : Collections.emptyList();
    }
}

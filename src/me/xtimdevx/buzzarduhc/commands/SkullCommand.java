package me.xtimdevx.buzzarduhc.commands;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * Created by xTimDevx on 7/07/2017.
 */
public class SkullCommand implements CommandExecutor{

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can spawn player heads.");
            return true;
        }

        Player player = (Player) sender;
        User user = User.get(player);

        if(user.getRank() != User.Rank.ADMIN && user.getRank() != User.Rank.OWNER && user.getRank() != User.Rank.BUILDER) {
            player.sendMessage(PrefixUtils.NO_PERM_MSG);
            return true;
        }
        if (args.length == 0) {
            player.sendMessage("§cUsage: /skull <name>");
            return true;
        }

        ItemStack item = new ItemStack (Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(args[0]);
        item.setItemMeta(meta);

        player.getInventory().addItem(item);

        player.sendMessage(PrefixUtils.PREFIX + "You've been given the head of §b" + args[0] + "§f.");
        return true;
    }
}

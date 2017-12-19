package me.xtimdevx.buzzarduhc.border;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Created by xTimDevx on 27/07/2017.
 */
public class CreateBorder {

    public static String PREFIX = "§8[§7BORDER§8]: §f";

    public static void generateFirstBorder(int size, Player host) {
        if(!host.isOp()) {
            host.sendMessage(PREFIX + "You need to be OP to create the border, please ask a senior if you are no OP.");
            return;
        }
        for(Player online : Bukkit.getOnlinePlayers()) {
            online.sendMessage(PREFIX + "Generating a border at §7" + size + "§fx" + "§7" + size + "§f! §8(§cExpect Lag!§8)");
        }
        //wall 1
        host.performCommand("/pos1 1500,200,1500");
        host.performCommand("/pos2 1500,1,-1500");
        host.performCommand("/replace log,log2,leaves,leaves2,flower,175,tallgrass,vine,snow 0");
        host.performCommand("/overlay 7");
        host.performCommand("/overlay 7");
        host.performCommand("/overlay 7");
        host.performCommand("/overlay 7");
        host.sendMessage(PREFIX + "Created §7Wall1§f!");
        //wall 2
        host.performCommand("/pos1 1500,200,-1500");
        host.performCommand("/pos2 -1500,1,-1500");
        host.performCommand("/replace log,log2,leaves,leaves2,flower,175,tallgrass,vine,snow 0");
        host.performCommand("/overlay 7");
        host.performCommand("/overlay 7");
        host.performCommand("/overlay 7");
        host.performCommand("/overlay 7");
        host.sendMessage(PREFIX + "Created §7Wall2§f!");
        //wall 3
        host.performCommand("/pos1 -1500,200,-1500");
        host.performCommand("/pos2 -1500,1,1500");
        host.performCommand("/replace log,log2,leaves,leaves2,flower,175,tallgrass,vine,snow 0");
        host.performCommand("/overlay 7");
        host.performCommand("/overlay 7");
        host.performCommand("/overlay 7");
        host.performCommand("/overlay 7");
        host.sendMessage(PREFIX + "Created §7Wall3§f!");
        //wall 4
        host.performCommand("/pos1 1500,200,1500");
        host.performCommand("/pos2 -1500,1,1500");
        host.performCommand("/replace log,log2,leaves,leaves2,flower,175,tallgrass,vine,snow 0");
        host.performCommand("/overlay 7");
        host.performCommand("/overlay 7");
        host.performCommand("/overlay 7");
        host.performCommand("/overlay 7");
        host.sendMessage(PREFIX + "Created §7Wall4§f!");
        //groundwalls
        host.performCommand("/pos1 0,0,0");
        host.performCommand("/pos2 0,0,0");
        host.performCommand("/expand 1500 N");
        host.performCommand("/expand 1500 S");
        host.performCommand("/expand 1500 W");
        host.performCommand("/expand 1500 E");
        host.performCommand("/expand 60 up");
        host.performCommand("/walls 7");
        host.sendMessage(PREFIX + "Created §7Groundborder§f!");
        Settings.getData().set("border.size", "1500x1500");
        Settings.getInstance().saveData();

    }

    public static void createBorder(int size, Player host) {
        if(!host.isOp()) {
            host.sendMessage(PREFIX + "You need to be OP to create the border, please ask a senior if you are no OP.");
            return;
        }
        //wall 1
        host.performCommand("/pos1 " + size + ",200," + size + "");
        host.performCommand("/pos2 " + size + ",1,-" + size + "");
        host.performCommand("/replace log,log2,leaves,leaves2,flower,175,tallgrass,vine,snow 0");
        host.performCommand("/overlay 7");
        host.performCommand("/overlay 7");
        host.performCommand("/overlay 7");
        host.performCommand("/overlay 7");
        //wall 2
        host.performCommand("/pos1 " + size + ",200,-" + size + "");
        host.performCommand("/pos2 -" + size + ",1,-" + size + "");
        host.performCommand("/replace log,log2,leaves,leaves2,flower,175,tallgrass,vine,snow 0");
        host.performCommand("/overlay 7");
        host.performCommand("/overlay 7");
        host.performCommand("/overlay 7");
        host.performCommand("/overlay 7");
        //wall 3
        host.performCommand("/pos1 -" + size + ",200,-" + size + "");
        host.performCommand("/pos2 -" + size + ",1," + size + "");
        host.performCommand("/replace log,log2,leaves,leaves2,flower,175,tallgrass,vine,snow 0");
        host.performCommand("/overlay 7");
        host.performCommand("/overlay 7");
        host.performCommand("/overlay 7");
        host.performCommand("/overlay 7");
        //wall 4
        host.performCommand("/pos1 " + size + ",200," + size + "");
        host.performCommand("/pos2 -" + size + ",1," + size + "");
        host.performCommand("/replace log,log2,leaves,leaves2,flower,175,tallgrass,vine,snow 0");
        host.performCommand("/overlay 7");
        host.performCommand("/overlay 7");
        host.performCommand("/overlay 7");
        host.performCommand("/overlay 7");
        Settings.getData().set("border.size", size + "x" + size);
        Settings.getInstance().saveData();
    }
}

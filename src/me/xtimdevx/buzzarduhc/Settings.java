package me.xtimdevx.buzzarduhc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * Created by xTimDevx on 22/04/2017.
 */
public class Settings {

    private static FileConfiguration data;

    private File dFile;

    private boolean creating = false;

    private Settings() {}
    private static Settings instance = new Settings();
    public static Settings getInstance() { return instance; }

    public void setup(Plugin p) {
        if(!p.getDataFolder().exists()) {
            p.getDataFolder().mkdir();
        }
        dFile = new File(p.getDataFolder(), "data.yml");

        if(!dFile.exists()) {
            try {
                dFile.createNewFile();
                creating = true;
            } catch (IOException ex) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create data.yml!");
            }
        }

        data = YamlConfiguration.loadConfiguration(dFile);

        if(creating) {
            data.set("CutClean", false);
        }
    }

    public static FileConfiguration getData() {
        return data;
    }

    public void saveData() {
        try {
            data.save(dFile);
        } catch (Exception ex) {
            Bukkit.getServer().getLogger().severe("Could not save date.yml!");
        }
    }

    public void reloadData() {
        data = YamlConfiguration.loadConfiguration(dFile);
    }
}

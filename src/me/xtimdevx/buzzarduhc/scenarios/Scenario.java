package me.xtimdevx.buzzarduhc.scenarios;

import me.xtimdevx.buzzarduhc.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

/**
 * Created by xTimDevx on 7/10/2017.
 */
public abstract class Scenario {
    private boolean enabled = false;

    private String name;
    private String desc;

    protected  Scenario(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return desc;
    }

    public void setEnabled(boolean enable) {
        enabled = enable;

        if(enable) {
            if(this instanceof Listener) {
                Bukkit.getPluginManager().registerEvents((Listener) this, Main.plugin);
            }
            onEnable();
        }else {
            if(this instanceof Listener) {
                HandlerList.unregisterAll((Listener) this);
            }
            onDisable();
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public abstract void onDisable();

    public abstract void onEnable();
}

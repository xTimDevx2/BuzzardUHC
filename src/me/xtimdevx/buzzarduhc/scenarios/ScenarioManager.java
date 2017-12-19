package me.xtimdevx.buzzarduhc.scenarios;

import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.scenarios.types.*;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by xTimDevx on 28/05/2017.
 */
public class ScenarioManager {
    private List<Scenario> scenarios = new ArrayList<Scenario>();
    private static ScenarioManager manager = new ScenarioManager();

    public static ScenarioManager getInstance() {
        return manager;
    }

    public void setup() {
        scenarios.add(new AchievementParanoia());
        scenarios.add(new BackPacks());
        scenarios.add(new Balance());
        scenarios.add(new BaldChicken());
        scenarios.add(new BiomeParanoia());
        scenarios.add(new Birds());
        scenarios.add(new Blocked());
        scenarios.add(new BloodBooks());
        scenarios.add(new BloodDiamonds());
        scenarios.add(new Bowless());
        scenarios.add(new CharityChump());
        scenarios.add(new CraftClean());
        scenarios.add(new CutClean());
        scenarios.add(new Diamondless());
        scenarios.add(new Dungeoneering());
        scenarios.add(new EnchantParanoia());
        scenarios.add(new FastSmelting());
        scenarios.add(new Fireless());
        scenarios.add(new Halloween());
        scenarios.add(new Hasteyboys());
        scenarios.add(new MeleeFun());
        scenarios.add(new Moles());
        scenarios.add(new NoFall());
        scenarios.add(new Paranoia());
        scenarios.add(new PeriodOfResistance());
        scenarios.add(new RewardingLongShots());
        scenarios.add(new RewardingLongShotsPlus());
        scenarios.add(new Rodless());
        scenarios.add(new SuperHeroes());
        scenarios.add(new SwitcherOres());
        scenarios.add(new SlaveMarket());
        scenarios.add(new Teaminventory());
        scenarios.add(new Timber());
        scenarios.add(new TimberPlus());
        scenarios.add(new Timebomb());
        scenarios.add(new TripleOres());
        scenarios.add(new VoidScape());
        scenarios.add(new WeakestLink());
        Main.plugin.getLogger().info("All scenarios have been setup.");
    }

    public Scenario getScenario(String name) {
        for(Scenario s : scenarios) {
            if(s.getName().equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }

    public <T> T getScenario(Class<T> scenarioClass) {
        for(Scenario s : scenarios) {
            if(s.getClass().equals(scenarioClass)) {
                return (T) s;
            }
        }
        return null;
    }

    public List<Scenario> getScenarios() {
        return new ArrayList<Scenario>(scenarios);
    }

    public List<Scenario> getEnabledScenarios() {
        List<Scenario> list = new ArrayList<Scenario>();

        for(Scenario s : scenarios) {
            if(s.isEnabled()) {
                list.add(s);
            }
        }
        return list;
    }

    public List<Scenario> getDisabledScenarios() {
        List<Scenario> list = new ArrayList<Scenario>();

        for(Scenario s : scenarios) {
            if(!s.isEnabled()) {
                list.add(s);
            }
        }
        return list;
    }
}

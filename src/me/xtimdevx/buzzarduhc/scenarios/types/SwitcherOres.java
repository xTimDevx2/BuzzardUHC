package me.xtimdevx.buzzarduhc.scenarios.types;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.scenarios.Scenario;
import me.xtimdevx.buzzarduhc.scenarios.ScenarioManager;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * Created by xTimDevx on 4/06/2017.
 */
public class SwitcherOres extends Scenario implements Listener, CommandExecutor{

    public SwitcherOres() {
        super("Switcherores", "All ores are switched arround. Use /so for the list of switched ores.");
    }

    @Override
    public void onDisable() {}

    @Override
    public void onEnable() {}

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        Entity en = e.getEntity();
        if (en.getType() == EntityType.CHICKEN) {
            for (ItemStack drops : e.getDrops()) {
                if (drops.getType() == Material.RAW_CHICKEN) {
                    drops.setType(Material.COOKED_CHICKEN);
                }
            }
        }
        if (en.getType() == EntityType.COW) {
            for (ItemStack drops : e.getDrops()) {
                if (drops.getType() == Material.RAW_BEEF) {
                    drops.setType(Material.COOKED_BEEF);
                }
            }
        }
        if (en.getType() == EntityType.SHEEP) {
            for (ItemStack drops : e.getDrops()) {
                if (drops.getType() == Material.MUTTON) {
                    drops.setType(Material.COOKED_MUTTON);
                }
                if (drops.getType() == Material.WOOL) {
                    drops.setType(Material.AIR);
                }
            }
        }
        if (en.getType() == EntityType.PIG) {
            for (ItemStack drops : e.getDrops()) {
                if (drops.getType() == Material.PORK) {
                    drops.setType(Material.GRILLED_PORK);
                }
            }
        }
        if (en.getType() == EntityType.CHICKEN) {
            for (ItemStack drops : e.getDrops()) {
                if (drops.getType() == Material.RAW_CHICKEN) {
                    drops.setType(Material.COOKED_CHICKEN);
                }
            }
        }
        if (en.getType() == EntityType.RABBIT) {
            for (ItemStack drops : e.getDrops()) {
                if (drops.getType() == Material.RABBIT) {
                    drops.setType(Material.COOKED_RABBIT);
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (!e.getBlock().getWorld().equals(Bukkit.getWorld("UHC"))) {
            return;
        }
        if (e.isCancelled()) {
            return;
        }
        if (e.getPlayer().getGameMode() != GameMode.SURVIVAL) {
            return;
        }
        Block b = e.getBlock();
        Location clone = new Location(b.getWorld(),
                b.getLocation().getBlockX() + 0.5D, b.getLocation().getBlockY(),
                b.getLocation().getBlockZ() + 0.5D);
        if (b.getType() == Material.COAL_ORE) {
            b.setType(Material.AIR);
            b.getState().update();
            b.getWorld().dropItemNaturally(clone, new ItemStack(Material.IRON_INGOT));
            ((ExperienceOrb) b.getWorld().spawn(clone, ExperienceOrb.class)).setExperience(1);
        }
        if (b.getType() == Material.IRON_ORE) {
            b.setType(Material.AIR);
            b.getState().update();
            ((ExperienceOrb) b.getWorld().spawn(clone, ExperienceOrb.class)).setExperience(3);
            b.getWorld().dropItemNaturally(clone, new ItemStack(Material.COAL));
        }
        if (b.getType() == Material.GOLD_ORE) {
            b.setType(Material.AIR);
            b.getState().update();
            ((ExperienceOrb) b.getWorld().spawn(clone, ExperienceOrb.class)).setExperience(3);
            b.getWorld().dropItemNaturally(clone, new ItemStack(Material.INK_SACK, 3, (short) 4));
        }
        if (b.getType() == Material.LAPIS_ORE) {
            b.setType(Material.AIR);
            b.getState().update();
            ((ExperienceOrb) b.getWorld().spawn(clone, ExperienceOrb.class)).setExperience(3);
            b.getWorld().dropItemNaturally(clone, new ItemStack(Material.GOLD_INGOT));
        }
        if (b.getType() == Material.DIAMOND_ORE) {
            b.setType(Material.AIR);
            b.getState().update();
            ((ExperienceOrb) b.getWorld().spawn(clone, ExperienceOrb.class)).setExperience(3);
            b.getWorld().dropItemNaturally(clone, new ItemStack(Material.REDSTONE));
        }
        if (b.getType() == Material.REDSTONE_ORE || b.getType() == Material.GLOWING_REDSTONE_ORE) {
            Random random = new Random();
            int r = random.nextInt(6);
            if(r == 0) {
                b.setType(Material.AIR);
                b.getState().update();
                ((ExperienceOrb) b.getWorld().spawn(clone, ExperienceOrb.class)).setExperience(3);
                b.getWorld().dropItemNaturally(clone, new ItemStack(Material.REDSTONE));
                e.getPlayer().sendMessage("Random ore: §CREDSTONE");
            }
            if(r == 1) {
                b.setType(Material.AIR);
                b.getState().update();
                ((ExperienceOrb) b.getWorld().spawn(clone, ExperienceOrb.class)).setExperience(3);
                b.getWorld().dropItemNaturally(clone, new ItemStack(Material.COAL));
                e.getPlayer().sendMessage("Random ore: §CCOAL");
            }
            if(r == 2) {
                b.setType(Material.AIR);
                b.getState().update();
                ((ExperienceOrb) b.getWorld().spawn(clone, ExperienceOrb.class)).setExperience(3);
                b.getWorld().dropItemNaturally(clone, new ItemStack(Material.IRON_INGOT));
                e.getPlayer().sendMessage("Random ore: §CIRON");
            }
            if(r == 3) {
                b.setType(Material.AIR);
                b.getState().update();
                ((ExperienceOrb) b.getWorld().spawn(clone, ExperienceOrb.class)).setExperience(3);
                b.getWorld().dropItemNaturally(clone, new ItemStack(Material.GOLD_INGOT));
                e.getPlayer().sendMessage("Random ore: §CGOLD");
            }
            if(r == 4) {
                b.setType(Material.AIR);
                b.getState().update();
                ((ExperienceOrb) b.getWorld().spawn(clone, ExperienceOrb.class)).setExperience(3);
                b.getWorld().dropItemNaturally(clone, new ItemStack(Material.DIAMOND));
                e.getPlayer().sendMessage("Random ore: §CDIAMOND");
            }
            if(r == 5) {
                b.setType(Material.AIR);
                b.getState().update();
                ((ExperienceOrb) b.getWorld().spawn(clone, ExperienceOrb.class)).setExperience(3);
                b.getWorld().dropItemNaturally(clone, new ItemStack(Material.INK_SACK, 3, (short) 4));
                e.getPlayer().sendMessage("Random ore: §CLAPIS");
            }
        }
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("switcherores")) {
            Player player = (Player) sender;
            User user = User.get(player);
            if (!ScenarioManager.getInstance().getScenario("switcherores").isEnabled()) {
                player.sendMessage(PrefixUtils.PREFIX + "§5Switcherores §fis not enabled.");
                return true;
            }
            if(args.length == 0) {
                player.sendMessage(PrefixUtils.PREFIX + "Switcher ores:");
                player.sendMessage("§8» §fCoal §8-> §fIron");
                player.sendMessage("§8» §fIron §8-> §fCoal");
                player.sendMessage("§8» §fGold §8-> §fLapis");
                player.sendMessage("§8» §fLapis §8-> §fGold");
                player.sendMessage("§8» §fDiamonds §8-> §fRedstone");
                player.sendMessage("§8» §fRedstone §8-> §fRandom ore");
                player.sendMessage("§8» §fThe only way to obtain §cdiamonds §fis to get it as a §crandom §fdrop from §credstone§f.");
            }
        }
        return true;
    }
}

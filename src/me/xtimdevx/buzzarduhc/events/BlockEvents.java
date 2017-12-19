package me.xtimdevx.buzzarduhc.events;

import comp.xtimdevx.buzzardhavex.User;
import me.xtimdevx.buzzarduhc.Main;
import me.xtimdevx.buzzarduhc.Settings;
import me.xtimdevx.buzzarduhc.utils.BlockUtils;
import me.xtimdevx.buzzarduhc.utils.EntityUtils;
import me.xtimdevx.buzzarduhc.utils.PrefixUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

/**
 * Created by xTimDevx on 20/05/2017.
 */
public class BlockEvents implements Listener{

    @EventHandler
    public void onLeavesDecay(LeavesDecayEvent event) {
        Block block = event.getBlock();

        if (!block.getWorld().getName().equals("UHC")) {
            event.setCancelled(true);
            return;
        }
        if (Settings.getData().get("rates.apple").equals("Vanilla")) {
            return;
        }
        if (block.getType() != Material.LEAVES && block.getType() != Material.LEAVES_2) {
            return;
        }

        Location loc = block.getLocation();
        Material type = block.getType();

        short damage = block.getState().getData().toItemStack().getDurability();
        Random rand = new Random();

        event.setCancelled(true);
        block.setType(Material.AIR);
        block.getState().update(true);
        if (type == Material.LEAVES) {
            if (rand.nextInt(99) < 5) {
                Item item = block.getWorld().dropItem(loc.clone().add(0.5, 0.7, 0.5), BlockUtils.getSaplingFor(Material.LEAVES, damage));
                item.setVelocity(EntityUtils.randomOffset());
            }

            if (damage != 0 && damage != 4 && damage != 8 && damage != 12) {
                return;
            }

            if (rand.nextInt(99) >= Main.applerate) {
                return;
            }

            Item item = block.getWorld().dropItem(loc.clone().add(0.5, 0.7, 0.5), new ItemStack(Material.APPLE, 1));
            item.setVelocity(EntityUtils.randomOffset());
            return;
        }
        if (type == Material.LEAVES_2) {
            if (rand.nextInt(99) < 5) {
                Item item = block.getWorld().dropItem(loc.clone().add(0.5, 0.7, 0.5), BlockUtils.getSaplingFor(Material.LEAVES_2, damage));
                item.setVelocity(EntityUtils.randomOffset());
            }

            if (damage != 1 && damage != 5 && damage != 9 && damage != 13) {
                return;
            }

            if (rand.nextInt(99) >= Main.applerate) {
                return;
            }

            Item item = block.getWorld().dropItem(loc.clone().add(0.5, 0.7, 0.5), new ItemStack(Material.APPLE, 1));
            item.setVelocity(EntityUtils.randomOffset());
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        User user = User.get(player);
        if(user.getRank() != User.Rank.OWNER && user.getRank() != User.Rank.ADMIN && user.getRank() != User.Rank.BUILDER) {
            if(player.getWorld().getName().equalsIgnoreCase("HUB")) {
                event.setCancelled(true);
                player.sendMessage(PrefixUtils.PREFIX + "You are not allowed to break blocks here.");
                return;
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        User user = User.get(player);
        if(user.getRank() != User.Rank.OWNER && user.getRank() != User.Rank.ADMIN && user.getRank() != User.Rank.BUILDER) {
            if(player.getWorld().getName().equalsIgnoreCase("HUB")) {
                event.setCancelled(true);
                player.sendMessage(PrefixUtils.PREFIX + "You are not allowed to place blocks here.");
                return;
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (!block.getWorld().getName().equals("UHC")) {
            return;
        }

        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        Random rand = new Random();

        if (block.getType() == Material.GRAVEL) {
            if(Settings.getData().get("rates.flint").equals("Vanilla")) {
                return;
            }
            if (rand.nextInt(99) < Main.flintrate) {
                Main.toReplace.put(Material.GRAVEL, new ItemStack (Material.FLINT));
            } else {
                Main.toReplace.put(Material.FLINT, new ItemStack (Material.GRAVEL));
            }
            return;
        }

        if (block.getType() != Material.LEAVES && block.getType() != Material.LEAVES_2) {
            return;
        }

        short damage = block.getState().getData().toItemStack().getDurability();

        if (block.getType() == Material.LEAVES) {
            if(Settings.getData().get("rates.apple").equals("Vanilla")) {
                return;
            }
            if (rand.nextInt(99) < 5) {
                Item item = block.getWorld().dropItem(block.getLocation().add(0.5, 0.7, 0.5), BlockUtils.getSaplingFor(Material.LEAVES, damage));
                item.setVelocity(EntityUtils.randomOffset());
            }

            if (damage != 0 && damage != 4 && damage != 8 && damage != 12) {
                return;
            }

            ItemStack hand = player.getItemInHand();
            if(Settings.getData().getBoolean("rates.shears") == false) {
                return;
            }
            if (Settings.getData().getBoolean("rates.shears") == true && hand != null && hand.getType() == Material.SHEARS) {
                if (rand.nextInt(99) >= Main.applerate) {
                    return;
                }

                Item item = block.getWorld().dropItem(block.getLocation().add(0.5, 0.7, 0.5), new ItemStack(Material.APPLE, 1));
                item.setVelocity(EntityUtils.randomOffset());
            } else {
                if (rand.nextInt(99) >= Main.applerate) {
                    return;
                }

                Item item = block.getWorld().dropItem(block.getLocation().add(0.5, 0.7, 0.5), new ItemStack(Material.APPLE, 1));
                item.setVelocity(EntityUtils.randomOffset());
            }
            return;
        }

        if (block.getType() == Material.LEAVES_2) {
            if (rand.nextInt(99) < 5) {
                Item item = block.getWorld().dropItem(block.getLocation().add(0.5, 0.7, 0.5), BlockUtils.getSaplingFor(Material.LEAVES_2, damage));
                item.setVelocity(EntityUtils.randomOffset());
            }

            if (damage != 1 && damage != 5 && damage != 9 && damage != 13) {
                return;
            }

            ItemStack hand = player.getItemInHand();

            if (Settings.getData().getBoolean("rates.shears") == true && hand != null && hand.getType() == Material.SHEARS) {
                if (rand.nextInt(99) >= Main.applerate) {
                    return;
                }

                Item item = block.getWorld().dropItem(block.getLocation().add(0.5, 0.7, 0.5), new ItemStack(Material.APPLE, 1));
                item.setVelocity(EntityUtils.randomOffset());
            } else {
                if (rand.nextInt(99) >= Main.applerate) {
                    return;
                }

                Item item = block.getWorld().dropItem(block.getLocation().add(0.5, 0.7, 0.5), new ItemStack(Material.APPLE, 1));
                item.setVelocity(EntityUtils.randomOffset());
            }
        }
    }
}

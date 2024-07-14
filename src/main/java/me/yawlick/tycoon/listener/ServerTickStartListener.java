package me.yawlick.tycoon.listener;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import me.yawlick.tycoon.LoadModule;
import me.yawlick.tycoon.RandomTycoon;
import me.yawlick.tycoon.item.ItemHandler;
import me.yawlick.tycoon.item.upgrader.list.FirstUpgrader;
import me.yawlick.tycoon.util.IPaper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import java.util.UUID;

public class ServerTickStartListener implements Listener, IPaper {
    @EventHandler
    void onServerTick(ServerTickStartEvent event) {
        ItemHandler itemHandler = RandomTycoon.INSTANCE.loadModule.getItemHandler();
        for(Player player : Bukkit.getOnlinePlayers()) {
            for(UUID itemUuid : getTycoon(player.getUniqueId()).getItems()) {
                if(Bukkit.getEntity(itemUuid) == null) {
                    getTycoon(player.getUniqueId()).removeItem(itemUuid);
                    return;
                }
                Item item = (Item) Bukkit.getEntity(itemUuid);
                Block block = item.getLocation().toBlockLocation().add(0.0, -1.0, 0.0).getBlock();
                Block blockIn = item.getLocation().toBlockLocation().getBlock();
                if(block.getType() == Material.MAGENTA_GLAZED_TERRACOTTA) {
                    if(block.getBlockData() instanceof Directional) {
                        Directional blockData = (Directional) block.getBlockData();
                        BlockFace facing = blockData.getFacing();
                        if(facing == BlockFace.EAST) { // -X
                            Bukkit.getScheduler().scheduleSyncDelayedTask(RandomTycoon.INSTANCE, new Runnable() {
                                @Override
                                public void run() {
                                    item.setVelocity(new Vector(-0.055,0,0));
                                }
                            }, 9);
                        } else if (facing == BlockFace.SOUTH) { // -Z
                            Bukkit.getScheduler().scheduleSyncDelayedTask(RandomTycoon.INSTANCE, new Runnable() {
                                @Override
                                public void run() {
                                    item.setVelocity(new Vector(0,0,-0.055));
                                }
                            }, 9);
                        } else if (facing == BlockFace.WEST) { // +X
                            Bukkit.getScheduler().scheduleSyncDelayedTask(RandomTycoon.INSTANCE, new Runnable() {
                                @Override
                                public void run() {
                                    item.setVelocity(new Vector(0.055,0,0));
                                }
                            }, 9);
                        } else if (facing == BlockFace.NORTH) { // +Z
                            Bukkit.getScheduler().scheduleSyncDelayedTask(RandomTycoon.INSTANCE, new Runnable() {
                                @Override
                                public void run() {
                                    item.setVelocity(new Vector(0,0,0.055));
                                }
                            }, 9);
                        }
                    }
                } else if(block.getType() == Material.LIME_WOOL) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(RandomTycoon.INSTANCE, new Runnable() {
                        @Override
                        public void run() {
                            for(Player player : Bukkit.getOnlinePlayers()) {
                                if(getTycoon(player.getUniqueId()).getItems().contains(itemUuid)) {
                                    PersistentDataContainer data = item.getPersistentDataContainer();
                                    String dropperName = data.get(NamespacedKey.fromString("cube.dropper"), PersistentDataType.STRING);
                                    long value = data.get(NamespacedKey.fromString("cube.value"), PersistentDataType.LONG);
                                    msg(player, "+" + value + "$ §6от " + itemHandler.getDropper(dropperName).cubeName);
                                    getTycoon(player.getUniqueId()).removeItem(itemUuid);
                                    item.remove();
                                }
                            }
                        }
                    }, 3);
                }
                if(blockIn.getType() == Material.SUGAR_CANE) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(RandomTycoon.INSTANCE, new Runnable() {
                        @Override
                        public void run() {
                            PersistentDataContainer data = item.getPersistentDataContainer();
                            if(
                                    data.has(NamespacedKey.fromString("x:" + blockIn.getX()), PersistentDataType.INTEGER) &&
                                    data.has(NamespacedKey.fromString("y:" + blockIn.getY()), PersistentDataType.INTEGER) &&
                                    data.has(NamespacedKey.fromString("z:" + blockIn.getZ()), PersistentDataType.INTEGER)
                            ) {
                                return;
                            }
                            String dropperName = data.get(NamespacedKey.fromString("cube.dropper"), PersistentDataType.STRING);
                            long newValue = data.get(NamespacedKey.fromString("cube.value"), PersistentDataType.LONG) * itemHandler.firstUpgrader.boost;
                            data.set(NamespacedKey.fromString("cube.value"), PersistentDataType.LONG, newValue);
                            item.setCustomName("§6" + itemHandler.getDropper(dropperName).cubeName + " §a" + newValue + "$");
                            data.set(NamespacedKey.fromString("x:" + blockIn.getX()), PersistentDataType.INTEGER, 1);
                            data.set(NamespacedKey.fromString("y:" + blockIn.getY()), PersistentDataType.INTEGER, 1);
                            data.set(NamespacedKey.fromString("z:" + blockIn.getZ()), PersistentDataType.INTEGER, 1);
                        }
                    }, 5);
                }
            }
        }
    }
}

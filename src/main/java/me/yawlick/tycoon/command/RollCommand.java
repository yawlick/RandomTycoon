package me.yawlick.tycoon.command;

import me.yawlick.tycoon.LoadModule;
import me.yawlick.tycoon.RandomTycoon;
import me.yawlick.tycoon.item.ItemHandler;
import me.yawlick.tycoon.item.ItemType;
import me.yawlick.tycoon.item.AbstractItem;
import me.yawlick.tycoon.item.dropper.Dropper;
import me.yawlick.tycoon.item.seller.Seller;
import me.yawlick.tycoon.item.upgrader.Upgrader;
import me.yawlick.tycoon.util.IPaper;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class RollCommand implements CommandExecutor, IPaper {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(commandSender instanceof Player))
            return false;
        Player player = (Player) commandSender;
        Random random = new Random("btwkgay".getBytes().length * System.currentTimeMillis());
        HashMap<AbstractItem, Float> map = getHashMap();
        getData(player.getUniqueId()).setRolls(getData(player.getUniqueId()).getRolls() + 1);
        for(int i = 0; i < 5; i++) {
            int i2 = i;
            Bukkit.getScheduler().scheduleSyncDelayedTask(RandomTycoon.INSTANCE, new Runnable() {
                @Override
                public void run() {
                    AbstractItem item = getRandomItem(map, random);
                    if (item != null) {
                        if(i2 != 4) {
                            player.sendTitle("§6" + item.name, "§7" + item.type.name + " (" + getString(item.chance) + ")");
                            playSound(player, Sound.BLOCK_COMPARATOR_CLICK);
                        } else {
                            player.sendTitle("§6" + item.name, "§7" + item.type.name + " (" + getString(item.chance) + ")");
                            playSound(player, Sound.ENTITY_VILLAGER_TRADE);
                            if(item.type == ItemType.DROPPER) {
                                getData(player.getUniqueId()).addItem(((Dropper) item).copy());
                            }
                            if(item.type == ItemType.SELLER) {
                                getData(player.getUniqueId()).addItem(((Seller) item).copy());
                            }
                            if(item.type == ItemType.UPGRADER) {
                                getData(player.getUniqueId()).addItem(((Upgrader) item).copy());
                            }
                        }
                    }
                }
            }, 10L * i);
        }

        return true;
    }

    public HashMap<AbstractItem, Float> getHashMap() {
        ItemHandler items = RandomTycoon.INSTANCE.getLoadModule().getItemHandler();
        HashMap<AbstractItem, Float> map = new HashMap();

        for(AbstractItem item : items.getAllItems()) {
            map.put(item, item.chance);
        }

        return map;
    }

    public String getString(Float chance) {
        return "1 на " + (int) (100 / chance);
    }

    public AbstractItem getRandomItem(HashMap<AbstractItem, Float> map, Random random) {
        float totalChance = 0;
        for (Float chance : map.values()) {
            totalChance += chance;
        }

        float randomValue = random.nextFloat() * totalChance;

        for (Map.Entry<AbstractItem, Float> entry : map.entrySet()) {
            randomValue -= entry.getValue();
            if (randomValue <= 0) {
                return entry.getKey();
            }
        }

        return null;
    }
}

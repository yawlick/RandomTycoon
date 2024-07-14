package me.yawlick.tycoon.listener;

import me.yawlick.tycoon.RandomTycoon;
import me.yawlick.tycoon.item.AbstractItem;
import me.yawlick.tycoon.tycoon.TycoonHandler;
import me.yawlick.tycoon.util.IPaper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.UUID;

public class PlayerJoinListener implements Listener, IPaper {
    @EventHandler
    void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(!RandomTycoon.DATASTORE.containsKey(player.getUniqueId()))
            RandomTycoon.DATASTORE.put(player.getUniqueId(), new TycoonHandler(
                    player.getUniqueId()
            ));
        playSound(player, Sound.BLOCK_CAMPFIRE_CRACKLE, 5);
        ItemStack itemStack = new ItemStack(Material.IRON_BARS);
        writeNBT(itemStack, "item.settings", "true");
        renameItem(itemStack, "§6§lНастройки");
        player.getInventory().setItem(8, itemStack);
    }

    @EventHandler
    void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        for(UUID uuid : getTycoon(player.getUniqueId()).getItems()) {
            Objects.requireNonNull(Bukkit.getEntity(uuid)).remove();
        }
        for(AbstractItem item : getTycoon(player.getUniqueId()).getData().getItems()) {
            if(item.placed) {
                item.stand.remove();
                Bukkit.getWorld("world").getBlockAt(item.location).setType(Material.LIGHT_GRAY_STAINED_GLASS);
            }
        }
    }

    @EventHandler
    void onPlayerKick(PlayerKickEvent event) {
        Player player = event.getPlayer();
        for(UUID uuid : getTycoon(player.getUniqueId()).getItems()) {
            Objects.requireNonNull(Bukkit.getEntity(uuid)).remove();
        }
        for(AbstractItem item : getTycoon(player.getUniqueId()).getData().getItems()) {
            if(item.placed) {
                item.stand.remove();
                Bukkit.getWorld("world").getBlockAt(item.location).setType(Material.LIGHT_GRAY_STAINED_GLASS);
            }
        }
    }
}

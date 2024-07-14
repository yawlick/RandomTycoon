package me.yawlick.tycoon;

import me.yawlick.tycoon.command.PlaceCommand;
import me.yawlick.tycoon.command.RollCommand;
import me.yawlick.tycoon.item.ItemHandler;
import me.yawlick.tycoon.listener.*;
import me.yawlick.tycoon.util.IPaper;
import org.bukkit.Bukkit;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class LoadModule implements IPaper {
    public static ItemHandler itemHandler;

    public void load() {
        itemHandler = new ItemHandler();

        RandomTycoon.INSTANCE.registerListeners(
                new PlayerJoinListener(),
                new ServerTickStartListener(),
                new CancelledEventsListener(),
                new BlockRightClickListener(),
                new InventoryItemClickListener()
        );

        Objects.requireNonNull(Bukkit.getServer().getPluginCommand("place")).setExecutor(new PlaceCommand());
        Objects.requireNonNull(Bukkit.getServer().getPluginCommand("roll")).setExecutor(new RollCommand());
    }

    public void unload() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            for(UUID item : getTycoon(player.getUniqueId()).getItems()) {
                Bukkit.getEntity(item).remove();
            }
        }
        for(ArmorStand stand : Bukkit.getWorld("world").getEntitiesByClass(ArmorStand.class)) {
            stand.remove();
        }
    }
}

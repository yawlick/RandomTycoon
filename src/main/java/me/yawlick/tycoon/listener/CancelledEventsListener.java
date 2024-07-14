package me.yawlick.tycoon.listener;

import me.yawlick.tycoon.util.IPaper;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class CancelledEventsListener implements Listener, IPaper {
    @EventHandler
    void onBlockPhysics(BlockPhysicsEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    void onSugarCaneGrow(BlockGrowEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    void onBlockBreak(BlockBreakEvent event) {
        if(event.getPlayer().getGameMode() != GameMode.CREATIVE)
            event.setCancelled(true);
    }

    @EventHandler
    void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    void onDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    void onLeavesDecay(LeavesDecayEvent event) {
        event.setCancelled(true);
    }
}

package me.yawlick.tycoon.listener;

import me.yawlick.tycoon.util.IPaper;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockRightClickListener implements Listener, IPaper {
    @EventHandler
    void onBlockInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK ||
            event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if(event.getClickedBlock().getType() == Material.LIGHT_GRAY_STAINED_GLASS) {
                Block under = event.getClickedBlock().getWorld().getBlockAt(event.getClickedBlock().getLocation().add(0.0, -1.0, 0.0));
                if(under.getType() == Material.AIR) {
                    openInv(event.getPlayer(), getTycoon(event.getPlayer().getUniqueId()).getDroppersInventory());
                    getTycoon(event.getPlayer().getUniqueId()).setLastClickedBlock(event.getClickedBlock());
                }
                if(under.getType() == Material.LIME_SHULKER_BOX) {
                    openInv(event.getPlayer(), getTycoon(event.getPlayer().getUniqueId()).getSellersInventory());
                    getTycoon(event.getPlayer().getUniqueId()).setLastClickedBlock(event.getClickedBlock());
                }
                if(under.getType() == Material.MAGENTA_GLAZED_TERRACOTTA) {
                    openInv(event.getPlayer(), getTycoon(event.getPlayer().getUniqueId()).getUpgradersInventory());
                    getTycoon(event.getPlayer().getUniqueId()).setLastClickedBlock(event.getClickedBlock());
                }
            }
        }
    }
}

package me.yawlick.tycoon.listener;

import me.yawlick.tycoon.tycoon.TycoonHandler;
import me.yawlick.tycoon.util.IPaper;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

public class BlockRightClickListener implements Listener, IPaper {
    @EventHandler
    void onBlockInteract(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_BLOCK && action != Action.LEFT_CLICK_BLOCK) return;

        Block clickedBlock = event.getClickedBlock();
        if (clickedBlock.getType() != Material.LIGHT_GRAY_STAINED_GLASS) return;

        Block under = clickedBlock.getWorld().getBlockAt(clickedBlock.getLocation().add(0.0, -1.0, 0.0));
        Material material = under.getType();
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        TycoonHandler tycoon = getTycoon(uuid);
        if (material == Material.AIR) {
            openInv(player, tycoon.getDroppersInventory());
            tycoon.setLastClickedBlock(clickedBlock);
        } else if (material == Material.LIME_SHULKER_BOX) {
            openInv(player, tycoon.getSellersInventory());
            tycoon.setLastClickedBlock(clickedBlock);
        } else if (material == Material.MAGENTA_GLAZED_TERRACOTTA) {
            openInv(player, tycoon.getUpgradersInventory());
        }
    }
}

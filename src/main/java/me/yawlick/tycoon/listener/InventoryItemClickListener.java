package me.yawlick.tycoon.listener;

import me.yawlick.tycoon.LoadModule;
import me.yawlick.tycoon.RandomTycoon;
import me.yawlick.tycoon.item.ItemHandler;
import me.yawlick.tycoon.item.ItemType;
import me.yawlick.tycoon.tycoon.TycoonHandler;
import me.yawlick.tycoon.util.BlockPos;
import me.yawlick.tycoon.util.IPaper;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class InventoryItemClickListener implements Listener, IPaper {
    @EventHandler
    void onItemClick(InventoryClickEvent event) {
        if(event.getInventory().getSize() == 9) {
            ItemStack item = event.getCurrentItem();
            ItemType type = getItemType(readNBT(item, "item.type", new String()));
            String name = readNBT(item, "item.name", new String());
            TycoonHandler tycoonHandler = getTycoon(event.getWhoClicked().getUniqueId());
            BlockPos blockPos = new BlockPos(tycoonHandler.getLastClickedBlock().getX(), tycoonHandler.getLastClickedBlock().getY(), tycoonHandler.getLastClickedBlock().getZ());
            ItemHandler itemHandler = RandomTycoon.INSTANCE.loadModule.getItemHandler();
            if(item.getAmount() > 1) {
                item.setAmount(item.getAmount() - 1);
            } else {
                ItemStack air = new ItemStack(Material.AIR);
                item.setType(Material.AIR);
                item.setItemMeta(air.getItemMeta());
            }
            if(type == ItemType.DROPPER) {
                tycoonHandler.placeDropper(
                        itemHandler.getDropper(name),
                        blockPos
                );
            }
            if(type == ItemType.SELLER) {
                tycoonHandler.placeSeller(
                        itemHandler.getSeller(name),
                        blockPos
                );
            }
            if(type == ItemType.UPGRADER) {
                tycoonHandler.placeUpgrader(
                        itemHandler.getUpgrader(name),
                        blockPos
                );
            }
            event.getClickedInventory().close();
            event.setCancelled(true);
        }
    }

    public ItemType getItemType(String name) {
        for(ItemType type : ItemType.values()) {
            if(type.name.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}

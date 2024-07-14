package me.yawlick.tycoon.listener;

import me.yawlick.tycoon.LoadModule;
import me.yawlick.tycoon.item.ItemType;
import me.yawlick.tycoon.item.dropper.Dropper;
import me.yawlick.tycoon.item.seller.Seller;
import me.yawlick.tycoon.item.upgrader.Upgrader;
import me.yawlick.tycoon.player.TycoonHandler;
import me.yawlick.tycoon.util.BlockPos;
import me.yawlick.tycoon.util.IPaper;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class InventoryItemClickListener implements Listener, IPaper {
    @EventHandler
    void onItemClick(InventoryClickEvent event) {
        if(event.getInventory().getSize() == 9) {
            ItemStack item = event.getCurrentItem();
            PersistentDataContainer data = item.getItemMeta().getPersistentDataContainer();
            ItemType type = getItemType(data.get(NamespacedKey.fromString("item.type"), PersistentDataType.STRING));
            String name = data.get(NamespacedKey.fromString("item.name"), PersistentDataType.STRING);
            TycoonHandler tycoonHandler = getTycoon(event.getWhoClicked().getUniqueId());
            if(item.getAmount() > 1) {
                item.setAmount(item.getAmount() - 1);
            } else {
                ItemStack air = new ItemStack(Material.AIR);
                item.setType(Material.AIR);
                item.setItemMeta(air.getItemMeta());
            }
            if(type == ItemType.DROPPER) {
                tycoonHandler.placeDropper(
                        LoadModule.itemHandler.getDropper(name),
                        new BlockPos(tycoonHandler.getLastClickedBlock().getX(), tycoonHandler.getLastClickedBlock().getY(), tycoonHandler.getLastClickedBlock().getZ())
                );
            }
            if(type == ItemType.SELLER) {
                tycoonHandler.placeSeller(
                        LoadModule.itemHandler.getSeller(name),
                        new BlockPos(tycoonHandler.getLastClickedBlock().getX(), tycoonHandler.getLastClickedBlock().getY(), tycoonHandler.getLastClickedBlock().getZ())
                );
            }
            if(type == ItemType.UPGRADER) {
                tycoonHandler.placeUpgrader(
                        LoadModule.itemHandler.getUpgrader(name),
                        new BlockPos(tycoonHandler.getLastClickedBlock().getX(), tycoonHandler.getLastClickedBlock().getY(), tycoonHandler.getLastClickedBlock().getZ())
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

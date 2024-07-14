package me.yawlick.tycoon.tycoon.player;

import me.yawlick.tycoon.item.AbstractItem;
import me.yawlick.tycoon.item.ItemType;
import me.yawlick.tycoon.util.IPaper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerData implements IPaper {
    protected UUID uuid;
    protected long cash;
    protected int rolls;
    protected int level;
    protected ArrayList<AbstractItem> items = new ArrayList();

    public PlayerData(UUID uuid, long cash, int rolls) {
        this.uuid = uuid;
        this.cash = cash;
        this.rolls = rolls;
    }

    // Setters & Getters
    public int getRolls() {
        return rolls;
    }

    public long getCash() {
        return cash;
    }

    public void setRolls(int rolls) {
        this.rolls = rolls;
    }

    public void setCash(long cash) {
        this.cash = cash;
    }

    public ArrayList<AbstractItem> getItems() {
        return items;
    }

    public void addItem(AbstractItem abstractItem) {
        for(AbstractItem item : items) {
            if(item.uuid.equals(abstractItem.uuid)) {
                debug("Can not add item to data because uuid equals! " + abstractItem.uuid);
                return;
            }
        }
        ArrayList<String> lore = new ArrayList<>();
        lore.add(" ");
        lore.add("§7Описание предмета");
        lore.add(" ");
        lore.add("§7§lШанс выпадения: §r§7" + getString(abstractItem.chance));
        lore.add("§7§l" + abstractItem.type.name);
        ItemStack itemStack = new ItemStack(abstractItem.material, 1);
        ItemMeta meta = itemStack.getItemMeta();
        writeNBT(itemStack, "item.type", abstractItem.type.name);
        writeNBT(itemStack, "item.name", abstractItem.name);
        writeNBT(itemStack, "item.uuid", abstractItem.uuid.toString());
        meta.setDisplayName("§6§l" + abstractItem.name);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        if(abstractItem.type == ItemType.DROPPER) {
            getTycoon(uuid).getDroppersInventory().addItem(itemStack);
        }
        if(abstractItem.type == ItemType.SELLER) {
            getTycoon(uuid).getSellersInventory().addItem(itemStack);
        }
        if(abstractItem.type == ItemType.UPGRADER) {
            getTycoon(uuid).getUpgradersInventory().addItem(itemStack);
        }
        items.add(abstractItem);
    }

    public String getString(Float chance) {
        return "1 на " + (int) (100 / chance);
    }
}

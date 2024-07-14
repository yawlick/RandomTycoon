package me.yawlick.tycoon.item.seller;

import me.yawlick.tycoon.item.ItemType;
import me.yawlick.tycoon.item.AbstractItem;
import me.yawlick.tycoon.item.upgrader.Upgrader;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;

import java.util.UUID;

public class Seller extends AbstractItem {
    private final SellerClass info = this.getClass().getAnnotation(SellerClass.class);

    public long boost;

    public Seller(String name, String description, Long boost, Material material, Float chance) {
        type = ItemType.SELLER;
        this.name = name;
        this.description = description;
        this.boost = boost;
        this.material = material;
        this.uuid = UUID.randomUUID();
        this.chance = chance;
    }

    public Seller() {
        initProperties();
    }

    private void initProperties() {
        type = ItemType.SELLER;
        name = info.sellerName();
        description = info.sellerDescription();
        boost = info.boost();
        material = info.material();
        uuid = UUID.randomUUID();
        chance = info.chance();
    }

    public Seller copy() {
        return new Seller(
                name,
                description,
                boost,
                material,
                chance
        );
    }
}

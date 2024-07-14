package me.yawlick.tycoon.item.upgrader;

import me.yawlick.tycoon.item.ItemType;
import me.yawlick.tycoon.item.AbstractItem;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;

import java.util.UUID;

public class Upgrader extends AbstractItem {
    private final UpgraderClass info = this.getClass().getAnnotation(UpgraderClass.class);

    public long boost;

    public Upgrader(String name, String description, Long boost, Material material, Float chance) {
        type = ItemType.UPGRADER;
        this.name = name;
        this.description = description;
        this.boost = boost;
        this.material = material;
        this.uuid = UUID.randomUUID();
        this.chance = chance;
    }

    public Upgrader() {
        initProperties();
    }

    private void initProperties() {
        type = ItemType.UPGRADER;
        name = info.upgraderName();
        description = info.upgraderDescription();
        boost = info.boost();
        material = info.material();
        uuid = UUID.randomUUID();
        chance = info.chance();
    }

    public Upgrader copy() {
        return new Upgrader(
                name,
                description,
                boost,
                material,
                chance
        );
    }
}

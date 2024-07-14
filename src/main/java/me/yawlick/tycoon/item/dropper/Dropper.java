package me.yawlick.tycoon.item.dropper;

import me.yawlick.tycoon.item.ItemType;
import me.yawlick.tycoon.item.AbstractItem;
import me.yawlick.tycoon.item.upgrader.Upgrader;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;

import java.util.UUID;

public class Dropper extends AbstractItem {
    private final DropperClass info = this.getClass().getAnnotation(DropperClass.class);

    public long dropperSpeed;
    public String cubeName;
    public long cubeValue;
    public Material cubeMaterial;
    public int scheduler;

    public Dropper(String name, String description, Long dropperSpeed, String cubeName, long cubeValue, Material cubeMaterial, Material material, Float chance) {
        type = ItemType.DROPPER;
        this.name = name;
        this.description = description;
        this.dropperSpeed = dropperSpeed;
        this.cubeName = cubeName;
        this.cubeValue = cubeValue;
        this.cubeMaterial = cubeMaterial;
        this.material = material;
        this.uuid = UUID.randomUUID();
        this.chance = chance;
    }

    public Dropper() {
        initProperties();
    }

    private void initProperties() {
        type = ItemType.DROPPER;
        name = info.dropperName();
        description = info.dropperDescription();
        dropperSpeed = info.dropperSpeed();
        cubeName = info.cubeName();
        cubeValue = info.cubeValue();
        cubeMaterial = info.cubeMaterial();
        material = info.material();
        uuid = UUID.randomUUID();
        chance = info.chance();
    }

    public Dropper copy() {
        return new Dropper(
                name,
                description,
                dropperSpeed,
                cubeName,
                cubeValue,
                cubeMaterial,
                material,
                chance
        );
    }
}

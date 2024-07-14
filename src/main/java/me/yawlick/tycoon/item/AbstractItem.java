package me.yawlick.tycoon.item;

import me.yawlick.tycoon.item.upgrader.Upgrader;
import me.yawlick.tycoon.util.IPaper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;

import java.util.UUID;

public abstract class AbstractItem implements IPaper {
    public ItemType type = ItemType.NONE;
    public String name = null;
    public String description = null;
    public ArmorStand stand;
    public float chance = 100f;
    public boolean placed;
    public Material material;
    public UUID uuid;
    public Location location;
}
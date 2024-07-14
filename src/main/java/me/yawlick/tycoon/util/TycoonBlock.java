package me.yawlick.tycoon.util;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;

public class TycoonBlock {
    private final Material material;
    private final BlockFace face;

    public TycoonBlock(Material material, BlockFace face) {
        this.material = material;
        this.face = face;
    }

    public Material getMaterial() {
        return material;
    }

    public BlockFace getFace() {
        return face;
    }

    public String toString() {
        return String.format(material.name() + "," + face.name());
    }
}

package me.yawlick.tycoon.util;

import org.jetbrains.annotations.NotNull;

public class BlockPos {
    private double x;
    private double y;
    private double z;

    public BlockPos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockPos(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public @NotNull BlockPos toCenterLocation() {
        BlockPos centerLoc = this.clone();
        centerLoc.setX(x + 0.5);
        centerLoc.setY(y + 0.5);
        centerLoc.setZ(z + 0.5);
        return centerLoc;
    }

    public @NotNull BlockPos clone() {
        return new BlockPos(x, y, z);
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public String toString() {
        return String.format("%s,%s,%s", x, y, z);
    }
}

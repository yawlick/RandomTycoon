package me.yawlick.tycoon.item.dropper;

import org.bukkit.Material;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DropperClass {
    String dropperName() default "tycoon.dropper.first.name";
    String dropperDescription() default "tycoon.dropper.first.description";
    long dropperSpeed() default 100;
    float chance() default 100;
    String cubeName() default "tycoon.cube.first.name";
    Material cubeMaterial() default Material.POLISHED_DIORITE;
    long cubeValue() default 100L;
    Material material() default Material.ACACIA_BUTTON;
}
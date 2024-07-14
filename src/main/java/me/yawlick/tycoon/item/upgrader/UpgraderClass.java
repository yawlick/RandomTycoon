package me.yawlick.tycoon.item.upgrader;

import org.bukkit.Material;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface UpgraderClass {
    String upgraderName() default "tycoon.upgrader.first.name";
    String upgraderDescription() default "tycoon.upgrader.first.description";
    float chance() default 100;
    long boost() default 5;
    Material material() default Material.SUGAR_CANE;
}
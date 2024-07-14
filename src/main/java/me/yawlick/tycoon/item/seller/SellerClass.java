package me.yawlick.tycoon.item.seller;

import org.bukkit.Material;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SellerClass {
    String sellerName() default "tycoon.seller.first.name";
    String sellerDescription() default "tycoon.seller.first.description";
    float chance() default 100;
    long boost() default 10;
    Material material() default Material.LIME_WOOL;
}
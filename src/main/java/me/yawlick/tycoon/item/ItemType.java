package me.yawlick.tycoon.item;

public enum ItemType {
    NONE("Остальное"),
    DROPPER("Дроппер"),
    UPGRADER("Улучшатель"),
    SELLER("Продавец");

    public String name;

    ItemType(String name) {
        this.name = name;
    }
}

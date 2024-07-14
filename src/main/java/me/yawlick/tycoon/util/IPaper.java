package me.yawlick.tycoon.util;

import me.yawlick.tycoon.tycoon.player.PlayerData;
import me.yawlick.tycoon.tycoon.TycoonHandler;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

import static me.yawlick.tycoon.RandomTycoon.DATASTORE;

public interface IPaper {
    String PREFIX = "   §r§3§l[§b§lRandomTycoon§3§l] §r§a";

    default void msg(Player player, String msg) {
        player.sendMessage(PREFIX + msg);
    }

    default void msg(CommandSender sender, String msg) {
        if(sender instanceof Player)
            sender.sendMessage(PREFIX + msg);
    }

    default void msg(UUID uuid, String msg) {
        Objects.requireNonNull(Bukkit.getPlayer(uuid)).sendMessage(PREFIX + msg);
    }

    default void debug(String msg) {
        System.out.println(PREFIX + msg);
    }

    default void openInv(Player player, Inventory inventory) {
        player.openInventory(inventory);
    }

    default void playSound(Player player, Sound sound) {
        playSound(player, sound, SoundCategory.MASTER);
    }

    default void playSound(Player player, Sound sound, int i) {
        for(;i > 0; i--) {
            playSound(player, sound, SoundCategory.MASTER);
        }
    }

    default void playSound(Player player, Sound sound, SoundCategory soundCategory) {
        player.playSound(player.getLocation(), sound, soundCategory, 1f, 1f);
    }

    default void renameItem(ItemStack itemStack, String name) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
    }

    default void writeNBT(ItemStack itemStack, String key, Object value) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        if(value instanceof String) {
            data.set(NamespacedKey.fromString(key), PersistentDataType.STRING, (String) value);
        } else if(value instanceof Integer) {
            data.set(NamespacedKey.fromString(key), PersistentDataType.INTEGER, (int) value);
        } else if(value instanceof Float) {
            data.set(NamespacedKey.fromString(key), PersistentDataType.FLOAT, (float) value);
        } else if(value instanceof Long) {
            data.set(NamespacedKey.fromString(key), PersistentDataType.LONG, (long) value);
        } else if(value instanceof Short) {
            data.set(NamespacedKey.fromString(key), PersistentDataType.SHORT, (short) value);
        } else if(value instanceof Double) {
            data.set(NamespacedKey.fromString(key), PersistentDataType.DOUBLE, (double) value);
        } else if(value instanceof PersistentDataContainer[]) {
            data.set(NamespacedKey.fromString(key), PersistentDataType.TAG_CONTAINER_ARRAY, (PersistentDataContainer[]) value);
        } else if(value instanceof PersistentDataContainer) {
            data.set(NamespacedKey.fromString(key), PersistentDataType.TAG_CONTAINER, (PersistentDataContainer) value);
        } else if(value instanceof UUID) {
            data.set(NamespacedKey.fromString(key), PersistentDataType.STRING, ((UUID) value).toString());
        }
        itemStack.setItemMeta(meta);
    }

    default <ZOV> ZOV readNBT(ItemStack itemStack, String key, ZOV type) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        if(type instanceof String || type instanceof UUID) {
            return (ZOV) data.get(NamespacedKey.fromString(key), PersistentDataType.STRING);
        } else if(type instanceof Integer) {
            return (ZOV) data.get(NamespacedKey.fromString(key), PersistentDataType.INTEGER);
        } else if(type instanceof Float) {
            return (ZOV) data.get(NamespacedKey.fromString(key), PersistentDataType.FLOAT);
        } else if(type instanceof Long) {
            return (ZOV) data.get(NamespacedKey.fromString(key), PersistentDataType.LONG);
        } else if(type instanceof Short) {
            return (ZOV) data.get(NamespacedKey.fromString(key), PersistentDataType.SHORT);
        } else if(type instanceof Double) {
            return (ZOV) data.get(NamespacedKey.fromString(key), PersistentDataType.DOUBLE);
        } else if(type instanceof PersistentDataContainer[]) {
            return (ZOV) data.get(NamespacedKey.fromString(key), PersistentDataType.TAG_CONTAINER_ARRAY);
        } else if(type instanceof PersistentDataContainer) {
            return (ZOV) data.get(NamespacedKey.fromString(key), PersistentDataType.TAG_CONTAINER);
        }
        return null;
    }

    default boolean hasNBT(ItemStack itemStack, String key, Object type) {
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();
        if(type instanceof String || type instanceof UUID) {
            return data.has(NamespacedKey.fromString(key), PersistentDataType.STRING);
        } else if(type instanceof Integer) {
            return data.has(NamespacedKey.fromString(key), PersistentDataType.INTEGER);
        } else if(type instanceof Float) {
            return data.has(NamespacedKey.fromString(key), PersistentDataType.FLOAT);
        } else if(type instanceof Long) {
            return data.has(NamespacedKey.fromString(key), PersistentDataType.LONG);
        } else if(type instanceof Short) {
            return data.has(NamespacedKey.fromString(key), PersistentDataType.SHORT);
        } else if(type instanceof Double) {
            return data.has(NamespacedKey.fromString(key), PersistentDataType.DOUBLE);
        } else if(type instanceof PersistentDataContainer[]) {
            return data.has(NamespacedKey.fromString(key), PersistentDataType.TAG_CONTAINER_ARRAY);
        } else if(type instanceof PersistentDataContainer) {
            return data.has(NamespacedKey.fromString(key), PersistentDataType.TAG_CONTAINER);
        }
        return false;
    }

    @Nullable default PlayerData getData(UUID playerUuid) {
        return DATASTORE.getOrDefault(playerUuid, null).getData();
    }

    @Nullable default TycoonHandler getTycoon(UUID playerUuid) {
        return DATASTORE.getOrDefault(playerUuid, null);
    }

    @Nullable default PlayerData getData(CommandSender commandSender) {
        return DATASTORE.getOrDefault(((Player) commandSender).getUniqueId(), null).getData();
    }

    @Nullable default TycoonHandler getTycoon(CommandSender commandSender) {
        return DATASTORE.getOrDefault(((Player) commandSender).getUniqueId(), null);
    }
}

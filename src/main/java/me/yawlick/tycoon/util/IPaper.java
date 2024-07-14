package me.yawlick.tycoon.util;

import me.yawlick.tycoon.RandomTycoon;
import me.yawlick.tycoon.player.PlayerData;
import me.yawlick.tycoon.player.TycoonHandler;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

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

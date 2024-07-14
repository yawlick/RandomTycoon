package me.yawlick.tycoon.player;

import me.yawlick.tycoon.RandomTycoon;
import me.yawlick.tycoon.item.AbstractItem;
import me.yawlick.tycoon.item.dropper.Dropper;
import me.yawlick.tycoon.item.seller.Seller;
import me.yawlick.tycoon.item.upgrader.Upgrader;
import me.yawlick.tycoon.util.BlockPos;
import me.yawlick.tycoon.util.IPaper;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class TycoonHandler implements IPaper {
    @Nonnull private final UUID owner;
    @Nonnull private final PlayerData data;
    @Nonnull private final World world;
    @Nonnull private final ArrayList<UUID> items;
    @Nonnull private final Inventory droppersInventory;
    @Nonnull private final Inventory sellersInventory;
    @Nonnull private final Inventory upgradersInventory;
    @Nonnull private final ArrayList<Location> locations;
    private Block lastClickedBlock = null;

    public TycoonHandler(UUID uuid) {
        owner = uuid;
        world = Objects.requireNonNull(Bukkit.getWorld("world"));
        data = new PlayerData(
                uuid,
                0L,
                0
        );
        items = new ArrayList<>();
        droppersInventory = Bukkit.createInventory(null, 9, "§2Ваши §6Дропперы§2:");
        sellersInventory = Bukkit.createInventory(null, 9, "§2Ваши §6Продавцы§2:");
        upgradersInventory = Bukkit.createInventory(null, 9, "§2Ваши §6Улучшатели§2:");
        locations = new ArrayList<>();
    }

    public void placeDropper(Dropper dropper, BlockPos blockPos) {
        Block block = world.getBlockAt((int) blockPos.getX(), (int) blockPos.getY(), (int) blockPos.getZ());
        Block under = world.getBlockAt(block.getLocation().add(0.0, -1.0, 0.0));
        if(under.getType() != Material.AIR) {
            Bukkit.getPlayer(owner).kick(Component.text("§с§lВы §сне§с §сможете §споставить §ссюда §6Дроппер §с>=("));
            return;
        }
        block.setType(dropper.material);
        if(dropper.placed) {
            dropper.stand.remove();
        }
        dropper.stand = (ArmorStand) world.spawnEntity(
                new Location(world, blockPos.toCenterLocation().getX(), blockPos.toCenterLocation().getY() + 1, blockPos.toCenterLocation().getZ()),
                EntityType.ARMOR_STAND
        );
        dropper.stand.setCanTick(false);
        dropper.stand.setCanMove(false);
        dropper.stand.setCustomNameVisible(true);
        dropper.stand.setVisible(true);
        dropper.stand.setInvisible(true);
        dropper.stand.setMarker(true);
        dropper.stand.setGravity(false);
        dropper.stand.setCustomName("§6Дроппер §l" + dropper.name);
        dropper.placed = true;
        playSound(Bukkit.getPlayer(owner), Sound.ENTITY_VILLAGER_TRADE);
        msg(owner, "Вы успешно поставили §6Дроппер " + dropper.name + "§a!");
        dropper.scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(RandomTycoon.INSTANCE, new Runnable() {
            @Override
            public void run() {
                ItemStack itemStack = new ItemStack(dropper.cubeMaterial, 1);
                Item item = world.dropItem(
                        new Location(world, blockPos.toCenterLocation().getX(), blockPos.toCenterLocation().getY() - 0.3, blockPos.toCenterLocation().getZ()),
                        itemStack
                );
                item.setVelocity(new Vector(0,0,0));
                item.teleport(new Location(world, blockPos.toCenterLocation().getX(), blockPos.toCenterLocation().getY() - 0.3, blockPos.toCenterLocation().getZ()));
                item.setCanPlayerPickup(false);
                item.setCanMobPickup(false);
                item.setCustomNameVisible(true);
                item.setCustomName("§6" + dropper.cubeName + " §a" + dropper.cubeValue + "$");
                PersistentDataContainer data = item.getPersistentDataContainer();
                data.set(NamespacedKey.fromString("cube.value"), PersistentDataType.LONG, dropper.cubeValue);
                data.set(NamespacedKey.fromString("cube.dropper"), PersistentDataType.STRING, dropper.name);
                items.add(item.getUniqueId());
            }
        }, dropper.dropperSpeed * 2, dropper.dropperSpeed); // тут желательно потом сделать что бы меньше тпс = меньше кд в тиках
        locations.add(block.getLocation());
    }

    public void placeSeller(Seller seller, BlockPos blockPos) {
        Block block = world.getBlockAt((int) blockPos.getX(), (int) blockPos.getY(), (int) blockPos.getZ());
        Block under = world.getBlockAt(block.getLocation().add(0.0, -1.0, 0.0));
        if(under.getType() != Material.LIME_SHULKER_BOX) {
            Bukkit.getPlayer(owner).kick(Component.text("§с§lВы §сне§с §сможете §споставить §ссюда §6Продавца §с>=("));
            return;
        }
        block.setType(seller.material);
        if(seller.placed) {
            seller.stand.remove();
        }
        seller.stand = (ArmorStand) world.spawnEntity(
                new Location(world, blockPos.toCenterLocation().getX(), blockPos.toCenterLocation().getY() + 1, blockPos.toCenterLocation().getZ()),
                EntityType.ARMOR_STAND
        );
        seller.stand.setCanTick(false);
        seller.stand.setCanMove(false);
        seller.stand.setCustomNameVisible(true);
        seller.stand.setVisible(true);
        seller.stand.setInvisible(true);
        seller.stand.setMarker(true);
        seller.stand.setGravity(false);
        seller.stand.setCustomName("§6Продавец §l" + seller.name);
        seller.placed = true;
        playSound(Bukkit.getPlayer(owner), Sound.ENTITY_VILLAGER_TRADE);
        locations.add(block.getLocation());
    }

    public void placeUpgrader(Upgrader upgrader, BlockPos blockPos) {
        Block block = world.getBlockAt((int) blockPos.getX(), (int) blockPos.getY(), (int) blockPos.getZ());
        Block under = world.getBlockAt(block.getLocation().add(0.0, -1.0, 0.0));
        if(under.getType() != Material.MAGENTA_GLAZED_TERRACOTTA) {
            Bukkit.getPlayer(owner).kick(Component.text("§с§lВы §сне§с §сможете §споставить §ссюда §6Улучшатель §с>=("));
            return;
        }
        block.setType(upgrader.material);
        if(upgrader.placed) {
            upgrader.stand.remove();
        }
        upgrader.stand = (ArmorStand) world.spawnEntity(
                new Location(world, blockPos.toCenterLocation().getX(), blockPos.toCenterLocation().getY() + 1, blockPos.toCenterLocation().getZ()),
                EntityType.ARMOR_STAND
        );
        upgrader.stand.setCanTick(false);
        upgrader.stand.setCanMove(false);
        upgrader.stand.setVisible(true);
        upgrader.stand.setInvisible(true);
        upgrader.stand.setMarker(true);
        upgrader.stand.setCustomNameVisible(true);
        upgrader.stand.setGravity(false);
        upgrader.stand.setCustomName("§6Улучшатель §l" + upgrader.name);
        upgrader.placed = true;
        playSound(Bukkit.getPlayer(owner), Sound.ENTITY_VILLAGER_TRADE);
        locations.add(block.getLocation());
    }

    @Nonnull
    public PlayerData getData() {
        return data;
    }

    @Nonnull
    public UUID getOwner() {
        return owner;
    }

    @Nonnull
    public World getWorld() {
        return world;
    }

    @Nonnull
    public ArrayList<UUID> getItems() {
        return items;
    }

    @Nonnull
    public ArrayList<Location> getLocations() {
        return locations;
    }

    @Nonnull
    public Inventory getDroppersInventory() {
        return droppersInventory;
    }

    @Nonnull
    public Inventory getSellersInventory() {
        return sellersInventory;
    }

    @Nonnull
    public Inventory getUpgradersInventory() {
        return upgradersInventory;
    }

    public void setLastClickedBlock(Block block) {
        lastClickedBlock = block;
    }

    public Block getLastClickedBlock() {
        return lastClickedBlock;
    }

    public void removeItem(UUID uuid) {
        items.remove(uuid);
    }
}

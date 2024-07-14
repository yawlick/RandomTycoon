package me.yawlick.tycoon.command;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.yawlick.tycoon.LoadModule;
import me.yawlick.tycoon.RandomTycoon;
import me.yawlick.tycoon.item.ItemHandler;
import me.yawlick.tycoon.util.BlockPos;
import me.yawlick.tycoon.util.IPaper;
import me.yawlick.tycoon.util.TycoonBlock;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Objects;

public class PlaceCommand implements CommandExecutor, IPaper {
    private final Gson gson = new Gson();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        ItemHandler itemHandler = RandomTycoon.INSTANCE.loadModule.getItemHandler();
        if(args.length > 1) {
            if(args[0].equalsIgnoreCase("dropper")) {
                if(itemHandler.getDropper(args[1]) != null) {
                    if(!(commandSender instanceof Player))
                        return false;
                    Player player = (Player) commandSender;
                    Location loc = player.getLocation();
                    getTycoon(player).placeDropper(
                            itemHandler.firstDropper,
                            new BlockPos(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())
                    );
                }
            }

            if(args[0].equalsIgnoreCase("seller")) {
                if(itemHandler.getSeller(args[1]) != null) {
                    if(!(commandSender instanceof Player))
                        return false;
                    Player player = (Player) commandSender;
                    Location loc = player.getLocation();
                    Objects.requireNonNull(getTycoon(player)).placeSeller(
                            itemHandler.firstSeller,
                            new BlockPos(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())
                    );
                }
            }

            if(args[0].equalsIgnoreCase("upgrader")) {
                if(itemHandler.getUpgrader(args[1]) != null) {
                    if(!(commandSender instanceof Player))
                        return false;
                    Player player = (Player) commandSender;
                    Location loc = player.getLocation();
                    Objects.requireNonNull(getTycoon(player)).placeUpgrader(
                            itemHandler.firstUpgrader,
                            new BlockPos(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())
                    );
                }
            }

            if(args[0].equalsIgnoreCase("save")) {
                String level = "\\level" + args[1] + ".json";
                HashMap<String, String> keyMap = new HashMap<>();
                int minX = -1; int minY = 200; int minZ = -1;
                int maxX = 26; int maxY = 255; int maxZ = 26;
                for (int x = minX; x <= maxX; x++) {
                    for (int y = minY; y <= maxY; y++) {
                        for (int z = minZ; z <= maxZ; z++) {
                            BlockPos blockPos = new BlockPos(x, y, z);
                            Block block = Bukkit.getWorld("world").getBlockAt(x,y,z);
                            BlockFace face = BlockFace.SELF;
                            try {
                                face = ((Directional) block.getBlockData()).getFacing();
                            } catch (Exception ignored) {

                            }
                            TycoonBlock tycoonBlock = new TycoonBlock(block.getType(), face);
                            String materialName = tycoonBlock.toString();
                            keyMap.put(blockPos.toString(), materialName);
                        }
                    }
                }


                try (FileWriter file = new FileWriter(RandomTycoon.INSTANCE.getDataFolder() + level)) {
                    gson.toJson(keyMap, file);
                    msg(commandSender, "Уровень был успешно сохранён!");
                } catch (IOException e) {
                    msg(commandSender, "§cВо время сохранения произошла ошибка..");
                    e.printStackTrace();
                }
            }

            if(args[0].equalsIgnoreCase("load")) {
                String level = "\\level" + args[1] + ".json";
                HashMap<String, String> keyMap = new HashMap<>();

                try (FileReader reader = new FileReader(RandomTycoon.INSTANCE.getDataFolder() + level)) {
                    Type type = new TypeToken<HashMap<String, String>>() {}.getType();
                    keyMap = gson.fromJson(reader, type);
                    for (String key : keyMap.keySet()) {
                        String[] cords = key.split(",");
                        String[] blockData = keyMap.get(key).split(",");
                        double x = Double.parseDouble(cords[0]); double y = Double.parseDouble(cords[1]); double z = Double.parseDouble(cords[2]);
                        Block block = Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld("world"), x, y, z));
                        Material material = Material.getMaterial(blockData[0]);
                        block.setType(material);
                        try {
                            Directional data = ((Directional) block.getBlockData());
                            data.setFacing(BlockFace.valueOf(blockData[1]));
                            block.setBlockData(data);
                        } catch (Exception ignored) {

                        }
                    }
                    msg(commandSender, "Уровень был успешно загружен!");
                } catch (IOException e) {
                    msg(commandSender, "§cВо время загрузки произошла ошибка..");
                    e.printStackTrace();
                }
            }
        } else {
            msg(commandSender, "§cВы не указали аргументы! §7(/p itemType itemName)");
        }
        return true;
    }
}

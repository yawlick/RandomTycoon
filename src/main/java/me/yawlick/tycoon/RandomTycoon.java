package me.yawlick.tycoon;

import me.yawlick.tycoon.tycoon.TycoonHandler;
import me.yawlick.tycoon.util.IPaper;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class RandomTycoon extends JavaPlugin implements IPaper {
    public static RandomTycoon INSTANCE;
    public static HashMap<UUID, TycoonHandler> DATASTORE = new HashMap<>();
    public LoadModule loadModule;

    @Override
    public void onEnable() {
        INSTANCE = this;

        loadModule = new LoadModule();
        loadModule.load();
    }

    @Override
    public void onDisable() {
        loadModule.unload();
    }

    public void registerListeners(Listener... listeners) {
        for(Listener listener : listeners) {
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    public LoadModule getLoadModule() {
        return loadModule;
    }
}
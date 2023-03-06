package net.philocraft;

import org.bukkit.plugin.java.JavaPlugin;

import net.philocraft.models.Database;

public final class ScoreboardEssentials extends JavaPlugin {
    
    private static Database database;
    
    public static Database getDatabase() {
        return database;
    }

    @Override
    public void onEnable() {
        database = Database.init(this);

        this.getLogger().info("Plugin enabled.");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Plugin disabled.");
    }

}
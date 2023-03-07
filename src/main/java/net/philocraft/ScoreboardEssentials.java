package net.philocraft;

import org.bukkit.plugin.java.JavaPlugin;

import net.philocraft.events.OnPlayerJoinEvent;
import net.philocraft.events.OnPlayerQuitEvent;
import net.philocraft.models.Database;
import net.philocraft.models.EssentialsScoreboard;

public final class ScoreboardEssentials extends JavaPlugin {
    
    private static Database database;
    private static EssentialsScoreboard scoreboard;
    
    public static Database getDatabase() {
        return database;
    }

    public static EssentialsScoreboard getScoreboard() {
        return scoreboard;
    }

    @Override
    public void onEnable() {
        database = Database.init(this);
        scoreboard = new EssentialsScoreboard(this);
        scoreboard.update();

        //!REGISTER EVENTS
        this.getServer().getPluginManager().registerEvents(new OnPlayerJoinEvent(), this);
        this.getServer().getPluginManager().registerEvents(new OnPlayerQuitEvent(), this);

        this.getLogger().info("Plugin enabled.");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Plugin disabled.");
    }

}

package net.philocraft;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import dev.littlebigowl.api.EssentialsAPI;
import dev.littlebigowl.api.models.EssentialsTeam;
import net.philocraft.events.OnPlayerJoinEvent;
import net.philocraft.events.OnPlayerQuitEvent;

public final class ScoreboardEssentials extends JavaPlugin {
    
    public static final EssentialsAPI api = (EssentialsAPI) Bukkit.getServer().getPluginManager().getPlugin("EssentialsAPI");

    @Override
    public void onEnable() {
        
        this.getServer().getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                EssentialsTeam rank = api.scoreboard.getEssentialsTeam(player);
                
                if(rank == null) {
                    rank = api.scoreboard.setTeam(player);
                }
                
                api.scoreboard.setScores(player);
            }
        }, 0, 10);

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

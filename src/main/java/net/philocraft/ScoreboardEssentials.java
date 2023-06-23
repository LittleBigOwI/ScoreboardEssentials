package net.philocraft;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import dev.littlebigowl.api.EssentialsAPI;
import dev.littlebigowl.api.models.EssentialsPermission;
import dev.littlebigowl.api.models.EssentialsTeam;
import net.philocraft.commands.AlterItemCommand;
import net.philocraft.commands.TrailCommand;
import net.philocraft.events.OnPlayerChatEvent;
import net.philocraft.events.OnPlayerJoinEvent;
import net.philocraft.events.OnPlayerMoveEvent;
import net.philocraft.events.OnPlayerQuitEvent;
import net.philocraft.utils.ParticleUtil;

public final class ScoreboardEssentials extends JavaPlugin {
    
    public static final EssentialsAPI api = (EssentialsAPI) Bukkit.getServer().getPluginManager().getPlugin("EssentialsAPI");

    private static ScoreboardEssentials plugin;

    public static ScoreboardEssentials getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {

        plugin = this;
        try {
            ParticleUtil.loadParticles();
        } catch (SQLException e) {
            this.getLogger().severe("Copuldn't load particles : " + e.getMessage());
        }
        
        this.getServer().getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                EssentialsTeam oldTeam = api.scoreboard.getEssentialsTeam(player);
                EssentialsTeam newTeam = api.scoreboard.setTeam(player);

                if(!oldTeam.getName().equals(newTeam.getName())) {
                    EssentialsPermission.resetPermissions(player);
                    newTeam.setPermissions(player);
                
                } else {
                    oldTeam.setPermissions(player);
                }

                api.scoreboard.setScores(player);
                api.scoreboard.setScoreboard(player);
                api.scoreboard.setTitles(player);
            }
        }, 0, 20);

        //!REGISTER COMMANDS
        this.getCommand("alteritem").setExecutor(new AlterItemCommand());
        this.getCommand("trail").setExecutor(new TrailCommand());

        //!REGISTER EVENTS
        this.getServer().getPluginManager().registerEvents(new OnPlayerJoinEvent(), this);
        this.getServer().getPluginManager().registerEvents(new OnPlayerQuitEvent(), this);
        this.getServer().getPluginManager().registerEvents(new OnPlayerChatEvent(), this);
        this.getServer().getPluginManager().registerEvents(new OnPlayerMoveEvent(), this);

        this.getLogger().info("Plugin enabled.");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Plugin disabled.");
    }

}

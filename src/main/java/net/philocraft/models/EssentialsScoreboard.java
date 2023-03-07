package net.philocraft.models;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.philocraft.ScoreboardEssentials;
import net.philocraft.constants.Colors;

public class EssentialsScoreboard {
    
    private ScoreboardEssentials plugin;
    private Scoreboard scoreboard;
    private Objective playtime;
    private Objective health;

    public EssentialsScoreboard(ScoreboardEssentials plugin) {
        Scoreboard scoreboard = plugin.getServer().getScoreboardManager().getNewScoreboard();
        
        this.plugin = plugin;
        this.playtime = scoreboard.registerNewObjective("playtime", Criteria.create("PLAYTIME"), "playtime");
        this.health = scoreboard.registerNewObjective("health", Criteria.HEALTH, Colors.FAILURE.getChatColor() + "❤");

        playtime.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        health.setDisplaySlot(DisplaySlot.BELOW_NAME);

        for(Team t : scoreboard.getTeams()) { t.unregister(); }

        this.scoreboard = scoreboard;
    }

    public void update() {
        
        this.plugin.getServer().getScheduler().runTaskTimer(this.plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Rank rank = Rank.getRank(player);
                
                if(rank != null) {
                    rank.giveToPlayer(player);
                }

                this.playtime.getScore(player.getName()).setScore(Rank.getHourPlaytime(player));
                player.setScoreboard(this.scoreboard);
            }
        }, 0, 10);

    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

}

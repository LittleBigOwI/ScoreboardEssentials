package net.philocraft.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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
    
    private static HashMap<Integer, String> teamNames = new HashMap<>();

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
        this.registerTeams();
    }

    public void registerTeams() {
        int teamsSize = Database.getRanks().size();
        ArrayList<String> teamCodes = new ArrayList<>();

        int teamLength = String.valueOf(teamsSize).length();

        for(int i = 0; i < teamsSize; i++) {
            String code = "" + i;
            while(code.length() < teamLength) {
                code = "0" + code;
            }
            teamCodes.add(code);
        }

        for(String code : teamCodes) {
            this.scoreboard.registerNewTeam(code);
        }

        ArrayList<Integer> playtimes = new ArrayList<>();
        Database.getRanks().forEach(rank -> playtimes.add(rank.getPlaytime()));
        
        Collections.sort(playtimes);
        Collections.reverse(playtimes);

        int i = 0;
        for(int playtime : playtimes) {
            EssentialsScoreboard.teamNames.put(playtime, teamCodes.get(i));
            i++;
        }
    }

    public Team getTeam(Player player) {
        return scoreboard.getTeam(EssentialsScoreboard.teamNames.get(Rank.getRank(player).getPlaytime()));
    }

    public void update() {
        
        this.plugin.getServer().getScheduler().runTaskTimer(this.plugin, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                Rank rank = Rank.getRank(player);
                
                if(rank != null) {
                    rank.giveToPlayer(player);
                }

                this.playtime.getScore(player.getName()).setScore(Rank.getHourPlaytime(player));
                player.setPlayerListHeader(Database.getHeader());
                player.setPlayerListFooter(Database.getFooter(player));
                
                player.setScoreboard(this.scoreboard);

                this.getTeam(player).addEntry(player.getName());
            }
        }, 0, 10);

    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

}

package net.philocraft.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.awt.Color;

import net.philocraft.ScoreboardEssentials;

public class Database {
    
    private static ArrayList<Rank> ranks = new ArrayList<>();

    private ScoreboardEssentials plugin;

    private Database(ScoreboardEssentials plugin) {
        this.plugin = plugin;
    }

    public static Database init(ScoreboardEssentials scoreboardEssentials) {
        scoreboardEssentials.saveDefaultConfig();

        List<?> cRankPrefixes = scoreboardEssentials.getConfig().getList("ranks");
        List<?> cRankPlaytimes = scoreboardEssentials.getConfig().getList("playtime");
        List<?> cRankColors = scoreboardEssentials.getConfig().getList("colors");

        if(cRankPrefixes.size() != cRankPlaytimes.size() || cRankPrefixes.size() != cRankColors.size() || cRankPlaytimes.size() != cRankColors.size()) {
            scoreboardEssentials.getLogger().info("Invalid rank configuration, switching to default config.");

            cRankPrefixes = new ArrayList<>(Arrays.asList("G", "P", "P+", "M", "PC", "PC+"));
            cRankPlaytimes = new ArrayList<>(Arrays.asList(0, 500, 1000, 1500, 2000, 2500));
            cRankColors = new ArrayList<>(Arrays.asList("aaaaaa", "59deff", "59afff", "5975ff", "ffaa00", "ffaa00"));
        }
        
        for(int i = 0; i < cRankPrefixes.size(); i++) {
            String rankPrefix = cRankPrefixes.get(i).toString();
            int rankPlaytime = Integer.parseInt(cRankPlaytimes.get(i).toString());
            Color rankColor;
            
            try { 
                rankColor = Color.decode("#" + cRankColors.get(i).toString()); 
            } catch (NumberFormatException e) {
                rankColor = Color.decode("#ffffff");
            }

            ranks.add(new Rank(rankPrefix, rankPlaytime, rankColor));
        }

        scoreboardEssentials.getLogger().info("Loaded " + cRankPlaytimes.size() + " ranks.");

        return new Database(scoreboardEssentials);
    }

    public static ArrayList<Rank> getRanks() {
        return ranks;
    }

    public ScoreboardEssentials getPlugin() {
        return this.plugin;
    }

}

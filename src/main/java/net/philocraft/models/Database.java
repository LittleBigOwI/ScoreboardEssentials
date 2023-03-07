package net.philocraft.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.awt.Color;

import net.md_5.bungee.api.ChatColor;
import net.philocraft.ScoreboardEssentials;

public class Database {
    
    private static ArrayList<Rank> ranks = new ArrayList<>();
    private static String scoreboardHeader;
    private static String scoreboardFooter;
    private static String maxPlayers;
    
    private ScoreboardEssentials plugin;

    private Database(ScoreboardEssentials plugin) {
        this.plugin = plugin;
    }

    private static String getLine(int a, ChatColor c) {
        String spaces = "";
        for(int i = 0; i < a; i++) {
            spaces += " ";
        }

        return c + "" + ChatColor.STRIKETHROUGH + spaces + ChatColor.RESET + "" + c;
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

        scoreboardHeader = scoreboardEssentials.getConfig().getString("header");
        scoreboardFooter = scoreboardEssentials.getConfig().getString("footer");
        maxPlayers = scoreboardEssentials.getConfig().getString("maxPlayers");

        return new Database(scoreboardEssentials);
    }

    public static ArrayList<Rank> getRanks() {
        return ranks;
    }

    public static String getHeader() {
        int players = Bukkit.getOnlinePlayers().size();
        
        return ChatColor.translateAlternateColorCodes('&', Database.scoreboardHeader) + "\n" +
            getLine(7, ChatColor.DARK_GRAY) + "[ " + ChatColor.RED + players + ChatColor.DARK_GRAY + "/" + ChatColor.RED + maxPlayers + ChatColor.DARK_GRAY + " ]" + getLine(7, ChatColor.DARK_GRAY) + "\n";
    }

    public static String getFooter(Player player) {
        return ChatColor.translateAlternateColorCodes('&', "\n" + getLine(17, ChatColor.DARK_GRAY) + "\n" + Database.scoreboardFooter.replace("{ping}", Math.round(player.getPing()) + ""));
    }

    public ScoreboardEssentials getPlugin() {
        return this.plugin;
    }

}

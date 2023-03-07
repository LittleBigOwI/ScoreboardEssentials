package net.philocraft.models;

import java.awt.Color;
import java.util.ArrayList;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Rank {
    
    private String prefix;
    private int playtime;
    private Color color;

    public Rank(String prefix, int playtime, Color color) {
        this.prefix = prefix;
        this.playtime = playtime;
        this.color = color;
    }

    public static Rank getRank(int playerPlaytime) {        
        ArrayList<Integer> playtimes = new ArrayList<>();
        Database.getRanks().forEach(rank -> playtimes.add(rank.playtime));

        int distance = Math.abs(playtimes.get(0) - playerPlaytime);
        int idx = 0;
        for(int i = 1; i < playtimes.size(); i++){
            int cdistance = Math.abs(playtimes.get(i) - playerPlaytime);
            if(cdistance < distance && playerPlaytime >= playtimes.get(i)){
                idx = i;
                distance = cdistance;
            }
        }
        int rankPlaytime = playtimes.get(idx);

        for(Rank rank : Database.getRanks()) {
            if(rank.getPlaytime() == rankPlaytime) {
                return rank;
            }
        }
        return null;
    }

    public static int getPlaytime(Player player) {
        return Math.round(player.getStatistic(Statistic.PLAY_ONE_MINUTE)/1200);
    }

    public static int getHourPlaytime(Player player) {
        return Math.round(Rank.getPlaytime(player)/60);
    }

    public static Rank getRank(Player player) {
        return Rank.getRank(Rank.getPlaytime(player));
    }

    public void giveToPlayer(Player player) {
        player.setPlayerListName(
            ChatColor.WHITE + "[" +
            ChatColor.of(this.color) + this.prefix +
            ChatColor.WHITE + "] " +
            ChatColor.of(this.color) + player.getName()
        );
    }

    public String getPrefix() {
        return this.prefix;
    }

    public int getPlaytime() {
        return this.playtime;
    }

    public Color getColor() {
        return this.color;
    }

}

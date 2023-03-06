package net.philocraft.models;

import java.awt.Color;

public class Rank {
    
    private String prefix;
    private int playtime;
    private Color color;

    public Rank(String prefix, int playtime, Color color) {
        this.prefix = prefix;
        this.playtime = playtime;
        this.color = color;
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

package net.philocraft.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import dev.littlebigowl.api.constants.Colors;
import dev.littlebigowl.api.models.EssentialsTeam;
import net.md_5.bungee.api.ChatColor;
import net.philocraft.ScoreboardEssentials;

public class OnPlayerChatEvent implements Listener {
    
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        EssentialsTeam team = ScoreboardEssentials.api.scoreboard.getEssentialsTeam(player);

        event.setFormat(
            ChatColor.RESET + "[" + ChatColor.of(team.getColor()) + team.getPrefix() + ChatColor.RESET + "]" +
            ChatColor.of(team.getColor()) + " %s " +
            Colors.MINOR.getChatColor() + "»" + ChatColor.RESET +" %s "
        );
    }

}

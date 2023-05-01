package net.philocraft.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import dev.littlebigowl.api.constants.Colors;

public class OnPlayerQuitEvent implements Listener {
    
    @EventHandler
    public void onPlayerJoin(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String msg = 
            Colors.MINOR.getChatColor() + "[" +
            Colors.FAILURE.getChatColor() + "-" +
            Colors.MINOR.getChatColor() + "] " + player.getName();

        event.setQuitMessage(msg);
    }
}

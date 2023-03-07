package net.philocraft.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import net.philocraft.constants.Colors;

public class OnPlayerQuitEvent implements Listener {
    
    @EventHandler
    public void onPlayerJoin(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String msg = 
            Colors.OBFUSCATE.getChatColor() + "[" +
            Colors.FAILURE.getChatColor() + "-" +
            Colors.OBFUSCATE.getChatColor() + "] " + player.getName();

        event.setQuitMessage(msg);
    }
}

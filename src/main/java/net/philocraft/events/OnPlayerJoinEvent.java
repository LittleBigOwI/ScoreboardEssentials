package net.philocraft.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.philocraft.constants.Colors;

public class OnPlayerJoinEvent implements Listener {
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String msg = 
            Colors.OBFUSCATE.getChatColor() + "[" +
            Colors.SUCCESS.getChatColor() + "+" +
            Colors.OBFUSCATE.getChatColor() + "] " + player.getName();

        event.setJoinMessage(msg);
    }

}

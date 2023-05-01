package net.philocraft.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import dev.littlebigowl.api.constants.Colors;

public class OnPlayerJoinEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String msg = 
            Colors.MINOR.getChatColor() + "[" +
            Colors.SUCCESS.getChatColor() + "+" +
            Colors.MINOR.getChatColor() + "] " + player.getName();

        event.setJoinMessage(msg);     
    }

}

package net.philocraft.events;

import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import net.philocraft.utils.ParticleUtil;

public class OnPlayerMoveEvent implements Listener {
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        
        Player player = event.getPlayer();        
        Particle particle = ParticleUtil.getParticle(player.getUniqueId());
        
        if(particle != null) {
            player.getWorld().spawnParticle(particle, player.getLocation(), 1, 0, 0, 0, 0.1);
        }   
    }
}

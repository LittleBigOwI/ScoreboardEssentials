package net.philocraft.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Particle;
import org.bukkit.entity.Player;

import net.philocraft.ScoreboardEssentials;

public class ParticleUtil {
    
    private static final HashMap<UUID, Particle> playerParticles = new HashMap<>();

    public static void loadParticles() throws SQLException {
        ScoreboardEssentials.api.database.create(
            "CREATE TABLE IF NOT EXISTS Particles(" +
            "id int NOT NULL UNIQUE AUTO_INCREMENT, " +
            "uuid TEXT NOT NULL, " +
            "particle TEXT NOT NULL);"
        );

        ResultSet results = ScoreboardEssentials.api.database.fetch("SELECT * FROM Particles");

        while(results.next()) {
            playerParticles.put(UUID.fromString(results.getString("uuid")), Particle.valueOf(results.getString("particle")));
        }

        ScoreboardEssentials.getPlugin().getLogger().info("Loaded " + playerParticles.size() + " particles.");
    }

    public static void setParticle(UUID uuid, Particle particle) throws SQLException {
        Particle oldParticle = playerParticles.get(uuid);

        if(oldParticle == null) {
            ScoreboardEssentials.api.database.create("INSERT INTO Particles(uuid, particle) VALUES ('" + uuid + "', '" + particle.name() + "');");
        
        } else {
            ScoreboardEssentials.api.database.update("UPDATE Particles SET particle='" + particle.name() + "' WHERE uuid='" + uuid + "';");
        }
        
        playerParticles.put(uuid, particle);
    }

    public static void setParticle(Player player, Particle particle) throws SQLException {
        ParticleUtil.setParticle(player.getUniqueId(), particle);
    }

    public static Particle getParticle(UUID uuid) {
        return ParticleUtil.playerParticles.get(uuid);
    }

    public static Particle getParticle(Player player) {
        return ParticleUtil.getParticle(player.getUniqueId());
    }

    public static void removeParticle(UUID uuid) throws SQLException {
        ScoreboardEssentials.api.database.update("DELETE FROM Particles WHERE UUID='" + uuid + "';");
        ParticleUtil.playerParticles.remove(uuid);
    }

    public static void removeParticle(Player player) throws SQLException {
        ParticleUtil.removeParticle(player.getUniqueId());
    }

    public static boolean hasParticle(UUID uuid) {
        return ParticleUtil.getParticle(uuid) != null;
    }

    public static boolean hasParticle(Player player) {
        return ParticleUtil.hasParticle(player.getUniqueId());
    }
}

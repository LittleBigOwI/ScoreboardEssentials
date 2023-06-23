package net.philocraft.commands;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import dev.littlebigowl.api.constants.Colors;
import dev.littlebigowl.api.errors.InvalidArgumentsException;
import dev.littlebigowl.api.errors.InvalidSenderException;
import net.philocraft.ScoreboardEssentials;
import net.philocraft.utils.ParticleUtil;

public class TrailCommand implements CommandExecutor, TabCompleter{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player && label.equals("trail"))) {
            return new InvalidSenderException().sendCause(sender);
        }

        Player player = (Player)sender;

        if((args[0].equals("set") && args.length != 2) || args.length == 0) {
            return new InvalidArgumentsException().sendCause(sender);
        
        } else if(args[0].equals("remove") && args.length != 1) {
            return new InvalidArgumentsException().sendCause(sender);
        }

        if(args[0].equals("set")) {
            ArrayList<String> particles = new ArrayList<>();
            for(Particle particle : Particle.values()) {
                particles.add("minecraft:" + particle.toString());
            }

            if(!particles.contains(args[1])) {
                return new InvalidArgumentsException("This particle doesn't exist.").sendCause(sender);
            }

            Particle particle = Particle.valueOf(args[1].replace("minecraft:", ""));
            
            try {
                ParticleUtil.setParticle(player, particle);
            } catch (SQLException e) {
                ScoreboardEssentials.getPlugin().getLogger().severe("Couldn't set particle : " + e.getMessage());
            }

            player.sendMessage(
                Colors.SUCCESS.getChatColor() + "Successfully set new " + 
                Colors.SUCCESS_DARK.getChatColor() + particle.name() + 
                Colors.SUCCESS.getChatColor() + " trail."
            );

        } else if(args[0].equals("remove")) {
            Particle particle = ParticleUtil.getParticle(player);

            if(ParticleUtil.hasParticle(player)) {
                try {
                    ParticleUtil.removeParticle(player);
                } catch (SQLException e) {
                    ScoreboardEssentials.getPlugin().getLogger().severe("Couldn't remove particle : " + e.getMessage());
                }

                player.sendMessage(
                    Colors.SUCCESS.getChatColor() + "Successfully removed your " + 
                    Colors.SUCCESS_DARK.getChatColor() + particle.name() + 
                    Colors.SUCCESS.getChatColor() + " trail."
                );
            
            } else {
                player.sendMessage(Colors.FAILURE.getChatColor() + "You currently don't have any trail.");
            }
        }
        
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) {
            return Arrays.asList("set", "remove");

        } else if(args.length == 2 && args[0].equals("set")) {
            ArrayList<String> stringParticles = new ArrayList<>();

            for(Particle particle : Particle.values()) {
                stringParticles.add("minecraft:" + particle.toString());
            }

            return StringUtil.copyPartialMatches(args[1], stringParticles, new ArrayList<>());
        }

        return new ArrayList<>();
    }
    
}


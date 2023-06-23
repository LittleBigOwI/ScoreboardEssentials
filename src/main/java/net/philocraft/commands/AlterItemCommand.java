package net.philocraft.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import dev.littlebigowl.api.constants.Colors;
import dev.littlebigowl.api.errors.InvalidArgumentsException;
import dev.littlebigowl.api.errors.InvalidSenderException;
import net.md_5.bungee.api.ChatColor;

public class AlterItemCommand implements CommandExecutor, TabCompleter{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player && label.equals("alteritem"))) {
            return new InvalidSenderException().sendCause(sender);
        }

        Player player = (Player)sender;

        if(args.length != 0 && args[0].equals("name")) {
            StringBuilder name = new StringBuilder();
            for(int i = 1; i < args.length; i++) {
                name.append(args[i]).append(" ");
            }
            
            name.setLength(name.length() - 1);

            ItemStack item = player.getInventory().getItemInMainHand();
            if(item == null) {
                return new InvalidArgumentsException("You need to have an item in your hand.").sendCause(sender);
            }
            
            ItemMeta itemMeta = item.getItemMeta();
            if(itemMeta == null) {
                return new InvalidArgumentsException("You need to have an item in your hand.").sendCause(sender);
            }
            
            itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&r&f" + name.toString()));
            item.setItemMeta(itemMeta);

            player.sendMessage(Colors.SUCCESS.getChatColor() + "Successfully altered item!");

        } else if(args.length != 0 && args[0].equals("lore")) {
            StringBuilder name = new StringBuilder();
            for(int i = 1; i < args.length; i++) {
                name.append(args[i]).append(" ");
            }

            name.setLength(name.length() - 1);

            List<String> lore = Arrays.asList(ChatColor.translateAlternateColorCodes('&', "&r&f" + name.toString().replaceAll(";", ";&r&f")).split(";"));

            ItemStack item = player.getInventory().getItemInMainHand();
            if(item == null) {
                return new InvalidArgumentsException("You need to have an item in your hand.").sendCause(sender);
            }
            
            ItemMeta itemMeta = item.getItemMeta();
            if(itemMeta == null) {
                return new InvalidArgumentsException("You need to have an item in your hand.").sendCause(sender);
            }

            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);

            player.sendMessage(Colors.SUCCESS.getChatColor() + "Successfully altered item!");
        
        } else {
            return new InvalidArgumentsException().sendCause(sender);
        }

        return true;
    }

    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 1) {
            return Arrays.asList("name", "lore");
        } else {
            return new ArrayList<>();
        }
    }
    
}

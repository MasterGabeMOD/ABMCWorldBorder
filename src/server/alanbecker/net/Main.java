package server.alanbecker.net;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("ABMC Border Plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("ABMC Border Plugin has been disabled.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) || !((Player) sender).isOp()) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }
        if (command.getName().equalsIgnoreCase("abmcborder")) {
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Usage: /abmcborder <get|set|add|warning> [world]");
                return true;
            }
            if (args[0].equalsIgnoreCase("get")) {
                World world = Bukkit.getWorlds().get(0);
                if (args.length > 1) {
                    world = Bukkit.getWorld(args[1]);
                    if (world == null) {
                        sender.sendMessage(ChatColor.RED + "World not found.");
                        return true;
                    }
                }
                double size = world.getWorldBorder().getSize();
                sender.sendMessage(ChatColor.GREEN + "World border size in " + world.getName() + ": " + String.format("%.0f", size));
                return true;
            }
            if (args[0].equalsIgnoreCase("set")) {
                if (args.length < 3) {
                    sender.sendMessage(ChatColor.RED + "Usage: /abmcborder set <size> [time] [world]");
                    return true;
                }
                double size = Double.parseDouble(args[1]);
                int time = args.length > 3 ? Integer.parseInt(args[2]) : 0;
                World world = Bukkit.getWorld(args[3]);
                if (world == null) {
                    sender.sendMessage(ChatColor.RED + "World not found.");
                    return true;
                }
                world.getWorldBorder().setSize(size, time);
                sender.sendMessage(ChatColor.GREEN + "World border size set to " + String.format("%.0f", size) + " over " + time + " seconds in " + world.getName() + ".");
                return true;
            }
            if (args[0].equalsIgnoreCase("add")) {
                if (args.length < 3) {
                    sender.sendMessage(ChatColor.RED + "Usage: /abmcborder add <size> [time] [world]");
                    return true;
                }
                double size = Double.parseDouble(args[1]);
                int time = args.length > 3 ? Integer.parseInt(args[3]) : 0;
                World world = Bukkit.getWorld(args[2]);
                if (world == null) {
                    sender.sendMessage(ChatColor.RED + "World not found.");
                    return true;
                }
                world.getWorldBorder().setSize(size + world.getWorldBorder().getSize(), time);
                sender.sendMessage(ChatColor.GREEN + "World border size added " + String.format("%.0f", size) + " over " + time + " seconds in " + world.getName() + ".");
                return true;
            }
            if (args[0].equalsIgnoreCase("warning")) {
                if (args.length < 2) {
                    sender.sendMessage(ChatColor.RED + "Usage: /abmcborder warning <distance> [world]");
                    return true;
                }
                int distance = Integer.parseInt(args[1]);
                World world = Bukkit.getWorlds().get(0);
                if (args.length > 2) {
                    world = Bukkit.getWorld(args[2]);
                    if (world == null) {
                        sender.sendMessage(ChatColor.RED + "World not found.");
                        return true;
                    }
                }
                world.getWorldBorder().setWarningDistance(distance);
                sender.sendMessage(ChatColor.GREEN + "World border warning distance set to " + distance + " in " + world.getName() + ".");
                return true;
            }
            sender.sendMessage(ChatColor.RED + "Usage: /abmcborder <get|set|add|warning> [world]");
            return true;
        }
        return false;
    }
}
 
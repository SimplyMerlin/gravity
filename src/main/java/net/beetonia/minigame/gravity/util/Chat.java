package net.beetonia.minigame.gravity.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Chat {

    public static void send(CommandSender receiver, String message) {
        receiver.sendMessage(colorize(message));
    }

    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void log(String message) {
        send(Bukkit.getConsoleSender(), message);
    }

}

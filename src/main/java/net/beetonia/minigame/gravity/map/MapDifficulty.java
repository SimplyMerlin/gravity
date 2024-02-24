package net.beetonia.minigame.gravity.map;

import org.bukkit.ChatColor;

public enum MapDifficulty {

    EASY(ChatColor.GREEN, "Easy"),
    MEDIUM(ChatColor.YELLOW, "Medium"),
    HARD(ChatColor.RED, "Hard"),
    ELITE(ChatColor.LIGHT_PURPLE, "Elite");

    ChatColor color;
    String name;

    MapDifficulty(ChatColor color, String name) {
        this.color = color;
        this.name = name;
    }

    public ChatColor getColor() {
        return color;
    }

    public String getName() {
        return name;
    }
}

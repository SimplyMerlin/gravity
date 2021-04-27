package net.beetonia.minigame.gravity.scoreboard;

import io.github.thatkawaiisam.assemble.AssembleAdapter;
import net.beetonia.minigame.gravity.util.Chat;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PregameBoard implements AssembleAdapter {

    @Override
    public String getTitle(Player player) {
        return Chat.colorize("&6&lBee&e&ltonia");
    }

    @Override
    public List<String> getLines(Player player) {
        final List<String> toReturn = new ArrayList<>();

        toReturn.add("");
        toReturn.add("&a&lTokens");
        toReturn.add("&7N/A");
        toReturn.add("");
        toReturn.add("&e&lYour stats");
        toReturn.add("&3Points: &bN/A");
        toReturn.add("&3Games Played: &bN/A");
        toReturn.add("&3Victories: &bN/A");
        toReturn.add("&3Win streak: &bN/A");
        toReturn.add("&3Fails: &bN/A");
        toReturn.add("&3Hardcore Victories: &bN/A");
        toReturn.add("&3Hardcore Points: &bN/A");
        toReturn.add("");
        toReturn.add("&8&l-------------");
        toReturn.add("&6play.&eBeetonia&6.net");

        return toReturn;
    }

}
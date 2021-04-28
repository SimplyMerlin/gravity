package net.beetonia.minigame.gravity.scoreboard;

import io.github.thatkawaiisam.assemble.AssembleAdapter;
import net.beetonia.minigame.gravity.PlayerData;
import net.beetonia.minigame.gravity.manager.PlayerManager;
import net.beetonia.minigame.gravity.util.Chat;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PregameBoard implements AssembleAdapter {

    PlayerManager playerManager;

    public PregameBoard(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public String getTitle(Player player) {
        return Chat.colorize("&6&lBee&e&ltonia");
    }

    @Override
    public List<String> getLines(Player player) {

        PlayerData playerData = playerManager.getGamePlayer(player).getPlayerData();

        final List<String> toReturn = new ArrayList<>();

        toReturn.add("");
        toReturn.add("&a&lTokens");
        toReturn.add("&7N/A");
        toReturn.add("");
        toReturn.add("&e&lYour stats");
        toReturn.add("&3Points: &b" + playerData.points);
        toReturn.add("&3Games Played: &b" + playerData.gamesPlayed);
        toReturn.add("&3Victories: &b" + playerData.victories);
        toReturn.add("&3Win streak: &b" + playerData.winStreak);
        toReturn.add("&3Fails: &b" + playerData.totalFails);
        toReturn.add("&3Hardcore Victories: &b" + playerData.hardcoreVictories);
        toReturn.add("&3Hardcore Points: &b" + playerData.hardcorePoints);
        toReturn.add("");
        toReturn.add("&8&l-------------");
        toReturn.add("&6play.&eBeetonia&6.net");

        return toReturn;
    }

}
package net.beetonia.minigame.gravity.scoreboard;

import io.github.thatkawaiisam.assemble.AssembleAdapter;
import net.beetonia.minigame.gravity.GamePlayer;
import net.beetonia.minigame.gravity.manager.PlayerManager;
import net.beetonia.minigame.gravity.phase.GameplayPhase;
import net.beetonia.minigame.gravity.util.Chat;
import net.beetonia.minigame.gravity.util.TimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class GravityBoard implements AssembleAdapter {

    GameplayPhase phase;
    PlayerManager playerManager;

    public GravityBoard(GameplayPhase phase) {
        this.phase = phase;
        this.playerManager = phase.getPlayerManager();
    }

    @Override
    public String getTitle(Player player) {
        return Chat.colorize("&b&lGra&a&lvi&e&lty");
    }

    @Override
    public List<String> getLines(Player player) {
        final List<String> toReturn = new ArrayList<>();

        toReturn.add("&a");
        toReturn.add("&e&lTime Left");
        toReturn.add(TimeUtils.formatTimeMMSS(phase.getRemainingDuration().getSeconds()));
        toReturn.add("&b");
        toReturn.add("&b&lRanking");
        //toReturn.add("&7&l#1 &fWaiting..");
        /**
        int i = 1;
        for (UUID uuid : phase.getFinishers()) {
            if (i > 5) continue;

            toReturn.add("&a#" + i + " &9" + Bukkit.getPlayer(uuid).getName().substring(0, 9) + ". &f(" + TimeUtils.formatTimeMMSS(phase.getPlayerManager().getFinalTime(Bukkit.getPlayer(uuid)).getSeconds()) + ")");

            i++;
        }
        for (Map.Entry<Integer, UUID> e : phase.getPositions().entrySet()) {
            if (i > 5) continue;

            toReturn.add("&a#" + i + " &9" + Bukkit.getPlayer(e.getValue()).getName());

            i++;
        }
         **/
        int i = 1;
        for (GamePlayer gamePlayer : playerManager.getPlayers()) {
            if (i >= 5) break;
            if (gamePlayer.getFinished() != null) {
                toReturn.add("&a#" + i + " &9" + gamePlayer.getPlayer().getName().substring(0, 9) + ". &f(" + TimeUtils.formatTimeMMSS(Duration.between(phase.getStart(), gamePlayer.getFinished()).getSeconds()) + ")");
            } else {
                toReturn.add("&a#" + i + " &9" + gamePlayer.getPlayer().getName());
            }
            i++;
        }
        toReturn.add("&c");
        toReturn.add("&8&l-------------");
        toReturn.add("&6play.&eBeetonia&6.net");

        return toReturn;
    }

}

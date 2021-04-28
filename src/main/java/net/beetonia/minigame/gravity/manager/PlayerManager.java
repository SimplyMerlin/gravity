package net.beetonia.minigame.gravity.manager;

import net.beetonia.minigame.gravity.GamePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class PlayerManager {

    List<GamePlayer> players = new ArrayList<>();

    public void addPlayer(Player player) {
        players.add(new GamePlayer(player));
    }

    public void removePlayer(Player player) {
        players.removeIf(gamePlayer -> gamePlayer.getPlayer() == player);
    }

    public List<GamePlayer> sort() {
        players.sort((playerA, playerB) -> {
            if (playerA.getFinished() != null || playerB.getFinished() != null) {
                return (int) ((playerA.getFinished() != null ? playerA.getFinished().toEpochMilli() : 0) - (playerB.getFinished() != null ? playerB.getFinished().toEpochMilli() : 0));
            }
            if (playerA.getLevel() != playerB.getLevel()) {
                return playerB.getLevel() - playerA.getLevel();
            }
            return playerA.getY() - playerB.getY();

        });
        return players;
    }

    public List<GamePlayer> getPlayers() {
        return players;
    }

    public GamePlayer getGamePlayer(Player player) {
        for (GamePlayer gamePlayer : players) {
            if (gamePlayer.getPlayer() == player) return gamePlayer;
        }
        return null;
    }

    public int getPosition(GamePlayer gamePlayer) {
        return IntStream.range(0, players.size())
                .filter(i -> players.get(i).equals(gamePlayer))
                .findFirst().orElse(-1);
    }

}

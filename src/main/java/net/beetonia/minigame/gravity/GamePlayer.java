package net.beetonia.minigame.gravity;

import org.bukkit.entity.Player;

import java.time.Instant;

public class GamePlayer {

    Player player;

    PlayerData playerData;

    Instant finished = null;
    int level = 0;
    boolean online = true;
    int fails = 0;

    public GamePlayer(Player player) {
        this.player = player;
        this.playerData = new PlayerData(this);
    }

    public Player getPlayer() {
        return player;
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public int getLevel() {
        return level;
    }

    public Instant getFinished() {
        return finished;
    }

    public void setFinished(Instant finished) {
        this.finished = finished;
    }

    public int getY() {
        return (int) player.getLocation().getY();
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public int getFails() {
        return fails;
    }

    public void addFail() {
        fails++;
        playerData.totalFails++;
    }

    public void increaseLevel() {
        level++;
    }
}

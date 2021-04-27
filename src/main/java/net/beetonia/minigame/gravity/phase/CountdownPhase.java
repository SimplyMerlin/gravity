package net.beetonia.minigame.gravity.phase;

import net.beetonia.minigame.gravity.GamePlayer;
import net.beetonia.minigame.gravity.Gravity;
import net.beetonia.minigame.gravity.events.ShowtimeEvents;
import net.beetonia.minigame.gravity.manager.PlayerManager;
import net.beetonia.minigame.gravity.map.BaseMap;
import net.beetonia.minigame.gravity.state.GameState;
import net.beetonia.minigame.gravity.util.Chat;
import net.beetonia.minigame.gravity.util.CircleNumber;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public class CountdownPhase extends GameState {
    Duration duration;
    String levels;
    Gravity arena;

    public CountdownPhase(Gravity plugin, PlayerManager playerManager) {
        super(plugin, playerManager);
        arena = plugin;
        duration = Duration.ofSeconds(10);
    }

    @NotNull
    @Override
    public Duration getDuration() {
        return duration;
    }

    @Override
    protected void onEnd() {

    }

    @Override
    protected void onStart() {

        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (BaseMap map : arena.getCurrentMapSeries().getMaps()) {
            sb.append(map.getDifficulty().getColor()).append(map.getName());
            sb.append(ChatColor.GRAY).append(arena.getCurrentMapSeries().getMaps().size() - 2 == i ? " & " : arena.getCurrentMapSeries().getMaps().size() - 1 == i ? "." : ", ");
            i++;
        }

        levels = sb.toString();

        getGamePlayers().forEach(this::teleportToSpawn);

        getPlayers().forEach(player -> player.getPlayer().sendTitle(Chat.colorize("&6Gravity"), Chat.colorize("&7A &fplay.Beetonia.com &7Recreation"), 10, 60, 10));
        register(new ShowtimeEvents());
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }

    @Override
    public void onUpdatePerSecond() {
        getPlayers().forEach(player -> player.getPlayer().setLevel((int) getRemainingDuration().getSeconds() + 1));
        getPlayers().forEach(player -> player.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(levels)));
        if (getRemainingDuration().getSeconds() + 1 <= 5) {
            if (getRemainingDuration().getSeconds() + 1 == 1) {
                getPlayers().forEach(player -> {
                    player.getPlayer().sendTitle(Chat.colorize("&e" + CircleNumber.get((int) getRemainingDuration().getSeconds() + 1)),
                            Chat.colorize("&7until start"), 0, 20, 0);
                });
            } else if (getRemainingDuration().getSeconds() + 1 <= 3) {
                getPlayers().forEach(player -> {
                    player.getPlayer().sendTitle(Chat.colorize("&6" + CircleNumber.get((int) getRemainingDuration().getSeconds() + 1)),
                            Chat.colorize("&7until start"), 0, 30, 0);
                });
            } else {
                getPlayers().forEach(player -> {
                    player.getPlayer().sendTitle(Chat.colorize("&c" + CircleNumber.get((int) getRemainingDuration().getSeconds() + 1)),
                            Chat.colorize("&7until start"), 0, 30, 0);
                });
            }
        }
    }

    public void teleportToSpawn(GamePlayer player) {
        player.getPlayer().setVelocity(new Vector(0, 0, 0));
        player.getPlayer().setFallDistance(0);
        player.getPlayer().setHealth(20);
        if (arena.getCurrentMapSeries().getMaps().size() < player.getLevel()) {
            player.getPlayer().teleport(arena.getCurrentMapSeries().getMap(0).getSpawnpoints().get(0));
        } else {
            player.getPlayer().teleport(arena.getCurrentMapSeries().getMap(player.getLevel()).getSpawnpoints().get(0));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        playerManager.removePlayer(e.getPlayer());
    }

}

package net.beetonia.minigame.gravity.phase;

import io.github.thatkawaiisam.assemble.Assemble;
import io.github.thatkawaiisam.assemble.AssembleStyle;
import net.beetonia.minigame.gravity.manager.PlayerManager;
import net.beetonia.minigame.gravity.scoreboard.PregameBoard;
import net.beetonia.minigame.gravity.state.GameState;
import net.beetonia.minigame.gravity.util.Chat;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;

public class PregamePhase extends GameState {

    Assemble scoreboard;
    Boolean startedCountdown = false;
    Duration duration = Duration.ZERO;
    Boolean isReadyToEnd = false;

    public PregamePhase(JavaPlugin plugin, PlayerManager playerManager) {
        super(plugin, playerManager);
    }

    @NotNull
    @Override
    public Duration getDuration() {
        return duration;
    }

    @Override
    protected void onEnd() {
        scoreboard.cleanup();
    }

    @Override
    protected void onStart() {
        scoreboard = new Assemble(plugin, new PregameBoard(playerManager));
        scoreboard.setTicks(20);
        scoreboard.setAssembleStyle(AssembleStyle.MODERN);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        isReadyToEnd = startedCountdown && getRemainingDuration() == Duration.ZERO;
        if (getPlayers().size() >= 2) {
            if (!startedCountdown) {
                duration = Duration.ofSeconds(20);
                setStartInstant(Instant.now());
                startedCountdown = true;
            }
        } else {
            getPlayers().forEach(player -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Chat.colorize("&e" + (2 - getPlayers().size()) + " players needed to start..."))));
            startedCountdown = false;
        }
    }

    public void onUpdatePerSecond() {
        if (startedCountdown) {
            if (getRemainingDuration().getSeconds() + 1 > 5) {
                getPlayers().forEach(player -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Chat.colorize("&aStarting game in &l" + (getRemainingDuration().getSeconds() + 1)))));
            } else {
                getPlayers().forEach(player -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Chat.colorize("&aStarting game in &c&l" + (getRemainingDuration().getSeconds() + 1)))));
            }
        }
    }

    @Override
    public boolean isReadyToEnd() {
        return this.isReadyToEnd;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        playerManager.addPlayer(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        playerManager.removePlayer(e.getPlayer());
    }

}

package net.beetonia.minigame.gravity.state;

import net.beetonia.minigame.gravity.GamePlayer;
import net.beetonia.minigame.gravity.manager.PlayerManager;
import net.beetonia.minigame.gravity.util.Chat;
import net.minikloon.fsmgasm.State;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class GameState extends State implements Listener {
    protected final JavaPlugin plugin; // this could be your game's "main" class

    protected final PlayerManager playerManager;

    protected final Set<Listener> listeners = new HashSet<>();
    protected final Set<BukkitTask> tasks = new HashSet<>();
    protected long cachedDuration;

    public GameState(JavaPlugin plugin, PlayerManager playerManager) {
        this.plugin = plugin;
        this.playerManager = playerManager;
    }

    @Override
    public final void start() {
        super.start();
        register(this);
    }

    @Override
    public void onUpdate() {
        if (getRemainingDuration().getSeconds() != cachedDuration) {
            onUpdatePerSecond();
        }
        cachedDuration = getRemainingDuration().getSeconds();
    }

    @Override
    public final void end() {
        super.end();
        if (!super.getEnded())
            return;
        listeners.forEach(HandlerList::unregisterAll);
        tasks.forEach(BukkitTask::cancel);
        listeners.clear();
        tasks.clear();
    }

    protected final List<Player> getPlayers() {
        return playerManager.getPlayers().stream().map(GamePlayer::getPlayer).collect(Collectors.toList());
    }

    protected final List<GamePlayer> getGamePlayers() {
        return playerManager.getPlayers();
    }

    protected final void broadcast(String message) {
        getPlayers().forEach(p -> Chat.send(p.getPlayer(), message));
    }

    protected void register(Listener listener) {
        listeners.add(listener);
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    protected void schedule(Runnable runnable, long delay) {
        BukkitTask task = plugin.getServer().getScheduler().runTaskLater(plugin, runnable, delay);
        tasks.add(task);
    }

    protected void scheduleRepeating(Runnable runnable, long delay, long interval) {
        BukkitTask task = plugin.getServer().getScheduler().runTaskTimer(plugin, runnable, delay, interval);
        tasks.add(task);
    }

    public void onUpdatePerSecond() {

    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }
}
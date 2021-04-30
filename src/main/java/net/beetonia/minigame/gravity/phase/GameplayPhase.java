package net.beetonia.minigame.gravity.phase;

import io.github.thatkawaiisam.assemble.Assemble;
import io.github.thatkawaiisam.assemble.AssembleStyle;
import net.beetonia.minigame.gravity.GamePlayer;
import net.beetonia.minigame.gravity.Gravity;
import net.beetonia.minigame.gravity.events.ShowtimeEvents;
import net.beetonia.minigame.gravity.manager.PlayerManager;
import net.beetonia.minigame.gravity.map.BaseMap;
import net.beetonia.minigame.gravity.scoreboard.GravityBoard;
import net.beetonia.minigame.gravity.state.GameState;
import net.beetonia.minigame.gravity.util.Chat;
import net.beetonia.minigame.gravity.util.CircleNumber;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.Instant;

public class GameplayPhase extends GameState {

    Assemble scoreboard;
    Gravity gravity;
    Boolean playerFinished = false;
    Duration duration = Duration.ofMinutes(10);
    Instant start;

    public GameplayPhase(Gravity plugin, PlayerManager playerManager) {
        super(plugin, playerManager);
        this.gravity = plugin;
    }

    @Override
    protected void onEnd() {
        scoreboard.cleanup();
        System.out.println("Game ended, shut down the server.");
        getPlayers().forEach(player -> player.kickPlayer("Server is restarting"));
        Bukkit.getServer().shutdown();
    }

    @Override
    public void onStart() {
        register(new ShowtimeEvents());
        scoreboard = new Assemble(plugin, new GravityBoard(this));
        scoreboard.setTicks(2);
        scoreboard.setAssembleStyle(AssembleStyle.MODERN);
        gravity.getCurrentMapSeries().getMaps().forEach(BaseMap::openSpawn);
        getGamePlayers().forEach(player -> {
            showStage(player);
            start = Instant.now();
            player.getPlayer().setLevel((int) player.getPlayer().getLocation().getY());
        });

        scheduleRepeating(playerManager::sort, 0, 4);

    }

    @Override
    public void onUpdate() {
        super.onUpdate();
    }

    @Override
    public void onUpdatePerSecond() {
        super.onUpdatePerSecond();
        int allPlayers = getGamePlayers().size();
        getGamePlayers().forEach(player -> {
            int fail = player.getFails();
            StringBuilder sb = new StringBuilder();
            sb.append("&bPosition ");
            sb.append("&e&l").append(playerManager.getPosition(player) + 1);
            sb.append("&7/");
            sb.append("&c").append(allPlayers);
            sb.append("&8 | ");
            for (int x = 0; x < gravity.getCurrentMapSeries().getMaps().size(); x++) {
                if (x == player.getLevel()) sb.append(ChatColor.YELLOW);
                else if (x > player.getLevel()) sb.append(ChatColor.WHITE);
                else sb.append(ChatColor.GREEN);
                sb.append(CircleNumber.get(x + 1));
            }
            sb.append("&8 | ");
            sb.append("&c&l");
            sb.append(fail);
            sb.append(" &f");
            sb.append(fail == 1 ? "Fail" : "Fails");

            player.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Chat.colorize(sb.toString())));
        });

    }

    @NotNull
    @Override
    public Duration getDuration() {
        return duration;
    }

    public void playerWon(GamePlayer player) {
        player.setFinished(Instant.now());
        playerFinished();
        Chat.send(player.getPlayer(), "You win!");
        Chat.send(player.getPlayer(), "Your time: " + Duration.between(start, player.getFinished()).getSeconds());
    }

    public void playerFinished() {
        if (!playerFinished) {
            playerFinished = true;
            setStartInstant(Instant.now());
            duration = Duration.ofMinutes(2);
            getPlayers().forEach(player -> player.sendTitle(Chat.colorize("&eA player has finished!"), Chat.colorize("&7Game ending in 2 minutes..."), 10, 20, 10));
        }
    }

    public void teleportToSpawn(GamePlayer player) {
        player.getPlayer().setVelocity(new Vector(0, 0, 0));
        player.getPlayer().setFallDistance(0);
        player.getPlayer().setHealth(20);
        if (gravity.getCurrentMapSeries().getMaps().size() - 1 <= player.getLevel()) {
            player.getPlayer().teleport(gravity.getCurrentMapSeries().getMap(0).getSpawnpoints().get(0));
        } else {
            player.getPlayer().teleport(gravity.getCurrentMapSeries().getMap(player.getLevel()).getSpawnpoints().get(0));
        }
    }

    public void showStage(GamePlayer player) {
        BaseMap map = gravity.getCurrentMapSeries().getMap(player.getLevel());
        player.getPlayer().sendTitle("Stage " + (player.getLevel() + 1), Chat.colorize(map.getName() + "&8 - " + map.getDifficulty().getColor() + map.getDifficulty().getName()), 0, 20, 10);
    }

    public Instant getStart() {
        return start;
    }

    //Events

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        GamePlayer gamePlayer = playerManager.getGamePlayer(player);
        e.getPlayer().setLevel((int) e.getTo().getY());
        if (e.getTo().getBlock().isLiquid()) {
            if (gamePlayer.getLevel() >= gravity.getCurrentMapSeries().getMaps().size() - 1) {
                if (gamePlayer.getFinished() == null) {
                    playerWon(gamePlayer);
                    gamePlayer.increaseLevel();
                    teleportToSpawn(gamePlayer);
                }
            } else {
                gamePlayer.increaseLevel();
                teleportToSpawn(gamePlayer);
                showStage(gamePlayer);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntityType() != EntityType.PLAYER) {
            return;
        }
        Player player = (Player) e.getEntity();
        if (player.getHealth() - e.getDamage() <= 0) {
            teleportToSpawn(playerManager.getGamePlayer(player));
            playerManager.getGamePlayer(player).addFail();
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        playerManager.getGamePlayer(e.getPlayer()).setOnline(false);
    }

}

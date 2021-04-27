package net.beetonia.minigame.gravity.events;

import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class GlobalEvents implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.getPlayer().setGameMode(GameMode.ADVENTURE);
    }

    @EventHandler
    public void onRegenerate(EntityRegainHealthEvent e) {
        e.setCancelled(e.getEntityType() == EntityType.PLAYER);
    }

    @EventHandler
    public void onPvP(EntityDamageByEntityEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        e.setCancelled(e.getPlayer().getGameMode() != GameMode.CREATIVE);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        e.setCancelled(e.getPlayer().getGameMode() != GameMode.CREATIVE);
    }

}
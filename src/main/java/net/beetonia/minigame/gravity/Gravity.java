package net.beetonia.minigame.gravity;

import co.aikar.commands.PaperCommandManager;
import net.beetonia.minigame.gravity.command.FreezePhaseCommand;
import net.beetonia.minigame.gravity.command.SkipPhaseCommand;
import net.beetonia.minigame.gravity.events.GlobalEvents;
import net.beetonia.minigame.gravity.manager.PlayerManager;
import net.beetonia.minigame.gravity.map.MapSelector;
import net.beetonia.minigame.gravity.map.MapSeries;
import net.beetonia.minigame.gravity.phase.CountdownPhase;
import net.beetonia.minigame.gravity.phase.GameplayPhase;
import net.beetonia.minigame.gravity.phase.PregamePhase;
import net.beetonia.minigame.gravity.state.ScheduledStateSeries;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public final class Gravity extends JavaPlugin {

    PaperCommandManager paperCommandManager;
    ScheduledStateSeries mainState;
    MapSeries currentMapSeries;
    World arenaWorld;
    PlayerManager playerManager;

    @Override
    public void onEnable() {
        arenaWorld = Bukkit.getWorld("dropper");
        Bukkit.getWorld("dropper").setAutoSave(false);
        MapSeries mapSeries = new MapSeries();
        MapSelector mapSelector = new MapSelector(this);
        for (int i = 0; i < 5; i++) {
            mapSeries.addMap(mapSelector.getMaps().get(i));
        }
        setCurrentMapSeries(mapSeries);

        initializeStates();
        registerCommands();
        getServer().getPluginManager().registerEvents(new GlobalEvents(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerCommands() {
        paperCommandManager = new PaperCommandManager(this);
        paperCommandManager.registerCommand(new SkipPhaseCommand(mainState));
        paperCommandManager.registerCommand(new FreezePhaseCommand(mainState));
    }

    private void initializeStates() {
        playerManager = new PlayerManager();
        mainState = new ScheduledStateSeries(this);
        mainState.add(new PregamePhase(this, playerManager));
        mainState.add(new CountdownPhase(this, playerManager));
        mainState.add(new GameplayPhase(this, playerManager));
        mainState.start();
    }

    public MapSeries getCurrentMapSeries() {
        return currentMapSeries;
    }

    public void setCurrentMapSeries(MapSeries mapSeries) {
        currentMapSeries = mapSeries;
    }

    public World getArenaWorld() {
        return arenaWorld;
    }
}

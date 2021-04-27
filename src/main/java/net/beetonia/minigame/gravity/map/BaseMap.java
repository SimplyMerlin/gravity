package net.beetonia.minigame.gravity.map;

import org.bukkit.Location;

import java.util.List;

public interface BaseMap {

    String getName();

    String getID();

    MapDifficulty getDifficulty();

    List<Location> getSpawnpoints();

    void openSpawn();
}

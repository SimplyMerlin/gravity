package net.beetonia.minigame.gravity.map.maps;

import net.beetonia.minigame.gravity.Gravity;
import net.beetonia.minigame.gravity.map.BaseMap;
import net.beetonia.minigame.gravity.map.MapDifficulty;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class CarsMap implements BaseMap {

    Gravity plugin;
    World world;
    List<Block> blocks;

    public CarsMap(Gravity plugin) {
        this.world = plugin.getArenaWorld();

        blocks = new ArrayList<>();
        blocks.add(world.getBlockAt(-556, 250, -749));
        blocks.add(world.getBlockAt(-556, 250, -750));
        blocks.add(world.getBlockAt(-556, 250, -751));
        blocks.add(world.getBlockAt(-556, 250, -752));
        blocks.add(world.getBlockAt(-556, 250, -753));
    }

    @Override
    public String getName() {
        return "Cars";
    }

    @Override
    public String getID() {
        return "CARS";
    }

    @Override
    public MapDifficulty getDifficulty() {
        return MapDifficulty.EASY;
    }

    @Override
    public List<Location> getSpawnpoints() {
        final List<Location> toReturn = new ArrayList<>();
        toReturn.add(new Location(world, -555.5, 252, -746.5, 180, 0));
        return toReturn;
    }

    @Override
    public void openSpawn() {
        for (Block block : blocks) {
            block.setType(Material.AIR);
        }
    }
}

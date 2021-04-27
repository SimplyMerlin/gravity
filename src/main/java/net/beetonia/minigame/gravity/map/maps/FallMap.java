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

public class FallMap implements BaseMap {

    Gravity plugin;
    World world;
    List<Block> blocks;

    public FallMap(Gravity plugin) {
        this.world = plugin.getArenaWorld();

        blocks = new ArrayList<>();
        blocks.add(world.getBlockAt(-635, 250, -693));
        blocks.add(world.getBlockAt(-635, 250, -692));
        blocks.add(world.getBlockAt(-635, 250, -691));
        blocks.add(world.getBlockAt(-635, 250, -690));
        blocks.add(world.getBlockAt(-635, 250, -689));
    }

    @Override
    public String getName() {
        return "Fall";
    }

    @Override
    public String getID() {
        return "FALL";
    }

    @Override
    public MapDifficulty getDifficulty() {
        return MapDifficulty.MEDIUM;
    }

    @Override
    public List<Location> getSpawnpoints() {
        final List<Location> toReturn = new ArrayList<>();
        toReturn.add(new Location(world, -634.5, 252, -694.5, -90, 0));
        return toReturn;
    }

    @Override
    public void openSpawn() {
        for (Block block : blocks) {
            block.setType(Material.AIR);
        }
    }
}

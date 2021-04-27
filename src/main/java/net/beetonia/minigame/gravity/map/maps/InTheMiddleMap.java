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

public class InTheMiddleMap implements BaseMap {

    Gravity plugin;
    World world;
    List<Block> blocks;

    public InTheMiddleMap(Gravity plugin) {
        this.world = plugin.getArenaWorld();

        blocks = new ArrayList<>();
        blocks.add(world.getBlockAt(-582, 253, -701));
        blocks.add(world.getBlockAt(-582, 252, -701));
    }

    @Override
    public String getName() {
        return "In the middle";
    }

    @Override
    public String getID() {
        return "IN_THE_MIDDLE";
    }

    @Override
    public MapDifficulty getDifficulty() {
        return MapDifficulty.EASY;
    }

    @Override
    public List<Location> getSpawnpoints() {
        final List<Location> toReturn = new ArrayList<>();
        toReturn.add(new Location(world, -581.5, 252, -703.5, 0, 0));
        return toReturn;
    }

    @Override
    public void openSpawn() {
        for (Block block : blocks) {
            block.setType(Material.AIR);
        }
    }
}

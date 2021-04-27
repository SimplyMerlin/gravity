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

public class MineMap implements BaseMap {

    Gravity plugin;
    World world;
    List<Block> blocks;

    public MineMap(Gravity plugin) {
        this.world = plugin.getArenaWorld();

        blocks = new ArrayList<>();
        blocks.add(world.getBlockAt(-525, 250, -753));
        blocks.add(world.getBlockAt(-525, 250, -754));
        blocks.add(world.getBlockAt(-525, 250, -755));
        blocks.add(world.getBlockAt(-525, 250, -756));
        blocks.add(world.getBlockAt(-525, 250, -757));
        blocks.add(world.getBlockAt(-525, 250, -758));
    }

    @Override
    public String getName() {
        return "Mine";
    }

    @Override
    public String getID() {
        return "MINE";
    }

    @Override
    public MapDifficulty getDifficulty() {
        return MapDifficulty.MEDIUM;
    }

    @Override
    public List<Location> getSpawnpoints() {
        final List<Location> toReturn = new ArrayList<>();
        toReturn.add(new Location(world, -524.5, 252, -750.5, 180, 0));
        return toReturn;
    }

    @Override
    public void openSpawn() {
        for (Block block : blocks) {
            block.setType(Material.AIR);
        }
    }
}

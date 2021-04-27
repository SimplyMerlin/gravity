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

public class ColorsMap implements BaseMap {

    Gravity plugin;
    World world;
    List<Block> blocks;

    public ColorsMap(Gravity plugin) {
        this.world = plugin.getArenaWorld();

        blocks = new ArrayList<>();
        blocks.add(world.getBlockAt(-247, 253, -498));
        blocks.add(world.getBlockAt(-247, 252, -498));
    }

    @Override
    public String getName() {
        return "Colors";
    }

    @Override
    public String getID() {
        return "COLORS";
    }

    @Override
    public MapDifficulty getDifficulty() {
        return MapDifficulty.INSANE;
    }

    @Override
    public List<Location> getSpawnpoints() {
        final List<Location> toReturn = new ArrayList<>();
        toReturn.add(new Location(world, -243.5, 252, -497.5, 90, 0));
        return toReturn;
    }

    @Override
    public void openSpawn() {
        for (Block block : blocks) {
            block.setType(Material.AIR);
        }
    }
}

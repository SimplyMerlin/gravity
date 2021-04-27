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

public class MadnessMap implements BaseMap {

    Gravity plugin;
    World world;
    List<Block> blocks;

    public MadnessMap(Gravity plugin) {
        this.world = plugin.getArenaWorld();

        blocks = new ArrayList<>();
        blocks.add(world.getBlockAt(-613, 250, -746));
        blocks.add(world.getBlockAt(-612, 250, -746));
        blocks.add(world.getBlockAt(-611, 250, -746));
        blocks.add(world.getBlockAt(-610, 250, -746));
        blocks.add(world.getBlockAt(-609, 250, -746));
    }

    @Override
    public String getName() {
        return "Madness";
    }

    @Override
    public String getID() {
        return "MADNESS";
    }

    @Override
    public MapDifficulty getDifficulty() {
        return MapDifficulty.EASY;
    }

    @Override
    public List<Location> getSpawnpoints() {
        final List<Location> toReturn = new ArrayList<>();
        toReturn.add(new Location(world, -615.5, 252, -745.5, -90, 0));
        return toReturn;
    }

    @Override
    public void openSpawn() {
        for (Block block : blocks) {
            block.setType(Material.AIR);
        }
    }
}

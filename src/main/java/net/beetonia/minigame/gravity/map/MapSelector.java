package net.beetonia.minigame.gravity.map;

import net.beetonia.minigame.gravity.Gravity;
import net.beetonia.minigame.gravity.map.maps.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapSelector {

    List<BaseMap> maps;

    public MapSelector(Gravity gravity) {
        maps = new ArrayList<BaseMap>();
        maps.add(new CarsMap(gravity));
        maps.add(new ColorsMap(gravity));
        maps.add(new FallMap(gravity));
        maps.add(new InTheMiddleMap(gravity));
        maps.add(new MadnessMap(gravity));
        maps.add(new MineMap(gravity));

        Collections.shuffle(maps);
    }

    public List<BaseMap> getMaps() {
        return maps;
    }
}

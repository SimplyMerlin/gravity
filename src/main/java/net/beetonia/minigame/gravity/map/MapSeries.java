package net.beetonia.minigame.gravity.map;

import java.util.ArrayList;
import java.util.List;

public class MapSeries {

    List<BaseMap> maps;


    public MapSeries() {
        maps = new ArrayList<BaseMap>();
    }

    public List<BaseMap> getMaps() {
        return maps;
    }

    public BaseMap getMap(int i) {
        return maps.get(i);
    }

    public void addMap(BaseMap map) {
        maps.add(map);
    }

}

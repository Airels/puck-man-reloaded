package view.maps;

import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.loadables.LoadableMap;

public class MapLevelLoader {

    private LoadableMap currentMap;
    private KernelEngine deltaEngine;

    public MapLevelLoader(KernelEngine deltaEngine) {
        this.deltaEngine = deltaEngine;
    }

    public void loadMapLevel(LoadableMap map) {
        if (currentMap != null)
            this.unloadMapLevel(currentMap);

        map.load(deltaEngine);
        currentMap = map;
    }

    public void unloadMapLevel(LoadableMap inputs) {
        inputs.unload(deltaEngine);
        currentMap = null;
    }
}

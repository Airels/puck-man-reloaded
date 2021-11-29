package view.maps;

import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.loadables.LoadableMap;
import model.utils.WallSpriteApplier;

/**
 * Component of Level Loader. Loads the given map of the level.
 */
public final  class MapLevelLoader {

    private final KernelEngine deltaEngine;
    private LoadableMap currentMap;

    /**
     * Default constructor.
     * @param deltaEngine the Engine
     */
    public MapLevelLoader(KernelEngine deltaEngine) {
        this.deltaEngine = deltaEngine;
    }

    /**
     * Load given map
     * @param map LoadableMap to load
     */
    public void loadMapLevel(LoadableMap map) {
        if (currentMap != null)
            this.unloadMapLevel(currentMap);

        map.load(deltaEngine);

        WallSpriteApplier.apply(map.getMapLevel());

        currentMap = map;
    }

    /**
     * Unload given map
     * @param map LoadableMap to unload
     */
    public void unloadMapLevel(LoadableMap map) {
        map.unload(deltaEngine);
        currentMap = null;
    }
}

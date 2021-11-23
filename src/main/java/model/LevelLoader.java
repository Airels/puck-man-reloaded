package model;

import controller.InputsLoader;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.levels.Level;
import view.maps_levels.MapLevelLoader;

/**
 * Level Loader. Loads given Level (its map and inputs), and load it on the Engine
 */
public final class LevelLoader {

    private final MapLevelLoader mapLoader;
    private final InputsLoader inputsLoader;
    private final KernelEngine deltaEngine;
    private Level currentLevel;

    public LevelLoader(KernelEngine deltaEngine) {
        this.deltaEngine = deltaEngine;
        this.mapLoader = new MapLevelLoader(deltaEngine);
        this.inputsLoader = new InputsLoader(deltaEngine);
    }

    /**
     * Load given level, its map and its inputs
     * @param level Level to load
     */
    public void load(Level level) {
        if (currentLevel != null)
            unload(currentLevel);

        mapLoader.loadMapLevel(level.getMapLevelLoadable());
        inputsLoader.loadInputs(level.getInputsLoadable());
        level.load(deltaEngine);
        currentLevel = level;
    }

    /**
     * Unload given level, its maps and its inputs,
     * @param level Level to unload
     */
    public void unload(Level level) {
        level.unload(deltaEngine);
        mapLoader.unloadMapLevel(level.getMapLevelLoadable());
        inputsLoader.unloadInputs(level.getInputsLoadable());
        currentLevel = null;
    }

    /**
     * Returns current level of the game
     * @return current Level, or null if no level is currently running
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }
}

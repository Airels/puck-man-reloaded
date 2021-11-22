package model;

import controller.InputsLoader;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.maps.Level;
import view.maps.MapLevelLoader;

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
     * Loads level, its map and its inputs,
     * and set number of PacGums in level according to retrieved value in associated Map.
     * @param level level to load
     */
    public void load(Level level) {
        if (currentLevel != null)
            unload(currentLevel);

        level.load(deltaEngine);
        mapLoader.loadMapLevel(level.getMapLevelLoadable());
        inputsLoader.loadInputs(level.getInputsLoadable());
        currentLevel = level;

        level.setNbOfPacGums(level.getMapLevelLoadable().getNbOfGeneratedPacGums());
    }

    public void unload(Level level) {
        level.unload(deltaEngine);
        mapLoader.unloadMapLevel(level.getMapLevelLoadable());
        inputsLoader.unloadInputs(level.getInputsLoadable());
        currentLevel = null;
    }

    public Level getCurrentLevel() {
        return currentLevel;
    }
}

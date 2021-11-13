package model;

import controller.InputsLoader;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.levels.Level;
import view.MapLevelLoader;

public class LevelLoader {

    private MapLevelLoader mapLoader;
    private InputsLoader inputsLoader;

    public LevelLoader(KernelEngine deltaEngine) {
        this.mapLoader = new MapLevelLoader(deltaEngine);
        this.inputsLoader = new InputsLoader(deltaEngine);
    }

    public void load(Level level) {
        level.load();
        mapLoader.loadMapLevel(level.getMapLevelLoadable());
        inputsLoader.loadInputs(level.getInputsLoadable());
    }

    public void unload(Level level) {
        level.unload();
        mapLoader.unloadMapLevel(level.getMapLevelLoadable());
        inputsLoader.unloadInputs(level.getInputsLoadable());
    }
}

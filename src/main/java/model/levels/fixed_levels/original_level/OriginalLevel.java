package model.levels.fixed_levels.original_level;

import controller.maps.original_level.OriginalLevelInputs;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.elements.entities.PacMan;
import model.levels.Level;
import model.loadables.LoadableInput;
import model.loadables.LoadableMap;
import view.maps.original_level.OriginalLevelMap;

public class OriginalLevel implements Level {

    private final LoadableMap mapLevel;
    private final LoadableInput inputLevel;
    private final PacMan pacMan;

    public OriginalLevel() {
        this.pacMan = new PacMan();

        this.mapLevel = new OriginalLevelMap(pacMan);
        this.inputLevel = new OriginalLevelInputs(pacMan);
    }

    @Override
    public void load(KernelEngine deltaEngine) {

    }

    @Override
    public void unload(KernelEngine deltaEngine) {

    }

    @Override
    public LoadableMap getMapLevelLoadable() {
        return mapLevel;
    }

    @Override
    public LoadableInput getInputsLoadable() {
        return inputLevel;
    }
}

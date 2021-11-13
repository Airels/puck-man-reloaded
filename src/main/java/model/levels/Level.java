package model.levels;

import model.loadables.LoadableInput;
import model.loadables.LoadableMap;

public class Level {

    private LoadableMap mapLevel;
    private LoadableInput inputs;

    public Level(LoadableMap map, LoadableInput inputs) {
        this.mapLevel = map;
        this.inputs = inputs;
    }

    public LoadableMap getMapLevelLoadable() {
        return mapLevel;
    }

    public LoadableInput getInputsLoadable() {
        return inputs;
    }
}

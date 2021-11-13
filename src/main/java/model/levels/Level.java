package model.levels;

import model.loadables.LoadableInput;
import model.loadables.LoadableMap;

public interface Level {

    void load();

    void unload();

    LoadableMap getMapLevelLoadable();

    LoadableInput getInputsLoadable();
}

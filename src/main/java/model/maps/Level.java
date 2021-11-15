package model.maps;

import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.loadables.LoadableInput;
import model.loadables.LoadableMap;

public interface Level {

    void load(KernelEngine deltaEngine);

    void unload(KernelEngine deltaEngine);

    LoadableMap getMapLevelLoadable();

    LoadableInput getInputsLoadable();
}

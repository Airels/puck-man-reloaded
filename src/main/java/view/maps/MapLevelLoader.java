package view.maps;

import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.loadables.LoadableMap;

public class MapLevelLoader {

    private LoadableMap currentInputs;
    private KernelEngine deltaEngine;

    public MapLevelLoader(KernelEngine deltaEngine) {
        this.deltaEngine = deltaEngine;
    }

    public void loadMapLevel(LoadableMap inputs) {
        if (currentInputs != null)
            this.unloadMapLevel(currentInputs);

        inputs.load(deltaEngine);
        currentInputs = inputs;
    }

    public void unloadMapLevel(LoadableMap inputs) {
        inputs.unload(deltaEngine);
        currentInputs = null;
    }
}

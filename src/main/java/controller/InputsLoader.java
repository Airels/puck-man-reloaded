package controller;

import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.loadables.LoadableInput;

/**
 * A Loader who loading inputs.
 * Call load method from LoadableInput to load inputs given into the engine.
 * Memorize the current inputs loaded, and unload them when trying to load inputs again without unload previous ones.
 */
public final class InputsLoader {

    private LoadableInput currentInputs;
    private KernelEngine deltaEngine;

    /**
     * Default constructor.
     * @param deltaEngine The DeltaEngine
     */
    public InputsLoader(KernelEngine deltaEngine) {
        this.deltaEngine = deltaEngine;
    }

    /**
     * Loads given inputs. If previous inputs wasn't unloaded, it will unload it before.
     * @param inputs Inputs to load
     */
    public void loadInputs(LoadableInput inputs) {
        if (currentInputs != null)
            this.unloadInputs(currentInputs);

        inputs.load(deltaEngine);
        currentInputs = inputs;
    }

    /**
     * Unload given inputs.
     * @param inputs Inputs to unload
     */
    public void unloadInputs(LoadableInput inputs) {
        inputs.unload(deltaEngine);
        currentInputs = null;
    }
}

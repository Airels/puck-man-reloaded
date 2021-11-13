package controller;

import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.loadables.LoadableInput;

public class InputsLoader {

    private LoadableInput currentInputs;
    private KernelEngine deltaEngine;

    public InputsLoader(KernelEngine deltaEngine) {
        this.deltaEngine = deltaEngine;
    }

    public void loadInputs(LoadableInput inputs) {
        if (currentInputs != null)
            this.unloadInputs(currentInputs);

        inputs.load(deltaEngine);
        currentInputs = inputs;
    }

    public void unloadInputs(LoadableInput inputs) {
        inputs.unload(deltaEngine);
        currentInputs = null;
    }
}

package controller;

import model.loadables.LoadableInput;

public class InputsLoader {

    private LoadableInput currentInputs;

    public void loadInputs(LoadableInput inputs) {
        if (currentInputs != null)
            this.unloadInputs(currentInputs);

        inputs.load();
        currentInputs = inputs;
    }

    public void unloadInputs(LoadableInput inputs) {
        inputs.unload();
        currentInputs = null;
    }
}

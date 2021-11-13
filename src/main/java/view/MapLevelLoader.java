package view;

import model.loadables.LoadableMap;

public class MapLevelLoader {

    private LoadableMap currentInputs;

    public void loadInputs(LoadableMap inputs) {
        if (currentInputs != null)
            this.unloadInputs(currentInputs);

        inputs.load();
        currentInputs = inputs;
    }

    public void unloadInputs(LoadableMap inputs) {
        inputs.unload();
        currentInputs = null;
    }
}

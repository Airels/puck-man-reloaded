package model.levels.generators;

import model.levels.Level;
import model.loadables.LoadableInput;

/**
 * A loadable inputs builder, allowing to build default LoadableInputs
 */
public enum LoadableInputBuilder {

    CLASSIC() {
        @Override
        public LoadableInput build(Level level) {
            return new ClassicLoadableInput(level);
        }
    };

    LoadableInputBuilder() {
    }

    public abstract LoadableInput build(Level level);

}

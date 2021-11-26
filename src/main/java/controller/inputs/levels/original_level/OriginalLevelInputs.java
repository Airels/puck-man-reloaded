package controller.inputs.levels.original_level;

import controller.elements.entities.PacManInputs;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.elements.entities.PacMan;
import model.loadables.LoadableInput;

/**
 * Inputs of the Original level. Loads required inputs for the map.
 */
public class OriginalLevelInputs implements LoadableInput {

    private PacMan pacman;
    private PacManInputs pacManInputs;

    public OriginalLevelInputs(PacMan pacMan) {
        this.pacman = pacMan;
        pacManInputs = new PacManInputs(pacMan);
    }

    @Override
    public void load(KernelEngine engine) {
        pacManInputs.load(engine);
    }

    @Override
    public void unload(KernelEngine engine) {
        pacManInputs.unload(engine);
    }
}

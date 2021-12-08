package model.levels.generators;

import controller.elements.entities.PacManInputs;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.elements.entities.PacMan;
import model.levels.Level;
import model.loadables.LoadableInput;

/**
 * Loading classical inputs, for each new levels
 */
public class ClassicLoadableInput implements LoadableInput {

    private final PacMan pacMan;
    private final PacManInputs pacManInputs;

    public ClassicLoadableInput (Level level) {
        pacMan = level.getGame().getPacMan();
        pacManInputs = new PacManInputs(pacMan);
    }

    @Override
    public void load (KernelEngine engine) {
        pacManInputs.load(engine);
    }

    @Override
    public void unload (KernelEngine engine) {
        pacManInputs.unload(engine);
    }

}

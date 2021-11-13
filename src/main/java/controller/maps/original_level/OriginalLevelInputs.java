package controller.maps.original_level;

import controller.elements.entities.PacManInputs;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.elements.entities.PacMan;
import model.loadables.LoadableInput;

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

        // TODO Escape = Quit OR Return to menu OR halt execution (pause) ?
    }

    @Override
    public void unload(KernelEngine engine) {
        pacManInputs.unload(engine);
    }
}

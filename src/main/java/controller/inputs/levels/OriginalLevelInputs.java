package controller.inputs.levels;

import controller.elements.entities.PacManInputs;
import controller.inputs.GameInputs;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.Game;
import model.elements.entities.PacMan;
import model.loadables.LoadableInput;

/**
 * Inputs of the Original level. Loads required inputs for the map.
 */
public class OriginalLevelInputs implements LoadableInput {

    private final LoadableInput pacManInputs, gameInputs;
    private boolean firstTime;

    public OriginalLevelInputs(Game game) {
        pacManInputs = new PacManInputs(game.getPacMan());
        gameInputs = new GameInputs(game);
        firstTime = true;
    }

    @Override
    public void load(KernelEngine engine) {
        pacManInputs.load(engine);
        if (firstTime) {
            new Thread(() -> {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                gameInputs.load(engine);
            }).start();
            firstTime = false;
        } else {
            gameInputs.load(engine);
        }
    }

    @Override
    public void unload(KernelEngine engine) {
        pacManInputs.unload(engine);
        gameInputs.unload(engine);
    }
}

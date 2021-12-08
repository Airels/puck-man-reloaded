package controller.inputs.levels;

import controller.elements.entities.PacManInputs;
import controller.inputs.GameInputs;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.Game;
import model.loadables.LoadableInput;

/**
 * Inputs of the Original level. Loads required inputs for the map.
 */
public class OriginalLevelInputs implements LoadableInput {

    private final LoadableInput pacManInputs, gameInputs;
    private boolean firstTime;

    /**
     * Default constructor.
     *
     * @param game      The Game
     * @param firstTime if it's the first time to load
     */
    public OriginalLevelInputs(Game game, boolean firstTime) {
        pacManInputs = new PacManInputs(game.getPacMan());
        gameInputs = new GameInputs(game);
        this.firstTime = firstTime;
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

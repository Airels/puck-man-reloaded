package controller.inputs.levels;

import fr.r1r0r0.deltaengine.exceptions.InputKeyStackingError;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.engines.utils.Key;
import fr.r1r0r0.deltaengine.model.events.InputEvent;
import model.Game;
import model.loadables.LoadableInput;

/**
 * Inputs of the Pause menu
 */
public class PauseMenuInputs implements LoadableInput {

    private final Game game;
    private final InputEvent quitGameEvent, resumeGameEvent;

    public PauseMenuInputs(Game game) {
        this.game = game;
        resumeGameEvent = new InputEvent(null, game::resumeGame);
        quitGameEvent = new InputEvent(null, game::quitGame);
    }

    /**
     * Load the object
     *
     * @param engine DeltaEngine
     */
    @Override
    public void load(KernelEngine engine) {
        try {
            engine.setInput(Key.ESCAPE, resumeGameEvent);
            engine.setInput(Key.Q, quitGameEvent);
        } catch (InputKeyStackingError e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Unload the object
     *
     * @param engine DeltaEngine
     */
    @Override
    public void unload(KernelEngine engine) {
        engine.clearInput(Key.ESCAPE);
        engine.clearInput(Key.Q);
    }
}

package controller.inputs;

import fr.r1r0r0.deltaengine.exceptions.InputKeyStackingError;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.engines.utils.Key;
import fr.r1r0r0.deltaengine.model.events.InputEvent;
import model.Game;
import model.loadables.LoadableInput;

/**
 * Default games inputs. Could be used anywhere, in any levels.
 */
public class GameInputs implements LoadableInput {

    private final Game game;
    private final InputEvent openPauseEvent;

    public GameInputs(Game game) {
        this.game = game;
        openPauseEvent = new InputEvent(null, game::pauseGame);
    }

    /**
     * Load the object
     *
     * @param engine DeltaEngine
     */
    @Override
    public void load(KernelEngine engine) {
        try {
            engine.setInput(Key.ESCAPE, openPauseEvent);
        } catch (InputKeyStackingError e) {
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
    }
}
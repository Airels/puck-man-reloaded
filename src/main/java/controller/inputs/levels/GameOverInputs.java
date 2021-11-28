package controller.inputs.levels;

import fr.r1r0r0.deltaengine.exceptions.InputKeyStackingError;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.engines.utils.Key;
import fr.r1r0r0.deltaengine.model.events.InputEvent;
import fr.r1r0r0.deltaengine.tools.dialog.Dialog;
import main.Main;
import model.Game;
import model.levels.fixed_levels.GameOverLevel;
import model.loadables.LoadableInput;

/**
 * Inputs on game over screen
 */
public class GameOverInputs implements LoadableInput {

    private final GameOverLevel level;
    private final Game game;

    public GameOverInputs(GameOverLevel level) {
        this.level = level;
        this.game = level.getGame();
    }

    /**
     * Load the inputs
     *
     * @param engine DeltaEngine
     */
    @Override
    public void load(KernelEngine engine) {
        try {
            engine.setInput(Key.ENTER, new InputEvent(game::returnToMenu, null));
        } catch (InputKeyStackingError e) {
            new Dialog(Main.APPLICATION_NAME, "GameOver screen inputs loading error", e).show();
        }
    }

    /**
     * Unload the inputs
     *
     * @param engine DeltaEngine
     */
    @Override
    public void unload(KernelEngine engine) {
        engine.clearInput(Key.ENTER);
    }
}

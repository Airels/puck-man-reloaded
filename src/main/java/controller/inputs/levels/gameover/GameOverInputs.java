package controller.inputs.levels.gameover;

import fr.r1r0r0.deltaengine.exceptions.InputKeyStackingError;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.engines.utils.Key;
import fr.r1r0r0.deltaengine.model.events.InputEvent;
import model.Game;
import model.levels.fixed_levels.gameover.GameOverLevel;
import model.loadables.LoadableInput;

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
            e.printStackTrace();
            System.exit(1);
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

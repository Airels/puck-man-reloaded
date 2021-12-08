package model.actions.events;

import fr.r1r0r0.deltaengine.model.events.Event;
import model.Game;

/**
 * Global HUD element to update time remaining before super pacgum expires when game is in energized mode
 */
public class GlobalHUDCheckEnergizedModeChangedEvent extends Event {

    private final Game game;
    private boolean previous;

    /**
     * Default constructor.
     * @param game The Game
     */
    public GlobalHUDCheckEnergizedModeChangedEvent(Game game) {
        this.game = game;
        this.previous = game.isInEnergizedMode();
    }

    @Override
    public void checkEvent() {
        if (game.isInEnergizedMode() || previous != game.isInEnergizedMode())
            runTriggers();

        previous = game.isInEnergizedMode();
    }
}

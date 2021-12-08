package model.actions.events;

import fr.r1r0r0.deltaengine.model.events.Event;
import model.Game;

/**
 * Global HUD element to updates life text counter when life counter is modified
 */
public class GlobalHUDCheckLivesChangedEvent extends Event {
    private final Game game;
    private int previous;

    /**
     * Default constructor
     * @param game The Game
     */
    public GlobalHUDCheckLivesChangedEvent(Game game) {
        this.game = game;
        this.previous = game.getLives();
    }

    @Override
    public void checkEvent() {
        if (game.getLives() != previous) {
            previous = game.getLives();
            runTriggers();
        }
    }
}

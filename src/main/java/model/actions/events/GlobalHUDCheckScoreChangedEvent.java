package model.actions.events;

import fr.r1r0r0.deltaengine.model.events.Event;
import model.Game;

/**
 * Global HUD element to updates score text counter when score counter is updated
 */
public class GlobalHUDCheckScoreChangedEvent extends Event {

    private final Game game;
    private double previous;

    /**
     * Default constructor.
     * @param game The Game
     */
    public GlobalHUDCheckScoreChangedEvent(Game game) {
        this.game = game;
        this.previous = game.getScore();
    }

    @Override
    public void checkEvent() {
        if (game.getScore() != previous) {
            previous = game.getScore();
            runTriggers();
        }
    }
}

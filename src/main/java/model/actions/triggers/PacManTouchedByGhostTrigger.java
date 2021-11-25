package model.actions.triggers;

import fr.r1r0r0.deltaengine.model.events.Trigger;
import model.Game;
import model.elements.entities.ghosts.Ghost;
import model.elements.entities.ghosts.GhostState;

/**
 * Trigger when PacMan got touched by a Ghost, usually says GAME OVER or eat Ghost
 */
public class PacManTouchedByGhostTrigger implements Trigger {

    private final Game game;
    private final Ghost ghost;

    /**
     * Default constructor
     * @param game The Game
     * @param ghost Ghost who touched PacMan
     */
    public PacManTouchedByGhostTrigger(Game game, Ghost ghost) {
        this.game = game;
        this.ghost = ghost;
    }

    @Override
    public void trigger() {
        if (ghost.isScared()) {
            ghost.setState(GhostState.FLEEING);
            game.ghostEaten();
        } else if (!ghost.isFleeing()) {
            game.gameOver();
        }
    }
}

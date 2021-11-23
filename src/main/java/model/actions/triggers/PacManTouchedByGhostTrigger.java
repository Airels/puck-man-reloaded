package model.actions.triggers;

import fr.r1r0r0.deltaengine.model.events.Trigger;
import model.Game;
import model.elements.entities.ghosts.Ghost;
import model.elements.entities.ghosts.GhostState;

public class PacManTouchedByGhostTrigger implements Trigger {

    private final Game game;
    private final Ghost ghost;

    public PacManTouchedByGhostTrigger(Game game, Ghost ghost) {
        this.game = game;
        this.ghost = ghost;
    }

    @Override
    public void trigger() {
        if (ghost.isScared()) {
            ghost.setState(GhostState.FLEEING);
            System.out.println("EAT IT"); // TODO
        } else
            game.gameOver();
    }
}

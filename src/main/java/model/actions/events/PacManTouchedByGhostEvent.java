package model.actions.events;

import fr.r1r0r0.deltaengine.model.events.Event;
import model.Game;
import model.actions.triggers.PacManTouchedByGhostTrigger;
import model.elements.entities.ghosts.Ghost;

/**
 * Event handling when PacMan is touched by a Ghost.
 * If PacMan got touched, event will trigger associated triggers, usually says PacMan dies or eat the Ghost
 */
public class PacManTouchedByGhostEvent extends Event {

    /**
     * Default constructor
     * @param game The Game
     * @param ghost Targeted Ghost
     */
    public PacManTouchedByGhostEvent(Game game, Ghost ghost) {
        addTrigger(new PacManTouchedByGhostTrigger(game, ghost));
    }

    @Override
    public void checkEvent() {
        runTriggers();
    }
}

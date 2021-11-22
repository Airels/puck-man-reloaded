package model.actions.events;

import fr.r1r0r0.deltaengine.model.events.Event;
import model.Game;
import model.actions.triggers.PacManTouchedByGhostTrigger;
import model.elements.entities.ghosts.Ghost;

public class PacManTouchedByGhostEvent extends Event {

    public PacManTouchedByGhostEvent(Game game, Ghost ghost) {
        addTrigger(new PacManTouchedByGhostTrigger(game, ghost));
    }

    @Override
    public void checkEvent() {
        runTriggers();
    }
}

package model.actions.events;

import fr.r1r0r0.deltaengine.model.events.Event;
import model.elements.entities.ghosts.Ghost;

public class GhostRegenerationPointEvent extends Event {

    private Ghost ghost;

    public GhostRegenerationPointEvent(Ghost ghost) {
        this.ghost = ghost;
    }

    @Override
    public void checkEvent() {
        if (ghost.isFleeing())
            runTriggers();
    }
}

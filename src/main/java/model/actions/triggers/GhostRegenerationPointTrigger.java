package model.actions.triggers;

import fr.r1r0r0.deltaengine.model.events.Trigger;
import model.elements.entities.ghosts.Ghost;
import model.elements.entities.ghosts.GhostState;

public class GhostRegenerationPointTrigger implements Trigger {

    private Ghost ghost;

    public GhostRegenerationPointTrigger(Ghost ghost) {
        this.ghost = ghost;
    }


    @Override
    public void trigger() {
        ghost.setState(GhostState.NORMAL);
    }
}

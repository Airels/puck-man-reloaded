package model.actions.triggers;

import fr.r1r0r0.deltaengine.model.events.Trigger;
import model.elements.entities.ghosts.Ghost;
import model.elements.entities.ghosts.GhostState;

/**
 * Trigger when Ghost reached its Regeneration Point, switching it from FLEEING state to NORMAL state
 */
public class GhostRegenerationPointTrigger implements Trigger {

    private final Ghost ghost;

    /**
     * Default constructor
     *
     * @param ghost Targeted ghost
     */
    public GhostRegenerationPointTrigger(Ghost ghost) {
        this.ghost = ghost;
    }

    @Override
    public void trigger() {
        if (ghost.isFleeing())
            ghost.setState(GhostState.NORMAL);
    }
}

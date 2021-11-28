package model.ai;

import fr.r1r0r0.deltaengine.model.AI;
import model.elements.entities.ghosts.Ghost;

/**
 * A Ghost AI
 */
public abstract class GhostAI extends AI {

    /**
     * Returns its attached Ghost
     * @return Ghost attached
     */
    public Ghost getGhost() {
        return (Ghost) super.getEntity();
    }

    /**
     * Returns a new instance of itself, ready to be used for another entity
     * @return new GhostAI instance
     */
    public abstract GhostAI clone();
}

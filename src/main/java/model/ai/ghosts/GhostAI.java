package model.ai.ghosts;

import fr.r1r0r0.deltaengine.model.AI;
import model.elements.entities.ghosts.Ghost;

/**
 * TODO
 */
public abstract class GhostAI extends AI {

    public GhostAI () {}

    public Ghost getGhost() {
        return (Ghost) super.getEntity();
    }

    public abstract GhostAI clone();
}

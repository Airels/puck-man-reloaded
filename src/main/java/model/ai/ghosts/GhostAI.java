package model.ai.ghosts;

import fr.r1r0r0.deltaengine.model.AI;
import model.elements.entities.ghosts.Ghost;

public abstract class GhostAI extends AI {

    public Ghost getGhost() {
        return ((Ghost) super.getEntity());
    }
}

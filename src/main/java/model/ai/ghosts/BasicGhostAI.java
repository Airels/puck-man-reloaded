package model.ai.ghosts;

import model.elements.entities.ghosts.Ghost;

/**
 * A ghost AI for the 4 basic ghost in PacMan
 * @see BlinkyAI
 * @see ClydeAI
 * @see InkyAI
 * @see PinkyAI
 */
public abstract class BasicGhostAI extends GhostAI {

    @Override
    public void tick() {
        Ghost ghost = getGhost();
        if (ghost.isScared()) scaryModeTick(ghost);
        else chaseModeTick(ghost);
    }

    /**
     * Action when the ghost is in scary mode
     * @param ghost a ghost
     */
    protected abstract void scaryModeTick (Ghost ghost);

    /**
     * Action when the ghost is in chase mode (when he is not in scary mode)
     * @param ghost a ghost
     */
    protected abstract void chaseModeTick (Ghost ghost);

}

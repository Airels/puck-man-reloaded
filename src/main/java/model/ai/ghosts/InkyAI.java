package model.ai.ghosts;

import model.elements.entities.ghosts.Ghost;

/**
 * TODO
 */
public final class InkyAI extends BasicGhostAI {

    /**
     * Blue - Bashful
     * TODO: des fois il va dans la direction oppose de pac-man
     */

    public InkyAI () {

    }

    @Override
    public GhostAI clone() {
        return new InkyAI();
    }

    @Override
    protected void scaryModeTick(Ghost ghost) {
        //TODO
    }

    @Override
    protected void chaseModeTick(Ghost ghost) {
        //TODO
    }

}

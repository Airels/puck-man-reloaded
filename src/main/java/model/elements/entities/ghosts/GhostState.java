package model.elements.entities.ghosts;

/**
 * Ghost state, to change its appearance and its behaviour depending on the gameplay
 */
public enum GhostState {
    /**
     * Normal mode, will chase the player according to its AI
     */
    NORMAL,

    /**
     * Scared mode, when PacMan eat a Super PacGum. Will try to escape PacMan.
     */
    SCARED,

    /**
     * Fleeing mode, when PacMan ate a Ghost. Will return to its regeneration point to flip its state back to NORMAL
     */
    FLEEING,

    /**
     * Scatter mode, could happen randomly. Will stop to chase PacMan.
     */
    SCATTER
}

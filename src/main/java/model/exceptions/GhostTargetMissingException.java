package model.exceptions;

import model.elements.entities.ghosts.Ghost;

/**
 * A exception throw when the target of a ghost is missing
 */
public class GhostTargetMissingException extends Exception {

    /**
     * Constructor
     * @param msg a message
     */
    public GhostTargetMissingException (String msg) {
        super(msg);
    }

    /**
     * Constructor
     * @param ghost a ghost
     */
    public GhostTargetMissingException (Ghost ghost) {
        this("The ghost is not able to find his target : " + ghost);
    }

}

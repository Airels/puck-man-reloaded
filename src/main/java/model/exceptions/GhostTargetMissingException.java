package model.exceptions;

import model.elements.entities.ghosts.Ghost;

public class GhostTargetMissingException extends Exception {

    public GhostTargetMissingException (String msg) {
        super(msg);
    }

    public GhostTargetMissingException (Ghost ghost) {
        this("The ghost is not able to find his target : " + ghost);
    }

}

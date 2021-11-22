package model.actions.events;

import fr.r1r0r0.deltaengine.model.events.Event;
import model.elements.entities.PacMan;

public class OriginalLevelLeftTunnelEvent extends Event {

    private final PacMan pacMan;

    public OriginalLevelLeftTunnelEvent(PacMan pacMan) {
        this.pacMan = pacMan;
    }

    @Override
    public void checkEvent() {
        if (pacMan.getCoordinates().getX() < 0)
            runTriggers();
    }
}

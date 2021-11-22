package model.actions.events;

import fr.r1r0r0.deltaengine.model.events.Event;
import model.elements.entities.PacMan;

public class OriginalLevelRightTunnelEvent extends Event {

    private final PacMan pacMan;
    private final int originalLevelWidth;

    public OriginalLevelRightTunnelEvent(int originalLevelWidth, PacMan pacMan) {
        this.pacMan = pacMan;
        this.originalLevelWidth = originalLevelWidth;
    }

    @Override
    public void checkEvent() {
        if (pacMan.getCoordinates().getX() > originalLevelWidth-1)
            runTriggers();
    }
}

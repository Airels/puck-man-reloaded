package model.actions.events;

import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.events.Event;

/**
 * Event
 */
public class OriginalLevelRightTunnelEvent extends Event {

    private final Entity entity;
    private final int originalLevelWidth;

    public OriginalLevelRightTunnelEvent(Entity entity, int originalLevelWidth) {
        this.entity = entity;
        this.originalLevelWidth = originalLevelWidth;
    }

    @Override
    public void checkEvent() {
        if (entity.getCoordinates().getX() > originalLevelWidth-1)
            runTriggers();
    }
}

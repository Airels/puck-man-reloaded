package model.actions.events;

import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.events.Event;
import model.elements.entities.PacMan;

public class OriginalLevelLeftTunnelEvent extends Event {

    private final Entity entity;

    public OriginalLevelLeftTunnelEvent(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void checkEvent() {
        if (entity.getCoordinates().getX() < 0.1)
            runTriggers();
    }
}

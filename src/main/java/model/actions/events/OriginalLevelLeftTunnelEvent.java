package model.actions.events;

import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.events.Event;
import model.elements.entities.PacMan;

/**
 * Event handling right tunnel of the original map.
 * Will run associated triggers when specified entity cross out of the map.
 */
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

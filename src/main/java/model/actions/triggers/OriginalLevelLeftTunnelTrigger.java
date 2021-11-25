package model.actions.triggers;

import config.levels.OriginalLevelConfigurator;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.events.Trigger;

/**
 * Trigger used when Entity crossed the Original tunnel on the left.
 * It will teleport Entity directly to the configured right tunnel spawn point
 */
public class OriginalLevelLeftTunnelTrigger implements Trigger {

    private final Entity entity;

    /**
     * Default constructor
     *
     * @param entity Entity to handle
     */
    public OriginalLevelLeftTunnelTrigger(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void trigger() {
        entity.setCoordinates(OriginalLevelConfigurator.CONF_RIGHT_TUNNEL_SPAWN_POINT);
    }
}

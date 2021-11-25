package model.actions.triggers;

import config.levels.OriginalLevelConfigurator;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.events.Trigger;

/**
 * Trigger used when Entity crossed the Original tunnel on the right.
 * It will teleport Entity directly to the configured left tunnel spawn point
 */
public class OriginalLevelRightTunnelTrigger implements Trigger {

    private final Entity entity;

    /**
     * Default constructor
     *
     * @param entity Entity to handle
     */
    public OriginalLevelRightTunnelTrigger(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void trigger() {
        entity.setCoordinates(OriginalLevelConfigurator.CONF_LEFT_TUNNEL_SPAWN_POINT);
    }
}

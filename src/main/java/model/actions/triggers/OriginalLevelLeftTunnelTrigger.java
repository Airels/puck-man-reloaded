package model.actions.triggers;

import config.levels.OriginalLevelConfigurator;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.events.Event;
import fr.r1r0r0.deltaengine.model.events.Trigger;
import model.elements.entities.PacMan;

public class OriginalLevelLeftTunnelTrigger implements Trigger {

    private final Entity entity;

    public OriginalLevelLeftTunnelTrigger(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void trigger() {
        entity.setCoordinates(OriginalLevelConfigurator.CONF_RIGHT_TUNNEL_SPAWN_POINT);
    }
}

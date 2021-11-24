package model.actions.triggers;

import config.levels.OriginalLevelConfigurator;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.events.Trigger;
import model.elements.entities.PacMan;

public class OriginalLevelRightTunnelTrigger implements Trigger {

    private final Entity entity;

    public OriginalLevelRightTunnelTrigger(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void trigger() {
        entity.setCoordinates(OriginalLevelConfigurator.CONF_LEFT_TUNNEL_SPAWN_POINT);
    }
}

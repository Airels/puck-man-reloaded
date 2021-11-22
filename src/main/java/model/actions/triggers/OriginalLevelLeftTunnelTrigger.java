package model.actions.triggers;

import config.levels.OriginalLevelConfigurator;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.events.Event;
import fr.r1r0r0.deltaengine.model.events.Trigger;
import model.elements.entities.PacMan;

public class OriginalLevelLeftTunnelTrigger implements Trigger {

    private final PacMan pacMan;

    public OriginalLevelLeftTunnelTrigger(PacMan pacMan) {
        this.pacMan = pacMan;
    }

    @Override
    public void trigger() {
        pacMan.setCoordinates(OriginalLevelConfigurator.CONF_RIGHT_TUNNEL_SPAWN_POINT);
    }
}

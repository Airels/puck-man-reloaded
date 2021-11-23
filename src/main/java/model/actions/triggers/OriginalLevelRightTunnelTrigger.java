package model.actions.triggers;

import config.levels.OriginalLevelConfigurator;
import fr.r1r0r0.deltaengine.model.events.Trigger;
import model.elements.entities.PacMan;

public class OriginalLevelRightTunnelTrigger implements Trigger {

    private final PacMan pacMan;

    public OriginalLevelRightTunnelTrigger(PacMan pacMan) {
        this.pacMan = pacMan;
    }

    @Override
    public void trigger() {
        pacMan.setCoordinates(OriginalLevelConfigurator.CONF_LEFT_TUNNEL_SPAWN_POINT);
    }
}

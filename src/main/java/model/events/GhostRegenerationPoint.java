package model.events;

import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.events.Event;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import fr.r1r0r0.deltaengine.view.colors.Color;
import model.actions.events.GhostRegenerationPointEvent;
import model.actions.triggers.GhostRegenerationPointTrigger;
import model.elements.entities.ghosts.Ghost;

public final class GhostRegenerationPoint extends Entity {

    private static int id = 0;

    public GhostRegenerationPoint(Ghost ghost) {
        super("GhostRegeneration" + id, ghost.getRetreatPoint(), new Rectangle(new Color(0, 0, 0, 100)), new Dimension(0.1, 0.1));

        Event event = new GhostRegenerationPointEvent(ghost);
        event.addTrigger(new GhostRegenerationPointTrigger(ghost));
        this.setCollisionEvent(ghost, event);

        id++;
    }
}

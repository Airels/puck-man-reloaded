package model.events;

import config.events.MapLevelChangerConfiguration;
import config.pacman.PacManConfiguration;
import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelEntityNameStackingException;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.elements.cells.Cell;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.events.AttributeListener;
import fr.r1r0r0.deltaengine.model.events.Event;
import fr.r1r0r0.deltaengine.model.events.Trigger;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import model.Game;
import model.elements.entities.PacMan;
import model.maps.Level;

public class MapLevelChanger extends Event {

    private final Level level;
    private final MapLevel mapLevel;
    private final Entity teleporter;
    private final PacMan pacMan;
    private boolean isActivated;

    public MapLevelChanger(Level level) {
        this.level = level;
        this.mapLevel = level.getMapLevelLoadable().getMapLevel();
        Coordinates<Double> coords = MapLevelChangerConfiguration.CONF_CELL_COORDINATES_TELEPORT;
        teleporter = new Entity("teleporter", coords, MapLevelChangerConfiguration.CONF_ACTIVATED_TELEPORTER_CELL, MapLevelChangerConfiguration.CONF_DEFAULT_TELEPORTER_DIMENSION);

        pacMan = (PacMan) mapLevel.getEntity(PacManConfiguration.CONF_PACMAN_NAME);

        level.addPacGumValueListener((oldValue, newValue) -> {
            if (newValue == 0)
                setPortalActivated(true);
        });

        Event collisionEvent = new Event() {
            @Override
            public void checkEvent() {
                if (isPortalActivated())
                    this.runTriggers();
            }
        };
        collisionEvent.addTrigger(this::runTriggers);
        teleporter.setCollisionEvent(pacMan, collisionEvent);
    }

    @Override
    public void checkEvent() {

    }

    public boolean isPortalActivated() {
        return isActivated;
    }

    public void setPortalActivated(boolean isActivated) {
        this.isActivated = isActivated;

        try {
            mapLevel.addEntity(teleporter);
        } catch (MapLevelEntityNameStackingException e) {
            e.printStackTrace(); // Should never happen
            System.exit(1);
        }
    }
}

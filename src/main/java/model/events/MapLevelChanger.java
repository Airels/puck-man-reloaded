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

/**
 * Level Changer Handler.
 * When PacMan collected all PacGums, activates a Teleporter Entity, and when it enters it, activates associated trigger
 * given by the Game manager (aka to transfert him to the next level)
 */
public final class MapLevelChanger extends Event {

    private final Level level;
    private final MapLevel mapLevel;
    private final Entity teleporter;
    private final PacMan pacMan;
    private boolean isActivated;

    /**
     * Default constructor
     * @param level attached level
     */
    public MapLevelChanger(Level level) {
        this.level = level;
        this.mapLevel = level.getMapLevelLoadable().getMapLevel();
        Coordinates<Double> coords = MapLevelChangerConfiguration.CONF_CELL_COORDINATES_TELEPORT;
        teleporter = new Entity("teleporter", coords, MapLevelChangerConfiguration.CONF_ACTIVATED_TELEPORTER_CELL, MapLevelChangerConfiguration.CONF_DEFAULT_TELEPORTER_DIMENSION);

        pacMan = (PacMan) mapLevel.getEntity(PacManConfiguration.CONF_PACMAN_NAME);

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
        if (level.getNbOfPacGums() == 0)
            setPortalActivated(true);
    }

    /**
     * Returns if portal is activated or not
     * @return boolean true if portal activated, false otherwise
     */
    public boolean isPortalActivated() {
        return isActivated;
    }

    /**
     * Set portal activation
     * @param isActivated true to activate it, false otherwise
     */
    public void setPortalActivated(boolean isActivated) {
        boolean previousValue = this.isActivated;
        this.isActivated = isActivated;

        try {
            if (isActivated && !previousValue)
                mapLevel.addEntity(teleporter);
            else if (!isActivated && previousValue)
                mapLevel.removeEntity(teleporter);
        } catch (MapLevelEntityNameStackingException e) {
            e.printStackTrace(); // Should never happen
            System.exit(1);
        }
    }
}
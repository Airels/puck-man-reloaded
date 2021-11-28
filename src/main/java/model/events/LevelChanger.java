package model.events;

import config.entities.PacManConfiguration;
import config.events.MapLevelChangerConfiguration;
import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelEntityNameStackingException;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.events.Event;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.tools.dialog.Dialog;
import main.Main;
import model.elements.entities.PacMan;
import model.levels.Level;

/**
 * Level Changer Handler.
 * When PacMan collected all PacGums, activates a Teleporter Entity, and when it enters it, activates associated trigger
 * given by the Game manager (aka to transfert him to the next level)
 */
public final class LevelChanger extends Event {

    private final Level level;
    private final MapLevel mapLevel;
    private final Entity levelChangerEntity;
    private final PacMan pacMan;
    private boolean isActivated;

    /**
     * Default constructor, allowing to create a Map level changer on specific coordinates
     *
     * @param level attached level
     * @param coordinates position of the MapLevelChanger
     */
    public LevelChanger(Level level, Coordinates<Double> coordinates) {
        this.level = level;
        this.mapLevel = level.getMapLevelLoadable().getMapLevel();
        levelChangerEntity = new Entity("LevelChanger", coordinates, MapLevelChangerConfiguration.CONF_ACTIVATED_TELEPORTER_CELL, MapLevelChangerConfiguration.CONF_DEFAULT_TELEPORTER_DIMENSION);

        pacMan = (PacMan) mapLevel.getEntity(PacManConfiguration.CONF_PACMAN_NAME);

        Event collisionEvent = new Event() {
            @Override
            public void checkEvent() {
                if (isPortalActivated())
                    this.runTriggers();
            }
        };
        collisionEvent.addTrigger(this::runTriggers);
        levelChangerEntity.setCollisionEvent(pacMan, collisionEvent);
    }

    @Override
    public void checkEvent() {
        if (level.getNbOfPacGums() == 0)
            setPortalActivated(true);
    }

    /**
     * Returns if portal is activated or not
     *
     * @return boolean true if portal activated, false otherwise
     */
    public boolean isPortalActivated() {
        return isActivated;
    }

    /**
     * Set portal activation
     *
     * @param isActivated true to activate it, false otherwise
     */
    public void setPortalActivated(boolean isActivated) {
        boolean previousValue = this.isActivated;
        this.isActivated = isActivated;

        try {
            if (isActivated && !previousValue)
                mapLevel.addEntity(levelChangerEntity);
            else if (!isActivated && previousValue)
                mapLevel.removeEntity(levelChangerEntity);
        } catch (MapLevelEntityNameStackingException e) {
            new Dialog(Main.APPLICATION_NAME, "Error while setting portal state", e).show();
        }
    }
}

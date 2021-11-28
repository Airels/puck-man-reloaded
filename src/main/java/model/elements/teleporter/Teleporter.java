package model.elements.teleporter;

import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelEntityNameStackingException;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.tools.dialog.Dialog;
import main.Main;

/**
 * Teleport any added entity to another point.
 * Like a tunnel, when an entity interact with one of two given points, could be teleported to the other one.
 * It is also possible to configure if points of the teleporter can teleport or not.
 */
public final class Teleporter {

    private final MapLevel mapLevel;
    private final TeleportPoint firstPoint, secondPoint;
    private final long cooldown;

    /**
     * Default constructor. Creates a Teleporter on a given map, with given teleport points, and specified cooldown.
     *
     * @param mapLevel    MapLevel where teleporter will be
     * @param firstPoint  First point of the teleporter
     * @param secondPoint Second point of the teleporter
     * @param cooldown    time before an entity can cross the teleporter again
     */
    public Teleporter(MapLevel mapLevel, TeleportPoint firstPoint, TeleportPoint secondPoint, long cooldown) {
        this.mapLevel = mapLevel;
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
        this.cooldown = cooldown;

        try {
            mapLevel.addEntity(firstPoint);
            mapLevel.addEntity(secondPoint);
        } catch (MapLevelEntityNameStackingException e) {
            new Dialog(Main.APPLICATION_NAME, "Error while creating a new Teleporter", e).show();
        }
    }

    /**
     * Returns the first teleport point
     *
     * @return TeleportPoint corresponding to the first teleport point
     */
    public TeleportPoint getFirstTeleportPoint() {
        return firstPoint;
    }

    /**
     * Returns the second teleport point
     *
     * @return TeleportPoint corresponding to the second teleport point
     */
    public TeleportPoint getSecondTeleportPoint() {
        return secondPoint;
    }

    /**
     * Add an entity that the portal must take into account
     *
     * @param entity Entity to handle
     */
    public void addEntityToHandle(Entity entity) {
        TeleportEvent.addLink(entity, firstPoint, secondPoint, cooldown);
    }

    /**
     * Returns the cooldown of the teleporter for any entity
     *
     * @return long cooldown time of the teleporter
     */
    public long getCooldown() {
        return cooldown;
    }
}

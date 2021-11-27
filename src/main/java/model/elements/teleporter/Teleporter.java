package model.elements.teleporter;

import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelEntityNameStackingException;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;

/**
 * TODO
 */
public final class Teleporter {

    private final MapLevel mapLevel;
    private final TeleportPoint firstPoint, secondPoint;
    private final long cooldown;

    public Teleporter(MapLevel mapLevel, TeleportPoint firstPoint, TeleportPoint secondPoint, long cooldown) {
        this.mapLevel = mapLevel;
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
        this.cooldown = cooldown;

        try {
            mapLevel.addEntity(firstPoint);
            mapLevel.addEntity(secondPoint);
        } catch (MapLevelEntityNameStackingException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public TeleportPoint getFirstTeleportPoint() {
        return firstPoint;
    }

    public TeleportPoint getSecondTeleportPoint() {
        return secondPoint;
    }

    public void addEntityToHandle(Entity entity) {
        TeleportEvent.addLink(entity, firstPoint, secondPoint, cooldown);
    }

    public long getCooldown() {
        return cooldown;
    }
}

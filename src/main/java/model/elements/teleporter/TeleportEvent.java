package model.elements.teleporter;

import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.events.Event;

/**
 * The Teleporter Event. Handle the Teleporter action : teleports entities from the first point to the second point,
 * and do not teleport them again before the cooldown ends.
 * <br />
 * Cannot be instanced directly. Static method addLink must be called in order to create events : one for each point of
 * the teleporter.
 */
public final class TeleportEvent extends Event {

    private final TeleportPoint teleportPoint;
    private long cooldown, lastPassage;
    private TeleportEvent otherPoint;

    /**
     * Default constructor. Disallow direct instantiation
     * @param entity Entity to bind
     * @param source TeleportPoint source
     * @param target TeleportPoint destination
     * @param cooldown time cooldown to apply for the entity before crossing the teleporter again
     */
    private TeleportEvent(Entity entity, TeleportPoint source, TeleportPoint target, long cooldown) {
        this.teleportPoint = source;
        this.cooldown = cooldown;
        this.lastPassage = 0;

        addTrigger(() -> entity.setCoordinates(target.getCoordinates()));
    }

    public static void addLink(Entity entity, TeleportPoint teleportPoint1, TeleportPoint teleportPoint2, long cooldown) {
        TeleportEvent t1 = new TeleportEvent(entity, teleportPoint1, teleportPoint2, cooldown);
        TeleportEvent t2 = new TeleportEvent(entity, teleportPoint2, teleportPoint1, cooldown);

        teleportPoint1.setCollisionEvent(entity, t1);
        teleportPoint2.setCollisionEvent(entity, t2);

        t1.setOtherPoint(t2);
        t2.setOtherPoint(t1);
    }

    @Override
    public void checkEvent() {
        if (!teleportPoint.canEnter()) return;

        if (inCooldown() || otherPoint.inCooldown()) return;

        runTriggers();
        lastPassage = System.currentTimeMillis();
    }

    /**
     * Set a new cooldown for the teleporter
     * @param cooldown new time cooldown
     */
    public void setCooldown(long cooldown) {
        this.cooldown = cooldown;
    }

    /**
     * Returns if the TeleportPoint is still in cooldown. If true, entity can't cross the Teleporter until cooldown ends
     * @return boolean true if in cooldown, false otherwise
     */
    public boolean inCooldown() {
        return (System.currentTimeMillis() - lastPassage < cooldown);
    }

    /**
     * Bind the other teleporter event, to synchronize cooldown with it.
     * @param otherPoint the other event
     */
    private void setOtherPoint(TeleportEvent otherPoint) {
        this.otherPoint = otherPoint;
    }
}

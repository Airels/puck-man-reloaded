package model.elements.teleporter;

import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.events.Event;

import java.util.Collection;
import java.util.LinkedList;

/**
 * TODO
 */
public final class TeleportEvent extends Event {

    private final TeleportPoint teleportPoint;
    private long cooldown, lastPassage;
    private TeleportEvent otherPoint;

    private TeleportEvent(Entity entity, TeleportPoint source, TeleportPoint target, long cooldown) {
        this.teleportPoint = source;
        this.cooldown = cooldown;
        this.lastPassage = 0;

        addTrigger(() -> entity.setCoordinates(target.getCoordinates()));
    }

    @Override
    public void checkEvent() {
        if (!teleportPoint.canEnter()) return;

        if (inCooldown() || otherPoint.inCooldown()) return;

        runTriggers();
        lastPassage = System.currentTimeMillis();
    }

    public void setCooldown(long cooldown) {
        this.cooldown = cooldown;
    }

    public boolean inCooldown() {
        return (System.currentTimeMillis() - lastPassage < cooldown);
    }

    private void setOtherPoint(TeleportEvent otherPoint) {
        this.otherPoint = otherPoint;
    }

    public static void addLink(Entity entity, TeleportPoint teleportPoint1, TeleportPoint teleportPoint2, long cooldown) {
        TeleportEvent t1 = new TeleportEvent(entity, teleportPoint1, teleportPoint2, cooldown);
        TeleportEvent t2 = new TeleportEvent(entity, teleportPoint2, teleportPoint1, cooldown);

        teleportPoint1.setCollisionEvent(entity, t1);
        teleportPoint2.setCollisionEvent(entity, t2);

        t1.setOtherPoint(t2);
        t2.setOtherPoint(t1);
    }
}

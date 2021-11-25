package model.events;

import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.events.Event;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import model.actions.events.OriginalLevelLeftTunnelEvent;
import model.actions.events.OriginalLevelRightTunnelEvent;
import model.actions.triggers.OriginalLevelLeftTunnelTrigger;
import model.actions.triggers.OriginalLevelRightTunnelTrigger;
import model.loadables.Loadable;

public final class BordersTunnelTeleportEvent implements Loadable {

    private final MapLevel mapLevel;
    private final Entity entity;
    private final Event leftTunnelEvent, rightTunnelEvent;

    public BordersTunnelTeleportEvent(MapLevel mapLevel, Entity entity) {
        this.mapLevel = mapLevel;
        this.entity = entity;

        this.leftTunnelEvent = new OriginalLevelLeftTunnelEvent(entity);
        this.rightTunnelEvent = new OriginalLevelRightTunnelEvent(entity, mapLevel.getWidth());

        this.leftTunnelEvent.addTrigger(new OriginalLevelLeftTunnelTrigger(entity));
        this.rightTunnelEvent.addTrigger(new OriginalLevelRightTunnelTrigger(entity));
    }

    /**
     * Load the event
     *
     * @param engine DeltaEngine
     */
    @Override
    public void load(KernelEngine engine) {
        mapLevel.addEvent(leftTunnelEvent);
        mapLevel.addEvent(rightTunnelEvent);
    }

    /**
     * Unload the event.
     * Completely useless due to fact that if attached map is unloading, then events will be automatically unloaded
     * by the DeltaEngine
     * @param engine DeltaEngine
     */
    @Override
    public void unload(KernelEngine engine) {
        mapLevel.removeEvent(leftTunnelEvent);
        mapLevel.removeEvent(rightTunnelEvent);
    }
}

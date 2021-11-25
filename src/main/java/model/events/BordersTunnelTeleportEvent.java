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

/**
 * An Event used for the Original Level to recreate the arcade tunnel on each side of the game.
 * Teleports given entity to the configured position when Entity exceed side limits of the map.
 *
 * @see config.levels.OriginalLevelConfigurator to configure spawn point where entities need to be teleported
 */
public final class BordersTunnelTeleportEvent implements Loadable {

    private final MapLevel mapLevel;
    private final Entity entity;
    private final Event leftTunnelEvent, rightTunnelEvent;

    /**
     * Default constructor
     *
     * @param mapLevel Map concerned about the tunnel
     * @param entity   Entity to relocate
     */
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
     *
     * @param engine DeltaEngine
     */
    @Override
    public void unload(KernelEngine engine) {
        mapLevel.removeEvent(leftTunnelEvent);
        mapLevel.removeEvent(rightTunnelEvent);
    }
}

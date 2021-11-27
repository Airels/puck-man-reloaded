package model.elements.teleporter;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.sprites.InvisibleSprite;

/**
 * A part of a Teleporter. Indicates the first enter/exit point of the teleporter.
 * Could be configured to not allow entities to enter it.
 *
 * @see Teleporter the effective Teleporter
 */
public final class TeleportPoint extends Entity {

    private static int id = 0;
    private boolean canEnter;

    /**
     * Default constructor. Set a new TeleportPoint by giving its coordinates, and if it could allow entities at first.
     *
     * @param coordinates Coordinates of the TeleportPoint
     * @param canEnter    boolean, set if TeleportPoint can allow entities to enter it. true to enable, false otherwise. Could be changed later.
     */
    public TeleportPoint(Coordinates<Double> coordinates, boolean canEnter) {
        super("TeleporterPoint" + id, coordinates, InvisibleSprite.getInstance(), Dimension.DEFAULT_DIMENSION, new Dimension(0.1, 0.1));
        id++;

        this.canEnter = canEnter;
    }

    /**
     * Second constructor. Set a new TeleportPoint by giving its coordinates, and allow entities to enter it
     *
     * @param coords Coordinates of the TeleportPoint
     */
    public TeleportPoint(Coordinates<Double> coords) {
        this(coords, true);
    }

    /**
     * Set if the TeleportPoint accepts or not entities to enter it
     *
     * @param canEnter boolean, true to accept entities to enter it, false otherwise
     */
    public void setEntering(boolean canEnter) {
        this.canEnter = canEnter;
    }

    /**
     * Returns if the TeleportPoint allows entities to enter it
     *
     * @return boolean true if entities can enter, false otherwise
     */
    public boolean canEnter() {
        return canEnter;
    }
}

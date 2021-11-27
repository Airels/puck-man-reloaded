package model.elements.teleporter;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.sprites.InvisibleSprite;

/**
 * TODO
 */
public final class TeleportPoint extends Entity {

    private static int id = 0;
    private boolean canEnter;

    public TeleportPoint(Coordinates<Double> coordinates, boolean canEnter) {
        super("TeleporterPoint" + id, coordinates, InvisibleSprite.getInstance(), Dimension.DEFAULT_DIMENSION, new Dimension(0.1, 0.1));
        id++;

        this.canEnter = canEnter;
    }

    public TeleportPoint(Coordinates<Double> coords) {
        this(coords, true);
    }

    public void setEntering(boolean canEnter) {
        this.canEnter = canEnter;
    }

    public boolean canEnter() {
        return canEnter;
    }
}

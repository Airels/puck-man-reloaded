package model.elements.entities.items;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Circle;
import fr.r1r0r0.deltaengine.view.colors.Color;

/**
 * A PacGum. Can be Super or not.
 * Increases Game's score, and also trigger energized mode if it's a Super PacGum
 */
public class PacGum extends Entity {

    private static int counter = 0; // Counter used to generate different names

    private boolean isSuper;

    /**
     * Default constructor. Creates a new PacGum. It will not be a Super PacGum.
     * @param coords Coordinates of the PacGum
     */
    public PacGum(Coordinates<Double> coords) {
        super("PacGum" + counter, coords, new Circle(1, Color.WHITE), new Dimension(0.1, 0.1));
        counter++;

        double scale = 0.4;
        setCoordinates(new Coordinates<>(coords.getX() + scale, coords.getY() + scale));

        isSuper = false;
    }

    /**
     * Another constructor. Creates a new PacGum with possibility to set if it's a Super PacGum or not.
     * @param coords Coordinates of the PacGum
     * @param isSuper boolean if PacGum is a Super PacGum
     */
    public PacGum(Coordinates<Double> coords, boolean isSuper) {
        this(coords);

        if (isSuper) {
            setDimension(new Dimension(0.5, 0.5));
            double scale = 0.25;
            setCoordinates(new Coordinates<>(coords.getX() + scale, coords.getY() + scale));
            this.isSuper = true;
        }
    }

    /**
     * Returns if PacGum is a Super PacGum
     * @return boolean true if it's a Super PacGum, false otherwise
     */
    public boolean isSuperPacGum() {
        return isSuper;
    }
}

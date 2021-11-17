package model.elements.entities.items;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.Entity;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Circle;
import fr.r1r0r0.deltaengine.view.colors.Color;

public class PacGum extends Entity {

    private static int counter = 0; // Counter used to generate different names

    private boolean isSuper;

    public PacGum(Coordinates<Double> coords) {
        super("PacGum" + counter, coords, new Circle(1, Color.WHITE), new Dimension(0.1, 0.1));
        counter++;

        double scale = 0.4;
        setCoordinates(new Coordinates<>(coords.getX() + scale, coords.getY() + scale));

        isSuper = false;
    }

    public PacGum(Coordinates<Double> coords, boolean isSuper) {
        this(coords);

        if (isSuper) {
            setDimension(new Dimension(0.5, 0.5));
            double scale = 0.25;
            setCoordinates(new Coordinates<>(coords.getX() + scale, coords.getY() + scale));
            this.isSuper = true;
        }
    }

    public boolean isSuperPacGum() {
        return isSuper;
    }
}
package model.elements.entities;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.Entity;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Circle;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import fr.r1r0r0.deltaengine.view.colors.Color;
import view.images.Image;

public class PacMan extends Entity {

    private boolean isEnergized;

    public PacMan(Coordinates<Double> coordinates) {
        // TODO super("PacMan", coordinates, Image.PAC_MAN.getSprite(), new Dimension(1, 1));
        super("PacMan", coordinates, new Circle(1, Color.YELLOW), new Dimension(1, 1));

        isEnergized = false;
    }

    public PacMan(double x, double y) {
        this(new Coordinates<>(x, y));
    }

    public PacMan() {
        this(4, 4);
    }

    public void energize() {
        // TODO Energize mais timer, a la fin du timer, plus energized
        // TODO Un event qui contient PacMan pour supprimer le energize ?
    }

    public void setEnergized(boolean isEnergized) {
        this.isEnergized = isEnergized;
    }

    public boolean isEnergized() {
        return isEnergized;
    }
}

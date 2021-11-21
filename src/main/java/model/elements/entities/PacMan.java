package model.elements.entities;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;

import static config.pacman.PacManConfiguration.CONF_PACMAN_NAME;
import static config.pacman.PacManConfiguration.CONF_PACMAN_SPRITE;

public class PacMan extends Entity {

    private boolean isEnergized;

    public PacMan(Coordinates<Double> coordinates) {
        // TODO super("PacMan", coordinates, Image.PAC_MAN.getSprite(), new Dimension(1, 1));
        super(CONF_PACMAN_NAME, coordinates, CONF_PACMAN_SPRITE, new Dimension(0.9, 0.9));

        isEnergized = false;
    }

    public PacMan(double x, double y) {
        this(new Coordinates<>(x, y));
    }

    public PacMan() {
        this(9 + 0.05, 16 + 0.05);
    }

    public void energize() {
        // TODO Energize mais timer, a la fin du timer, plus energized
        // TODO Un event qui contient PacMan pour supprimer le energize ?
    }

    public boolean isEnergized() {
        return isEnergized;
    }

    public void setEnergized(boolean isEnergized) {
        this.isEnergized = isEnergized;
    }
}

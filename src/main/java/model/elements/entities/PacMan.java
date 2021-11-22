package model.elements.entities;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import view.images.Image;

import static config.pacman.PacManConfiguration.CONF_PACMAN_SPRITE;

public class PacMan extends Entity {

    public static final String NAME =  "PacMan";

    private boolean isEnergized;

    public PacMan(Coordinates<Double> coordinates) {
        // TODO super("PacMan", coordinates, Image.PAC_MAN.getSprite(), new Dimension(1, 1));
        super(NAME, coordinates, Image.PAC_MAN.getSprite(), new Dimension(0.9,0.9));

        isEnergized = false;

        this.getAttributes().addDirectionListener((direction, t1) -> {
            Sprite s = CONF_PACMAN_SPRITE;
            switch (this.getAttributes().getDirection()) {
                case UP -> {
                    s.getNode().setScaleX(1);
                    s.getNode().setRotate(-90);
                }
                case DOWN -> {
                    s.getNode().setScaleX(1);
                    s.getNode().setRotate(90);
                }
                case RIGHT -> {
                    s.getNode().setScaleX(1);
                    s.getNode().setRotate(0);
                }
                case LEFT -> {
                    s.getNode().setScaleX(-1);
                    s.getNode().setRotate(0);
                }
            }

            this.setSprite(s);
        });
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

    public void setEnergized(boolean isEnergized) {
        this.isEnergized = isEnergized;
    }

    public boolean isEnergized() {
        return isEnergized;
    }
}

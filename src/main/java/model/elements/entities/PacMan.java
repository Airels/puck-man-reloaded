package model.elements.entities;

import config.pacman.PacManConfiguration;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.events.AttributeListener;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;

import static config.pacman.PacManConfiguration.CONF_PACMAN_NAME;
import static config.pacman.PacManConfiguration.CONF_PACMAN_SPRITE;

public class PacMan extends Entity {

    private boolean isEnergized;
    private Direction current;

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

    @Override
    public Sprite getSprite(){
        Sprite rotated = CONF_PACMAN_SPRITE;
        switch (this.getAttributes().getDirection()){
            case UP:
                rotated.getNode().setScaleX(1);
                rotated.getNode().setRotate(-90);
                break;
            case DOWN:
                rotated.getNode().setScaleX(1);
                rotated.getNode().setRotate(90);
                break;
            case RIGHT:
                rotated.getNode().setScaleX(1);
                rotated.getNode().setRotate(0);
                break;
            case LEFT:
                rotated.getNode().setScaleX(-1);
                rotated.getNode().setRotate(0);
                break;
        }
        return rotated;
    }
}

package model.elements.entities;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import view.images.Image;

import static config.pacman.PacManConfiguration.CONF_PACMAN_NAME;
import static config.pacman.PacManConfiguration.CONF_PACMAN_SPRITE;

/**
 * PacMan, the main Character.
 * Controlled by the Player.
 */
public class PacMan extends Entity {

    private boolean isDead;

    /**
     * Another constructor. Sets PacMan initial coordinates
     * @param coordinates initial coordinates
     */
    public PacMan(Coordinates<Double> coordinates) {
        super(CONF_PACMAN_NAME, coordinates, Image.PAC_MAN.getSprite(), new Dimension(0.9,0.9));
        isDead = false;
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

    /**
     * Another constructor. Sets PacMan initial location with doubles
     * @param x abscissa initial PacMan position
     * @param y ordinate initial PacMan position
     */
    public PacMan(double x, double y) {
        this(new Coordinates<>(x, y));
    }

    /**
     * Default builder.
     * Set PacMan coordinates to its original position
     */
    public PacMan() {
        this(9 + 0.05, 16 + 0.05);
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}

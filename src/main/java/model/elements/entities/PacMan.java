package model.elements.entities;

import config.entities.CharactersConfiguration;
import config.entities.PacManConfiguration;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import view.images.Image;

import static config.entities.PacManConfiguration.*;

/**
 * PacMan, the main Character.
 * Controlled by the Player.
 */
public class PacMan extends Entity {

    private boolean isDead;
    private Coordinates<Double> spawnPoint;

    /**
     * Another constructor. Sets PacMan initial coordinates
     *
     * @param coordinates initial coordinates
     */
    public PacMan(Coordinates<Double> coordinates) {
        super(CONF_PACMAN_NAME, coordinates, Image.PAC_MAN.getSprite(), CharactersConfiguration.CONF_DEFAULT_CHARACTERS_DIMENSION, CharactersConfiguration.CONF_DEFAULT_CHARACTERS_HITBOX_DIMENSION);
        spawnPoint = coordinates;
        isDead = false;

        this.getAttributes().addDirectionListener((direction, t1) -> {
            Sprite s = CONF_PACMAN_SPRITE;
            switch (this.getAttributes().getDirection()) {
                case UP -> {
                    s.setScale(1, s.getScale().getScaleY());
                    s.setRotate(-90);
                }
                case DOWN -> {
                    s.setScale(1, s.getScale().getScaleY());
                    s.setRotate(90);
                }
                case RIGHT -> {
                    s.setScale(1, s.getScale().getScaleY());
                    s.setRotate(0);
                }
                case LEFT -> {
                    s.setScale(-1, s.getScale().getScaleY());
                    s.setRotate(0);
                }
            }

            this.setSprite(s);
        });
    }

    /**
     * Another constructor. Sets PacMan initial location with doubles
     *
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
        this(PacManConfiguration.CONF_DEFAULT_PACMAN_POSITION);
    }

    /**
     * Returns if pacman was touched by a ghost
     *
     * @return true if pacman was touched, false otherwise
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Set if pacman was touched by a ghost
     *
     * @param dead boolean to set if pacman was touched by a ghost
     */
    public void setDead(boolean dead) {
        isDead = dead;

        this.setSprite((isDead) ? CONF_PACMAN_DEAD_SPRITE : CONF_PACMAN_SPRITE);
    }

    public void setSpawnPoint(Coordinates<Double> coords) {
        this.spawnPoint = coords;
    }

    public Coordinates<Double> getSpawnPoint() {
        return spawnPoint;
    }
}

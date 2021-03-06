package model.elements.entities;

import config.entities.CharactersConfiguration;
import config.entities.PacManConfiguration;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Direction;
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
        super(CONF_PACMAN_NAME, coordinates, Image.PAC_MAN_IDLE.getSprite(), CharactersConfiguration.CONF_DEFAULT_CHARACTERS_DIMENSION, CharactersConfiguration.CONF_DEFAULT_CHARACTERS_HITBOX_DIMENSION);
        spawnPoint = coordinates;
        isDead = false;

        this.getAttributes().addDirectionListener((oldDirection, newDirection) -> {
            Sprite s;
            Direction direction;
            if (newDirection == Direction.IDLE) {
                s = CONF_PACMAN_IDLE_SPRITE.getSprite();
                direction = oldDirection;
            } else {
                s = CONF_PACMAN_SPRITE.getSprite();
                direction = newDirection;
            }

            switch (direction) {
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

        if (isDead) {
            double rotate = switch (this.getAttributes().getDirection()) {
                case UP -> -90;
                case DOWN -> 90;
                case LEFT -> 180;
                default -> 0;
            };

            Sprite deadSprite = CONF_PACMAN_DEAD_SPRITE.getSprite();
            deadSprite.setRotate(rotate);
            this.setSprite(deadSprite);
        } else {
            setSprite(CONF_PACMAN_IDLE_SPRITE.getSprite());
        }
    }

    /**
     * Return the spawn point of PacMan (where pacman needs to spawn when it dies)
     *
     * @return Coordinates of the spawn point
     */
    public Coordinates<Double> getSpawnPoint() {
        return spawnPoint;
    }

    /**
     * Allows to set the spawn point of PacMan (where it spawns when dies
     *
     * @param coords Coordinates of the new spawn point
     */
    public void setSpawnPoint(Coordinates<Double> coords) {
        this.spawnPoint = coords;
    }
}

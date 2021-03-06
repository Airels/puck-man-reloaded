package config.entities;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import view.images.Image;

/**
 * PacMan configuration
 */
public final class PacManConfiguration {
    public final static String CONF_PACMAN_NAME = "PacMan";
    public final static double CONF_PACMAN_SPEED = 5;
    public final static Coordinates<Double> CONF_DEFAULT_PACMAN_POSITION = new Coordinates<>(9.05, 16.05);

    public final static Image CONF_PACMAN_IDLE_SPRITE = Image.PAC_MAN_IDLE;
    public final static Image CONF_PACMAN_SPRITE = Image.PAC_MAN;
    public final static Image CONF_PACMAN_DEAD_SPRITE = Image.PAC_MAN_DEATH;
}

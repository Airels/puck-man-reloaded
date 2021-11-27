package config.pacman;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import view.images.Image;

public final class PacManConfiguration {
    public final static String CONF_PACMAN_NAME = "PacMan";
    public final static double CONF_PACMAN_SPEED = 5;
    public final static Coordinates<Double> CONF_DEFAULT_PACMAN_POSITION = new Coordinates<>(9.05, 16.05);

    public final static Sprite CONF_PACMAN_SPRITE = Image.PAC_MAN.getSprite();
    public final static Sprite CONF_PACMAN_DEAD_SPRITE = Image.PAC_MAN_DEATH.getSprite();
}

package config.pacman;

import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import view.images.Image;

public class PacManConfiguration {
    public final static String CONF_PACMAN_NAME = "PacMan";
    public final static double CONF_PACMAN_SPEED = 7.5;
    // public final static String CONF_BLINKY_NORMAL_SPRITE_PATH TODO ?
    public final static Sprite CONF_PACMAN_SPRITE = Image.PAC_MAN.getSprite();
}

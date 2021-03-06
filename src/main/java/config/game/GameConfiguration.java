package config.game;

import fr.r1r0r0.deltaengine.model.Coordinates;
import view.colors.Color;

/**
 * Global game configuration
 */
public final class GameConfiguration {

    public final static long CONF_ENERGIZED_TIME = 7_500;

    public final static String CONF_READY_TEXT = "READY!";
    public final static Coordinates<Double> CONF_READY_POSITION = new Coordinates<>(8.5, 12.70);
    public final static int CONF_READY_SIZE = 30;
    public final static Color CONF_READY_COLOR = Color.YELLOW;

    public final static long CONF_TUNNELS_COOLDOWN = 350;

    public final static int CONF_GAINED_LIVES = 1;
    public final static int CONF_NUMBER_OF_LEVELS_TO_PASS_BEFORE_GAIN_LIVES = 1;

    public final static double CONF_SOUND_SIREN_CHASE_SPEED = 0.5;
    public final static double CONF_SOUND_SIREN_SCARED_SPEED = 8;
    public final static double CONF_SOUND_SIREN_CHASE_VOLUME = 0.15;
    public final static double CONF_SOUND_SIREN_SCARED_VOLUME = 1;
}

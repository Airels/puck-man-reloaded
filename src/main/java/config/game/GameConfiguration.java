package config.game;

import fr.r1r0r0.deltaengine.model.Coordinates;
import view.colors.Color;

public final class GameConfiguration {

    public final static long CONF_ENERGIZED_TIME = 7_500;

    public final static String CONF_READY_TEXT = "READY!";
    public final static Coordinates<Double> CONF_READY_POSITION = new Coordinates<>(8.5, 12.70);
    public final static int CONF_READY_SIZE = 30;
    public final static Color CONF_READY_COLOR = Color.YELLOW;
}

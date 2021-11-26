package config.game;

import fr.r1r0r0.deltaengine.model.Coordinates;
import view.colors.Color;

/**
 * Game HUD Configuration
 */
public final class GlobalHUDConfiguration {

    public final static int CONF_GLOBAL_HUD_HEIGHT_SIZE = 2;
    public final static int CONF_GLOBAL_HUD_HEIGHT_MARGIN = 1;

    public final static int CONF_GLOBAL_HUD_LIVES_TEXT_SIZE = 40;
    public final static int CONF_GLOBAL_HUD_SCORE_TEXT_SIZE = 40;
    public final static int CONF_GLOBAL_HUD_ENERGIZED_MODE_TIMER_TEXT_SIZE = 20;

    public final static String CONF_GLOBAL_HUD_LIVES_PRE_TEXT = "Lives: ";
    public final static String CONF_GLOBAL_HUD_SCORE_PRE_TEXT = "Score: ";
    public final static String CONF_GLOBAL_HUD_ENERGIZED_MODE_TIMER_PRE_TEXT = "Remaining time: ";

    public final static Color CONF_GLOBAL_HUD_LIVES_TEXT_COLOR = Color.WHITE;
    public final static Color CONF_GLOBAL_HUD_SCORE_TEXT_COLOR = Color.WHITE;
    public final static Color CONF_GLOBAL_HUD_ENERGIZED_MODE_TEXT_COLOR = Color.CYAN;

    public final static String CONF_GLOBAL_HUD_LIVES_TEXT_FONT = "Consolas";
    public final static String CONF_GLOBAL_HUD_SCORE_TEXT_FONT = "Consolas";
    public final static String CONF_GLOBAL_HUD_ENERGIZED_MODE_TIMER_TEXT_FONT = "Consolas";

    public final static Coordinates<Double> CONF_GLOBAL_HUD_LIVES_TEXT_POSITION = new Coordinates<>(0., 0.);
    public final static Coordinates<Double> CONF_GLOBAL_HUD_SCORE_TEXT_POSITION = new Coordinates<>(10., 0.);
    public final static Coordinates<Double> CONF_GLOBAL_HUD_ENERGIZED_MODE_TIMER_TEXT_POSITION = new Coordinates<>(2.5, .5);
}

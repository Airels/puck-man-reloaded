package config.levels;

import fr.r1r0r0.deltaengine.model.Coordinates;
import view.colors.Color;

public class GameOverScreenConfigurator {
    public final static String CONF_GAME_OVER_TITLE = "GAME OVER";
    public final static Coordinates<Double> CONF_GAME_OVER_TITLE_POSITION = new Coordinates<>(10.0, 1.0);
    public final static int CONF_GAME_OVER_TITLE_SIZE = 100;
    public final static Color CONF_GAME_OVER_TITLE_COLOR = Color.RED;

    public final static Coordinates<Double> CONF_GAME_OVER_SCORE_POSITION = new Coordinates<>(30.0, 5.0);
    public final static int CONF_GAME_OVER_SCORE_SIZE = 50;
    public final static Color CONF_GAME_OVER_SCORE_COLOR = Color.WHITE;
    public final static String CONF_GAME_OVER_SCORE_PREFIX_TEXT = "Score: ";

    public final static String CONF_GAME_OVER_COMMENT_TEXT = "Press ENTER to return to menu...";
    public final static Coordinates<Double> CONF_GAME_OVER_COMMENT_POSITION = new Coordinates<>(20.0, 10.0);
    public final static int CONF_GAME_OVER_COMMENT_SIZE = 25;
    public final static Color CONF_GAME_OVER_COMMENT_COLOR = Color.WHITE;
}

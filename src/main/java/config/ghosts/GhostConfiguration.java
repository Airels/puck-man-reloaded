package config.ghosts;

import fr.r1r0r0.deltaengine.model.sprites.Image;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import fr.r1r0r0.deltaengine.view.colors.Color;
import model.ai.ghosts.*;

public class GhostConfiguration {
    public final static String CONF_BLINKY_NAME = "Blinky";
    public final static double CONF_BLINKY_SPEED = 5.1;
    // public final static String CONF_BLINKY_NORMAL_SPRITE_PATH TODO ?
    // public final static String CONF_BLINKY_SCARED_SPRITE_PATH TODO ?
    public final static Sprite CONF_BLINKY_NORMAL_SPRITE = new Rectangle(Color.RED);
    public final static Sprite CONF_BLINKY_SCARED_SPRITE = new Rectangle(Color.BLUE);
    public final static GhostAI CONF_BLINKY_AI = new BlinkyAI();


    public final static String CONF_PINKY_NAME = "Pinky";
    public final static double CONF_PINKY_SPEED = 5.1;
    // public final static String CONF_PINKY_NORMAL_SPRITE_PATH
    // public final static String CONF_PINKY_SCARED_SPRITE_PATH
    public final static Sprite CONF_PINKY_NORMAL_SPRITE = new Rectangle(view.colors.Color.PINK.getEngineColor());
    public final static Sprite CONF_PINKY_SCARED_SPRITE = new Rectangle(Color.BLUE);
    public final static GhostAI CONF_PINKY_AI = new PinkyAI();


    public final static String CONF_INKY_NAME = "Inky";
    public final static double CONF_INKY_SPEED = 5.1;
    // public final static String CONF_INKY_NORMAL_SPRITE_PATH
    // public final static String CONF_INKY_SCARED_SPRITE_PATH
    public final static Sprite CONF_INKY_NORMAL_SPRITE = new Rectangle(Color.CYAN);
    public final static Sprite CONF_INKY_SCARED_SPRITE = new Rectangle(Color.BLUE);
    public final static GhostAI CONF_INKY_AI = new InkyAI();


    public final static String CONF_CLYDE_NAME = "Clyde";
    public final static double CONF_CLYDE_SPEED = 5.1;
    // public final static String CONF_CLYDE_NORMAL_SPRITE_PATH
    // public final static String CONF_CLYDE_SCARED_SPRITE_PATH
    public final static Sprite CONF_CLYDE_NORMAL_SPRITE = new Rectangle(view.colors.Color.ORANGE.getEngineColor());
    public final static Sprite CONF_CLYDE_SCARED_SPRITE = new Rectangle(Color.BLUE);
    public final static GhostAI CONF_CLYDE_AI = new ClydeAI();
}

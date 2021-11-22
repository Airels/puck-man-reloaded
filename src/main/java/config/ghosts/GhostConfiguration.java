package config.ghosts;

import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import fr.r1r0r0.deltaengine.view.colors.Color;
import model.ai.ghosts.*;
import view.images.Image;
import java.util.Arrays;
import java.util.List;

public class GhostConfiguration {
    public final static List<Sprite> CONF_DEAD_GHOST_EYES_SPRITES = Arrays.asList(
            Image.EYES_LEFT.getSprite(),
            Image.EYES_RIGHT.getSprite(),
            Image.EYES_UP.getSprite(),
            Image.EYES_DOWN.getSprite());
    public final static String CONF_BLINKY_NAME = "Blinky";
    public final static double CONF_BLINKY_SPEED = 5.1;
    public final static double CONF_BLINKY_SCARED_SPEED = CONF_BLINKY_SPEED/2;
    // public final static String CONF_BLINKY_NORMAL_SPRITE_PATH TODO ?
    // public final static String CONF_BLINKY_SCARED_SPRITE_PATH TODO ?
    public final static Sprite CONF_BLINKY_NORMAL_SPRITE = Image.BLINKY_LEFT.getSprite();
    public final static List<Sprite> CONF_BLINKY_NORMAL_SPRITES = Arrays.asList(
                Image.BLINKY_LEFT.getSprite(),
                Image.BLINKY_RIGHT.getSprite(),
                Image.BLINKY_UP.getSprite(),
                Image.BLINKY_DOWN.getSprite());


    public final static Sprite CONF_BLINKY_SCARED_SPRITE = Image.SCARED_GHOST.getSprite();
    public final static GhostAI CONF_BLINKY_AI = new BlinkyAI();


    public final static String CONF_PINKY_NAME = "Pinky";
    public final static double CONF_PINKY_SPEED = 5.1;
    public final static double CONF_PINKY_SCARED_SPEED = CONF_PINKY_SPEED/2;
    // public final static String CONF_PINKY_NORMAL_SPRITE_PATH
    // public final static String CONF_PINKY_SCARED_SPRITE_PATH
    public final static Sprite CONF_PINKY_NORMAL_SPRITE = new Rectangle(view.colors.Color.PINK.getEngineColor());
    public final static List<Sprite> CONF_PINKY_NORMAL_SPRITES = Arrays.asList(
                Image.PINKY_LEFT.getSprite(),
                Image.PINKY_RIGHT.getSprite(),
                Image.PINKY_UP.getSprite(),
                Image.PINKY_DOWN.getSprite());
    public final static Sprite CONF_PINKY_SCARED_SPRITE = Image.SCARED_GHOST.getSprite();
    public final static GhostAI CONF_PINKY_AI = new PinkyAI();


    public final static String CONF_INKY_NAME = "Inky";
    public final static double CONF_INKY_SPEED = 5.1;
    public final static double CONF_INKY_SCARED_SPEED = CONF_INKY_SPEED/2;
    // public final static String CONF_INKY_NORMAL_SPRITE_PATH
    // public final static String CONF_INKY_SCARED_SPRITE_PATH
    public final static Sprite CONF_INKY_NORMAL_SPRITE = new Rectangle(Color.CYAN);
    public final static List<Sprite> CONF_INKY_NORMAL_SPRITES = Arrays.asList(
            Image.INKY_LEFT.getSprite(),
            Image.INKY_RIGHT.getSprite(),
            Image.INKY_UP.getSprite(),
            Image.INKY_DOWN.getSprite());
    public final static Sprite CONF_INKY_SCARED_SPRITE = Image.SCARED_GHOST.getSprite();
    public final static GhostAI CONF_INKY_AI = new InkyAI();


    public final static String CONF_CLYDE_NAME = "Clyde";
    public final static double CONF_CLYDE_SPEED = 5.1;
    public final static double CONF_CLYDE_SCARED_SPEED = CONF_CLYDE_SPEED/2;
    // public final static String CONF_CLYDE_NORMAL_SPRITE_PATH
    // public final static String CONF_CLYDE_SCARED_SPRITE_PATH
    public final static List<Sprite> CONF_CLYDE_NORMAL_SPRITES = Arrays.asList(
            Image.CLYDE_LEFT.getSprite(),
            Image.CLYDE_RIGHT.getSprite(),
            Image.CLYDE_UP.getSprite(),
            Image.CLYDE_DOWN.getSprite());
    public final static Sprite CONF_CLYDE_SCARED_SPRITE = Image.SCARED_GHOST.getSprite();
    public final static GhostAI CONF_CLYDE_AI = new ClydeAI();
}

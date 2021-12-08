package config.entities;

import model.ai.GhostAIs;
import view.SpriteContainer;
import view.images.Image;

/**
 * All Ghosts configurations and parameters
 */
public final class GhostConfiguration {

    public final static double CONF_GHOST_SPEED = 5.1;

    public final static String CONF_BLINKY_NAME = "Blinky";
    public final static GhostAIs CONF_BLINKY_AI = GhostAIs.BLINKY_AI;
    public final static double CONF_BLINKY_SPEED = CONF_GHOST_SPEED;
    public final static double CONF_BLINKY_SCARED_SPEED = CONF_BLINKY_SPEED/2;
    public final static double CONF_BLINKY_FLEEING_SPEED = CONF_BLINKY_SPEED*2;
    public final static SpriteContainer CONF_BLINKY_NORMAL_SPRITES = new SpriteContainer(
            Image.BLINKY_LEFT.getSprite(),
            Image.BLINKY_UP.getSprite(),
            Image.BLINKY_DOWN.getSprite(),
            Image.BLINKY_RIGHT.getSprite()
    );
    public final static SpriteContainer CONF_BLINKY_SCARED_SPRITE = new SpriteContainer(
            Image.SCARED_GHOST.getSprite(),
            Image.SCARED_GHOST.getSprite(),
            Image.SCARED_GHOST.getSprite(),
            Image.SCARED_GHOST.getSprite()
    );
    public final static SpriteContainer CONF_BLINKY_FLEEING_SPRITES = new SpriteContainer(
            Image.EYES_LEFT.getSprite(),
            Image.EYES_UP.getSprite(),
            Image.EYES_DOWN.getSprite(),
            Image.EYES_RIGHT.getSprite()
    );
    public final static double CONF_BLINKY_SWITCHING_NORMAL_TO_SCATTER_PROBABILITY = 0.05;
    //public final static int CONF_BLINKY_MAXIMUM_SCATTER_TRAVEL_CELLS = 50;


    public final static String CONF_PINKY_NAME = "Pinky";
    public final static GhostAIs CONF_PINKY_AI = GhostAIs.PINKY_AI;
    public final static double CONF_PINKY_SPEED = CONF_GHOST_SPEED;
    public final static double CONF_PINKY_SCARED_SPEED = CONF_PINKY_SPEED/2;
    public final static double CONF_PINKY_FLEEING_SPEED = CONF_BLINKY_SPEED*2;
    public final static SpriteContainer CONF_PINKY_NORMAL_SPRITES = new SpriteContainer(
            Image.PINKY_LEFT.getSprite(),
            Image.PINKY_UP.getSprite(),
            Image.PINKY_DOWN.getSprite(),
            Image.PINKY_RIGHT.getSprite()
    );
    public final static SpriteContainer CONF_PINKY_SCARED_SPRITE = new SpriteContainer(
            Image.SCARED_GHOST.getSprite(),
            Image.SCARED_GHOST.getSprite(),
            Image.SCARED_GHOST.getSprite(),
            Image.SCARED_GHOST.getSprite()
    );
    public final static SpriteContainer CONF_PINKY_FLEEING_SPRITES = new SpriteContainer(
            Image.EYES_LEFT.getSprite(),
            Image.EYES_UP.getSprite(),
            Image.EYES_DOWN.getSprite(),
            Image.EYES_RIGHT.getSprite()
    );
    public final static double CONF_PINKY_SWITCHING_NORMAL_TO_SCATTER_PROBABILITY = 0.02;
    //public final static int CONF_PINKY_MAXIMUM_SCATTER_TRAVEL_CELLS = 50;


    public final static String CONF_INKY_NAME = "Inky";
    public final static GhostAIs CONF_INKY_AI = GhostAIs.INKY_AI;
    public final static double CONF_INKY_SPEED = CONF_GHOST_SPEED;
    public final static double CONF_INKY_SCARED_SPEED = CONF_INKY_SPEED/2;
    public final static double CONF_INKY_FLEEING_SPEED = CONF_BLINKY_SPEED*2;
    public final static SpriteContainer CONF_INKY_NORMAL_SPRITES = new SpriteContainer(
            Image.INKY_LEFT.getSprite(),
            Image.INKY_UP.getSprite(),
            Image.INKY_DOWN.getSprite(),
            Image.INKY_RIGHT.getSprite()
    );
    public final static SpriteContainer CONF_INKY_SCARED_SPRITE = new SpriteContainer(
            Image.SCARED_GHOST.getSprite(),
            Image.SCARED_GHOST.getSprite(),
            Image.SCARED_GHOST.getSprite(),
            Image.SCARED_GHOST.getSprite()
    );
    public final static SpriteContainer CONF_INKY_FLEEING_SPRITES = new SpriteContainer(
            Image.EYES_LEFT.getSprite(),
            Image.EYES_UP.getSprite(),
            Image.EYES_DOWN.getSprite(),
            Image.EYES_RIGHT.getSprite()
    );
    public final static double CONF_INKY_SWITCHING_NORMAL_TO_SCATTER_PROBABILITY = 0;
    //public final static int CONF_INKY_MAXIMUM_SCATTER_TRAVEL_CELLS = 50;


    public final static String CONF_CLYDE_NAME = "Clyde";
    public final static GhostAIs CONF_CLYDE_AI = GhostAIs.CLYDE_AI;
    public final static double CONF_CLYDE_SPEED = CONF_GHOST_SPEED;
    public final static double CONF_CLYDE_SCARED_SPEED = CONF_CLYDE_SPEED/2;
    public final static double CONF_CLYDE_FLEEING_SPEED = CONF_BLINKY_SPEED*2;
    public final static SpriteContainer CONF_CLYDE_NORMAL_SPRITES = new SpriteContainer(
            Image.CLYDE_LEFT.getSprite(),
            Image.CLYDE_UP.getSprite(),
            Image.CLYDE_DOWN.getSprite(),
            Image.CLYDE_RIGHT.getSprite()
    );
    public final static SpriteContainer CONF_CLYDE_SCARED_SPRITE = new SpriteContainer(
            Image.SCARED_GHOST.getSprite(),
            Image.SCARED_GHOST.getSprite(),
            Image.SCARED_GHOST.getSprite(),
            Image.SCARED_GHOST.getSprite()
    );
    public final static SpriteContainer CONF_CLYDE_FLEEING_SPRITES = new SpriteContainer(
            Image.EYES_LEFT.getSprite(),
            Image.EYES_UP.getSprite(),
            Image.EYES_DOWN.getSprite(),
            Image.EYES_RIGHT.getSprite()
    );
    public final static double CONF_CLYDE_SWITCHING_NORMAL_TO_SCATTER_PROBABILITY = 0;
    //public final static int CONF_CLYDE_MAXIMUM_SCATTER_TRAVEL_CELLS = 50;


    public final static String CONF_BLACKY_NAME = "Blacky";
    public final static GhostAIs CONF_BLACKY_AI = GhostAIs.BLACKY_AI;
    public final static double CONF_BLACKY_SPEED = CONF_GHOST_SPEED;
    public final static double CONF_BLACKY_SCARED_SPEED = CONF_BLACKY_SPEED/2;
    public final static double CONF_BLACKY_FLEEING_SPEED = CONF_BLACKY_SPEED*2;
    public final static SpriteContainer CONF_BLACKY_NORMAL_SPRITES = new SpriteContainer(
            Image.BLACKY_LEFT.getSprite(),
            Image.BLACKY_UP.getSprite(),
            Image.BLACKY_DOWN.getSprite(),
            Image.BLACKY_RIGHT.getSprite()
    );
    public final static SpriteContainer CONF_BLACKY_SCARED_SPRITE = new SpriteContainer(
            Image.SCARED_GHOST.getSprite(),
            Image.SCARED_GHOST.getSprite(),
            Image.SCARED_GHOST.getSprite(),
            Image.SCARED_GHOST.getSprite()
    );
    public final static SpriteContainer CONF_BLACKY_FLEEING_SPRITES = new SpriteContainer(
            Image.EYES_LEFT.getSprite(),
            Image.EYES_UP.getSprite(),
            Image.EYES_DOWN.getSprite(),
            Image.EYES_RIGHT.getSprite()
    );
    public final static double CONF_BLACKY_SWITCHING_NORMAL_TO_SCATTER_PROBABILITY = 0.05;
    //public final static int CONF_BLACKY_MAXIMUM_SCATTER_TRAVEL_CELLS = 50;


    public final static String CONF_SKEAZLY_NAME = "Skeazly";
    public final static GhostAIs CONF_SKEAZLY_AI = GhostAIs.SKEAZLY;
    public final static double CONF_SKEAZLY_SPEED = CONF_GHOST_SPEED;
    public final static double CONF_SKEAZLY_SCARED_SPEED = CONF_SKEAZLY_SPEED/2;
    public final static double CONF_SKEAZLY_FLEEING_SPEED = CONF_SKEAZLY_SPEED*2;
    public final static SpriteContainer CONF_SKEAZLY_NORMAL_SPRITES = new SpriteContainer(
            Image.SKEAZLY_LEFT.getSprite(),
            Image.SKEAZLY_UP.getSprite(),
            Image.SKEAZLY_DOWN.getSprite(),
            Image.SKEAZLY_RIGHT.getSprite()
    );
    public final static SpriteContainer CONF_SKEAZLY_SCARED_SPRITE = new SpriteContainer(
            Image.SCARED_GHOST.getSprite(),
            Image.SCARED_GHOST.getSprite(),
            Image.SCARED_GHOST.getSprite(),
            Image.SCARED_GHOST.getSprite()
    );
    public final static SpriteContainer CONF_SKEAZLY_FLEEING_SPRITES = new SpriteContainer(
            Image.EYES_LEFT.getSprite(),
            Image.EYES_UP.getSprite(),
            Image.EYES_DOWN.getSprite(),
            Image.EYES_RIGHT.getSprite()
    );
    public final static double CONF_SKEAZLY_SWITCHING_NORMAL_TO_SCATTER_PROBABILITY = 0;
    //public final static int CONF_SKEAZLY_MAXIMUM_SCATTER_TRAVEL_CELLS = 50;
}

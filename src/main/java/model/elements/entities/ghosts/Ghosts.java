package model.elements.entities.ghosts;

import config.pacman.PacManConfiguration;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import model.Game;
import model.actions.events.PacManTouchedByGhostEvent;
import model.ai.ghosts.*;
import model.maps.Level;

import java.util.List;

import static config.ghosts.GhostConfiguration.*;

public enum Ghosts {

    BLINKY(CONF_BLINKY_NAME, CONF_BLINKY_NORMAL_SPRITES, CONF_BLINKY_SCARED_SPRITE, CONF_BLINKY_AI, CONF_BLINKY_SPEED, CONF_BLINKY_SCARED_SPEED),
    PINKY(CONF_PINKY_NAME, CONF_PINKY_NORMAL_SPRITES, CONF_PINKY_SCARED_SPRITE, CONF_PINKY_AI, CONF_PINKY_SPEED, CONF_PINKY_SCARED_SPEED),
    INKY(CONF_INKY_NAME, CONF_INKY_NORMAL_SPRITES, CONF_INKY_SCARED_SPRITE, CONF_INKY_AI, CONF_INKY_SPEED, CONF_INKY_SCARED_SPEED),
    CLYDE(CONF_CLYDE_NAME, CONF_CLYDE_NORMAL_SPRITES, CONF_CLYDE_SCARED_SPRITE, CONF_CLYDE_AI, CONF_CLYDE_SPEED, CONF_CLYDE_SCARED_SPEED);

    private final String name;
    private final Sprite scaredSprite;
    private final GhostAI ai;
    private final List<Sprite> normalSprites;
    private final double normalSpeed, scaredSpeed;

    Ghosts(String name, List<Sprite> normalSprites, Sprite scaredSprite, GhostAI ai, double normalSpeed, double scaredSpeed) {
        this.name = name;
        this.normalSprites = normalSprites;
        this.scaredSprite = scaredSprite;
        this.ai = ai;
        this.normalSpeed = normalSpeed;
        this.scaredSpeed = scaredSpeed;
    }

    public Ghost build(Level currentLevel) {
        MapLevel currentMap = currentLevel.getMapLevelLoadable().getMapLevel();
        Game game = currentLevel.getGame();

        Ghost ghost = new Ghost(name, currentMap, normalSprites, scaredSprite, ai, normalSpeed, scaredSpeed);
        ghost.setCollisionEvent(currentMap.getEntity(PacManConfiguration.CONF_PACMAN_NAME), new PacManTouchedByGhostEvent(game, ghost));

        return ghost;
    }

    public Ghost build(Level currentLevel, Coordinates<Double> coords) {
        Ghost g = build(currentLevel);
        g.setCoordinates(coords);
        return g;
    }
}

package model.elements.entities.ghosts;

import config.pacman.PacManConfiguration;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import model.Game;
import model.actions.events.PacManTouchedByGhostEvent;
import model.ai.ghosts.*;
import model.levels.Level;
import view.SpriteContainer;

import java.util.List;

import static config.ghosts.GhostConfiguration.*;

/**
 * Declarations of All ghosts, and build them according to their properties.
 * @see config.ghosts.GhostConfiguration to change Ghost properties
 */
public enum Ghosts {

    BLINKY(CONF_BLINKY_NAME, CONF_BLINKY_NORMAL_SPRITES, CONF_BLINKY_SCARED_SPRITE, CONF_BLINKY_FLEEING_SPRITES, CONF_BLINKY_AI, CONF_BLINKY_SPEED, CONF_BLINKY_SCARED_SPEED, CONF_BLINKY_FLEEING_SPEED),
    PINKY(CONF_PINKY_NAME, CONF_PINKY_NORMAL_SPRITES, CONF_PINKY_SCARED_SPRITE, CONF_PINKY_FLEEING_SPRITES, CONF_PINKY_AI, CONF_PINKY_SPEED, CONF_PINKY_SCARED_SPEED, CONF_PINKY_FLEEING_SPEED),
    INKY(CONF_INKY_NAME, CONF_INKY_NORMAL_SPRITES, CONF_INKY_SCARED_SPRITE, CONF_INKY_FLEEING_SPRITES, CONF_INKY_AI, CONF_INKY_SPEED, CONF_INKY_SCARED_SPEED, CONF_INKY_FLEEING_SPEED),
    CLYDE(CONF_CLYDE_NAME, CONF_CLYDE_NORMAL_SPRITES, CONF_CLYDE_SCARED_SPRITE, CONF_CLYDE_FLEEING_SPRITES, CONF_CLYDE_AI, CONF_CLYDE_SPEED, CONF_CLYDE_SCARED_SPEED, CONF_CLYDE_FLEEING_SPEED);

    private final String name;
    private final GhostAIs ai;
    private final SpriteContainer normalSprites, scaredSprites, fleeingSprites;
    private final double normalSpeed, scaredSpeed, fleeingSpeed;

    /**
     * Default ghost constructor.
     * @param name String name of the Ghost
     * @param normalSprites Sprites when Ghost is in Normal state
     * @param scaredSprites Sprite when Ghost is in Scared state (energized mode)
     * @param fleeingSprites Sprites when Ghost is in Fleeing state (when PacMan eat it)
     * @param ai Ghost AI
     * @param normalSpeed double speed when Ghost is in Normal state
     * @param scaredSpeed double speed when Ghost is in Scared state
     * @param fleeingSpeed double speed when Ghost is in Fleeing state
     */
    Ghosts(String name, SpriteContainer normalSprites, SpriteContainer scaredSprites, SpriteContainer fleeingSprites, GhostAIs ai, double normalSpeed, double scaredSpeed, double fleeingSpeed) {
        this.name = name;
        this.normalSprites = normalSprites;
        this.scaredSprites = scaredSprites;
        this.fleeingSprites = fleeingSprites;
        this.ai = ai;
        this.normalSpeed = normalSpeed;
        this.scaredSpeed = scaredSpeed;
        this.fleeingSpeed = fleeingSpeed;
    }

    /**
     * Returns a new instance of the chosen Ghost
     * @param currentLevel Current level where Ghost will reside
     * @return new instance of a Ghost
     */
    public Ghost build(Level currentLevel) {
        return build(currentLevel, new Coordinates<>(0.0, 0.0));
    }

    /**
     * Second builder, returns a new instance of the chosen Ghost, with default coordinates.
     * @param currentLevel Current level where Ghost will reside
     * @param coords Initial coordinates of the Ghost
     * @return new instance of a Ghost
     */
    public Ghost build(Level currentLevel, Coordinates<Double> coords) {
        return build(currentLevel, coords, new Coordinates<>(0.0, 0.0), new Coordinates<>(0.0, 0.0));
    }

    /**
     * TODO
     * @param currentLevel
     * @param coords
     * @param retreatPoint
     * @return
     */
    public Ghost build(Level currentLevel, Coordinates<Double> coords, Coordinates<Double> retreatPoint) {
        return build(currentLevel, coords, coords.copy(), retreatPoint);
    }

    /**
     * TODO
     * @param currentLevel
     * @param coords
     * @param spawnPoint
     * @param retreatPoint
     * @return
     */
    public Ghost build(Level currentLevel, Coordinates<Double> coords, Coordinates<Double> spawnPoint, Coordinates<Double> retreatPoint) {
        MapLevel currentMap = currentLevel.getMapLevelLoadable().getMapLevel();
        Game game = currentLevel.getGame();

        GhostBuilder ghostBuilder = new GhostBuilder(name, currentMap, normalSprites, scaredSprites, fleeingSprites);
        ghostBuilder.setAI(ai)
                .setNormalSpeed(normalSpeed)
                .setScaredSpeed(scaredSpeed)
                .setFleeingSpeed(fleeingSpeed)
                .setCoordinates(coords)
                .setSpawnPoint(spawnPoint)
                .setRetreatPoint(retreatPoint)
                .setDimension(new Dimension(0.9, 0.9));

        Ghost ghost = ghostBuilder.build();
        ghost.setCollisionEvent(currentMap.getEntity(PacManConfiguration.CONF_PACMAN_NAME), new PacManTouchedByGhostEvent(game, ghost));

        return ghost;
    }
}

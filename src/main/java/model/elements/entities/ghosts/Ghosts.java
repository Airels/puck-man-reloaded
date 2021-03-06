package model.elements.entities.ghosts;

import config.entities.GhostConfiguration;
import config.entities.PacManConfiguration;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import model.Game;
import model.actions.events.PacManTouchedByGhostEvent;
import model.ai.GhostAIs;
import model.levels.Level;
import view.SpriteContainer;

import java.util.*;

import static config.entities.GhostConfiguration.*;

/**
 * Declarations of All ghosts, and build them according to their properties.
 *
 * @see GhostConfiguration to change Ghost properties
 */
public enum Ghosts {

    BLINKY(CONF_BLINKY_NAME, CONF_BLINKY_NORMAL_SPRITES, CONF_BLINKY_SCARED_SPRITE, CONF_BLINKY_FLEEING_SPRITES, CONF_BLINKY_AI, CONF_BLINKY_SPEED, CONF_BLINKY_SCARED_SPEED, CONF_BLINKY_FLEEING_SPEED, CONF_BLINKY_SWITCHING_NORMAL_TO_SCATTER_PROBABILITY),
    PINKY(CONF_PINKY_NAME, CONF_PINKY_NORMAL_SPRITES, CONF_PINKY_SCARED_SPRITE, CONF_PINKY_FLEEING_SPRITES, CONF_PINKY_AI, CONF_PINKY_SPEED, CONF_PINKY_SCARED_SPEED, CONF_PINKY_FLEEING_SPEED, CONF_PINKY_SWITCHING_NORMAL_TO_SCATTER_PROBABILITY),
    INKY(CONF_INKY_NAME, CONF_INKY_NORMAL_SPRITES, CONF_INKY_SCARED_SPRITE, CONF_INKY_FLEEING_SPRITES, CONF_INKY_AI, CONF_INKY_SPEED, CONF_INKY_SCARED_SPEED, CONF_INKY_FLEEING_SPEED, CONF_INKY_SWITCHING_NORMAL_TO_SCATTER_PROBABILITY),
    CLYDE(CONF_CLYDE_NAME, CONF_CLYDE_NORMAL_SPRITES, CONF_CLYDE_SCARED_SPRITE, CONF_CLYDE_FLEEING_SPRITES, CONF_CLYDE_AI, CONF_CLYDE_SPEED, CONF_CLYDE_SCARED_SPEED, CONF_CLYDE_FLEEING_SPEED, CONF_CLYDE_SWITCHING_NORMAL_TO_SCATTER_PROBABILITY),
    BLACKY(CONF_BLACKY_NAME, CONF_BLACKY_NORMAL_SPRITES, CONF_BLACKY_SCARED_SPRITE, CONF_BLACKY_FLEEING_SPRITES, CONF_BLACKY_AI, CONF_BLACKY_SPEED, CONF_BLACKY_SCARED_SPEED, CONF_BLACKY_FLEEING_SPEED, CONF_BLACKY_SWITCHING_NORMAL_TO_SCATTER_PROBABILITY),
    SKEAZLY(CONF_SKEAZLY_NAME, CONF_SKEAZLY_NORMAL_SPRITES, CONF_SKEAZLY_SCARED_SPRITE, CONF_SKEAZLY_FLEEING_SPRITES, CONF_SKEAZLY_AI, CONF_SKEAZLY_SPEED, CONF_SKEAZLY_SCARED_SPEED, CONF_SKEAZLY_FLEEING_SPEED, CONF_SKEAZLY_SWITCHING_NORMAL_TO_SCATTER_PROBABILITY);

    private final String name;
    private final GhostAIs ai;
    private final SpriteContainer normalSprites, scaredSprites, fleeingSprites;
    private final double normalSpeed, scaredSpeed, fleeingSpeed;
    private final double probaScatter;

    /**
     * Default ghost constructor.
     *
     * @param name           String name of the Ghost
     * @param normalSprites  Sprites when Ghost is in Normal state
     * @param scaredSprites  Sprite when Ghost is in Scared state (energized mode)
     * @param fleeingSprites Sprites when Ghost is in Fleeing state (when PacMan eat it)
     * @param ai             Ghost AI
     * @param normalSpeed    double speed when Ghost is in Normal state
     * @param scaredSpeed    double speed when Ghost is in Scared state
     * @param fleeingSpeed   double speed when Ghost is in Fleeing state
     * @param probaScatter   double probability to go in scatter mode
     */
    Ghosts(String name, SpriteContainer normalSprites, SpriteContainer scaredSprites, SpriteContainer fleeingSprites, GhostAIs ai, double normalSpeed, double scaredSpeed, double fleeingSpeed, double probaScatter) {
        this.name = name;
        this.normalSprites = normalSprites;
        this.scaredSprites = scaredSprites;
        this.fleeingSprites = fleeingSprites;
        this.ai = ai;
        this.normalSpeed = normalSpeed;
        this.scaredSpeed = scaredSpeed;
        this.fleeingSpeed = fleeingSpeed;
        this.probaScatter = probaScatter;
    }

    /**
     * First builder, returns a new instance of the chosen Ghost
     *
     * @param currentLevel Current level where Ghost will reside
     * @return new instance of a Ghost
     */
    public Ghost build(Level currentLevel) {
        return build(currentLevel, new Coordinates<>(0.0, 0.0));
    }

    /**
     * Second builder, returns a new instance of the chosen Ghost, with given coordinates.
     *
     * @param currentLevel Current level where Ghost will reside
     * @param coords       Initial coordinates of the Ghost
     * @return new instance of a Ghost
     */
    public Ghost build(Level currentLevel, Coordinates<Double> coords) {
        return build(currentLevel, coords, new Coordinates<>(0.0, 0.0), new Coordinates<>(0.0, 0.0));
    }

    /**
     * Third builder, returns a new instance of the chosen Ghost, with given coordinates and a chosen retreat point.
     *
     * @param currentLevel Current level where Ghost will reside
     * @param coords       Initial coordinates of the Ghost
     * @param retreatPoint Retreat point where Ghost could go when it was eaten by PacMan to re-transform itself into NORMAL
     * @return new instance of a Ghost
     */
    public Ghost build(Level currentLevel, Coordinates<Double> coords, Coordinates<Double> retreatPoint) {
        return build(currentLevel, coords, coords.copy(), retreatPoint);
    }

    /**
     * Fourth builder, returns a new instance of the chosen Ghost, with all required parameters to create a Ghost.
     *
     * @param currentLevel Current level where Ghost will reside
     * @param coords       Initial coordinates of the Ghost
     * @param spawnPoint   Spawn point of the Ghost, where Ghost will respawn when level is resetting (typically when PacMan dies)
     * @param retreatPoint Retreat point where Ghost could go when it was eaten by PacMan to re-transform itself into NORMAL
     * @return new instance of a Ghost
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
                .setProbaScatter(probaScatter);

        Ghost ghost = ghostBuilder.build();
        ghost.setCollisionEvent(currentMap.getEntity(PacManConfiguration.CONF_PACMAN_NAME), new PacManTouchedByGhostEvent(game, ghost));

        return ghost;
    }

    /**
     * Returns a random ghost of the enumeration
     * @param undesiredGhosts All ghosts that must not return (if empty, any ghost can be returned)
     * @return random Ghost or null if no one can be chosen
     */
    public static Ghosts getRandomGhost(Ghosts... undesiredGhosts) {
        List<Ghosts> ghosts = new LinkedList<>(Arrays.asList(Ghosts.values()));
        ghosts.removeAll(Arrays.asList(undesiredGhosts));
        if (ghosts.size() == 0)
            return null;

        int randomNumber = new Random().nextInt(ghosts.size());
        return ghosts.get(randomNumber);
    }
}

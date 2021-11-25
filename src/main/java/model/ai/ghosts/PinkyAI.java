package model.ai.ghosts;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import model.elements.entities.PacMan;
import model.elements.entities.ghosts.Ghost;
import model.exceptions.GhostTargetMissingException;

import java.util.Collection;
import java.util.Stack;

/**
 * An AI corresponding to the pink ghost in the game PacMan
 * His movement used to go to the closest cross according to pacMan current direction to kill him,
 * he always tries to anticipated him and chose the shortest way to do that
 *
 * In the original game, he go 2 cases in front of pacMan want to go
 */
public final class PinkyAI extends BasicGhostAI {

    /**
     * Pink - Speedy
     * try to ambush pac-man
     */

    private static final String[] FORBIDDEN_TARGET_NAMES = new String[]{
            config.ghosts.GhostConfiguration.CONF_BLINKY_NAME,
            config.ghosts.GhostConfiguration.CONF_INKY_NAME,
            config.ghosts.GhostConfiguration.CONF_CLYDE_NAME};

    /**
     * Constructor
     */
    public PinkyAI () {
        super();
    }

    @Override
    public GhostAI clone() {
        return new PinkyAI();
    }

    @Override
    protected Direction chooseDirection (Ghost ghost, MapLevel mapLevel) {
        Coordinates<Integer> destination;
        try {
            destination = findDestination(ghost,mapLevel);
        } catch (GhostTargetMissingException e) {
            return Direction.IDLE;
        }
        Collection<Coordinates<Integer>> forbiddenWays = findForbiddenWays(mapLevel);
        Coordinates<Integer> ghostCoordinate = Utils.getIntegerCoordinates(ghost);
        for (Coordinates<Integer> forbiddenWay : forbiddenWays) {
            if (forbiddenWay.equals(ghostCoordinate)) {
                return Direction.IDLE;
            }
        }
        return Utils.findShortestWay(ghost,mapLevel,destination,forbiddenWays);
    }

    /**
     * Find and return the final where the ghost must go
     * @param ghost a ghost
     * @param mapLevel a mapLevel
     * @return the final where the ghost must go
     * @throws GhostTargetMissingException if pacMan is not in the mapLevel
     */
    private Coordinates<Integer> findDestination (Ghost ghost, MapLevel mapLevel) throws GhostTargetMissingException {
        PacMan pacMan = findPacMan(ghost,mapLevel);
        Coordinates<Integer> pacManPosition = Utils.getIntegerCoordinates(pacMan);
        return Utils.findNextCross(ghost,mapLevel,pacManPosition,pacMan.getDirection());

    }

    /**
     * Find and return a collection of coordinates to avoid
     * @param mapLevel a mapLevel
     * @return a collection of coordinates to avoid
     */
    private Collection<Coordinates<Integer>> findForbiddenWays (MapLevel mapLevel) {
        Stack<Coordinates<Integer>> forbiddenWays = new Stack<>();
        for (String targetName : FORBIDDEN_TARGET_NAMES) {
            Entity entity = mapLevel.getEntity(targetName);
            if (entity != null) forbiddenWays.push(Utils.getIntegerCoordinates(entity));
        }
        return forbiddenWays;
    }

    @Override
    protected Coordinates<Integer> selectTarget (Ghost ghost, MapLevel mapLevel) {
        Coordinates<Integer> position = Utils.getIntegerCoordinates(ghost);
        return new Coordinates<>(position.getX() + direction.getX(),
                position.getY() + direction.getY());
    }

}

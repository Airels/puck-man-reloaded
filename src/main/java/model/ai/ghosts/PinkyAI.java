package model.ai.ghosts;

import config.pacman.PacManConfiguration;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import model.elements.entities.ghosts.Ghost;
import model.exceptions.GhostTargetMissingException;

import java.util.ArrayList;

/**
 * TODO
 */
public final class PinkyAI extends BasicGhostAI {

    /**
     * Pink - Speedy
     * try to ambush pac-man
     */

    //TODO: ameliorer pour ne pas etre similaire au rouge Blinky

    private static final String[] FORBIDDEN_TARGET_NAMES = new String[]{
            config.ghosts.GhostConfiguration.CONF_BLINKY_NAME,
            config.ghosts.GhostConfiguration.CONF_INKY_NAME,
            config.ghosts.GhostConfiguration.CONF_CLYDE_NAME};

    public PinkyAI () {
        super();
    }

    @Override
    public GhostAI clone() {
        return new PinkyAI();
    }

    @Override
    protected void scaryModeTick (Ghost ghost) {

    }

    @Override
    protected Direction chooseDirection (Ghost ghost, MapLevel mapLevel) {
        Coordinates<Integer> destination;
        try {
            destination = findDestination(ghost,mapLevel);
        } catch (GhostTargetMissingException e) {
            return Direction.IDLE;
        }
        ArrayList<Coordinates<Integer>> forbiddenWays = findForbiddenWays(mapLevel);
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
     * @throws GhostTargetMissingException if pacMan is not on the mapLevel
     */
    private Coordinates<Integer> findDestination (Ghost ghost, MapLevel mapLevel) throws GhostTargetMissingException {
        Entity entity = mapLevel.getEntity(PacManConfiguration.CONF_PACMAN_NAME);
        if (entity == null) throw new GhostTargetMissingException(ghost);
        Coordinates<Integer> pacManPosition = Utils.getIntegerCoordinates(entity);
        Direction pacManDirection = entity.getDirection();
        return Utils.findNextCross(ghost,mapLevel,pacManPosition,pacManDirection);

    }

    /**
     * TODO
     * @param mapLevel
     * @return
     */
    private ArrayList<Coordinates<Integer>> findForbiddenWays (MapLevel mapLevel) {
        ArrayList<Coordinates<Integer>> forbiddenWays = new ArrayList<>();
        for (String targetName : FORBIDDEN_TARGET_NAMES) {
            Entity entity = mapLevel.getEntity(targetName);
            if (entity != null) forbiddenWays.add(Utils.getIntegerCoordinates(entity));
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

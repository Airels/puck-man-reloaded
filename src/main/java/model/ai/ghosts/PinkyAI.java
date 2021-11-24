package model.ai.ghosts;

import config.pacman.PacManConfiguration;
import fr.r1r0r0.deltaengine.exceptions.NotInitializedException;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.elements.CollisionPositions;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.engines.DeltaEngine;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import model.elements.entities.ghosts.Ghost;
import model.exceptions.GhostTargetMissingException;

import java.util.ArrayList;
import java.util.LinkedList;

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

    private Coordinates<Integer> target;
    private Direction direction;

    public PinkyAI () {

    }

    @Override
    public GhostAI clone() {
        return new PinkyAI();
    }

    @Override
    protected void scaryModeTick(Ghost ghost) {

    }
    @Override
    protected void chaseModeTick(Ghost ghost) {

        if ( target != null && ! isTargetReach(ghost)) return;

        MapLevel mapLevel = ghost.getMapLevel();
        Coordinates<Integer> destination;
        try {
            destination = findDestination(ghost,mapLevel);
        } catch (GhostTargetMissingException e) {
            ghost.setDirection(Direction.IDLE);
            return;
        }

        Direction direction = Utils.findShortestWay_blinky(ghost,mapLevel,destination);
        ghost.setDirection(direction);
        this.direction = direction;
        this.target = nextTarget(ghost);

    }

    /**
     * Find and return the final where the ghost must go
     * //TODO milieu entre pinky et pacMan
     * @param ghost a ghost
     * @param mapLevel a mapLevel
     * @return the final where the ghost must go
     * @throws GhostTargetMissingException if pacMan is not on the mapLevel
     */
    private Coordinates<Integer> findDestination (Ghost ghost, MapLevel mapLevel) throws GhostTargetMissingException {
        Entity entity = mapLevel.getEntity(PacManConfiguration.CONF_PACMAN_NAME);
        if (entity == null) throw new GhostTargetMissingException(ghost);

        Coordinates<Integer> pacManPosition = Utils.getIntegerCoordinates(entity);

        //TODO, le croisement vers où il regarde

        LinkedList<Coordinates<Integer>> coordinatesLinkedList =
                Utils.findShortestWay_coordinates(ghost,mapLevel,pacManPosition);
        return coordinatesLinkedList.get(coordinatesLinkedList.size() / 2);
    }

    private ArrayList<Coordinates<Integer>> findForbiddenWays (MapLevel mapLevel) {
        ArrayList<Coordinates<Integer>> forbiddenWays = new ArrayList<>();
        for (String targetName : FORBIDDEN_TARGET_NAMES) {
            Entity entity = mapLevel.getEntity(targetName);
            if (entity == null) continue;
        }
        return forbiddenWays;
    }

    /**
     * Return and find the next target where the ghost must go
     * @param ghost a ghost
     * @return the next target where the ghost must go
     */
    private Coordinates<Integer> nextTarget (Ghost ghost) {
        Coordinates<Double> position = ghost.getCoordinates();
        Coordinates<Integer> delta = direction.getCoordinates();
        return new Coordinates<>(position.getX().intValue() + delta.getX(),
                position.getY().intValue() + delta.getY());
    }

    /**
     * Return if the target is reach
     * @param ghost a ghost
     * @return if the target is reach
     */
    private boolean isTargetReach (Ghost ghost) {
        try {
            if ( ! DeltaEngine.getKernelEngine().isAvailableDirection(ghost,direction)) return true;
        } catch (NotInitializedException e) {
            //e.printStackTrace();
        }
        return Utils.isOnTarget(ghost,target);
    }

}

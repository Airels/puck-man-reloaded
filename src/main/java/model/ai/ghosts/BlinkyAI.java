package model.ai.ghosts;

import config.pacman.PacManConfiguration;
import fr.r1r0r0.deltaengine.exceptions.NotInitializedException;
import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelCoordinatesOutOfBoundException;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.elements.CollisionPositions;
import fr.r1r0r0.deltaengine.model.elements.cells.Cell;
import fr.r1r0r0.deltaengine.model.elements.cells.CrossableCell;
import fr.r1r0r0.deltaengine.model.elements.cells.UncrossableCell;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.engines.DeltaEngine;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import fr.r1r0r0.deltaengine.view.colors.Color;
import model.elements.entities.PacMan;
import model.elements.entities.ghosts.Ghost;
import model.exceptions.GhostTargetMissingException;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * An AI corresponding to the red ghost in the game PacMan
 * His movement used to follow pacMan to kill him, he always chose the shortest way
 *
 * In the original game, he followed pacMan like his shadow
 */
public final class BlinkyAI extends BasicGhostAI {

    //TODO: faire le comportement de fuite

    /**
     * Red - Shadow
     * follow pac-man
     */

    private Coordinates<Integer> target;
    private Direction direction;

    //TODO mettre des attributs pour alleger les arguments des methodes
    public BlinkyAI () {}

    @Override
    public GhostAI clone() {
        return new BlinkyAI();
    }

    @Override
    protected void scaryModeTick (Ghost ghost) {
        //TODO
    }

    @Override
    protected void chaseModeTick (Ghost ghost) {
        if ( target != null && ! isTargetReach(ghost)) return;

        MapLevel mapLevel = ghost.getMapLevel();
        Coordinates<Integer> destination;
        try {
            destination = findDestination(ghost,mapLevel);
        } catch (GhostTargetMissingException e) {
            ghost.setDirection(Direction.IDLE);
            return;
        }

        Direction direction = Utils.findShortestWay_direction(ghost,mapLevel,destination);
        ghost.setDirection(direction);
        this.direction = direction;
        this.target = nextTarget(ghost);

    }

    /**
     * Find and return the final where the ghost must go
     * It is corresponding to pacMan position
     * @param ghost a ghost
     * @param mapLevel a mapLevel
     * @return the final where the ghost must go
     * @throws GhostTargetMissingException if pacMan is not on the mapLevel
     */
    private Coordinates<Integer> findDestination (Ghost ghost, MapLevel mapLevel) throws GhostTargetMissingException {
        Entity entity = mapLevel.getEntity(PacManConfiguration.CONF_PACMAN_NAME);
        if (entity == null) throw new GhostTargetMissingException(ghost);
        Coordinates<Double> coordinates = entity.getCoordinates();
        Dimension dimension = entity.getDimension();
        Coordinates<Double> topLeft = CollisionPositions.LEFT_TOP.calcPosition(coordinates,dimension);
        Coordinates<Double> botRight = CollisionPositions.RIGHT_BOT.calcPosition(coordinates,dimension);
        return new Coordinates<>((int) ((topLeft.getX() + botRight.getX()) / 2),
                (int) ((topLeft.getY() + botRight.getY()) / 2));
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
        Coordinates<Double> coordinates = ghost.getCoordinates();
        Dimension dimension = ghost.getDimension();
        Coordinates<Double> topLeft = CollisionPositions.LEFT_TOP.calcPosition(coordinates,dimension);
        Coordinates<Double> rightBot = CollisionPositions.RIGHT_BOT.calcPosition(coordinates,dimension);
        return topLeft.getX().intValue() == rightBot.getX().intValue()
                && topLeft.getY().intValue() == rightBot.getY().intValue()
                && topLeft.getX().intValue() == target.getX()
                && topLeft.getY().intValue() == target.getY();
    }

}

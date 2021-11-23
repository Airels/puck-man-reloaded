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

        Direction direction = findShortestWay(ghost,mapLevel,destination);
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
     * Find and return the direction that minimise the path to the destination
     * @param ghost a ghost
     * @param mapLevel a malLevel
     * @param destination a destionation where the ghost want to go
     * @return the direction that minimise the path to the destination
     */
    private Direction findShortestWay (Ghost ghost, MapLevel mapLevel, Coordinates<Integer> destination) {
        Coordinates<Double> coordinates = ghost.getCoordinates();
        Dimension dimension = ghost.getDimension();
        Coordinates<Double> topLeft = CollisionPositions.LEFT_TOP.calcPosition(coordinates,dimension);
        Coordinates<Double> botRight = CollisionPositions.RIGHT_BOT.calcPosition(coordinates,dimension);
        Coordinates<Integer> source = new Coordinates<>((int) ((topLeft.getX() + botRight.getX()) / 2),
                (int) ((topLeft.getY() + botRight.getY()) / 2));
        if (source.equals(destination)) return Direction.IDLE;
        return breadthFirstSearch(ghost,mapLevel,source,destination);
    }

    /**
     * Return the direction that minimise the length a path from source to direction
     * We use a breadth first search algorithm to explore the mapLevel from the source to the destination
     * @param ghost a ghost
     * @param mapLevel a malLevel
     * @param source the source where we run throw the mapLevel
     * @param destination the destination that we want to achieve
     * @return the direction that minimise the length a path from source to direction
     */
    private Direction breadthFirstSearch (Ghost ghost, MapLevel mapLevel, Coordinates<Integer> source,
                                             Coordinates<Integer> destination) {
        LinkedList<Node> nodes = new LinkedList<>();
        HashSet<Coordinates<Integer>> visited = new HashSet<>();
        Direction direction = breadthFirstSearch_aux(nodes,visited,source,null,ghost,mapLevel,destination);
        if (direction != null) return direction;
        LinkedList<Node> nextNodes;
        while ( ! nodes.isEmpty()) {
            nextNodes = new LinkedList<>();
            for (Node node : nodes) {
                direction = breadthFirstSearch_aux(nextNodes,visited,node.coordinates,node.direction,
                        ghost,mapLevel,destination);
                if (direction != null) return direction;
            }
            nodes = nextNodes;
        }
        return Direction.IDLE;
    }

    /**
     * Breadth first search algorithm to explore the mapLevel from the source to the destination
     * @param nodes list of node
     * @param visited set of coordinates already explored
     * @param source the source
     * @param origin the direction that was used to travel to this point
     * @param ghost a ghost
     * @param mapLevel a mapLevel
     * @param destination the destination
     * @return the direction that minimise the length a path from source to direction, return null if it is impossible
     * to determine this direction with the current data
     */
    private Direction breadthFirstSearch_aux (LinkedList<Node> nodes, HashSet<Coordinates<Integer>> visited,
                                    Coordinates<Integer> source, Direction origin,
                                    Ghost ghost, MapLevel mapLevel, Coordinates<Integer> destination) {
        for (Direction direction : Direction.values()) {
            if (direction == Direction.IDLE) continue;
            Coordinates<Integer> directionCoordinates = direction.getCoordinates();
            Coordinates<Integer> nextCoordinates = new Coordinates<>(source.getX() + directionCoordinates.getX(),
                    source.getY() + directionCoordinates.getY());
            if (nextCoordinates.equals(destination)) return (origin == null) ? direction : origin;

            if (visited.contains(nextCoordinates)) continue;
            visited.add(nextCoordinates);
            if (mapLevel.getCell(nextCoordinates.getX(),nextCoordinates.getY()).isCrossableBy(ghost))
                nodes.add(new Node(nextCoordinates,(origin == null) ? direction : origin));
        }
        return null;
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

    /**
     * A private class used to create a breath first search algorithm
     */
    private class Node {

        public Coordinates<Integer> coordinates;
        public Direction direction;

        /**
         * Constructor
         * @param coordinates a coordinates
         * @param direction a direction
         */
        private Node (Coordinates<Integer> coordinates, Direction direction) {
            this.coordinates = coordinates;
            this.direction = direction;
        }

    }

}

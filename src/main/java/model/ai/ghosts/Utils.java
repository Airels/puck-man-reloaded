package model.ai.ghosts;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.elements.CollisionPositions;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import model.elements.entities.ghosts.Ghost;

import java.util.HashSet;
import java.util.LinkedList;

public final class Utils {

    private Utils () {
        System.exit(42);
    }

    /**
     * Find and return the direction that minimise the path to the destination
     * @param ghost a ghost
     * @param mapLevel a malLevel
     * @param destination a destionation where the ghost want to go
     * @return the direction that minimise the path to the destination
     */
    public static Direction findShortestWay (Ghost ghost, MapLevel mapLevel, Coordinates<Integer> destination) {
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
    private static Direction breadthFirstSearch (Ghost ghost, MapLevel mapLevel, Coordinates<Integer> source,
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
    private static Direction breadthFirstSearch_aux (LinkedList<Node> nodes, HashSet<Coordinates<Integer>> visited,
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
     * A private class used to create a breath first search algorithm
     */
    private static class Node {

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

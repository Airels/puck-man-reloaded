package model.ai.ghosts;

import config.pacman.PacManConfiguration;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.elements.CollisionPositions;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import model.elements.entities.PacMan;
import model.elements.entities.ghosts.Ghost;
import model.exceptions.GhostTargetMissingException;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * TODO
 */
public final class BlinkyAI extends BasicGhostAI {

    /**
     * Red - Shadow
     * follow pac-man
     */

    //TODO mettre des attributs pour alleger les arguments des methodes
    public BlinkyAI () {}

    @Override
    protected void scaryModeTick (Ghost ghost) {
        //TODO
    }

    @Override
    protected void chaseModeTick (Ghost ghost) {
        MapLevel mapLevel = ghost.getMapLevel();
        Coordinates<Integer> target;
        try {
            target = findTarget(ghost,mapLevel);
        } catch (GhostTargetMissingException e) {
            ghost.setDirection(Direction.IDLE);
            return;
        }
        Direction direction = findShortestWay(ghost,mapLevel,target);
        ghost.setDirection(direction);
        System.out.println(target + " " + direction);
    }

    private Coordinates<Integer> findTarget (Ghost ghost, MapLevel mapLevel) throws GhostTargetMissingException {
        Entity entity = mapLevel.getEntity(PacManConfiguration.CONF_PACMAN_NAME);
        if (entity == null) throw new GhostTargetMissingException(ghost);
        Coordinates<Double> topLeft = entity.getCoordinates();
        Coordinates<Double> botRight = CollisionPositions.RIGHT_BOT.calcPosition(topLeft,entity.getDimension());
        return new Coordinates<>((int) ((topLeft.getX() + botRight.getX()) / 2),
                (int) ((topLeft.getY() + botRight.getY()) / 2));
    }

    private Direction findShortestWay (Ghost ghost, MapLevel mapLevel, Coordinates<Integer> destination) {
        Coordinates<Double> topLeft = ghost.getCoordinates();
        Coordinates<Double> botRight = CollisionPositions.RIGHT_BOT.calcPosition(topLeft,ghost.getDimension());
        Coordinates<Integer> source = new Coordinates<>((int) ((topLeft.getX() + botRight.getX()) / 2),
                (int) ((topLeft.getY() + botRight.getY()) / 2));
        return breadthFirstSearch(ghost,mapLevel,source,destination);
    }

    private Direction breadthFirstSearch (Ghost ghost, MapLevel mapLevel, Coordinates<Integer> source,
                                             Coordinates<Integer> destination) {
        LinkedList<Node> nodes = new LinkedList<>();
        HashSet<Coordinates<Integer>> visited = new HashSet<>();
        createAndAddNodes(nodes,visited,source,null,ghost,mapLevel,destination);
        LinkedList<Node> nextNodes;
        while ( ! nodes.isEmpty()) {
            nextNodes = new LinkedList<>();
            for (Node node : nodes) {
                Direction direction = createAndAddNodes(nextNodes,visited,node.coordinates,node.direction,
                        ghost,mapLevel,destination);
                if (direction != null) return direction;
            }
            nodes = nextNodes;
        }
        return Direction.IDLE;
    }

    private Direction createAndAddNodes (LinkedList<Node> nodes, HashSet<Coordinates<Integer>> visited,
                                    Coordinates<Integer> source, Direction origin,
                                    Ghost ghost, MapLevel mapLevel, Coordinates<Integer> destination) {
        for (Direction direction : Direction.values()) {
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

    private class Node {
        public Coordinates<Integer> coordinates;
        public Direction direction;
        private Node (Coordinates<Integer> coordinates, Direction direction) {
            this.coordinates = coordinates;
            this.direction = direction;
        }
    }

}

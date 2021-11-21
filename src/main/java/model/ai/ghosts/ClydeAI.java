package model.ai.ghosts;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.elements.CollisionPositions;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import model.elements.entities.ghosts.Ghost;

import java.util.ArrayList;
import java.util.Random;

/**
 * TODO
 */
public final class ClydeAI extends GhostAI {

    /**
     * Orange - Pokey
     * random pathfinding
     */

    private final Random random;
    private Coordinates<Integer> target;
    private Direction direction;

    public ClydeAI () {
        random = new Random();
        target = null;
    }

    @Override
    public void tick() {
        Ghost ghost = getGhost();
        if (ghost.isScared()) scaryTick(ghost);
        else offensiveTick(ghost);
    }

    private void scaryTick (Ghost ghost) {
        //TODO
    }

    private void offensiveTick (Ghost ghost){
        if (target == null || isTargetReach(ghost)) {
            direction = chooseDirection(ghost);
            target = selectTarget(ghost);
            ghost.setDirection(direction);
        }
    }

    private Direction chooseDirection (Ghost ghost) {
        MapLevel mapLevel = ghost.getMapLevel();
        Coordinates<Double> position = ghost.getCoordinates();
        int positionX = position.getX().intValue();
        int positionY = position.getY().intValue();

        ArrayList<Direction> directions = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            Coordinates<Integer> coordinates = direction.getCoordinates();
            int x = coordinates.getX();
            int y = coordinates.getY();
            if (mapLevel.getCell(positionX + x,positionY + y).isCrossableBy(ghost)) directions.add(direction);
        }
        int size = directions.size();
        return (size == 0) ? Direction.IDLE : directions.get(random.nextInt(size));
    }

    private Coordinates<Integer> selectTarget (Ghost ghost) {
        if (direction == Direction.IDLE) return null;
        MapLevel mapLevel = ghost.getMapLevel();
        Coordinates<Integer> directionCoordinate = direction.getCoordinates();
        Coordinates<Double> position = ghost.getCoordinates();
        int x = position.getX().intValue();
        int y = position.getY().intValue();
        while (mapLevel.getCell(x,y).isCrossableBy(ghost)) {
            x += directionCoordinate.getX();
            y += directionCoordinate.getY();
        }
        return new Coordinates<>(x - directionCoordinate.getX(), y - directionCoordinate.getY());
    }

    private boolean isTargetReach (Ghost ghost) {
        Coordinates<Double> topLeft = ghost.getCoordinates();
        Coordinates<Double> rightBot = CollisionPositions.RIGHT_BOT.calcPosition(topLeft,ghost.getDimension());
        return topLeft.getX().intValue() == rightBot.getX().intValue()
                && topLeft.getY().intValue() == rightBot.getY().intValue()
                && topLeft.getX().intValue() == target.getX()
                && topLeft.getY().intValue() == target.getY();
    }

}

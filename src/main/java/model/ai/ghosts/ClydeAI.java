package model.ai.ghosts;

import fr.r1r0r0.deltaengine.exceptions.NotInitializedException;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.elements.CollisionPositions;
import fr.r1r0r0.deltaengine.model.engines.DeltaEngine;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import model.elements.entities.ghosts.Ghost;

import java.util.ArrayList;
import java.util.Random;

/**
 * An AI corresponding to the orange ghost in the game PacMan
 * His movement are randomly choose, but he can only change direction when he reach a junction
 *
 * In the original game, his movement was not totally random, he used to chase pacMan exactly like
 * Blinky, and he started to retreat to the lower left corner when he was too close to pacMan
 */
public final class ClydeAI extends GhostAI {

    /**
     * Orange - Pokey
     * random pathfinding
     */

    private final Random random;
    private Coordinates<Integer> target;
    private Direction direction;

    /**
     * Constructor
     */
    public ClydeAI () {
        random = new Random();
        target = null;
    }

    @Override
    public void tick() {
        Ghost ghost = getGhost();
        if (ghost.isScared()) scaryModeTick(ghost);
        else chaseModeTick(ghost);
    }

    /**
     * Action when the ghost is in scary mode
     * @param ghost a ghost
     */
    private void scaryModeTick (Ghost ghost) {
        //TODO
    }

    /**
     * Action when the ghost is in chase mode (when he is not in scary mode)
     * @param ghost a ghost
     */
    private void chaseModeTick (Ghost ghost){
        if (target == null || isTargetReach(ghost)) {
            direction = chooseDirection(ghost);
            target = selectTarget(ghost);
            ghost.setDirection(direction);
        }
    }

    /**
     * Choose and return the next direction to follow
     * @param ghost a ghost
     * @return a direction
     */
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

    /**
     * Select and return the target, the next coordinates where the ghost must go
     * @param ghost a ghost
     * @return a coordinates
     */
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
        Coordinates<Double> topLeft = ghost.getCoordinates();
        Coordinates<Double> rightBot = CollisionPositions.RIGHT_BOT.calcPosition(topLeft,ghost.getDimension());
        return topLeft.getX().intValue() == rightBot.getX().intValue()
                && topLeft.getY().intValue() == rightBot.getY().intValue()
                && topLeft.getX().intValue() == target.getX()
                && topLeft.getY().intValue() == target.getY();
    }

}

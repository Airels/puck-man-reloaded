package model.ai.ghosts;

import fr.r1r0r0.deltaengine.exceptions.NotInitializedException;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Direction;
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
public final class ClydeAI extends BasicGhostAI {

    //TODO: faire le comportement de fuite

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
    public ClydeAI() {
        random = new Random();
        target = null;
    }

    @Override
    public GhostAI clone() {
        return new ClydeAI();
    }

    @Override
    protected void scaryModeTick(Ghost ghost) {
        //TODO
    }

    @Override
    protected void chaseModeTick(Ghost ghost) {
        if (target == null || isTargetReach(ghost)) {
            MapLevel mapLevel = ghost.getMapLevel();
            direction = chooseDirection(ghost);
            target = selectTarget(ghost, mapLevel);
            ghost.setDirection(direction);
        }
    }

    /**
     * Choose and return the next direction to follow
     * @param ghost a ghost
     * @return a direction
     */
    private Direction chooseDirection(Ghost ghost) {
        ArrayList<Direction> directions = new ArrayList<>();
        try {
            for (Direction direction : Direction.values()) {
                if (DeltaEngine.getKernelEngine().canGoToNextCell(ghost, direction)) directions.add(direction);
            }
        } catch (NotInitializedException e) {
            //e.printStackTrace();
        }
        int size = directions.size();
        return (size == 0) ? Direction.IDLE : directions.get(random.nextInt(size));
    }

    /**
     * Select and return the target, the next coordinates where the ghost must go
     * @param ghost a ghost
     * @param mapLevel a mapLevel
     * @return a coordinates
     */
    private Coordinates<Integer> selectTarget(Ghost ghost, MapLevel mapLevel) {
        if (direction == Direction.IDLE) return null;
        Coordinates<Integer> directionCoordinate = direction.getCoordinates();
        Coordinates<Double> position = ghost.getCoordinates();
        int x = position.getX().intValue();
        int y = position.getY().intValue();
        for (; ; ) {
            x += directionCoordinate.getX();
            y += directionCoordinate.getY();
            if (!mapLevel.getCell(x, y).isCrossableBy(ghost))
                return new Coordinates<>(x - directionCoordinate.getX(), y - directionCoordinate.getY());

            Direction opposite = direction.getOpposite();
            for (Direction other : Direction.values()) {
                if (other == direction || other == opposite || other == Direction.IDLE) continue;
                Coordinates<Integer> otherCoordinates = other.getCoordinates();
                if (mapLevel.getCell(x + otherCoordinates.getX(), y + otherCoordinates.getY())
                        .isCrossableBy(ghost)) return new Coordinates<>(x, y);
            }
        }
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

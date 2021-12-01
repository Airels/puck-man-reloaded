package model.ai;

import java.util.ArrayList;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import main.Main;
import model.elements.entities.ghosts.Ghost;
import model.exceptions.GhostTargetMissingException;

/**
 * A custom IA, is color is black.
 * He has 2 different states :
 *      - if pacMan is in same corridor, he run to him with a high speed
 *          when he cannot longer see pacMan, he run the previous position where he saw him
 *      - if pacMan is not in same corridor, he move randomly like Clyde, and he have slowness
 */
public final class BlackyAI extends BasicGhostAI {

    private static final double sentinelModeSpeed = 0.5;
    private static final double hunterModeSpeed = 1.5;

    private Double defaultSpeed;
    private Coordinates<Integer> lastViewPacManCoordinate;

    public BlackyAI () {
        defaultSpeed = null;
        lastViewPacManCoordinate = null;
    }

    @Override
    protected Direction chooseDirection (Ghost ghost, MapLevel mapLevel) {
        if (defaultSpeed == null) defaultSpeed = ghost.getSpeed();
        Direction directionChoose;
        try {
            directionChoose = chooseDirectionAux(ghost,mapLevel);
        } catch (GhostTargetMissingException e) {
            return Direction.IDLE;
        }
        if (directionChoose != null) return directionChoose;
        if (lastViewPacManCoordinate != null
                && ! Utils.getIntegerCoordinates(ghost).equals(lastViewPacManCoordinate)
                && Main.getEngine().canGoToNextCell(ghost,direction)
        ) return direction;
        lastViewPacManCoordinate = null;
        ArrayList<Direction> escapes = new ArrayList<>();
        Direction oppositeDirection = this.direction.getOpposite();
        for (Direction escape : Direction.values()) {
            if (escape == Direction.IDLE || escape == oppositeDirection) continue;
            if (Main.getEngine().canGoToNextCell(ghost,escape))
                escapes.add(escape);
        }
        return (escapes.size() == 0) ? oppositeDirection : escapes.get(RANDOM.nextInt(escapes.size()));
    }

    private Direction chooseDirectionAux (Ghost ghost, MapLevel mapLevel) throws GhostTargetMissingException {
        Coordinates<Integer> pacManCoordinates = Utils.getIntegerCoordinates(findPacMan(ghost,mapLevel));
        Coordinates<Integer> ghostCoordinate = Utils.getIntegerCoordinates(ghost);
        if (pacManCoordinates.equals(ghostCoordinate)) {
            lastViewPacManCoordinate = pacManCoordinates;
            return direction;
        }
        for (Direction directionChoose : Direction.values()) {
            if (directionChoose == Direction.IDLE) continue;
            for (Coordinates<Integer> coordinates = ghostCoordinate.copy();
                 mapLevel.getCell(coordinates.getX(),coordinates.getY()).isCrossableBy(ghost);
                    coordinates = Utils.calcNextPosition(coordinates,directionChoose)) {
                if (pacManCoordinates.equals(coordinates)) {
                    lastViewPacManCoordinate = pacManCoordinates;
                    return directionChoose;
                }
            }
        }
        return null;
    }

    @Override
    protected Coordinates<Integer> selectTarget (Ghost ghost, MapLevel mapLevel) {
        if (lastViewPacManCoordinate != null) {
            ghost.setSpeed(defaultSpeed * hunterModeSpeed);
            try {
                Utils.getIntegerCoordinates(findPacMan(ghost,mapLevel));
                return Utils.calcNextPosition(Utils.getIntegerCoordinates(ghost),direction);
            } catch (GhostTargetMissingException e) {
                return null;
            }
        }
        ghost.setSpeed(defaultSpeed * sentinelModeSpeed);
        Coordinates<Integer> position = Utils.getIntegerCoordinates(ghost);
        return new Coordinates<>(position.getX() + direction.getX(),
                position.getY() + direction.getY());
    }

}

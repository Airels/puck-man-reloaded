package model.ai;

import java.util.ArrayList;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import main.Main;
import model.elements.entities.ghosts.Ghost;
import model.exceptions.GhostTargetMissingException;

public class BlackyAI extends BasicGhostAI {

    private static double speedMultiplier1 = 0.5;
    private static double speedMultiplier2 = 1.33;

    private boolean findPacMan;

    public BlackyAI () {
        findPacMan = false;
    }

    @Override
    protected Direction chooseDirection(Ghost ghost, MapLevel mapLevel) {
        findPacMan = false;
        Direction direction;
        try {
            direction = chooseDirectionAux(ghost,mapLevel);
        } catch (GhostTargetMissingException e) {
            return Direction.IDLE;
        }
        if (direction != null) {
            findPacMan = true;
            return direction;
        }
        ArrayList<Direction> escapes = new ArrayList<>();
        Direction oppositeDirection = this.direction.getOpposite();
        for (Direction escape : Direction.values()) {
            if (escape == Direction.IDLE || escape == oppositeDirection) continue;
            if (Main.getEngine().canGoToNextCell(ghost,escape))
                escapes.add(escape);
        }
        return (escapes.size() == 0) ? oppositeDirection : escapes.get(random.nextInt(escapes.size()));
    }

    private Direction chooseDirectionAux (Ghost ghost, MapLevel mapLevel) throws GhostTargetMissingException {
        Coordinates<Integer> pacManCoordinates = Utils.getIntegerCoordinates(findPacMan(ghost,mapLevel));
        Coordinates<Integer> ghostCoordinate = Utils.getIntegerCoordinates(ghost);
        for (Direction direction : Direction.values()) {
            if (direction == Direction.IDLE) continue;
            for (Coordinates<Integer> coordinates = ghostCoordinate.copy();
                 mapLevel.getCell(coordinates.getX(),coordinates.getY()).isCrossableBy(ghost);
                    coordinates = Utils.calcNextPosition(coordinates,direction)) {
                if (pacManCoordinates.equals(coordinates)) return direction;
            }
        }
        return null;
    }

    private Double defaultSpeed;

    @Override
    protected Coordinates<Integer> selectTarget(Ghost ghost, MapLevel mapLevel) {
        if (defaultSpeed == null) defaultSpeed = ghost.getSpeed();
        if (findPacMan) {
            ghost.setSpeed(defaultSpeed * speedMultiplier2);
            try {
                return Utils.getIntegerCoordinates(findPacMan(ghost,mapLevel));
            } catch (GhostTargetMissingException e) {
                return null;
            }
        }
        ghost.setSpeed(defaultSpeed * speedMultiplier1);
        Coordinates<Integer> position = Utils.getIntegerCoordinates(ghost);
        return new Coordinates<>(position.getX() + direction.getX(),
                position.getY() + direction.getY());
    }

    @Override
    public GhostAI clone() {
        return new BlackyAI();
    }

}

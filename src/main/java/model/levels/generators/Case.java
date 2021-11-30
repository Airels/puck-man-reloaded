package model.levels.generators;

import fr.r1r0r0.deltaengine.model.Direction;

import java.util.LinkedList;
import java.util.List;

public class Case {

    private final List<Direction> closedDirections;

    public Case() {
        closedDirections = new LinkedList<>();

        for (Direction direction : Direction.values()) {
            double prob = Math.random();

            if (prob > 0.50)
                addClosedDirection(direction);
        }
    }

    public List<Direction> getClosedDirections() {
        return closedDirections;
    }

    public void addClosedDirection(Direction direction) {
        if (!closedDirections.contains(direction))
            closedDirections.add(direction);
    }

    public void removeClosedDirection(Direction direction) {
        closedDirections.remove(direction);
    }

    public boolean isUp() {
        return closedDirections.contains(Direction.UP);
    }

    public boolean isDown() {
        return closedDirections.contains(Direction.DOWN);
    }

    public boolean isLeft() {
        return closedDirections.contains(Direction.LEFT);
    }

    public boolean isRight() {
        return closedDirections.contains(Direction.RIGHT);
    }
}

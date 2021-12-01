package model.levels.generators;

import fr.r1r0r0.deltaengine.model.Direction;

import java.util.LinkedList;
import java.util.List;

/**
 * A chunk of 3*3 cells, used to generate random mazes
 */
public class Chunk {

    private final List<Direction> closedDirections;

    /**
     * Default constructor, generates automatically where directions need to be closed
     */
    public Chunk() {
        closedDirections = new LinkedList<>();

        for (Direction direction : Direction.values()) {
            double prob = Math.random();

            if (prob > 0.50)
                addClosedDirection(direction);
        }
    }

    /**
     * Returns list of closed directions
     * @return List of closed directions
     */
    public List<Direction> getClosedDirections() {
        return closedDirections;
    }

    /**
     * Add a closed direction
     * @param direction Direction to add
     */
    public void addClosedDirection(Direction direction) {
        if (!closedDirections.contains(direction))
            closedDirections.add(direction);
    }

    /**
     * Remove a cloed direction
     * @param direction Direction to remove
     */
    public void removeClosedDirection(Direction direction) {
        closedDirections.remove(direction);
    }

    /**
     * Returns if up chuck is closed
     * @return boolean true if closed, false otherwise
     */
    public boolean isUp() {
        return closedDirections.contains(Direction.UP);
    }

    /**
     * Returns if down chuck is closed
     * @return boolean true if closed, false otherwise
     */
    public boolean isDown() {
        return closedDirections.contains(Direction.DOWN);
    }

    /**
     * Returns if left chuck is closed
     * @return boolean true if closed, false otherwise
     */
    public boolean isLeft() {
        return closedDirections.contains(Direction.LEFT);
    }

    /**
     * Returns if right chuck is closed
     * @return boolean true if closed, false otherwise
     */
    public boolean isRight() {
        return closedDirections.contains(Direction.RIGHT);
    }
}

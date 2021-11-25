package model.ai.ghosts;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import main.Main;
import model.elements.entities.PacMan;
import model.elements.entities.ghosts.Ghost;
import model.exceptions.GhostTargetMissingException;

import java.util.ArrayList;

/**
 * An AI corresponding to the blue ghost in the game PacMan
 * His movement are the opposite of pacMan movement,
 * if he cannot do that, he chose a random direction
 *
 * In the original game, he has AI pretty similar
 */
public final class InkyAI extends BasicGhostAI {

    /**
     * Blue - Bashful
     * opposite direction of pacMan
     */

    /**
     * Constructor
     */
    public InkyAI () {
        super();
    }

    @Override
    public GhostAI clone () {
        return new InkyAI();
    }

    @Override
    protected Direction chooseDirection (Ghost ghost, MapLevel mapLevel) {
        PacMan pacMan;
        try {
            pacMan = findPacMan(ghost,mapLevel);
        } catch (GhostTargetMissingException e) {
            return Direction.IDLE;
        }
        Direction oppositeDirection = pacMan.getDirection().getOpposite();
        if (oppositeDirection != Direction.IDLE && Main.getEngine().canGoToNextCell(ghost,oppositeDirection))
            return oppositeDirection;
        ArrayList<Direction> directions = new ArrayList<>();
        for (Direction otherDirection : Direction.values()) {
            if (otherDirection == Direction.IDLE) continue;
            if (Main.getEngine().canGoToNextCell(ghost,otherDirection)) directions.add(otherDirection);
        }
        int size = directions.size();
        return (size == 0) ? Direction.IDLE : directions.get(random.nextInt(size));
    }

    @Override
    protected Coordinates<Integer> selectTarget(Ghost ghost, MapLevel mapLevel) {
        Coordinates<Integer> position = Utils.getIntegerCoordinates(ghost);
        return new Coordinates<>(position.getX() + direction.getX(),
                position.getY() + direction.getY());
    }

}

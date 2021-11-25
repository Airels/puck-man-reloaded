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
 * TODO
 */
public final class InkyAI extends BasicGhostAI {

    /**
     * Blue - Bashful
     * TODO: des fois il va dans la direction oppose de pac-man
     */

    public InkyAI () {
        super();
    }

    @Override
    public GhostAI clone () {
        return new InkyAI();
    }

    @Override
    protected Direction chooseDirection(Ghost ghost, MapLevel mapLevel) {
        PacMan pacMan;
        try {
            pacMan = findPacMan(ghost,mapLevel);
        } catch (GhostTargetMissingException e) {
            return Direction.IDLE;
        }
        Direction oppositeDirection = pacMan.getDirection().getOpposite();
        if (Main.getEngine().canGoToNextCell(ghost,oppositeDirection)) return oppositeDirection;
        ArrayList<Direction> directions = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            if (Main.getEngine().canGoToNextCell(ghost,direction)) directions.add(direction);
        }
        return (directions.size() == 0) ? Direction.IDLE : directions.get(random.nextInt(directions.size()));
    }

    @Override
    protected Coordinates<Integer> selectTarget(Ghost ghost, MapLevel mapLevel) {
        Coordinates<Integer> position = Utils.getIntegerCoordinates(ghost);
        return new Coordinates<>(position.getX() + direction.getX(),
                position.getY() + direction.getY());
    }

}

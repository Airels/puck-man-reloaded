package model.ai.ghosts;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import main.Main;
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

    /**
     * Constructor
     */
    public ClydeAI() {
        super();
        random = new Random();
    }

    @Override
    public GhostAI clone () {
        return new ClydeAI();
    }

    @Override
    protected void scaryModeTick (Ghost ghost) {
        //TODO
    }

    @Override
    protected Direction chooseDirection (Ghost ghost, MapLevel mapLevel) {
        ArrayList<Direction> directions = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            if (Main.getEngine().canGoToNextCell(ghost, direction)) directions.add(direction);
        }
        int size = directions.size();
        return (size == 0) ? Direction.IDLE : directions.get(random.nextInt(size));
    }

    @Override
    protected Coordinates<Integer> selectTarget (Ghost ghost, MapLevel mapLevel) {
        return Utils.findNextCross(ghost,mapLevel,Utils.getIntegerCoordinates(ghost),direction);
    }

}

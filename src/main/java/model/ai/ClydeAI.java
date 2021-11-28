package model.ai;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import main.Main;
import model.elements.entities.ghosts.Ghost;

import java.util.ArrayList;

/**
 * An AI corresponding to the orange ghost in the game PacMan
 * His movement are randomly choose, but he can only change direction when he reach a junction
 *
 * In the original game, his movement was not totally random, he used to chase pacMan exactly like
 * Blinky, and he started to retreat to the lower left corner when he was too close to pacMan
 */
public final class ClydeAI extends BasicGhostAI {

    /**
     * Orange - Pokey
     * random path-finding
     */

    /**
     * Constructor
     */
    public ClydeAI() {
        super();
    }

    @Override
    public GhostAI clone () {
        return new ClydeAI();
    }

    @Override
    protected Direction chooseDirection (Ghost ghost, MapLevel mapLevel) {
        ArrayList<Direction> directions = new ArrayList<>();
        Direction oppositeDirection = direction.getOpposite();
        for (Direction other : Direction.values()) {
            if (other == Direction.IDLE || other == oppositeDirection) continue;
            if (Main.getEngine().canGoToNextCell(ghost,other)) directions.add(other);
        }
        int size = directions.size();
        return (size == 0) ? Direction.IDLE : directions.get(random.nextInt(size));
    }

    @Override
    protected Coordinates<Integer> selectTarget (Ghost ghost, MapLevel mapLevel) {
        return Utils.findNextCross(ghost,mapLevel,Utils.getIntegerCoordinates(ghost),direction);
    }

}

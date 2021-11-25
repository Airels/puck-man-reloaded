package model.ai.ghosts;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import model.elements.entities.ghosts.Ghost;
import model.exceptions.GhostTargetMissingException;

/**
 * An AI corresponding to the red ghost in the game PacMan
 * His movement used to follow pacMan to kill him, he always chose the shortest way
 *
 * In the original game, he followed pacMan like his shadow
 */
public final class BlinkyAI extends BasicGhostAI {

    /**
     * Red - Shadow
     * follow pac-man
     */

    /**
     * Constructor
     */
    public BlinkyAI () {
        super();
    }

    @Override
    public GhostAI clone () {
        return new BlinkyAI();
    }

    @Override
    protected Direction chooseDirection (Ghost ghost, MapLevel mapLevel) {
        Coordinates<Integer> destination;
        try {
            destination = findDestination(ghost,mapLevel);
        } catch (GhostTargetMissingException e) {
            return Direction.IDLE;
        }
        return Utils.findShortestWay(ghost,mapLevel,destination);
    }

    /**
     * Find and return the final where the ghost must go
     * It is corresponding to pacMan position
     * @param ghost a ghost
     * @param mapLevel a mapLevel
     * @return the final where the ghost must go
     * @throws GhostTargetMissingException if pacMan is not in the mapLevel
     */
    private Coordinates<Integer> findDestination (Ghost ghost, MapLevel mapLevel) throws GhostTargetMissingException {
        return Utils.getIntegerCoordinates(findPacMan(ghost,mapLevel));
    }

    @Override
    protected Coordinates<Integer> selectTarget (Ghost ghost, MapLevel mapLevel) {
        Coordinates<Integer> position = Utils.getIntegerCoordinates(ghost);
        return new Coordinates<>(position.getX() + direction.getX(),
                position.getY() + direction.getY());
    }

}

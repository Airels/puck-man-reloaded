package model.ai.ghosts;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import main.Main;
import model.elements.entities.ghosts.Ghost;

/**
 * A ghost AI for the 4 basic ghost in PacMan
 * @see BlinkyAI
 * @see ClydeAI
 * @see InkyAI
 * @see PinkyAI
 */
public abstract class BasicGhostAI extends GhostAI {

    protected Coordinates<Integer> target;
    protected Direction direction;

    protected BasicGhostAI () {
        target = null;
        direction = Direction.IDLE;
    }

    @Override
    public final void tick() {
        Ghost ghost = getGhost();
        if (target != null && ! isTargetReach(ghost)) return;
        switch (ghost.getState()) {
            case NORMAL -> chaseModeTick(ghost);
            case SCARED -> scaryModeTick(ghost);
            case FLEEING -> fleeingModeTick(ghost);
            default -> {}
        }
        ghost.setDirection(direction);
    }

    /**
     * Action when the ghost is in scary mode
     * @param ghost a ghost
     */
    protected abstract void scaryModeTick (Ghost ghost);

    /**
     * Action when the ghost is in normal/chase mode
     * @param ghost a ghost
     */
    protected final void chaseModeTick (Ghost ghost) {
        MapLevel mapLevel = ghost.getMapLevel();
        direction = chooseDirection(ghost,mapLevel);
        target = (direction == Direction.IDLE) ? null : selectTarget(ghost, mapLevel);
    }

    /**
     * Choose and return the next direction to follow
     * @param ghost a ghost
     * @param mapLevel a mapLevel
     * @return a direction
     */
    protected abstract Direction chooseDirection (Ghost ghost, MapLevel mapLevel);

    /**
     * Select and return the target, the next coordinates where the ghost must go
     * @param ghost a ghost
     * @param mapLevel a mapLevel
     * @return a coordinates
     */
    protected abstract Coordinates<Integer> selectTarget (Ghost ghost, MapLevel mapLevel);

    /**
     * Action when the ghost is in fleeing mode
     * @param ghost a ghost
     */
    private void fleeingModeTick (Ghost ghost) {

    }

    /**
     * Return if the target is reach
     * @param ghost a ghost
     * @return if the target is reach
     */
    private boolean isTargetReach (Ghost ghost) {
        if ( ! Main.getEngine().isAvailableDirection(ghost,direction)) return true;
        return Utils.isOnTarget(ghost,target);
    }

}

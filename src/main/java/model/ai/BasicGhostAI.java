package model.ai;

import config.entities.PacManConfiguration;
import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelEntityNameStackingException;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import fr.r1r0r0.deltaengine.view.colors.Color;
import main.Main;
import model.elements.entities.PacMan;
import model.elements.entities.ghosts.Ghost;
import model.elements.entities.ghosts.GhostState;
import model.exceptions.GhostTargetMissingException;

import java.util.ArrayList;
import java.util.Random;

/**
 * A ghost AI for the 4 basic ghost in PacMan
 * @see BlinkyAI
 * @see ClydeAI
 * @see InkyAI
 * @see PinkyAI
 */
public abstract class BasicGhostAI extends GhostAI {

    private static final Entity focus = new Entity("focus",new Coordinates<>(0.,0.),
            new Rectangle(Color.GREEN),new Dimension(0.5,0.5));

    protected final Random random = new Random();
    protected Coordinates<Integer> target;
    protected Direction direction;

    /**
     * Constructor
     */
    protected BasicGhostAI () {
        target = null;
        direction = Direction.IDLE;
    }

    @Override
    public final void tick() {
        Ghost ghost = getGhost();
        MapLevel mapLevel = ghost.getMapLevel();
        GhostState ghostState = ghost.getState();
        if (ghost.getBlockTarget() != null && ghost.getDirection() != Direction.IDLE) return;
        if (ghostState == GhostState.NORMAL && random.nextDouble() < getProbaScatter(ghost)) {
            ghost.setState(GhostState.SCATTER);
            ghostState = ghost.getState();
        }
        switch (ghostState) {
            case NORMAL -> chaseModeTick(ghost,mapLevel);
            case SCARED -> scaryModeTick(ghost,mapLevel);
            case FLEEING -> fleeingModeTick(ghost,mapLevel);
            case SCATTER -> scatterModeTick(ghost,mapLevel);
            default -> {}
        }
        ghost.setDirection(direction);
        ghost.setBlockTarget(target);
        if (ghost.getName().equals("")) {
            try {
                mapLevel.addEntity(focus);
            } catch (MapLevelEntityNameStackingException e) {
                e.printStackTrace();
            }
            if (target != null) focus.setCoordinates(new Coordinates<>(target.getX().doubleValue(),target.getY().doubleValue()));
        }
    }

    /**
     * Action when the ghost is in normal/chase mode
     * @param ghost a ghost
     * @param mapLevel a mapLevel
     */
    protected final void chaseModeTick (Ghost ghost, MapLevel mapLevel) {
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
     * Action when the ghost is in scary mode
     * @param ghost a ghost
     * @param mapLevel a mapLevel
     */
    private void scaryModeTick (Ghost ghost, MapLevel mapLevel) {
        PacMan pacMan;
        try {
            pacMan = findPacMan(ghost,mapLevel);
        } catch (GhostTargetMissingException e) {
            direction = Direction.IDLE;
            target = null;
            return;
        }
        Coordinates<Integer> pacManPosition = Utils.getIntegerCoordinates(pacMan);
        Direction shortestDirection = Utils.findShortestWay(ghost,mapLevel,pacManPosition);
        ArrayList<Direction> escapes = new ArrayList<>();
        Direction oppositeDirection = direction.getOpposite();
        for (Direction escape : Direction.values()) {
            if (escape == Direction.IDLE || escape == shortestDirection || escape == oppositeDirection) continue;
            if (Main.getEngine().canGoToNextCell(ghost,escape))
                escapes.add(escape);
        }
        direction = (escapes.size() == 0) ? shortestDirection : escapes.get(random.nextInt(escapes.size()));
        target = Utils.findNextCross(ghost,mapLevel,Utils.getIntegerCoordinates(ghost),direction);
    }

    /**
     * Action when the ghost is in fleeing mode
     * @param ghost a ghost
     * @param mapLevel a mapLevel
     */
    private void fleeingModeTick (Ghost ghost, MapLevel mapLevel) {
        Coordinates<Double> point = ghost.getRetreatPoint();
        Coordinates<Integer> destination = new Coordinates<>(point.getX().intValue(),point.getY().intValue());
        direction = Utils.findShortestWay(ghost,mapLevel,destination);
        target = Utils.findNextCross(ghost,mapLevel,Utils.getIntegerCoordinates(ghost),direction);
    }

    /**
     * Action when the ghost is in scatter mode
     * @param ghost a ghost
     * @param mapLevel a mapLevel
     */
    private void scatterModeTick (Ghost ghost, MapLevel mapLevel) {
        scaryModeTick(ghost,mapLevel);
        ghost.setState(GhostState.NORMAL);
    }

    /**
     * Return the entity pacMan on the mapLevel
     * @param ghost a ghost
     * @param mapLevel a mapLeft
     * @return the entity pacMan on the mapLevel
     * @throws GhostTargetMissingException throw if the entity pacMan is not in the mapLevel
     */
    protected PacMan findPacMan (Ghost ghost, MapLevel mapLevel) throws GhostTargetMissingException {
        Entity entity = mapLevel.getEntity(PacManConfiguration.CONF_PACMAN_NAME);
        if (entity == null) throw new GhostTargetMissingException(ghost);
        return (PacMan) entity;
    }

    private double getProbaScatter (Ghost ghost) {
        String name = ghost.getName();
        switch (name) {
            case "Blinky" -> {return 0.02;}
            case "Pinky" -> {return 0.05;}
            case "Inky" -> {return 0.01;}
            default -> {return 0;}
        }
    }

}

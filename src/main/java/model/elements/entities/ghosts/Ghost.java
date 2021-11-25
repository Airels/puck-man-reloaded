package model.elements.entities.ghosts;

import fr.r1r0r0.deltaengine.exceptions.AIAlreadyAttachedException;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import model.ai.ghosts.GhostAI;
import view.SpriteContainer;

import java.util.List;

/**
 * A PacMan's Ghost.
 */
public class Ghost extends Entity {

    private final MapLevel currentMap;
    private final SpriteContainer normalSprites, scaredSprites, fleeingSprites;
    private final double normalSpeed, scaredSpeed, fleeingSpeed;
    private GhostState ghostState;
    private Coordinates<Double> spawnPoint, retreatPoint;

    /**
     * Default ghost constructor.
     * @param name String name of the Ghost
     * @param currentMap Current Map who Ghost resides
     * @param normalSprites Sprites when Ghost is in Normal state
     * @param scaredSprites Sprites when Ghost is in Scared state (energized mode)
     * @param fleeingSprites Sprites when Ghost is in Fleeing state (when PacMan eat it)
     * @param ghostAI Ghost AI
     * @param normalSpeed double speed when Ghost is in Normal state
     * @param scaredSpeed double speed when Ghost is in Scared state
     * @param fleeingSpeed double speed when Ghost is in Fleeing state
     */

    /**
     * Default ghost constructor.
     * @param name
     * @param currentMap
     * @param normalSprites
     * @param scaredSprites
     * @param fleeingSprites
     * @param ghostAI
     * @param normalSpeed
     * @param scaredSpeed
     * @param fleeingSpeed
     * @param coords
     * @param spawnPoint
     * @param retreatPoint
     * @param dimension
     */
    Ghost(String name,
          MapLevel currentMap,
          SpriteContainer normalSprites, SpriteContainer scaredSprites, SpriteContainer fleeingSprites,
          GhostAI ghostAI,
          double normalSpeed, double scaredSpeed, double fleeingSpeed,
          Coordinates<Double> coords, Coordinates<Double> spawnPoint, Coordinates<Double> retreatPoint,
          Dimension dimension) {

        super(name, coords, normalSprites.getSprite(Direction.IDLE), dimension);
        this.currentMap = currentMap;
        this.normalSprites = normalSprites;
        this.scaredSprites = scaredSprites;
        this.fleeingSprites = fleeingSprites;
        this.normalSpeed = normalSpeed;
        this.scaredSpeed = scaredSpeed;
        this.fleeingSpeed = fleeingSpeed;
        this.spawnPoint = spawnPoint;
        this.retreatPoint = retreatPoint;


        try {
            if (ghostAI != null) setAI(ghostAI);
        } catch (AIAlreadyAttachedException e) {
            e.printStackTrace(); // Should never happen
            System.exit(1);
        }

        setState(GhostState.NORMAL);
    }

    /**
     * Set new ghost state
     * @param ghostState new state of the ghost
     */
    public void setState(GhostState ghostState) {
        this.ghostState = ghostState;

        double speed = switch (ghostState) {
            case SCARED -> scaredSpeed;
            case FLEEING -> fleeingSpeed;
            default -> normalSpeed;
        };

        setSpeed(speed);
    }

    /**
     * Get current state of the Ghost
     * @return GhostState current Ghost state
     */
    public GhostState getState() {
        return ghostState;
    }

    /**
     * Returns if Ghost is in Scared state
     * @return true if Ghost is in Scared state, false otherwise
     */
    public boolean isScared() {
        return getState() == GhostState.SCARED;
    }

    /**
     * Returns if Ghost is in Fleeing state
     * @return true if Ghost is in Fleeing state, false otherwise
     */
    public boolean isFleeing() {
        return getState() == GhostState.FLEEING;
    }

    /**
     * Return if Ghost is in Scatter state
     * @return true if Ghost is in Scatter state, false otherwise
     */
    public boolean isScattering() {
        return getState() == GhostState.SCATTER;
    }

    /**
     * Returns coordinates of the spawn of the ghost
     * @return Coordinates of spawn point
     */
    public Coordinates<Double> getSpawnPoint() {
        return spawnPoint;
    }

    /**
     * Returns coordinates of the retreat point, reach by ghost when it's in FLEEING state
     * @return Coordinates of retreat point
     */
    public Coordinates<Double> getRetreatPoint() {
        return retreatPoint;
    }

    /**
     * Get the Map of the Level where Ghost resides
     * @return MapLevel current map of the ghost
     */
    public MapLevel getMapLevel() {
        return currentMap;
    }

    @Override
    public Sprite getSprite() {
        if (isScared())
            return this.scaredSprites.getSprite(this.getDirection());

        if (isFleeing())
            return this.fleeingSprites.getSprite(this.getDirection());

        return this.normalSprites.getSprite(this.getDirection());
    }
}

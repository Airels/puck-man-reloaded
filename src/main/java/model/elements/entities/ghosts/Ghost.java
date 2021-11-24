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
    public Ghost(String name, MapLevel currentMap, SpriteContainer normalSprites, SpriteContainer scaredSprites, SpriteContainer fleeingSprites, GhostAI ghostAI, double normalSpeed, double scaredSpeed, double fleeingSpeed) {
        super(name, new Coordinates<>(0.0, 0.0), normalSprites.getSprite(Direction.IDLE), new Dimension(0.9, 0.9));
        this.currentMap = currentMap;
        this.normalSprites = normalSprites;
        this.scaredSprites = scaredSprites;
        this.fleeingSprites = fleeingSprites;

        try {
            setAI(ghostAI);
        } catch (AIAlreadyAttachedException e) {
            e.printStackTrace(); // Should never happen
            System.exit(1);
        }

        this.normalSpeed = normalSpeed;
        this.scaredSpeed = scaredSpeed;
        this.fleeingSpeed = fleeingSpeed;

        setState(GhostState.NORMAL);
    }

    /**
     * Set new ghost state
     * @param ghostState new state of the ghost
     */
    public void setState(GhostState ghostState) {
        this.ghostState = ghostState;

        double speed = switch (ghostState) {
            case NORMAL -> normalSpeed;
            case SCARED -> scaredSpeed;
            case FLEEING -> fleeingSpeed;
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

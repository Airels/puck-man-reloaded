package model.elements.entities.ghosts;

import fr.r1r0r0.deltaengine.exceptions.AIAlreadyAttachedException;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import model.ai.ghosts.GhostAI;
import java.util.List;

/**
 * A PacMan's Ghost.
 */
public class Ghost extends Entity {

    private final MapLevel currentMap;
    private final Sprite scaredSprite;
    private final List<Sprite> normalSprites, fleeingSprites;
    private final double normalSpeed, scaredSpeed, fleeingSpeed;
    private GhostState ghostState;

    /**
     * Default ghost constructor.
     * @param name String name of the Ghost
     * @param currentMap Current Map who Ghost resides
     * @param normalSprites Sprites when Ghost is in Normal state
     * @param scaredSprite Sprite when Ghost is in Scared state (energized mode)
     * @param fleeingSprites Sprites when Ghost is in Fleeing state (when PacMan eat it)
     * @param ghostAI Ghost AI
     * @param normalSpeed double speed when Ghost is in Normal state
     * @param scaredSpeed double speed when Ghost is in Scared state
     * @param fleeingSpeed double speed when Ghost is in Fleeing state
     */
    public Ghost(String name, MapLevel currentMap, List<Sprite> normalSprites, Sprite scaredSprite, List<Sprite> fleeingSprites, GhostAI ghostAI, double normalSpeed, double scaredSpeed, double fleeingSpeed) {
        super(name, new Coordinates<>(0.0, 0.0), normalSprites.get(0), new Dimension(0.9, 0.9));
        this.currentMap = currentMap;
        this.normalSprites = normalSprites;
        this.scaredSprite = scaredSprite;
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
        if (isScared()) return scaredSprite;
        if (isFleeing()) return switch(this.getDirection()){
                case RIGHT -> fleeingSprites.get(1);
                case UP -> fleeingSprites.get(2);
                case DOWN -> fleeingSprites.get(3);
                default -> fleeingSprites.get(0);
        };
        else return switch (this.getDirection()) {
                case RIGHT -> normalSprites.get(1);
                case UP -> normalSprites.get(2);
                case DOWN -> normalSprites.get(3);
                default -> normalSprites.get(0);
        };
    }
}

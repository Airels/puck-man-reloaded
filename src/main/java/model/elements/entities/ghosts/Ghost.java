package model.elements.entities.ghosts;

import config.entities.CharactersConfiguration;
import fr.r1r0r0.deltaengine.exceptions.AIAlreadyAttachedException;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.sprites.Sprite;
import model.ai.GhostAI;
import view.SpriteContainer;

/**
 * A PacMan's Ghost.
 */
public class Ghost extends Entity {

    private final MapLevel currentMap;
    private final SpriteContainer normalSprites, scaredSprites, fleeingSprites;
    private final double normalSpeed, scaredSpeed, fleeingSpeed;
    private final Coordinates<Double> spawnPoint, retreatPoint;
    private GhostState ghostState;

    /**
     * Default ghost constructor.
     *
     * @param name           String name of the Ghost
     * @param currentMap     Current Map who Ghost is
     * @param normalSprites  Sprites when Ghost is in NORMAL state
     * @param scaredSprites  Sprites when Ghost is in SCARED state
     * @param fleeingSprites Sprites when Ghost is in FLEEING state
     * @param ghostAI        Ghost AI
     * @param normalSpeed    double speed when Ghost is in NORMAL state
     * @param scaredSpeed    double speed when Ghost is in SCARED state
     * @param fleeingSpeed   double speed when Ghost is in FLEEING state
     * @param coords         Initial Coordinates of the Ghost
     * @param spawnPoint     Spawn point of the Ghost, who Ghost will be teleported if the map is reset (usually when PacMan dies)
     * @param retreatPoint   Retreat point of the Ghost, who Ghost will go to regenerate itself from FLEEING to NORMAL state (when PacMan eats it)
     */
    Ghost(String name,
          MapLevel currentMap,
          SpriteContainer normalSprites, SpriteContainer scaredSprites, SpriteContainer fleeingSprites,
          GhostAI ghostAI,
          double normalSpeed, double scaredSpeed, double fleeingSpeed,
          Coordinates<Double> coords, Coordinates<Double> spawnPoint, Coordinates<Double> retreatPoint) {

        super(name, coords, normalSprites.getSprite(Direction.IDLE), CharactersConfiguration.CONF_DEFAULT_CHARACTERS_DIMENSION, CharactersConfiguration.CONF_DEFAULT_CHARACTERS_HITBOX_DIMENSION);
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
     * Get current state of the Ghost
     *
     * @return GhostState current Ghost state
     */
    public GhostState getState() {
        return ghostState;
    }

    /**
     * Set new ghost state
     *
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
     * Returns if Ghost is in Scared state
     *
     * @return true if Ghost is in Scared state, false otherwise
     */
    public boolean isScared() {
        return getState() == GhostState.SCARED;
    }

    /**
     * Returns if Ghost is in Fleeing state
     *
     * @return true if Ghost is in Fleeing state, false otherwise
     */
    public boolean isFleeing() {
        return getState() == GhostState.FLEEING;
    }

    /**
     * Return if Ghost is in Scatter state
     *
     * @return true if Ghost is in Scatter state, false otherwise
     */
    public boolean isScattering() {
        return getState() == GhostState.SCATTER;
    }

    /**
     * Returns coordinates of the spawn of the ghost
     *
     * @return Coordinates of spawn point
     */
    public Coordinates<Double> getSpawnPoint() {
        return spawnPoint;
    }

    /**
     * Returns coordinates of the retreat point, reach by ghost when it's in FLEEING state
     *
     * @return Coordinates of retreat point
     */
    public Coordinates<Double> getRetreatPoint() {
        return retreatPoint;
    }

    /**
     * Get the Map of the Level where Ghost resides
     *
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

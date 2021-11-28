package model.elements.entities.ghosts;

import fr.r1r0r0.deltaengine.model.Builder;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import model.ai.GhostAI;
import model.ai.GhostAIs;
import view.SpriteContainer;

/**
 * A Ghost builder, allowing to build a Ghost easily
 */
public class GhostBuilder implements Builder<Ghost> {

    private final String name;
    private final MapLevel mapLevel;
    private final SpriteContainer normalSprites, scaredSprites, fleeingSprites;

    private GhostAI ai;
    private double normalSpeed, scaredSpeed, fleeingSpeed;
    private Coordinates<Double> coordinates, spawnPoint, retreatPoint;

    /**
     * Default constructor, with required attributes for a Ghost that must be unique for each.
     * @param name Name of the Ghost
     * @param map Map of the level where Ghost will interact with
     * @param normalSprites Normal sprite container, who contains all Ghost sprite in NORMAL state
     * @param scaredSprites Scared sprite container, who contains all Ghost sprite in SCARED state
     * @param fleeingSprites Fleeing sprite container, who contains all Ghost sprite in FLEEING state
     */
    public GhostBuilder(String name, MapLevel map, SpriteContainer normalSprites, SpriteContainer scaredSprites, SpriteContainer fleeingSprites) {
        this.name = name;
        this.mapLevel = map;
        this.normalSprites = normalSprites;
        this.scaredSprites = scaredSprites;
        this.fleeingSprites = fleeingSprites;

        this.ai = null;
        this.normalSpeed = 0;
        this.scaredSpeed = 0;
        this.fleeingSpeed = 0;

        this.coordinates = new Coordinates<>(0.0, 0.0);
        this.spawnPoint = new Coordinates<>(0.0, 0.0);
        this.retreatPoint = new Coordinates<>(0.0, 0.0);
    }

    /**
     * Set a given AI to the future Ghost
     * @param ai AI to attach
     * @return the current builder
     */
    public GhostBuilder setAI(GhostAI ai) {
        this.ai = ai;
        return this;
    }

    /**
     * Set a given AI to the future Ghost by giving a pre-defined Ghost AI
     * @param ai Predefined Ghost AI
     * @return the current builder
     */
    public GhostBuilder setAI(GhostAIs ai) {
        this.ai = ai.build();
        return this;
    }

    /**
     * Set the speed of the Ghost in NORMAL state
     * @param normalSpeed speed to set in NORMAL state
     * @return the current builder
     */
    public GhostBuilder setNormalSpeed(double normalSpeed) {
        this.normalSpeed = normalSpeed;
        return this;
    }

    /**
     * Set the speed of the Ghost in SCARED state
     * @param scaredSpeed speed to set in SCARED state
     * @return the current builder
     */
    public GhostBuilder setScaredSpeed(double scaredSpeed) {
        this.scaredSpeed = scaredSpeed;
        return this;
    }

    /**
     * Set the speed of the Ghost in FLEEING state
     * @param fleeingSpeed speed to set in FLEEING state
     * @return the current builder
     */
    public GhostBuilder setFleeingSpeed(double fleeingSpeed) {
        this.fleeingSpeed = fleeingSpeed;
        return this;
    }

    /**
     * Set Coordinates of the Ghost
     * @param coordinates Coordinates to set
     * @return the current builder
     */
    public GhostBuilder setCoordinates(Coordinates<Double> coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    /**
     * Set Spawn Point of the Ghost, where Ghost will be teleported when Level will be reset (when PacMan dies usually)
     * @param spawnPoint Coordinates of the Spawn point
     * @return the current builder
     */
    public GhostBuilder setSpawnPoint(Coordinates<Double> spawnPoint) {
        this.spawnPoint = spawnPoint;
        return this;
    }

    /**
     * Set Retreat Point of the Ghost, where Ghost will go when it is in FLEEING state, to regenerate itself into a NORMAL state
     * @param retreatPoint Coordinates of the Retreat point
     * @return the current builder
     */
    public GhostBuilder setRetreatPoint(Coordinates<Double> retreatPoint) {
        this.retreatPoint = retreatPoint;
        return this;
    }

    @Override
    public Ghost build() {
        return new Ghost(name, mapLevel, normalSprites, scaredSprites, fleeingSprites, ai, normalSpeed, scaredSpeed, fleeingSpeed, coordinates, spawnPoint, retreatPoint);
    }
}

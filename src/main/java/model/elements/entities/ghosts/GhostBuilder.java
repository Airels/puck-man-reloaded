package model.elements.entities.ghosts;

import fr.r1r0r0.deltaengine.model.Builder;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import model.ai.ghosts.GhostAI;
import model.ai.ghosts.GhostAIs;
import view.SpriteContainer;

public class GhostBuilder implements Builder<Ghost> {

    private final String name;
    private final MapLevel mapLevel;
    private final SpriteContainer normalSprites, scaredSprites, fleeingSprites;

    private GhostAI ai;
    private double normalSpeed, scaredSpeed, fleeingSpeed;
    private Coordinates<Double> coordinates, spawnPoint, retreatPoint;
    private Dimension dimension;

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
        this.dimension = Dimension.DEFAULT_DIMENSION;
    }

    public GhostBuilder setAI(GhostAI ai) {
        this.ai = ai;
        return this;
    }

    public GhostBuilder setAI(GhostAIs ai) {
        this.ai = ai.build();
        return this;
    }

    public GhostBuilder setNormalSpeed(double normalSpeed) {
        this.normalSpeed = normalSpeed;
        return this;
    }

    public GhostBuilder setScaredSpeed(double scaredSpeed) {
        this.scaredSpeed = scaredSpeed;
        return this;
    }

    public GhostBuilder setFleeingSpeed(double fleeingSpeed) {
        this.fleeingSpeed = fleeingSpeed;
        return this;
    }

    public GhostBuilder setCoordinates(Coordinates<Double> coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public GhostBuilder setSpawnPoint(Coordinates<Double> spawnPoint) {
        this.spawnPoint = spawnPoint;
        return this;
    }

    public GhostBuilder setRetreatPoint(Coordinates<Double> retreatPoint) {
        this.retreatPoint = retreatPoint;
        return this;
    }

    public GhostBuilder setDimension(Dimension dimension) {
        this.dimension = dimension;
        return this;
    }

    @Override
    public Ghost build() {
        return new Ghost(name, mapLevel, normalSprites, scaredSprites, fleeingSprites, ai, normalSpeed, scaredSpeed, fleeingSpeed, coordinates, spawnPoint, retreatPoint, dimension);
    }
}

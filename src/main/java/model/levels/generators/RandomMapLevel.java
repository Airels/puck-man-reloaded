package model.levels.generators;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import model.elements.entities.ghosts.Ghost;
import model.levels.Level;
import model.loadables.LoadableMap;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;

public class RandomMapLevel implements LoadableMap {

    private final Level level;
    private final MapLevel generatedMapLevel;

    public RandomMapLevel(Level level) {
        this.level = level;
        MapLevelGenerator generator = new MapLevelGenerator(level, level.getGame().getPacMan());
        generatedMapLevel = generator.generate();
    }

    /**
     * Load the object
     *
     * @param engine DeltaEngine
     */
    @Override
    public void load(KernelEngine engine) {

    }

    /**
     * Unload the object
     *
     * @param engine DeltaEngine
     */
    @Override
    public void unload(KernelEngine engine) {

    }

    /**
     * Returns the associated Map of the Level
     *
     * @return MapLevel
     */
    @Override
    public @NotNull MapLevel getMapLevel() {
        return generatedMapLevel;
    }

    /**
     * Returns number of generated PacGums
     *
     * @return integer of PacGums generated
     */
    @Override
    public int getNbOfGeneratedPacGums() {
        return 0;
    }

    /**
     * Returns collection of generated Ghosts
     *
     * @return Collection of Ghosts generated
     */
    @Override
    public @NotNull Collection<Ghost> getGeneratedGhosts() {
        return null;
    }

    /**
     * Get spawn points of entities in the map
     *
     * @return Map of entity and their associated spawn points
     */
    @Override
    public @NotNull Map<Entity, Coordinates<Double>> getSpawnPoints() {
        return null;
    }
}

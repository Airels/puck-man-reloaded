package model.levels.generators;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.engines.DeltaEngine;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import model.Game;
import model.elements.entities.PacMan;
import model.elements.entities.ghosts.Ghost;
import model.events.LevelChanger;
import model.levels.Level;
import model.loadables.LoadableInput;
import model.loadables.LoadableMap;
import model.utils.WallSpriteApplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Map;

@SuppressWarnings("ALL")
class MapLevelGeneratorTest {

    @Test
    void generate() throws Exception {
        KernelEngine engine = DeltaEngine.launch();
        MapLevelGenerator generator = new MapLevelGenerator(new TestLevel(), new PacMan());
        MapLevel mapLevel = generator.generate();

        new WallSpriteApplier().apply(mapLevel);

        engine.addMap(mapLevel);
        engine.setCurrentMap(mapLevel.getName());
        for (;;);
    }
}

class TestLevel implements Level {

    /**
     * Load the level
     *
     * @param deltaEngine the Engine
     */
    @Override
    public void load(KernelEngine deltaEngine) {

    }

    /**
     * Unload the level
     *
     * @param deltaEngine the Engine
     */
    @Override
    public void unload(KernelEngine deltaEngine) {

    }

    /**
     * Returns the Map of the Level;
     *
     * @return LoadableMap of the level
     */
    @Override
    public @NotNull LoadableMap getMapLevelLoadable() {
        return null;
    }

    /**
     * Returns the Inputs of the Level
     *
     * @return LoadableInputs of the level
     */
    @Override
    public @NotNull LoadableInput getInputsLoadable() {
        return null;
    }

    /**
     * Returns number of PacGums in level
     *
     * @return int number of PacGums
     */
    @Override
    public int getNbOfPacGums() {
        return 0;
    }

    /**
     * Returns number of PacGums in level and decrease its value by one.
     *
     * @return number of PacGums before decreasing
     */
    @Override
    public int getAndDecreasePacGums() {
        return 0;
    }

    /**
     * Get all ghosts of the Level
     *
     * @return Collection of Ghost
     */
    @Override
    public @NotNull Collection<Ghost> getGhosts() {
        return null;
    }

    /**
     * Returns Game instance
     *
     * @return the Game
     */
    @Override
    public @NotNull Game getGame() {
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

    /**
     * Returns the level changer of the level
     *
     * @return Level Changer instance
     */
    @Override
    public @Nullable LevelChanger getLevelChanger() {
        return null;
    }
}
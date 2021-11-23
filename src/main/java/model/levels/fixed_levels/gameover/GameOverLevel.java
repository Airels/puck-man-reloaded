package model.levels.fixed_levels.gameover;

import controller.inputs_levels.gameover.GameOverInputs;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.Game;
import model.elements.entities.ghosts.Ghost;
import model.levels.Level;
import model.loadables.LoadableInput;
import model.loadables.LoadableMap;
import org.jetbrains.annotations.NotNull;
import view.maps_levels.gameover.GameOverMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class GameOverLevel implements Level {

    private final Game game;
    private final GameOverMap gameOverMap;
    private final GameOverInputs gameOverInputs;

    public GameOverLevel(Game game) {
        this.game = game;
        this.gameOverMap = new GameOverMap(this);
        this.gameOverInputs = new GameOverInputs(this);
    }

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
        return gameOverMap;
    }

    /**
     * Returns the Inputs of the Level
     *
     * @return LoadableInputs of the level
     */
    @Override
    public @NotNull LoadableInput getInputsLoadable() {
        return gameOverInputs;
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
        return new ArrayList<>();
    }

    /**
     * Returns Game instance
     *
     * @return the Game
     */
    @Override
    public @NotNull Game getGame() {
        return game;
    }

    /**
     * Get spawn points of entities in the map
     *
     * @return Map of entity and their associated spawn points
     */
    @Override
    public @NotNull Map<Entity, Coordinates<Double>> getSpawnPoints() {
        return new HashMap<>();
    }

    /**
     * reset to initial position ghosts and pacman
     */
    @Override
    public void reset() {

    }
}

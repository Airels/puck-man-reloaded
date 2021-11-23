package model.levels;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.Game;
import model.elements.entities.ghosts.Ghost;
import model.loadables.Loadable;
import model.loadables.LoadableInput;
import model.loadables.LoadableMap;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;


/**
 * A Level of the game.
 */
public interface Level extends Loadable {

    /**
     * Load the level
     * @param deltaEngine the Engine
     */
    @Override
    void load(KernelEngine deltaEngine);

    /**
     * Unload the level
     * @param deltaEngine the Engine
     */
    @Override
    void unload(KernelEngine deltaEngine);

    /**
     * Returns the Map of the Level;
     * @return LoadableMap of the level
     */
    @NotNull
    LoadableMap getMapLevelLoadable();

    /**
     * Returns the Inputs of the Level
     * @return LoadableInputs of the level
     */
    @NotNull
    LoadableInput getInputsLoadable();

    /**
     * Returns number of PacGums in level
     * @return int number of PacGums
     */
    int getNbOfPacGums();

    /**
     * Returns number of PacGums in level and decrease its value by one.
     * @return number of PacGums before decreasing
     */
    int getAndDecreasePacGums();

    /**
     * Get all ghosts of the Level
     * @return Collection of Ghost
     */
    @NotNull
    Collection<Ghost> getGhosts();

    /**
     * Returns Game instance
     * @return the Game
     */
    @NotNull
    Game getGame();

    /**
     * Get spawn points of entities in the map
     * @return Map of entity and their associated spawn points
     */
    @NotNull
    Map<Entity, Coordinates<Double>> getSpawnPoints();


    /**
     * reset to initial position ghosts and pacman
     */
    void reset();

}

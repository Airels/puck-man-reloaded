package model.maps;

import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.events.AttributeListener;
import model.Game;
import model.elements.entities.PacMan;
import model.elements.entities.ghosts.Ghost;
import model.loadables.Loadable;
import model.loadables.LoadableInput;
import model.loadables.LoadableMap;

import java.util.Collection;

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
    LoadableMap getMapLevelLoadable();

    /**
     * Returns the Inputs of the Level
     * @return LoadableInputs of the level
     */
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
    Collection<Ghost> getGhosts();

    /**
     * Returns Game instance
     * @return the Game
     */
    Game getGame();
}

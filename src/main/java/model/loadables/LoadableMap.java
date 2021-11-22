package model.loadables;

import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import model.elements.entities.ghosts.Ghost;

import java.util.Collection;

/**
 * A Loadable extension, used to specify that class implementing it loads maps
 */
public interface LoadableMap extends Loadable {
    /**
     * Returns the associated Map of the Level
     * @return MapLevel
     */
    MapLevel getMapLevel();

    /**
     * Returns number of generated PacGums
     * @return integer of PacGums generated
     */
    int getNbOfGeneratedPacGums();

    /**
     * Returns collection of generated Ghosts
     * @return Collection of Ghosts generated
     */
    Collection<Ghost> getGeneratedGhosts();
}

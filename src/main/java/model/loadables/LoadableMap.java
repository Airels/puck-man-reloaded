package model.loadables;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import model.elements.entities.ghosts.Ghost;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;


/**
 * A Loadable extension, used to specify that class implementing it loads maps
 */
public interface LoadableMap extends Loadable {
    /**
     * Returns the associated Map of the Level
     * @return MapLevel
     */
    @NotNull
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
    @NotNull
    Collection<Ghost> getGeneratedGhosts();

    /**
     * Get spawn points of entities in the map
     * @return Map of entity and their associated spawn points
     */
    @NotNull
    Map<Entity, Coordinates<Double>> getSpawnPoints();
}

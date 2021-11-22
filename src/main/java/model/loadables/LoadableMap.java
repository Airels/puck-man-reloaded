package model.loadables;

import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import model.elements.entities.ghosts.Ghost;

import java.util.Collection;

public interface LoadableMap extends Loadable {
    MapLevel getMapLevel();

    int getNbOfGeneratedPacGums();

    Collection<Ghost> getGeneratedGhosts();
}

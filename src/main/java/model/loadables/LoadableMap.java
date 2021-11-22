package model.loadables;

import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;

public interface LoadableMap extends Loadable {
    MapLevel getMapLevel();
    int getNbOfGeneratedPacGums();
}

package model.levels.generators;

import model.levels.Level;
import model.levels.generators.vidal.RandomLevel;
import model.loadables.LoadableMap;
import view.maps.OriginalLevelMap;

/**
 * A loadable map builder, allowing to build default LoadableMap
 */
public enum LoadableMapBuilder {

    ORIGINAL(){
        @Override
        public LoadableMap build (Level level) {
            return new OriginalLevelMap(level,level.getGame().getPacMan());
        }
    },
    RANDOM(){
        @Override
        public LoadableMap build (Level level) {
            return new RandomMapLevel(level);
        }
    };

    LoadableMapBuilder () {}

    /**
     * Build a new Loadable Map
     * @param level attached level
     * @return newly instance of a LoadableMap
     */
    public abstract LoadableMap build (Level level);

}

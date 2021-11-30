package model.levels.generators;

import model.levels.Level;
import model.levels.generators.vidal.RandomLevel;
import model.loadables.LoadableMap;
import view.maps.OriginalLevelMap;

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

    public abstract LoadableMap build (Level level);

}

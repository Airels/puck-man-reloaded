package model.levels.generators;

import model.levels.Level;
import model.loadables.LoadableMap;
import view.maps_levels.original_level.OriginalLevelMap;

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
            return new RandomLevel(level);
        }
    };

    LoadableMapBuilder () {}

    public abstract LoadableMap build (Level level);

}

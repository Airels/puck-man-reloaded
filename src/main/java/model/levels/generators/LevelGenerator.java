package model.levels.generators;

import model.Game;
import model.levels.Level;

/**
 * The Level Generator, allowing to generate new level randomly
 */
public class LevelGenerator {

    /**
     * Generate a new level randomly
     * @param game The Game
     * @return newly generated level
     */
    public Level generate(Game game) {
        LoadableMapBuilder loadableMapBuilder = null;
        LoadableInputBuilder loadableInputBuilder = LoadableInputBuilder.CLASSIC;
        GenericLevel genericLevel = new GenericLevel(game,loadableMapBuilder,loadableInputBuilder);
        return genericLevel;
    }

}

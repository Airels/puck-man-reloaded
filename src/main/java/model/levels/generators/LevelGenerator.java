package model.levels.generators;

import fr.r1r0r0.deltaengine.tools.dialog.Dialog;
import main.Main;
import model.Game;
import model.levels.Level;
import model.levels.fixed_levels.OriginalLevel;

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
        //noinspection CommentedOutCode
        try {
            // LoadableMapBuilder loadableMapBuilder = LoadableMapBuilder.ORIGINAL; // LoadableMapBuilder.RANDOM;
            // LoadableInputBuilder loadableInputBuilder = LoadableInputBuilder.CLASSIC;
            // GenericLevel genericLevel = new GenericLevel(game,loadableMapBuilder,loadableInputBuilder);
            return new OriginalLevel(game, false);
        } catch (Exception e) {
            new Dialog(Main.APPLICATION_NAME, "An error occured on map generation", e).show();
            return null;
        }
    }

}

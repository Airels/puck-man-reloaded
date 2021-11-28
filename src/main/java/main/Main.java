package main;

import fr.r1r0r0.deltaengine.exceptions.AlreadyInitializedException;
import fr.r1r0r0.deltaengine.exceptions.NotInitializedException;
import fr.r1r0r0.deltaengine.model.engines.DeltaEngine;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.tools.dialog.Dialog;
import model.Game;
import model.levels.fixed_levels.GameOverLevel;
import model.levels.fixed_levels.PauseMenuLevel;
import model.levels.fixed_levels.menu.MenuLevel;

/**
 * Main class of the program.
 */
public class Main {

    public final static String APPLICATION_NAME = "PuckMan Reloaded";

    static {
        try {
            DeltaEngine.launch();
        } catch (AlreadyInitializedException e) {
            new Dialog(APPLICATION_NAME, "Error on startup", e).show();
        }
    }

    public static void main(String[] args) {
        Game game = new Game(getEngine(), 144);
        MenuLevel menuLevel = new MenuLevel(game);
        PauseMenuLevel pauseMenu = new PauseMenuLevel(game);
        GameOverLevel gameOverLevel = new GameOverLevel(game);
        game.start(menuLevel, pauseMenu, gameOverLevel);
    }

    public static KernelEngine getEngine() {
        try {
            return DeltaEngine.getKernelEngine();
        } catch (NotInitializedException e) {
            new Dialog(APPLICATION_NAME, "Critical application error", e).show();
        }

        return null;
    }
}

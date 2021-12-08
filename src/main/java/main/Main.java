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
import org.jetbrains.annotations.NotNull;
import view.images.Image;

import java.io.FileNotFoundException;

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
        System.out.println("Starting game, please wait...");
        getEngine().setGameIcon(Image.GAME_LOGO.getPath());
        Game game = new Game(getEngine(), 60,100);
        MenuLevel menuLevel = new MenuLevel(game);
        PauseMenuLevel pauseMenu = new PauseMenuLevel(game);
        GameOverLevel gameOverLevel = new GameOverLevel(game);
        game.start(menuLevel, pauseMenu, gameOverLevel);
    }

    @NotNull
    public static KernelEngine getEngine() {
        try {
            return DeltaEngine.getKernelEngine();
        } catch (NotInitializedException e) {
            new Dialog(APPLICATION_NAME, "Critical application error", e).show();
            return null;
        }
    }
}

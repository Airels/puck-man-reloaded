package main;

import fr.r1r0r0.deltaengine.exceptions.AlreadyInitializedException;
import fr.r1r0r0.deltaengine.exceptions.NotInitializedException;
import fr.r1r0r0.deltaengine.model.engines.DeltaEngine;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.Game;
import model.levels.fixed_levels.gameover.GameOverLevel;
import model.levels.fixed_levels.menu.MenuLevel;

/**
 * Main class of the program.
 */
public class Main {

    static {
        try {
            DeltaEngine.launch();
        } catch (AlreadyInitializedException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        Game game = new Game(getEngine(), 60, 0.1);
        MenuLevel menuLevel = new MenuLevel(game);
        GameOverLevel gameOverLevel = new GameOverLevel(game);
        game.start(menuLevel, gameOverLevel);
    }

    public static KernelEngine getEngine() {
        try {
            return DeltaEngine.getKernelEngine();
        } catch (NotInitializedException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }
}

package main;

import fr.r1r0r0.deltaengine.exceptions.AlreadyInitializedException;
import fr.r1r0r0.deltaengine.exceptions.NotInitializedException;
import fr.r1r0r0.deltaengine.model.engines.DeltaEngine;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.Game;
import controller.maps.menu.MenuInputs;
import model.maps.fixed_levels.menu.MenuLevel;
import view.maps.menu.MenuMap;

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
        Game game = new Game(getEngine(), 60);
        MenuLevel menuLevel = new MenuLevel(game);
        game.start(menuLevel);
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

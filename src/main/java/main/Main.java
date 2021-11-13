package main;

import fr.r1r0r0.deltaengine.exceptions.AlreadyInitializedException;
import fr.r1r0r0.deltaengine.exceptions.NotInitializedException;
import fr.r1r0r0.deltaengine.model.engines.DeltaEngine;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.Game;
import controller.maps.menu.MenuInputs;
import model.levels.menu.MenuLevel;
import view.maps.menu.MenuMap;
import sounds.SoundLoader;

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

        MenuMap menuMap = new MenuMap();
        MenuInputs menuInputs = new MenuInputs(menuMap, game);
        MenuLevel menuLevel = new MenuLevel(menuMap, menuInputs);

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

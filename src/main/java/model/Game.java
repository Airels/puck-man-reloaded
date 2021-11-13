package model;

import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.levels.Level;
import sounds.SoundLoader;

public final class Game {

    private KernelEngine engine;
    private LevelLoader levelLoader;

    public Game(KernelEngine engine, int fps) {
        this.engine = engine;
        engine.setFrameRate(fps);
        engine.printFrameRate(false);

        SoundLoader.loadSounds();

        this.levelLoader = new LevelLoader(engine);
    }

    public void start(Level menuLevel) {
        levelLoader.load(menuLevel);
    }

    public void launchSinglePlayerGame() {
        // TODO Launch game
        System.out.println("LAUNCHING SINGLEPLAYER MODE!");
    }

    public void launchMultiPlayerGame() {
        // TODO New multiplayer menu
        System.out.println("LAUNCHING MULTIPLAYER MODE!");
    }

    public void quitGame() {
        System.exit(0);
    }
}

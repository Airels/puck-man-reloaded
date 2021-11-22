package model;

import config.game.GameConfiguration;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.elements.entities.ghosts.GhostState;
import model.events.MapLevelChanger;
import model.events.TimedEvent;
import model.maps.Level;
import model.maps.fixed_levels.original_level.OriginalLevel;
import sounds.SoundLoader;

public final class Game {

    private final KernelEngine deltaEngine;
    private final LevelLoader levelLoader;
    private MapLevelChanger mapLevelChanger;
    private boolean inEnergizedMode;

    public Game(KernelEngine engine, int fps) {
        this.deltaEngine = engine;
        engine.setFrameRate(fps);
        engine.printFrameRate(false);

        SoundLoader.loadSounds();

        this.levelLoader = new LevelLoader(engine);
    }

    public void start(Level menuLevel) {
        levelLoader.load(menuLevel);
    }

    public void launchSinglePlayerGame() {
        OriginalLevel originalLevel = new OriginalLevel(this);
        levelLoader.load(originalLevel);

        mapLevelChanger = new MapLevelChanger(levelLoader.getCurrentLevel());
        mapLevelChanger.addTrigger(this::nextLevel);
        deltaEngine.addGlobalEvent(mapLevelChanger);
    }

    public void launchMultiPlayerGame() {
        // TODO New multiplayer menu
        System.out.println("LAUNCHING MULTIPLAYER MODE!");
    }

    public void quitGame() {
        System.exit(0);
    }

    public void nextLevel() {
        System.out.println("NEXT LEVEL");

        /*
        deltaEngine.removeGlobalEvent(mapLevelChanger);

        // TODO generate new level

        mapLevelChanger = new MapLevelChanger(levelLoader.getCurrentLevel());
        mapLevelChanger.addTrigger(this::nextLevel);
        deltaEngine.addGlobalEvent(mapLevelChanger);
         */
    }

    public void runEnergizeMode() {
        TimedEvent timedEvent = new TimedEvent(GameConfiguration.CONF_ENERGIZED_TIME);
        timedEvent.addTrigger(() -> {
            inEnergizedMode = false;
            deltaEngine.removeGlobalEvent(timedEvent);
            levelLoader.getCurrentLevel().getGhosts().forEach(ghost -> {
                if (ghost.getState() == GhostState.SCARED)
                    ghost.setState(GhostState.NORMAL);
            });
        });

        levelLoader.getCurrentLevel().getGhosts().forEach(ghost -> ghost.setState(GhostState.SCARED));
        deltaEngine.addGlobalEvent(timedEvent);
    }

    public void gameOver() {
        System.out.println("GAME OVER"); // TODO
    }
}

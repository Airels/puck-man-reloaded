package model;

import config.game.GameConfiguration;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.elements.entities.ghosts.GhostState;
import model.events.MapLevelChanger;
import model.events.TimedEvent;
import model.maps.Level;
import model.maps.fixed_levels.original_level.OriginalLevel;
import model.maps.generators.LevelGenerator;
import sounds.SoundLoader;

/**
 * Main core of the game. Oversees game, and called when special game modes need to be activated and handled.
 * Also manage to load next map and make it accessible when player is progressing.
 */
public final class Game {

    private final KernelEngine deltaEngine;
    private final LevelLoader levelLoader;
    private MapLevelChanger mapLevelChanger;
    private final LevelGenerator levelGenerator;
    private boolean inEnergizedMode;

    /**
     * Default constructor.
     * @param engine KernelEngine reference
     * @param fps targeted game fps
     */
    public Game(KernelEngine engine, int fps) {
        this.deltaEngine = engine;
        engine.setFrameRate(fps);
        engine.printFrameRate(false);

        SoundLoader.loadSounds();

        this.levelLoader = new LevelLoader(engine);
        this.levelGenerator = new LevelGenerator();
    }

    /**
     * Start the game with the first given level.
     * @param menuLevel First game level
     */
    public void start(Level menuLevel) {
        levelLoader.load(menuLevel);
    }

    /**
     * When called, launch the game in singleplayer mode.
     */
    public void launchSinglePlayerGame() {
        OriginalLevel originalLevel = new OriginalLevel(this);
        levelLoader.load(originalLevel);

        mapLevelChanger = new MapLevelChanger(levelLoader.getCurrentLevel());
        mapLevelChanger.addTrigger(this::nextLevel);
        deltaEngine.addGlobalEvent(mapLevelChanger);
    }

    /**
     * When called, launch the game in multiplayer mode.
     * TODO WIP
     */
    public void launchMultiPlayerGame() {
        // TODO New multiplayer menu
        System.out.println("LAUNCHING MULTIPLAYER MODE!");
    }

    /**
     * Exit the program when called
     */
    public void quitGame() {
        System.exit(0);
    }

    /**
     * Transfers player character to the next generated level (in singleplayer)
     */
    public void nextLevel() {
        System.out.println("NEXT LEVEL");

        /*
        TODO
        deltaEngine.removeGlobalEvent(mapLevelChanger);

        levelLoader.load(levelGenerator.generate());

        mapLevelChanger = new MapLevelChanger(levelLoader.getCurrentLevel());
        mapLevelChanger.addTrigger(this::nextLevel);
        deltaEngine.addGlobalEvent(mapLevelChanger);
         */
    }

    /**
     * Run energized mode for configured amount of time, allowing to PacMan to eat ghosts
     */
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

        levelLoader.getCurrentLevel().getGhosts().forEach(ghost -> {
            if (ghost.getState() == GhostState.NORMAL)
                ghost.setState(GhostState.SCARED);
        });
        deltaEngine.addGlobalEvent(timedEvent);
    }

    /**
     * Triggers game over animation
     * TODO
     */
    public void gameOver() {
        System.out.println("GAME OVER"); // TODO
    }
}

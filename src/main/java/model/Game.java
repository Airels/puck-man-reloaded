package model;

import config.game.GameConfiguration;
import config.score.ScoreConfiguration;
import fr.r1r0r0.deltaengine.model.engines.Engines;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.elements.entities.PacMan;
import model.elements.entities.ghosts.GhostState;
import model.events.MapLevelChanger;
import model.events.TimedEvent;
import model.levels.Level;
import model.levels.fixed_levels.OriginalLevel;
import model.levels.generators.LevelGenerator;
import sounds.SoundLoader;

/**
 * Main core of the game. Oversees game, and called when special game modes need to be activated and handled.
 * Also manage to load next map and make it accessible when player is progressing.
 */
public final class Game {

    private final KernelEngine deltaEngine;
    private final LevelLoader levelLoader;
    private final LevelGenerator levelGenerator;
    private Level menuLevel, pauseLevel, gameOverLevel, bufferedLevel;
    private MapLevelChanger mapLevelChanger;
    private boolean inEnergizedMode, canPause;
    private int lifeCounter, ghostEatenChain, levelCounter;
    private double score;
    private TimedEvent energizeTimerEvent;
    private PacMan pacMan;

    /**
     * Default constructor.
     *
     * @param engine      KernelEngine reference
     * @param fps         targeted game fps
     */
    public Game(KernelEngine engine, int fps) {
        this.deltaEngine = engine;
        engine.setFrameRate(fps);
        engine.printFrameRate(true);

        SoundLoader.loadSounds();

        this.levelLoader = new LevelLoader(engine);
        this.levelGenerator = new LevelGenerator();
        this.canPause = true;
    }

    /**
     * Start the game with the first given level.
     *
     * @param menuLevel     First game level
     * @param gameOverLevel Level when game over
     */
    public void start(Level menuLevel, Level pauseLevel, Level gameOverLevel) {
        this.menuLevel = menuLevel;
        this.pauseLevel = pauseLevel;
        this.gameOverLevel = gameOverLevel;
        levelLoader.load(menuLevel, false);
    }

    /**
     * When called, launch the game in single player mode.
     */
    public void launchSinglePlayerGame() {
        this.pacMan = new PacMan();

        this.lifeCounter = 2;
        this.score = 0;
        this.ghostEatenChain = 0;
        this.levelCounter = 1;

        OriginalLevel originalLevel = new OriginalLevel(this);
        levelLoader.load(originalLevel);
        deltaEngine.haltCurrentMap();

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
     * Reload Game Menu when called
     */
    public void returnToMenu() {
        levelLoader.load(menuLevel, false);
    }

    /**
     * Load the Pause level
     */
    public void pauseGame() {
        if (!canPause) return;

        if (isInEnergizedMode()) energizeTimerEvent.pause();
        bufferedLevel = levelLoader.getCurrentLevel();
        levelLoader.load(pauseLevel, false);
    }

    /**
     * Resumes the current level
     */
    public void resumeGame() {
        if (isInEnergizedMode()) energizeTimerEvent.unpause();
        levelLoader.load(bufferedLevel);
        bufferedLevel = null;
    }

    /**
     * Transfers player character to the next generated level (in singleplayer)
     */
    public void nextLevel() {
        System.out.println("NEXT LEVEL"); // TODO

        deltaEngine.removeGlobalEvent(mapLevelChanger);
        Level nextLevel = levelGenerator.generate(this);
        levelLoader.load(nextLevel);

        if(levelCounter%(GameConfiguration.CONF_ADD_LIFE_EACH_X_LEVELS) == 0)
            lifeCounter+= GameConfiguration.CONF_ADD_Y_LIVES;

        levelCounter++;


        mapLevelChanger = new MapLevelChanger(levelLoader.getCurrentLevel());
        mapLevelChanger.addTrigger(this::nextLevel);
        deltaEngine.addGlobalEvent(mapLevelChanger);
    }

    /**
     * Run energized mode for configured amount of time, allowing to PacMan to eat ghosts
     */
    private void runEnergizeMode() {
        if (inEnergizedMode) energizeTimerEvent.runTriggers();

        energizeTimerEvent = new TimedEvent(GameConfiguration.CONF_ENERGIZED_TIME);
        energizeTimerEvent.addTrigger(this::turnOffEnergizeMode);

        inEnergizedMode = true;
        levelLoader.getCurrentLevel().getGhosts().forEach(ghost -> {
            if (ghost.getState() == GhostState.NORMAL)
                ghost.setState(GhostState.SCARED);
        });
        deltaEngine.addGlobalEvent(energizeTimerEvent);
    }

    /**
     * Toggle off energize mode, all scared ghosts returns to normal state.
     */
    public void turnOffEnergizeMode() {
        inEnergizedMode = false;
        ghostEatenChain = 0;
        deltaEngine.removeGlobalEvent(energizeTimerEvent);
        levelLoader.getCurrentLevel().getGhosts().forEach(ghost -> {
            if (ghost.getState() == GhostState.SCARED)
                ghost.setState(GhostState.NORMAL);
        });
        energizeTimerEvent = null;
    }

    /**
     * Returns if game is currently in energized mode
     *
     * @return boolean true if energized mode is enabled, false otherwise
     */
    public boolean isInEnergizedMode() {
        return inEnergizedMode;
    }

    /**
     * Triggers game over animation
     * TODO
     */
    public void gameOver() {
        try {
            this.canPause = false;

            deltaEngine.haltCurrentMap();
            Thread.sleep(1000);

            pacMan.setDead(true); // TODO Changement de sprite Pacman
            deltaEngine.tick(Engines.GRAPHICS_ENGINE); // TODO animation
            // TODO deltaEngine.getSoundEngine().play("GameOver.mp4");
            Thread.sleep(3000);
            if (lifeCounter > 0) {
                lifeCounter--;

                levelLoader.getCurrentLevel().reset();
                pacMan.setDead(false);
                deltaEngine.tick();
                Thread.sleep(3000);
                deltaEngine.resumeCurrentMap();
            } else {
                levelLoader.load(gameOverLevel, false);
            }

            this.canPause = true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public double getScore() {
        return score;
    }

    public int getLives() {
        return lifeCounter;
    }

    private void increaseScore(double scoreToAdd) {
        score += scoreToAdd;
    }

    public void pacGumEaten(boolean isSuper) {
        if (levelLoader.getCurrentLevel() == null) return;

        levelLoader.getCurrentLevel().getAndDecreasePacGums();

        increaseScore((isSuper) ?
                ScoreConfiguration.CONF_SUPER_PACGUM_REWARD_SCORE : ScoreConfiguration.CONF_PACGUM_REWARD_SCORE);
        if (isSuper) runEnergizeMode();
    }

    public void ghostEaten() {
        ghostEatenChain += 1;
        double eatingMultiplierScore = ScoreConfiguration.CONF_CHAIN_EATING_REWARD_SCORE;
        double eatingGhostScore = ScoreConfiguration.CONF_EATING_GHOST_REWARD_SCORE;
        increaseScore(eatingGhostScore * eatingMultiplierScore * ghostEatenChain);
    }

    /**
     * Returns PacMan player
     *
     * @return PacMan
     */
    public PacMan getPacMan() {
        return pacMan;
    }

    /**
     * Returns if game is currently paused
     * @return boolean true if in pause, false otherwise
     */
    public boolean isPaused() {
        return (bufferedLevel != null);
    }

    public TimedEvent getEnergizeTimerEvent() {
        return energizeTimerEvent;
    }
}

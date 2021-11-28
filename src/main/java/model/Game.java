package model;

import config.game.GameConfiguration;
import config.score.ScoreConfiguration;
import fr.r1r0r0.deltaengine.model.engines.Engines;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.elements.entities.PacMan;
import model.elements.entities.ghosts.GhostState;
import model.events.LevelChanger;
import model.events.TimedEvent;
import model.levels.Level;
import model.levels.fixed_levels.OriginalLevel;
import model.levels.generators.LevelGenerator;
import org.jetbrains.annotations.Nullable;
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
    private LevelChanger mapLevelChanger;
    private boolean inEnergizedMode, canPause;
    private int lifeCounter, ghostEatenChain;
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

        OriginalLevel originalLevel = new OriginalLevel(this);
        levelLoader.load(originalLevel);
        deltaEngine.haltCurrentMap();

        mapLevelChanger = originalLevel.getLevelChanger();
        if (mapLevelChanger == null) {
            throw new RuntimeException("LevelChanger can't be null for a normal level");
        }
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

        mapLevelChanger = nextLevel.getLevelChanger();
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
     * Triggers game over animation,
     * and run another game if possible, otherwise show game over screen
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

    /**
     * Returns the current score of the game
     * @return double the score
     */
    public double getScore() {
        return score;
    }

    /**
     * Returns number of lives players have before the game over.
     * @return integer number of lives remaining
     */
    public int getLives() {
        return lifeCounter;
    }

    /**
     * Increase the current score by the value given
     * @param scoreToAdd double value to add on the score
     */
    private void increaseScore(double scoreToAdd) {
        score += scoreToAdd;
    }

    /**
     * Calls its method when a PacGum is eaten by PacMan to trigger multiple actions according to the game
     * @param isSuper boolean if eaten PacGum is super
     */
    public void pacGumEaten(boolean isSuper) {
        if (levelLoader.getCurrentLevel() == null) return;

        levelLoader.getCurrentLevel().getAndDecreasePacGums();

        increaseScore((isSuper) ?
                ScoreConfiguration.CONF_SUPER_PACGUM_REWARD_SCORE : ScoreConfiguration.CONF_PACGUM_REWARD_SCORE);
        if (isSuper) runEnergizeMode();
    }

    /**
     * Calls its method when a Ghost is eaton by PacMan to trigger different actions
     */
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

    /**
     * Returns the timed event linked to the Energized Mode.
     * @return Timed event instance if energized mode is running, null otherwise
     */
    @Nullable
    public TimedEvent getEnergizeTimerEvent() {
        return energizeTimerEvent;
    }
}

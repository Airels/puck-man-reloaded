package model;

import config.game.GameConfiguration;
import config.score.ScoreConfiguration;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.elements.entities.ghosts.GhostState;
import model.events.MapLevelChanger;
import model.events.TimedEvent;
import model.levels.Level;
import model.levels.fixed_levels.gameover.GameOverLevel;
import model.levels.fixed_levels.original_level.OriginalLevel;
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
    private Level menuLevel, gameOverLevel;
    private MapLevelChanger mapLevelChanger;
    private boolean inEnergizedMode;
    private int lifeCounter, ghostEatenChain;
    private double score;

    /**
     * Default constructor.
     *
     * @param engine KernelEngine reference
     * @param fps    targeted game fps
     */
    public Game(KernelEngine engine, int fps) {
        this.deltaEngine = engine;
        engine.setFrameRate(fps);
        engine.printFrameRate(false);

        SoundLoader.loadSounds();

        this.levelLoader = new LevelLoader(engine);
        this.levelGenerator = new LevelGenerator();
        this.lifeCounter = 2;
        ghostEatenChain = 0;
    }

    /**
     * Start the game with the first given level.
     *
     * @param menuLevel First game level
     * @param gameOverLevel Level when game over
     */
    public void start(Level menuLevel, Level gameOverLevel) {
        this.menuLevel = menuLevel;
        this.gameOverLevel = gameOverLevel;
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
     * Reload Game Menu when called
     */
    public void returnToMenu() {
        levelLoader.load(menuLevel);
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
    private void runEnergizeMode() {
        TimedEvent timedEvent = new TimedEvent(GameConfiguration.CONF_ENERGIZED_TIME);
        timedEvent.addTrigger(() -> {
            inEnergizedMode = false;
            ghostEatenChain = 0;
            deltaEngine.removeGlobalEvent(timedEvent);
            levelLoader.getCurrentLevel().getGhosts().forEach(ghost -> {
                if (ghost.getState() == GhostState.SCARED)
                    ghost.setState(GhostState.NORMAL);
            });
        });

        inEnergizedMode = true;
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
        levelLoader.load(gameOverLevel);

        /*
        try {
            deltaEngine.haltCurrentMap();
            Thread.sleep(1000);

            // TODO Changement de sprite Pacman
            // TODO animation
            // TODO deltaEngine.getSoundEngine().play("GameOver.mp4");
            Thread.sleep(2000);
            levelLoader.getCurrentLevel().reset();
            deltaEngine.tick();
            Thread.sleep(3000);
            if (lifeCounter > 0) {
                deltaEngine.resumeCurrentMap();
                lifeCounter--;
            } else {
                // TODO FIN DU JEU
                // TODO Display ScoreDisplayer
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        }

         */
    }

    public double getScore() {
        return score;
    }

    private void increaseScore(double scoreToAdd) {
        score += scoreToAdd;
    }

    public void pacGumEaten(boolean isSuper){
        levelLoader.getCurrentLevel().getAndDecreasePacGums();

        increaseScore((isSuper) ?
                ScoreConfiguration.CONF_SUPER_PACGUM_REWARD_SCORE : ScoreConfiguration.CONF_PACGUM_REWARD_SCORE);
        if (isSuper) runEnergizeMode();
    }

    public void ghostEaten(){
        ghostEatenChain += 1;
        double eatingMultiplierScore = ScoreConfiguration.CONF_CHAIN_EATING_REWARD_SCORE;
        double eatingGhostScore = ScoreConfiguration.CONF_EATING_GHOST_REWARD_SCORE;
        increaseScore(eatingGhostScore * eatingMultiplierScore * ghostEatenChain);
    }
}

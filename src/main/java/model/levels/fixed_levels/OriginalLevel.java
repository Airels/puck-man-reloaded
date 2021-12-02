package model.levels.fixed_levels;

import controller.inputs.levels.OriginalLevelInputs;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.HUDElement;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.sprites.Text;
import model.Game;
import model.elements.entities.PacMan;
import model.elements.entities.ghosts.Ghost;
import model.elements.entities.ghosts.GhostState;
import model.events.LevelChanger;
import model.levels.Level;
import model.loadables.LoadableInput;
import model.loadables.LoadableMap;
import org.jetbrains.annotations.NotNull;
import sounds.Sounds;
import view.maps.OriginalLevelMap;

import java.util.Collection;
import java.util.Map;

import static config.game.GameConfiguration.*;


/**
 * The Original PacMan Level
 */
public class OriginalLevel implements Level {

    private final Game game;
    private final LoadableMap mapLevel;
    private final LoadableInput inputLevel;
    private final PacMan pacMan;
    private int nbOfPacGums;
    private HUDElement readyText;
    private boolean generated, firstTime;

    /**
     * Default constructor.
     *
     * @param game the Game
     * @param firstTime if true, plays the introduction music and the "READY!" text, otherwise, will generate a newly map with random ghosts
     */
    public OriginalLevel(Game game, boolean firstTime) {
        this.game = game;
        this.pacMan = game.getPacMan();

        this.mapLevel = new OriginalLevelMap(this, pacMan, !firstTime);
        this.inputLevel = new OriginalLevelInputs(game, firstTime);
        this.firstTime = firstTime;
        this.generated = false;

        if (firstTime) {
            Text rText = new Text(CONF_READY_TEXT);
            rText.setSize(CONF_READY_SIZE);
            rText.setColor(CONF_READY_COLOR.getEngineColor());
            readyText = new HUDElement(
                    "Ready Text",
                    CONF_READY_POSITION,
                    rText,
                    Dimension.DEFAULT_DIMENSION
            );
        }
    }

    @Override
    public void load(KernelEngine deltaEngine) {
        if (firstTime) {
            deltaEngine.addHUDElement(readyText);

            new Thread(() -> {
                Sounds.GAME_BEGIN.play();

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    deltaEngine.removeHUDElement(readyText);
                    deltaEngine.resumeCurrentMap();
                    Sounds.SIREN.setSpeed(CONF_SOUND_SIREN_CHASE_SPEED);
                    Sounds.SIREN.setVolume(CONF_SOUND_SIREN_CHASE_VOLUME);
                    Sounds.SIREN.play();
                }
            }).start();

            firstTime = false;
        }

        if (!generated) {
            nbOfPacGums = 1;
            generated = true;
        }
    }

    @Override
    public void unload(KernelEngine deltaEngine) {

    }

    @Override
    public @NotNull LoadableMap getMapLevelLoadable() {
        return mapLevel;
    }

    @Override
    public @NotNull LoadableInput getInputsLoadable() {
        return inputLevel;
    }

    @Override
    public int getNbOfPacGums() {
        return this.nbOfPacGums;
    }

    @Override
    public @NotNull Collection<Ghost> getGhosts() {
        return mapLevel.getGeneratedGhosts();
    }

    @Override
    public @NotNull Game getGame() {
        return game;
    }

    @Override
    public @NotNull Map<Entity, Coordinates<Double>> getSpawnPoints() {
        return mapLevel.getSpawnPoints();
    }

    /**
     * Returns the level changer of the level
     *
     * @return Level Changer instance
     */
    @Override
    public LevelChanger getLevelChanger() {
        return new LevelChanger(this, new Coordinates<>(9.0, 12.0));
    }

    @Override
    public int getAndDecreasePacGums() {
        try {
            return getNbOfPacGums();
        } finally {
            this.nbOfPacGums--;
        }
    }
}

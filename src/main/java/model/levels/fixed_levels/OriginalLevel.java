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
    private boolean firstTime;

    /**
     * Default constructor.
     *
     * @param game the Game
     */
    public OriginalLevel(Game game) {
        this.game = game;
        this.pacMan = game.getPacMan();

        this.mapLevel = new OriginalLevelMap(this, pacMan);
        this.inputLevel = new OriginalLevelInputs(game);


        Text rText = new Text(CONF_READY_TEXT);
        rText.setSize(CONF_READY_SIZE);
        rText.setColor(CONF_READY_COLOR.getEngineColor());
        readyText = new HUDElement(
                "Ready Text",
                CONF_READY_POSITION,
                rText,
                Dimension.DEFAULT_DIMENSION
        );

        firstTime = true;
    }

    @Override
    public void load(KernelEngine deltaEngine) {
        if (firstTime) {
            deltaEngine.addHUDElement(readyText);

            nbOfPacGums = mapLevel.getNbOfGeneratedPacGums();

            new Thread(() -> {
                Sounds.GAME_BEGIN.play();

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    deltaEngine.removeHUDElement(readyText);
                    deltaEngine.resumeCurrentMap();
                }
            }).start();

            firstTime = false;
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

    @Override
    public void reset() {
        for (Map.Entry<Entity, Coordinates<Double>> entry : mapLevel.getSpawnPoints().entrySet()) {
            entry.getKey().setCoordinates(entry.getValue());
        }
        for (Ghost ghost : getGhosts()) {
            ghost.setState(GhostState.NORMAL);

        }
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

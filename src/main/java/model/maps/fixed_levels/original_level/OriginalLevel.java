package model.maps.fixed_levels.original_level;

import controller.maps.original_level.OriginalLevelInputs;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.Game;
import model.elements.entities.PacMan;
import model.elements.entities.ghosts.Ghost;
import model.loadables.LoadableInput;
import model.loadables.LoadableMap;
import model.maps.Level;
import sounds.Sounds;
import view.maps.original_level.OriginalLevelMap;

import java.util.Collection;

/**
 * The Original PacMan Level
 */
public class OriginalLevel implements Level {

    private final Game game;
    private final LoadableMap mapLevel;
    private final LoadableInput inputLevel;
    private final PacMan pacMan;
    private int nbOfPacGums;

    /**
     * Default constructor.
     * @param game the Game
     */
    public OriginalLevel(Game game) {
        this.pacMan = new PacMan();
        this.game = game;

        this.mapLevel = new OriginalLevelMap(this, pacMan);
        this.inputLevel = new OriginalLevelInputs(pacMan);
    }

    @Override
    public void load(KernelEngine deltaEngine) {
        nbOfPacGums = mapLevel.getNbOfGeneratedPacGums();

        new Thread(() -> {
            deltaEngine.haltCurrentMap();
            Sounds.GAME_BEGIN.play();

            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                deltaEngine.resumeCurrentMap();
            }
        }).start();
    }

    @Override
    public void unload(KernelEngine deltaEngine) {

    }

    @Override
    public LoadableMap getMapLevelLoadable() {
        return mapLevel;
    }

    @Override
    public LoadableInput getInputsLoadable() {
        return inputLevel;
    }

    @Override
    public int getNbOfPacGums() {
        return this.nbOfPacGums;
    }

    @Override
    public Collection<Ghost> getGhosts() {
        return mapLevel.getGeneratedGhosts();
    }

    @Override
    public Game getGame() {
        return game;
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

package model.maps.fixed_levels.original_level;

import controller.maps.original_level.OriginalLevelInputs;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.events.AttributeListener;
import model.Game;
import model.elements.entities.PacMan;
import model.elements.entities.ghosts.Ghost;
import model.maps.Level;
import model.loadables.LoadableInput;
import model.loadables.LoadableMap;
import sounds.Sounds;
import view.maps.original_level.OriginalLevelMap;

import java.util.Collection;
import java.util.LinkedList;

public class OriginalLevel implements Level {

    private final Game game;
    private final LoadableMap mapLevel;
    private final LoadableInput inputLevel;
    private final PacMan pacMan;
    private int nbOfPacGums;
    private final Collection<AttributeListener<Integer>> pacgumListeners;

    public OriginalLevel(Game game) {
        this.pacMan = new PacMan();
        this.game = game;

        this.mapLevel = new OriginalLevelMap(this, pacMan);
        this.inputLevel = new OriginalLevelInputs(pacMan);

        pacgumListeners = new LinkedList<>();
    }

    @Override
    public void load(KernelEngine deltaEngine) {


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
    public void addPacGumValueListener(AttributeListener<Integer> listener) {
        pacgumListeners.add(listener);
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
        int before = getNbOfPacGums();

        this.nbOfPacGums--;

        try {
            return getNbOfPacGums();
        } finally {
            pacgumListeners.forEach(e -> e.attributeUpdated(before, getNbOfPacGums()));
        }
    }
}

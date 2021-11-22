package model.maps.fixed_levels.menu;

import fr.r1r0r0.deltaengine.exceptions.SoundDoesNotExistException;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.events.AttributeListener;
import model.Game;
import model.elements.entities.ghosts.Ghost;
import model.maps.Level;
import model.loadables.LoadableInput;
import model.loadables.LoadableMap;
import sounds.Sounds;

import java.util.ArrayList;
import java.util.Collection;

public class MenuLevel implements Level {

    private Game game;
    private LoadableMap map;
    private LoadableInput inputs;

    public MenuLevel(Game game, LoadableMap map, LoadableInput inputs) {
        this.map = map;
        this.inputs = inputs;
    }

    @Override
    public void load(KernelEngine deltaEngine) {
        try {
            Sounds.MAIN_THEME.play();
            deltaEngine.getSoundEngine().setLoop(Sounds.MAIN_THEME.getName(), true);
        } catch (SoundDoesNotExistException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void unload(KernelEngine deltaEngine) {
        Sounds.MAIN_THEME.stop();
    }

    @Override
    public LoadableMap getMapLevelLoadable() {
        return map;
    }

    @Override
    public LoadableInput getInputsLoadable() {
        return inputs;
    }

    @Override
    public int getNbOfPacGums() {
        return 0;
    }

    @Override
    public int getAndDecreasePacGums() {
        return 0;
    }

    @Override
    public void addPacGumValueListener(AttributeListener<Integer> listener) {

    }

    @Override
    public Collection<Ghost> getGhosts() {
        return new ArrayList<>();
    }

    @Override
    public Game getGame() {
        return game;
    }
}

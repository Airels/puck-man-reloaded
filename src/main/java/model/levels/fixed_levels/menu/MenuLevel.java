package model.levels.fixed_levels.menu;

import fr.r1r0r0.deltaengine.exceptions.SoundDoesNotExistException;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import main.Main;
import model.levels.Level;
import model.loadables.LoadableInput;
import model.loadables.LoadableMap;
import sounds.Sounds;

import java.awt.image.Kernel;

public class MenuLevel implements Level {

    private LoadableMap map;
    private LoadableInput inputs;

    public MenuLevel(LoadableMap map, LoadableInput inputs) {
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
}

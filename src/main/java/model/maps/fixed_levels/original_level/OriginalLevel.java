package model.maps.fixed_levels.original_level;

import controller.maps.original_level.OriginalLevelInputs;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import main.Main;
import model.elements.entities.PacMan;
import model.maps.Level;
import model.loadables.LoadableInput;
import model.loadables.LoadableMap;
import sounds.Sounds;
import view.maps.original_level.OriginalLevelMap;

public class OriginalLevel implements Level {

    private final LoadableMap mapLevel;
    private final LoadableInput inputLevel;
    private final PacMan pacMan;

    public OriginalLevel() {
        this.pacMan = new PacMan();

        this.mapLevel = new OriginalLevelMap(pacMan);
        this.inputLevel = new OriginalLevelInputs(pacMan);
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
}
package model.levels.fixed_levels.menu;

import controller.inputs.levels.menu.MenuInputs;
import fr.r1r0r0.deltaengine.exceptions.SoundDoesNotExistException;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import model.Game;
import model.elements.entities.ghosts.Ghost;
import model.levels.Level;
import model.loadables.LoadableInput;
import model.loadables.LoadableMap;
import org.jetbrains.annotations.NotNull;
import sounds.Sounds;
import view.maps_levels.menu.MenuMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


/**
 * The Main Menu Level
 */
public class MenuLevel implements Level {

    private Game game;
    private LoadableMap map;
    private LoadableInput inputs;

    /**
     * Default constructor.
     * @param game the Game
     */
    public MenuLevel(Game game) {
        MenuMap map = new MenuMap();

        this.game = game;
        this.map = map;
        this.inputs = new MenuInputs(map, game);
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
    public @NotNull LoadableMap getMapLevelLoadable() {
        return map;
    }

    @Override
    public @NotNull LoadableInput getInputsLoadable() {
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
    public @NotNull Collection<Ghost> getGhosts() {
        return new ArrayList<>();
    }

    @Override
    public @NotNull Game getGame() {
        return game;
    }

    @Override
    public @NotNull Map<Entity, Coordinates<Double>> getSpawnPoints() {

        return map.getSpawnPoints();
    }

    @Override
    public void reset() {

    }
}

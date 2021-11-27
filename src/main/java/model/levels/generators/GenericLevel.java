package model.levels.generators;

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

import java.util.Collection;
import java.util.Map;

import static config.game.GameConfiguration.*;
import static config.game.GameConfiguration.CONF_READY_POSITION;

public class GenericLevel implements Level {

    private final Game game;
    private final LoadableMap mapLevel;
    private final LoadableInput inputLevel;
    private final PacMan pacMan;
    private int nbOfPacGums;

    public GenericLevel(Game game, LoadableMapBuilder loadableMapBuilder, LoadableInputBuilder loadableInputBuilder) {
        this.game = game;
        pacMan = game.getPacMan();

        mapLevel = loadableMapBuilder.build(this);
        inputLevel = loadableInputBuilder.build(this);
        nbOfPacGums = mapLevel.getNbOfGeneratedPacGums();
    }

    @Override
    public void load(KernelEngine deltaEngine) {

    }

    @Override
    public void unload(KernelEngine deltaEngine) {

    }

    @Override
    public @NotNull LoadableMap getMapLevelLoadable () {
        return mapLevel;
    }

    @Override
    public @NotNull LoadableInput getInputsLoadable () {
        return inputLevel;
    }

    @Override
    public int getNbOfPacGums () {
        return this.nbOfPacGums;
    }

    @Override
    public @NotNull Collection<Ghost> getGhosts () {
        return mapLevel.getGeneratedGhosts();
    }

    @Override
    public @NotNull Game getGame () {
        return game;
    }

    @Override
    public @NotNull Map<Entity,Coordinates<Double>> getSpawnPoints () {
        return mapLevel.getSpawnPoints();
    }

    @Override
    public void reset () {
        for (Map.Entry<Entity,Coordinates<Double>> entry : mapLevel.getSpawnPoints().entrySet()) {
            entry.getKey().setCoordinates(entry.getValue());
        }
        for (Ghost ghost : getGhosts()) {
            ghost.setState(GhostState.NORMAL);
        }
    }

    @Override
    public int getAndDecreasePacGums () {
        try {
            return getNbOfPacGums();
        } finally {
            this.nbOfPacGums--;
        }
    }

}

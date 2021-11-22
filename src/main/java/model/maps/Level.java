package model.maps;

import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.events.AttributeListener;
import model.Game;
import model.elements.entities.PacMan;
import model.elements.entities.ghosts.Ghost;
import model.loadables.LoadableInput;
import model.loadables.LoadableMap;

import java.util.Collection;

public interface Level {

    void load(KernelEngine deltaEngine);

    void unload(KernelEngine deltaEngine);

    LoadableMap getMapLevelLoadable();

    LoadableInput getInputsLoadable();

    int getNbOfPacGums();

    int getAndDecreasePacGums();

    void addPacGumValueListener(AttributeListener<Integer> listener);

    Collection<Ghost> getGhosts();

    Game getGame();
}

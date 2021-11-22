package model.maps;

import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.events.AttributeListener;
import model.loadables.LoadableInput;
import model.loadables.LoadableMap;

public interface Level {

    void load(KernelEngine deltaEngine);

    void unload(KernelEngine deltaEngine);

    LoadableMap getMapLevelLoadable();

    LoadableInput getInputsLoadable();

    int getNbOfPacGums();

    void setNbOfPacGums(int nbOfPacGums);

    int getAndDecreasePacGums();

    void addPacGumValueListener(AttributeListener<Integer> listener);
}

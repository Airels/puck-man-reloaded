package model.loadables;

import fr.r1r0r0.deltaengine.model.engines.KernelEngine;

public interface Loadable {

    void load(KernelEngine engine);
    void unload(KernelEngine engine);
}

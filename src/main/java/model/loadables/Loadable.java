package model.loadables;

import fr.r1r0r0.deltaengine.model.engines.KernelEngine;

/**
 * A Loadable. Implemented when classes instances need to be loaded for the Engine. (like Level)
 */
public interface Loadable {

    /**
     * Load the object
     * @param engine DeltaEngine
     */
    void load(KernelEngine engine);

    /**
     * Unload the object
     * @param engine DeltaEngine
     */
    void unload(KernelEngine engine);
}

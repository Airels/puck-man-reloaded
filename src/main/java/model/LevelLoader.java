package model;

import config.game.GlobalHUDConfiguration;
import controller.InputsLoader;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import model.levels.Level;
import view.hud.GlobalHUD;
import view.maps.MapLevelLoader;

/**
 * Level Loader. Loads given Level (its map and inputs), and load it on the Engine
 */
public final class LevelLoader {

    private final MapLevelLoader mapLoader;
    private final InputsLoader inputsLoader;
    private final KernelEngine deltaEngine;
    private Level currentLevel;
    private GlobalHUD globalHUD;

    public LevelLoader(KernelEngine deltaEngine) {
        this.deltaEngine = deltaEngine;
        this.mapLoader = new MapLevelLoader(deltaEngine);
        this.inputsLoader = new InputsLoader(deltaEngine);
    }

    /**
     * Load given level, its map and its inputs, and Game HUD if wanted
     *
     * @param level         Level to load
     * @param loadGlobalHUD boolean true to load global game hud
     */
    public synchronized void load(Level level, boolean loadGlobalHUD) {
        if (currentLevel != null)
            unload(currentLevel);

        mapLoader.loadMapLevel(level.getMapLevelLoadable());
        inputsLoader.loadInputs(level.getInputsLoadable());
        level.load(deltaEngine);

        MapLevel mapLevel = level.getMapLevelLoadable().getMapLevel();
        double x = 0,
                y = mapLevel.getHeight() - GlobalHUDConfiguration.CONF_GLOBAL_HUD_HEIGHT_SIZE,
                width = mapLevel.getWidth(),
                height = mapLevel.getHeight();
        if (loadGlobalHUD) {
            globalHUD = new GlobalHUD(level.getGame(), new Coordinates<>(x, y), width, height);
            globalHUD.load(deltaEngine);
        }

        currentLevel = level;
    }

    /**
     * Load given level, its map and its inputs, and load game hud by default
     *
     * @param level Level to load
     */
    public void load(Level level) {
        load(level, true);
    }

    /**
     * Unload given level, its maps and its inputs,
     *
     * @param level Level to unload
     */
    public synchronized void unload(Level level) {
        level.unload(deltaEngine);
        mapLoader.unloadMapLevel(level.getMapLevelLoadable());
        inputsLoader.unloadInputs(level.getInputsLoadable());

        if (globalHUD != null) {
            globalHUD.unload(deltaEngine);
            globalHUD = null;
        }

        currentLevel = null;
    }

    /**
     * Returns current level of the game
     *
     * @return current Level, or null if no level is currently running
     */
    public Level getCurrentLevel() {
        return currentLevel;
    }
}

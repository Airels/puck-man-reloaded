package controller.inputs.levels;

import fr.r1r0r0.deltaengine.exceptions.InputKeyStackingError;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.engines.utils.Key;
import fr.r1r0r0.deltaengine.tools.dialog.Dialog;
import main.Main;
import model.Game;
import model.levels.fixed_levels.menu.MenuSelector;
import view.maps.MenuMap;
import model.loadables.LoadableInput;

/**
 * Inputs of the Menu
 */
public class MenuInputs implements LoadableInput {

    private final MenuSelector menuSelector;
    public final MenuMap menuMap;

    public MenuInputs(MenuMap menuMap, Game game) {
        this.menuMap = menuMap;
        this.menuSelector = new MenuSelector(menuMap, game);
    }

    @Override
    public void load(KernelEngine engine) {
        try {
            engine.setInput(Key.Z, menuSelector.getUpEvent());
            engine.setInput(Key.S, menuSelector.getDownEvent());
            engine.setInput(Key.ARROW_UP, menuSelector.getUpEvent());
            engine.setInput(Key.ARROW_DOWN, menuSelector.getDownEvent());
            engine.setInput(Key.ENTER, menuSelector.getEnterEvent());
        } catch (InputKeyStackingError e) {
            new Dialog(Main.APPLICATION_NAME, "Menu inputs loading error", e).show();
        }
    }

    @Override
    public void unload(KernelEngine engine) {
        engine.clearInput(Key.Z);
        engine.clearInput(Key.S);
        engine.clearInput(Key.ARROW_UP);
        engine.clearInput(Key.ARROW_DOWN);
        engine.clearInput(Key.ENTER);
    }
}

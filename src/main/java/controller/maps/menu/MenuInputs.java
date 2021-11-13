package controller.maps.menu;

import fr.r1r0r0.deltaengine.exceptions.InputKeyStackingError;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.engines.utils.Key;
import model.Game;
import model.levels.fixed_levels.menu.MenuSelector;
import view.maps.menu.MenuMap;
import model.loadables.LoadableInput;

public class MenuInputs implements LoadableInput {

    private MenuSelector menuSelector;
    public MenuMap menuMap;

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
        } catch (InputKeyStackingError inputKeyStackingError) {
            inputKeyStackingError.printStackTrace();
            System.exit(1);
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

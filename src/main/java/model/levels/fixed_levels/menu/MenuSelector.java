package model.levels.fixed_levels.menu;

import fr.r1r0r0.deltaengine.model.events.InputEvent;
import fr.r1r0r0.deltaengine.model.events.Trigger;
import model.Game;
import sounds.Sounds;
import view.maps.MenuMap;

/**
 * The Main Menu selector. Allow to user to select the action he wants.
 */
public class MenuSelector {

    private int currentSelected;
    private MenuMap menuMap;
    private Trigger upTrigger, downTrigger, enterTrigger;
    private Game game;

    /**
     * Default constructor
     *
     * @param menuMap the MenuMap
     * @param game    the Game
     */
    public MenuSelector(MenuMap menuMap, Game game) {
        this.game = game;
        currentSelected = 0;
        this.menuMap = menuMap;

        upTrigger = () -> {
            if (currentSelected > 0)
                currentSelected -= 2;

            selectMenuText();
        };

        downTrigger = () -> {
            if (currentSelected < 2)
                currentSelected += 2;

            selectMenuText();
        };

        enterTrigger = this::userMenuSelected;
    }

    public InputEvent getUpEvent() {
        return new InputEvent(upTrigger, null);
    }

    public InputEvent getDownEvent() {
        return new InputEvent(downTrigger, null);
    }

    public InputEvent getEnterEvent() {
        return new InputEvent(enterTrigger, null);
    }

    /**
     * Called when user switch menu selection, to change focused text and play sound
     */
    private void selectMenuText() {
        Sounds.MENU_SELECTION.stop();
        Sounds.MENU_SELECTION.play();

        switch (currentSelected) {
            case 0 -> menuMap.selectSinglePlayer();
            case 1 -> menuMap.selectMultiPlayer();
            case 2 -> menuMap.selectQuit();
        }
    }

    /**
     * Called when user selected something on the menu
     */
    private void userMenuSelected() {
        switch (currentSelected) {
            case 0 -> game.launchSinglePlayerGame();
            case 1 -> game.launchMultiPlayerGame();
            case 2 -> game.quitGame();
        }
    }
}

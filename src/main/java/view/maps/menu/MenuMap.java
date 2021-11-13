package view.maps.menu;

import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelAlreadyExistException;
import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelDoesNotExistException;
import fr.r1r0r0.deltaengine.model.MapLevel;
import fr.r1r0r0.deltaengine.model.elements.HUDElement;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.sprites.Text;
import javafx.scene.paint.Color;
import main.Main;
import model.builders.TextBuilder;
import model.loadables.LoadableMap;

public class MenuMap implements LoadableMap {

    private MapLevel mapLevel;
    private HUDElement title, singlePlayerText, multiPlayerText, quitText, selectedText;

    public MenuMap() {
        try {
            this.mapLevel = new MapLevel("menu", 20, 10);
            Main.getEngine().addMap(mapLevel);
        } catch (MapLevelAlreadyExistException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void load(KernelEngine deltaEngine) {
        TextBuilder textBuilder = new TextBuilder("PuckMan Reloaded");
        textBuilder.setCoordinates(10, 10)
                .setFont("Consolas")
                .setSize(50);
        title = textBuilder.build();

        textBuilder.setSize(12)
                .setCoordinates(50, 10)
                .setText("Single player");
        singlePlayerText = textBuilder.build();

        textBuilder.setCoordinates(70, 10)
                .setText("Multi player");
        multiPlayerText = textBuilder.build();

        textBuilder.setCoordinates(80, 10)
                .setText("Quit");
        quitText = textBuilder.build();

        try {
            deltaEngine.setCurrentMap("menu");
        } catch (MapLevelDoesNotExistException e) {
            e.printStackTrace(); // Should never happen
            System.exit(1);
        }

        deltaEngine.addHUDElement(title);
        deltaEngine.addHUDElement(singlePlayerText);
        deltaEngine.addHUDElement(multiPlayerText);
        deltaEngine.addHUDElement(quitText);

        selectSinglePlayer(); // Default choice
    }

    @Override
    public void unload(KernelEngine deltaEngine) {
        deltaEngine.removeHUDElement(title);
        deltaEngine.removeHUDElement(singlePlayerText);
        deltaEngine.removeHUDElement(multiPlayerText);
        deltaEngine.removeHUDElement(quitText);
    }

    public void selectSinglePlayer() {
        unselectCurrent();
        selectedText = singlePlayerText;
        selectCurrent();
    }

    public void selectMultiPlayer() {
        unselectCurrent();
        selectedText = multiPlayerText;
        selectCurrent();
    }

    public void selectQuit() {
        unselectCurrent();
        selectedText = quitText;
        selectCurrent();
    }

    private void unselectCurrent() {
        if (selectedText != null)
            ((Text) selectedText.getSprite()).setColor(Color.WHITE);
    }

    private void selectCurrent() {
        ((Text) selectedText.getSprite()).setColor(Color.GOLD);
    }
}

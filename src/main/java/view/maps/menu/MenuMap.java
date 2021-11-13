package view.maps.menu;

import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelAlreadyExistException;
import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelDoesNotExistException;
import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelEntityNameStackingException;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.Entity;
import fr.r1r0r0.deltaengine.model.elements.HUDElement;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevelBuilder;
import fr.r1r0r0.deltaengine.model.sprites.Text;
import fr.r1r0r0.deltaengine.model.sprites.shapes.Rectangle;
import main.Main;
import model.builders.TextBuilder;
import model.loadables.LoadableMap;
import view.colors.Color;
import view.images.Image;

public class MenuMap implements LoadableMap {

    private MapLevel mapLevel;
    private HUDElement title, singlePlayerText, multiPlayerText, quitText, selectedText, mainImg;

    public MenuMap() {
        // this.mapLevel = new MapLevel("menu", 20, 10);
        this.mapLevel = new MapLevelBuilder("menu", 30, 15).build();
        try {
            Main.getEngine().addMap(mapLevel);
        } catch (MapLevelAlreadyExistException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void load(KernelEngine deltaEngine) {
        TextBuilder textBuilder = new TextBuilder("PuckMan\nReloaded");
        textBuilder.setCoordinates(1, 3)
                .setFont("Consolas")
                .setSize(100);
        title = textBuilder.build();

        textBuilder.setSize(25)
                .setCoordinates(2, 10)
                .setText("Single player");
        singlePlayerText = textBuilder.build();

        textBuilder.setCoordinates(2, 11)
                .setText("Multi player");
        multiPlayerText = textBuilder.build();

        textBuilder.setCoordinates(2, 12)
                .setText("Quit");
        quitText = textBuilder.build();


        mainImg = new HUDElement("main_img", new Coordinates<>(15.0, 6.0), Image.MAIN_IMG.getImage(), new Dimension(1, 1));

        try {
            deltaEngine.setCurrentMap("menu");
        } catch (MapLevelDoesNotExistException e) {
            e.printStackTrace();
            System.exit(1);
        }


        deltaEngine.addHUDElement(title);
        deltaEngine.addHUDElement(singlePlayerText);
        deltaEngine.addHUDElement(multiPlayerText);
        deltaEngine.addHUDElement(quitText);
        deltaEngine.addHUDElement(mainImg);

        selectSinglePlayer(); // Default choice
    }

    @Override
    public void unload(KernelEngine deltaEngine) {
        deltaEngine.removeHUDElement(title);
        deltaEngine.removeHUDElement(singlePlayerText);
        deltaEngine.removeHUDElement(multiPlayerText);
        deltaEngine.removeHUDElement(quitText);
        deltaEngine.removeHUDElement(mainImg);
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
            ((Text) selectedText.getSprite()).setColor(Color.WHITE.getEngineColor());
    }

    private void selectCurrent() {
        ((Text) selectedText.getSprite()).setColor(Color.GOLD.getEngineColor());
    }
}

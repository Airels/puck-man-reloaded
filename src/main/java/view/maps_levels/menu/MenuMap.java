package view.maps_levels.menu;

import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelAlreadyExistException;
import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelDoesNotExistException;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.HUDElement;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevelBuilder;
import fr.r1r0r0.deltaengine.model.sprites.Text;
import main.Main;
import model.builders.TextBuilder;
import model.elements.entities.ghosts.Ghost;
import model.loadables.LoadableMap;
import org.jetbrains.annotations.NotNull;
import view.colors.Color;
import view.images.Image;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * The Map of the Game Main Menu, only loads HUD elements such image or title.
 */
public class MenuMap implements LoadableMap {

    private final MapLevel mapLevel;
    private HUDElement title, singlePlayerText, multiPlayerText, quitText, selectedText, mainImg;

    /**
     * Default constructor
     */
    public MenuMap() {
        this.mapLevel = new MapLevelBuilder("PuckMan Reloaded", 30, 15).build();

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


        mainImg = new HUDElement("main_img", new Coordinates<>(15.0, 6.0), Image.MAIN_IMG.getSprite(), new Dimension(1, 1));

        try {
            deltaEngine.setCurrentMap("PuckMan Reloaded");
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

    /**
     * Called when user select singleplayer in menu
     */
    public void selectSinglePlayer() {
        unselectCurrent();
        selectedText = singlePlayerText;
        selectCurrent();
    }

    /**
     * Called when user select multiplayer in menu
     */
    public void selectMultiPlayer() {
        unselectCurrent();
        selectedText = multiPlayerText;
        selectCurrent();
    }

    /**
     * Called when user select quit in menu
     */
    public void selectQuit() {
        unselectCurrent();
        selectedText = quitText;
        selectCurrent();
    }

    /**
     * Unselect current selection
     */
    private void unselectCurrent() {
        if (selectedText != null)
            ((Text) selectedText.getSprite()).setColor(Color.WHITE.getEngineColor());
    }

    /**
     * Select wanted user selection
     */
    private void selectCurrent() {
        ((Text) selectedText.getSprite()).setColor(Color.GOLD.getEngineColor());
    }

    @Override
    public @NotNull MapLevel getMapLevel() {
        return mapLevel;
    }

    @Override
    public int getNbOfGeneratedPacGums() {
        return 0;
    }

    @Override
    public @NotNull Collection<Ghost> getGeneratedGhosts() {
        return new ArrayList<>();
    }

    @Override
    public @NotNull Map<Entity, Coordinates<Double>> getSpawnPoints() {
        return new HashMap<>();
    }
}

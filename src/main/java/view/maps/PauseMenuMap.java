package view.maps;

import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelAlreadyExistException;
import fr.r1r0r0.deltaengine.exceptions.maplevel.MapLevelDoesNotExistException;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.HUDElement;
import fr.r1r0r0.deltaengine.model.elements.entity.Entity;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevelBuilder;
import fr.r1r0r0.deltaengine.tools.dialog.Dialog;
import main.Main;
import model.Game;
import model.builders.TextBuilder;
import model.elements.entities.ghosts.Ghost;
import model.loadables.LoadableMap;
import org.jetbrains.annotations.NotNull;
import view.images.Image;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class PauseMenuMap implements LoadableMap {

    private final String mapName = "PuckMan Reloaded - Pause";

    private final Game game;
    private MapLevel map;
    private HUDElement title, pauseText, livesText, scoreText, instructionsText, mainImg;

    public PauseMenuMap(Game game) {
        this.game = game;
        this.map = new MapLevelBuilder(mapName, 30, 15).build();
    }

    /**
     * Load the object
     *
     * @param engine DeltaEngine
     */
    @Override
    public void load(KernelEngine engine) {
        TextBuilder textBuilder = new TextBuilder("PuckMan\nReloaded");
        textBuilder.setCoordinates(1, 3)
                .setFont("Consolas")
                .setSize(100);
        title = textBuilder.build();

        textBuilder.setSize(50)
                .setCoordinates(25, 3)
                .setText("Pause");
        pauseText = textBuilder.build();

        textBuilder.setCoordinates(1, 12)
                .setText("Lives: " + game.getLives());
        livesText = textBuilder.build();

        textBuilder.setCoordinates(1, 14)
                .setText("Score: " + game.getScore());
        scoreText = textBuilder.build();

        String instructions = """
                ESC - Resume Game
                Q - Quit Game

                Game Controls :
                Z or ARROW UP - UP direction
                Q or ARROW LEFT - LEFT direction
                S or ARROW DOWN - DOWN direction
                D or ARROW RIGHT - RIGHT direction
                """;

        textBuilder.setCoordinates(20, 11)
                .setSize(25)
                .setText(instructions);
        instructionsText = textBuilder.build();


        mainImg = new HUDElement("main_img", new Coordinates<>(15.0, 6.0), Image.MAIN_IMG.getSprite(), new Dimension(1, 1));

        try {
            engine.addMap(map);
            engine.setCurrentMap(mapName);
        } catch (MapLevelDoesNotExistException | MapLevelAlreadyExistException e) {
            new Dialog(Main.APPLICATION_NAME, "Pause Menu loading error", e).show();
        }

        engine.addHUDElement(title);
        engine.addHUDElement(pauseText);
        engine.addHUDElement(livesText);
        engine.addHUDElement(scoreText);
        engine.addHUDElement(instructionsText);
        engine.addHUDElement(mainImg);
    }

    /**
     * Unload the object
     *
     * @param engine DeltaEngine
     */
    @Override
    public void unload(KernelEngine engine) {
        engine.removeHUDElement(title);
        engine.removeHUDElement(pauseText);
        engine.removeHUDElement(livesText);
        engine.removeHUDElement(scoreText);
        engine.removeHUDElement(instructionsText);
        engine.removeHUDElement(mainImg);

        engine.removeMap(mapName);
    }

    /**
     * Returns the associated Map of the Level
     *
     * @return MapLevel
     */
    @Override
    public @NotNull MapLevel getMapLevel() {
        return map;
    }

    /**
     * Returns number of generated PacGums
     *
     * @return integer of PacGums generated
     */
    @Override
    public int getNbOfGeneratedPacGums() {
        return 0;
    }

    /**
     * Returns collection of generated Ghosts
     *
     * @return Collection of Ghosts generated
     */
    @Override
    public @NotNull Collection<Ghost> getGeneratedGhosts() {
        return new ArrayList<>();
    }

    /**
     * Get spawn points of entities in the map
     *
     * @return Map of entity and their associated spawn points
     */
    @Override
    public @NotNull Map<Entity, Coordinates<Double>> getSpawnPoints() {
        return new HashMap<>();
    }
}

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
import fr.r1r0r0.deltaengine.model.sprites.Text;
import model.elements.entities.ghosts.Ghost;
import model.levels.fixed_levels.GameOverLevel;
import model.loadables.LoadableMap;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static config.levels.GameOverScreenConfigurator.*;

/**
 * The Game Over Map, contains nothing excepts Game Over text and some additional texts such as score
 */
public final class GameOverMap implements LoadableMap {

    private final GameOverLevel level;
    private final List<HUDElement> hudElements;
    private MapLevel map;

    /**
     * Default constructor
     * @param gameOverLevel the level of the map
     */
    public GameOverMap(GameOverLevel gameOverLevel) {
        this.level = gameOverLevel;
        this.hudElements = new LinkedList<>();
    }

    /**
     * Load the object
     *
     * @param engine DeltaEngine
     */
    @Override
    public void load(KernelEngine engine) {
        this.map = new MapLevelBuilder("Game Over", 50, 10).build();

        Text gameOverTitleText = new Text(CONF_GAME_OVER_TITLE);
        gameOverTitleText.setSize(CONF_GAME_OVER_TITLE_SIZE);
        gameOverTitleText.setColor(CONF_GAME_OVER_TITLE_COLOR.getEngineColor());
        hudElements.add(new HUDElement(
                "Game Over Title",
                CONF_GAME_OVER_TITLE_POSITION,
                gameOverTitleText,
                new Dimension(1, 1)
        ));

        Text scoreText = new Text(CONF_GAME_OVER_SCORE_PREFIX_TEXT + level.getGame().getScore());
        scoreText.setSize(CONF_GAME_OVER_SCORE_SIZE);
        scoreText.setColor(CONF_GAME_OVER_SCORE_COLOR.getEngineColor());
        hudElements.add(new HUDElement(
                "Game Over Score",
                CONF_GAME_OVER_SCORE_POSITION,
                scoreText,
                new Dimension(1, 1)
        ));

        Text commentText = new Text(CONF_GAME_OVER_COMMENT_TEXT);
        commentText.setSize(CONF_GAME_OVER_COMMENT_SIZE);
        commentText.setColor(CONF_GAME_OVER_COMMENT_COLOR.getEngineColor());
        hudElements.add(new HUDElement(
                "Game Over comments",
                CONF_GAME_OVER_COMMENT_POSITION,
                commentText,
                new Dimension(1, 1)
        ));

        for (HUDElement hudElement : hudElements)
            engine.addHUDElement(hudElement);

        try {
            engine.addMap(map);
            engine.setCurrentMap(map.getName());
        } catch (MapLevelAlreadyExistException ignored) {
        } catch (MapLevelDoesNotExistException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Unload the object
     *
     * @param engine DeltaEngine
     */
    @Override
    public void unload(KernelEngine engine) {
        for (HUDElement hudElement : hudElements)
            engine.removeHUDElement(hudElement);
        hudElements.clear();

        engine.removeMap(map.getName());
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

package view.hud;

import config.game.GlobalHUDConfiguration;
import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Dimension;
import fr.r1r0r0.deltaengine.model.elements.HUDElement;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.events.Event;
import fr.r1r0r0.deltaengine.model.sprites.Text;
import model.Game;
import model.actions.events.GlobalHUDCheckEnergizedModeChangedEvent;
import model.actions.events.GlobalHUDCheckLivesChangedEvent;
import model.actions.events.GlobalHUDCheckScoreChangedEvent;
import model.actions.triggers.GlobalHUDEnergizedModeChangedTrigger;
import model.loadables.Loadable;

import java.util.Collection;
import java.util.LinkedList;

import static config.game.GlobalHUDConfiguration.*;

/**
 * The Game HUD. Displays all required elements, such as lives, or score.
 */
public final class GlobalHUD implements Loadable {

    private final Game game;
    private final Coordinates<Double> position;
    private final double width, height;
    private final Collection<HUDElement> hudElements;
    private final Text livesText, scoreText, energizedModeText;
    private final Event checkLivesChanged, checkScoreChanged, checkEnergizedModeChanged;

    /**
     * Default constructor.
     *
     * @param game     The Game
     * @param position Position of the HUD
     * @param width    width of the hud
     * @param height   height of the hud
     */
    public GlobalHUD(Game game, Coordinates<Double> position, double width, double height) {
        this.game = game;
        this.position = position;
        this.width = width;
        this.height = height;
        hudElements = new LinkedList<>();

        livesText = new Text(CONF_GLOBAL_HUD_LIVES_PRE_TEXT + game.getLives());
        livesText.setColor(CONF_GLOBAL_HUD_LIVES_TEXT_COLOR.getEngineColor());
        livesText.setSize(GlobalHUDConfiguration.CONF_GLOBAL_HUD_LIVES_TEXT_SIZE);
        livesText.setFont(CONF_GLOBAL_HUD_LIVES_TEXT_FONT);
        hudElements.add(new HUDElement("lives text",
                coordinatesConvert(CONF_GLOBAL_HUD_LIVES_TEXT_POSITION),
                livesText,
                Dimension.DEFAULT_DIMENSION
        ));

        scoreText = new Text(CONF_GLOBAL_HUD_SCORE_PRE_TEXT + game.getScore());
        scoreText.setColor(CONF_GLOBAL_HUD_SCORE_TEXT_COLOR.getEngineColor());
        scoreText.setSize(GlobalHUDConfiguration.CONF_GLOBAL_HUD_SCORE_TEXT_SIZE);
        scoreText.setFont(CONF_GLOBAL_HUD_SCORE_TEXT_FONT);
        hudElements.add(new HUDElement("score text",
                coordinatesConvert(CONF_GLOBAL_HUD_SCORE_TEXT_POSITION),
                scoreText,
                Dimension.DEFAULT_DIMENSION
        ));

        energizedModeText = new Text("");
        energizedModeText.setColor(CONF_GLOBAL_HUD_ENERGIZED_MODE_TEXT_COLOR.getEngineColor());
        energizedModeText.setSize(GlobalHUDConfiguration.CONF_GLOBAL_HUD_ENERGIZED_MODE_TIMER_TEXT_SIZE);
        energizedModeText.setFont(CONF_GLOBAL_HUD_ENERGIZED_MODE_TIMER_TEXT_FONT);
        hudElements.add(new HUDElement("timer super pac gum text",
                coordinatesConvert(CONF_GLOBAL_HUD_ENERGIZED_MODE_TIMER_TEXT_POSITION),
                energizedModeText,
                Dimension.DEFAULT_DIMENSION
        ));

        checkLivesChanged = new GlobalHUDCheckLivesChangedEvent(game);
        checkScoreChanged = new GlobalHUDCheckScoreChangedEvent(game);
        checkEnergizedModeChanged = new GlobalHUDCheckEnergizedModeChangedEvent(game);

        checkLivesChanged.addTrigger(() -> setLives(game.getLives()));
        checkScoreChanged.addTrigger(() -> setScore(game.getScore()));
        checkEnergizedModeChanged.addTrigger(new GlobalHUDEnergizedModeChangedTrigger(game, this));
    }

    /**
     * Load the HUD
     *
     * @param engine DeltaEngine
     */
    @Override
    public void load(KernelEngine engine) {
        for (HUDElement e : hudElements)
            engine.addHUDElement(e);

        engine.addGlobalEvent(checkLivesChanged);
        engine.addGlobalEvent(checkScoreChanged);
        engine.addGlobalEvent(checkEnergizedModeChanged);
    }

    /**
     * Unload the HUD
     *
     * @param engine DeltaEngine
     */
    @Override
    public void unload(KernelEngine engine) {
        for (HUDElement e : hudElements)
            engine.removeHUDElement(e);

        engine.removeGlobalEvent(checkLivesChanged);
        engine.removeGlobalEvent(checkScoreChanged);
    }

    /**
     * Allowing to set lives counter of the HUD
     *
     * @param lives lives to show
     */
    public void setLives(int lives) {
        livesText.setText(CONF_GLOBAL_HUD_LIVES_PRE_TEXT + lives);
    }

    /**
     * Allowing to set score of the HUD
     *
     * @param score score to show
     */
    public void setScore(double score) {
        scoreText.setText(CONF_GLOBAL_HUD_SCORE_PRE_TEXT + score);
    }

    /**
     * Allows setting time remaining before ghosts aren't scared anymore.
     * Time on screen are seconds, but time given in parameter are milliseconds
     *
     * @param time time remaining to show
     */
    public void setEnergizedModeTimeRemaining(long time) {
        energizedModeText.setText(CONF_GLOBAL_HUD_ENERGIZED_MODE_TIMER_PRE_TEXT + (time) / 1_000);
    }

    /**
     * Hide energize timer when game isn't energized
     */
    public void hideEnergizedModeTimeRemaining() {
        energizedModeText.setText("");
    }

    /**
     * Convert coordinates to relative coordinates to fit with HUD
     *
     * @param coordinates Coordinates to convert
     * @return new Coordinates relative to the HUD
     */
    private Coordinates<Double> coordinatesConvert(Coordinates<Double> coordinates) {
        double x = coordinates.getX(),
                y = coordinates.getY();

        return new Coordinates<>(position.getX() + x, position.getY() + y + GlobalHUDConfiguration.CONF_GLOBAL_HUD_HEIGHT_MARGIN);
    }
}

package model.actions.triggers;

import config.game.GameConfiguration;
import fr.r1r0r0.deltaengine.model.events.Trigger;
import model.Game;
import view.hud.GlobalHUD;

/**
 * Game HUD trigger to update energize mode timer
 */
public class GlobalHUDEnergizedModeChangedTrigger implements Trigger {
    private long endTime, time;
    private final Game game;
    private final GlobalHUD hud;

    /**
     * Default constructor
     * @param game The Game
     * @param hud The Global Game HUD
     */
    public GlobalHUDEnergizedModeChangedTrigger(Game game, GlobalHUD hud) {
        this.hud = hud;
        this.game = game;
        this.time = 0;
    }

    @Override
    public void trigger() {
        if (!game.isInEnergizedMode()) {
            hud.hideEnergizedModeTimeRemaining();
            time = 0;
            return;
        }

        if (time <= 0)
            endTime = System.currentTimeMillis() + GameConfiguration.CONF_ENERGIZED_TIME;

        time = endTime - System.currentTimeMillis();

        hud.setEnergizedModeTimeRemaining(time);
    }
}

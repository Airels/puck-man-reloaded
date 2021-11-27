package model.actions.triggers;

import fr.r1r0r0.deltaengine.model.events.Trigger;
import model.Game;
import view.hud.GlobalHUD;

/**
 * Game HUD trigger to update energize mode timer
 */
public class GlobalHUDEnergizedModeChangedTrigger implements Trigger {

    private final Game game;
    private final GlobalHUD hud;

    /**
     * Default constructor
     *
     * @param game The Game
     * @param hud  The Global Game HUD
     */
    public GlobalHUDEnergizedModeChangedTrigger(Game game, GlobalHUD hud) {
        this.hud = hud;
        this.game = game;
    }

    @Override
    public void trigger() {
        if (!game.isInEnergizedMode())
            hud.hideEnergizedModeTimeRemaining();
        else
            hud.setEnergizedModeTimeRemaining(game.getEnergizeTimerEvent().remainingTime());
    }
}

package model.events;

import fr.r1r0r0.deltaengine.model.events.Trigger;
import model.Game;

public class LevelChangerTrigger implements Trigger {

    private boolean called;
    private final Game game;
    private final String str;

    public LevelChangerTrigger(Game game, String str) {
        this.game = game;
        called = false;
        this.str = str;
    }

    @Override
    public void trigger() {
        if (called) return;

        game.nextLevel();
        called = true;
    }
}

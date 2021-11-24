package model.ai.ghosts;

import model.elements.entities.ghosts.Ghost;

import java.lang.reflect.Constructor;

public enum GhostAIs {

    BLINKY_AI(new BlinkyAI()),
    PINKY_AI(new PinkyAI()),
    INKY_AI(new InkyAI()),
    CLYDE_AI(new ClydeAI());

    private GhostAI ghostAI;

    GhostAIs(GhostAI ai) {
        this.ghostAI = ai;
    }

    public GhostAI build() {
        return ghostAI.clone();
    }
}

package model.ai;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GhostAIsTest {

    @Test
    void build() {
        assertTrue(GhostAIs.BLINKY_AI.build() instanceof BlinkyAI);
        assertTrue(GhostAIs.PINKY_AI.build() instanceof PinkyAI);
        assertTrue(GhostAIs.INKY_AI.build() instanceof InkyAI);
        assertTrue(GhostAIs.CLYDE_AI.build() instanceof ClydeAI);
    }
}
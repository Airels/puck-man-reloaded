package model.ai;

/**
 * All AI's for ghosts behaviours
 */
public enum GhostAIs {

    BLINKY_AI(new BlinkyAI()),
    PINKY_AI(new PinkyAI()),
    INKY_AI(new InkyAI()),
    CLYDE_AI(new ClydeAI()),
    BLACKY_AI(new BlackyAI());

    private final GhostAI ghostAI;

    GhostAIs(GhostAI ai) {
        this.ghostAI = ai;
    }

    /**
     * Creates a new instance of the AI and returns it
     * @return new GhostAI instance
     */
    public GhostAI build() {
        return ghostAI.clone();
    }
}

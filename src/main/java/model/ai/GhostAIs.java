package model.ai;

/**
 * All AI's for ghosts behaviours
 */
public enum GhostAIs {

    BLINKY_AI(){
        @Override
        public GhostAI build() {
            return new BlinkyAI();
        }
    },
    PINKY_AI(){
        @Override
        public GhostAI build() {
            return new PinkyAI();
        }
    },
    INKY_AI(){
        @Override
        public GhostAI build() {
            return new InkyAI();
        }
    },
    CLYDE_AI(){
        @Override
        public GhostAI build() {
            return new ClydeAI();
        }
    },
    BLACKY_AI(){
        @Override
        public GhostAI build() {
            return new BlackyAI();
        }
    },
    SKEAZLY(){
        @Override
        public GhostAI build() {
            return new SkeazlyAI();
        }
    };

    GhostAIs () {}

    /**
     * Creates a new instance of the AI and returns it
     * @return new GhostAI instance
     */
    public abstract GhostAI build();

}

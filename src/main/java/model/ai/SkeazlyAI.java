package model.ai;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import model.elements.entities.ghosts.Ghost;

/**
 * A custom IA, his color is yellow.
 * He has many different states :
 *      - DUMB : go in the opposite way, with slowness
 *      - STANDARD : act like Clyde, with slowness
 *      - HUNTER : act like Blinky
 *      - INTELLECTUAL : act like Pinky
 *      - CHAOTIC : act "like Pinky", with speed boost
 */
public final class SkeazlyAI extends BasicGhostAI {

    /**
     * The king of brain used by the AI of Skeazly
     */
    private enum BrainMode {
        DUMB(null,2,1,0.33){
            @Override
            public Direction chooseDirection (Ghost ghost, MapLevel mapLevel, Direction direction) {
                return direction.getOpposite();
            }
            @Override
            public Coordinates<Integer> selectTarget (Ghost ghost, MapLevel mapLevel, Direction direction) {
                return Utils.findNextCross(ghost,mapLevel,Utils.getIntegerCoordinates(ghost),direction);
            }
        },
        STANDARD(new ClydeAI(),4,6,0.66){
        },
        HUNTER(new BlinkyAI(),3,10,1){
        },
        INTELLECTUAL(new PinkyAI(),3,20,1){
        },
        CHAOTIC(new PinkyAI(),1,3,1.5){
            @Override
            public Coordinates<Integer> selectTarget (Ghost ghost, MapLevel mapLevel, Direction direction) {
                return Utils.findNextCross(ghost,mapLevel,Utils.getIntegerCoordinates(ghost),direction);
            }
        };

        final BasicGhostAI brainAi;
        final int probabilityToAppear;
        final int nbUsage;
        final double speedMultiplier;

        /**
         * Constructor
         * @param brainAi a BasicGhostAI, if it's required
         * @param probabilityToAppear the probability to appear
         * @param nbUsage the number of usage
         * @param speedMultiplier a speed multiplier
         */
        BrainMode (BasicGhostAI brainAi, int probabilityToAppear, int nbUsage, double speedMultiplier) {
            this.brainAi = brainAi;
            this.probabilityToAppear = probabilityToAppear;
            this.nbUsage = nbUsage;
            this.speedMultiplier = speedMultiplier;
        }

        /**
         * Return the direction chosen
         * @param ghost a ghost
         * @param mapLevel a mapLevel
         * @param direction the previous direction
         * @return the direction chosen
         */
        public Direction chooseDirection (Ghost ghost, MapLevel mapLevel, Direction direction) {
            brainAi.direction = direction;
            brainAi.target = null;
            return brainAi.chooseDirection(ghost,mapLevel);
        }

        /**
         * Return the target selected
         * @param ghost a ghost
         * @param mapLevel a mapLevel
         * @param direction the current direction
         * @return the target selected
         */
        public Coordinates<Integer> selectTarget (Ghost ghost, MapLevel mapLevel, Direction direction) {
            brainAi.direction = direction;
            brainAi.target = null;
            return brainAi.selectTarget(ghost,mapLevel);
        }

        /**
         * Return the next brain, according to the current brain (this) and the
         * probability to appear of each other brain
         * @return the next brain
         */
        public BrainMode pickOtherBrain () {
            if (this == DUMB) return INTELLECTUAL;
            if (this == CHAOTIC) return HUNTER;
            int sumProbabilityToAppear = 0;
            for (BrainMode brainMode : BrainMode.values()) {
                if (brainMode != this) sumProbabilityToAppear += brainMode.probabilityToAppear;
            }
            int probaPick = RANDOM.nextInt(sumProbabilityToAppear);
            int proba = 0;
            for (BrainMode brainMode : BrainMode.values()) {
                if (brainMode == this) continue;
                if (probaPick <= proba) return brainMode;
                proba += brainMode.probabilityToAppear;
            }
            return BrainMode.STANDARD;
        }

    }

    private BrainMode brainMode;
    private Double defaultSpeed;
    private int nbUsage;

    /**
     * Constructor
     */
    public SkeazlyAI () {
        brainMode = BrainMode.STANDARD;
        defaultSpeed= null;
        nbUsage = 0;
    }

    /**
     * Change the brain of the entity, if there is no usage left
     */
    private void changeBrain () {
        if (nbUsage == brainMode.nbUsage) {
            brainMode = brainMode.pickOtherBrain();
            nbUsage = 0;
            System.out.println(brainMode);
        }
        nbUsage++;
    }

    @Override
    protected Direction chooseDirection (Ghost ghost, MapLevel mapLevel) {
        if (defaultSpeed == null) defaultSpeed = ghost.getSpeed();
        changeBrain();
        return brainMode.chooseDirection(ghost,mapLevel,direction);
    }

    @Override
    protected Coordinates<Integer> selectTarget (Ghost ghost, MapLevel mapLevel) {
        ghost.setSpeed(defaultSpeed * brainMode.speedMultiplier);
        return brainMode.selectTarget(ghost,mapLevel,direction);
    }

}

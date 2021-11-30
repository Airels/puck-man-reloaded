package model.ai;

import fr.r1r0r0.deltaengine.model.Coordinates;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.maplevel.MapLevel;
import model.elements.entities.ghosts.Ghost;

import java.util.Arrays;

public class SkeazlyAI extends BasicGhostAI {

    private enum BrainMode {
        DUMB(null,1,2,0.33){
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

        BrainMode (BasicGhostAI brainAi, int probabilityToAppear, int nbUsage, double speedMultiplier) {
            this.brainAi = brainAi;
            this.probabilityToAppear = probabilityToAppear;
            this.nbUsage = nbUsage;
            this.speedMultiplier = speedMultiplier;
        }

        public Direction chooseDirection (Ghost ghost, MapLevel mapLevel, Direction direction) {
            brainAi.direction = direction;
            brainAi.target = null;
            return brainAi.chooseDirection(ghost,mapLevel);
        }

        public Coordinates<Integer> selectTarget (Ghost ghost, MapLevel mapLevel, Direction direction) {
            brainAi.direction = direction;
            brainAi.target = null;
            return brainAi.selectTarget(ghost,mapLevel);
        }

        public BrainMode pickOtherBrain () {
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

    public SkeazlyAI () {
        brainMode = BrainMode.STANDARD;
        defaultSpeed= null;
        nbUsage = 0;
    }

    private void changeBrain () {
        if (nbUsage == brainMode.nbUsage) {
            brainMode = brainMode.pickOtherBrain();
            nbUsage = 0;
            System.out.println(brainMode);
        }
    }

    @Override
    protected Direction chooseDirection (Ghost ghost, MapLevel mapLevel) {
        if (defaultSpeed == null) defaultSpeed = ghost.getSpeed();
        changeBrain();
        nbUsage++;
        return brainMode.chooseDirection(ghost,mapLevel,direction);
    }

    @Override
    protected Coordinates<Integer> selectTarget (Ghost ghost, MapLevel mapLevel) {
        ghost.setSpeed(defaultSpeed * brainMode.speedMultiplier);
        return brainMode.selectTarget(ghost,mapLevel,direction);
    }

}

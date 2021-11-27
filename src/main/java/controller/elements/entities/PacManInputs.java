package controller.elements.entities;

import config.entities.PacManConfiguration;
import fr.r1r0r0.deltaengine.exceptions.InputKeyStackingError;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.engines.utils.Key;
import fr.r1r0r0.deltaengine.model.events.Event;
import fr.r1r0r0.deltaengine.model.events.InputEvent;
import fr.r1r0r0.deltaengine.model.events.Trigger;
import model.elements.entities.PacMan;
import model.loadables.LoadableInput;

public class PacManInputs implements LoadableInput {

    private PacMan pacMan;
    private Direction desiredDirection;
    private Event inputEvent;

    public PacManInputs(PacMan pacMan) {
        this.pacMan = pacMan;
    }

    @Override
    public void load(KernelEngine engine) {
        try {
            engine.setInput(Key.Z, getPressEvent(Direction.UP));
            engine.setInput(Key.Q, getPressEvent(Direction.LEFT));
            engine.setInput(Key.S, getPressEvent(Direction.DOWN));
            engine.setInput(Key.D, getPressEvent(Direction.RIGHT));

            engine.setInput(Key.ARROW_UP, getPressEvent(Direction.UP));
            engine.setInput(Key.ARROW_DOWN, getPressEvent(Direction.DOWN));
            engine.setInput(Key.ARROW_LEFT, getPressEvent(Direction.LEFT));
            engine.setInput(Key.ARROW_RIGHT, getPressEvent(Direction.RIGHT));
        } catch (InputKeyStackingError e) {
            e.printStackTrace();
            System.exit(1);
        }

        desiredDirection = Direction.IDLE;

        inputEvent = new Event() {
            @Override
            public void checkEvent() {
                if (desiredDirection == Direction.IDLE) return;

                if (!engine.canGoToNextCell(pacMan, desiredDirection)) {
                    if (pacMan.getDirection().getOpposite() != desiredDirection)
                        return;
                }

                runTriggers();
            }
        };

        inputEvent.addTrigger(() -> {
            pacMan.setDirection(desiredDirection);
            pacMan.setSpeed(PacManConfiguration.CONF_PACMAN_SPEED);
            desiredDirection = Direction.IDLE;
        });

        engine.addGlobalEvent(inputEvent);
    }

    @Override
    public void unload(KernelEngine engine) {
        engine.clearInput(Key.Z);
        engine.clearInput(Key.Q);
        engine.clearInput(Key.S);
        engine.clearInput(Key.D);
        engine.clearInput(Key.ARROW_UP);
        engine.clearInput(Key.ARROW_LEFT);
        engine.clearInput(Key.ARROW_DOWN);
        engine.clearInput(Key.ARROW_RIGHT);

        engine.removeGlobalEvent(inputEvent);
    }

    private InputEvent getPressEvent(Direction direction) {
        return new InputEvent(getPressTrigger(direction), getReleaseTrigger());
    }

    private Trigger getPressTrigger(Direction direction) {
        return () -> desiredDirection = direction;
    }

    private Trigger getReleaseTrigger() {
        // return () -> desiredDirection = Direction.IDLE;
        return () -> {};
    }
}

package controller.elements.entities;

import config.pacman.PacManConfiguration;
import fr.r1r0r0.deltaengine.exceptions.InputKeyStackingError;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.engines.utils.Key;
import fr.r1r0r0.deltaengine.model.events.InputEvent;
import fr.r1r0r0.deltaengine.model.events.Trigger;
import model.elements.entities.PacMan;
import model.loadables.LoadableInput;

public class PacManInputs implements LoadableInput {

    private PacMan pacMan;
    private InputEvent upEvent, downEvent, leftEvent, rightEvent;
    private Trigger upTrigger, downTrigger, leftTrigger, rightTrigger;

    public PacManInputs(PacMan pacMan) {
        this.pacMan = pacMan;
    }

    @Override
    public void load(KernelEngine engine) {
        try {
            engine.setInput(Key.Z, getUpEvent(engine));
            engine.setInput(Key.Q, getLeftEvent(engine));
            engine.setInput(Key.S, getDownEvent(engine));
            engine.setInput(Key.D, getRightEvent(engine));

            engine.setInput(Key.ARROW_UP, getUpEvent(engine));
            engine.setInput(Key.ARROW_DOWN, getDownEvent(engine));
            engine.setInput(Key.ARROW_LEFT, getLeftEvent(engine));
            engine.setInput(Key.ARROW_RIGHT, getRightEvent(engine));
        } catch (InputKeyStackingError e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void unload(KernelEngine engine) {
        engine.clearInput(Key.Z);
        engine.clearInput(Key.Q);
        engine.clearInput(Key.S);
        engine.clearInput(Key.D);
    }

    private InputEvent getUpEvent(KernelEngine kernelEngine) {
        if (upEvent == null) {
            upEvent = new InputEvent(getUpTrigger(kernelEngine), null);
        }

        return upEvent;
    }

    private InputEvent getDownEvent(KernelEngine kernelEngine) {
        if (downEvent == null) {
            downEvent = new InputEvent(getDownTrigger(kernelEngine), null);
        }

        return downEvent;
    }

    private InputEvent getLeftEvent(KernelEngine kernelEngine) {
        if (leftEvent == null) {
            leftEvent = new InputEvent(getLeftTrigger(kernelEngine), null);
        }

        return leftEvent;
    }

    private InputEvent getRightEvent(KernelEngine kernelEngine) {
        if (rightEvent == null) {
            rightEvent = new InputEvent(getRightTrigger(kernelEngine), null);
        }

        return rightEvent;
    }

    private Trigger getUpTrigger(KernelEngine kernelEngine) {
        if (upTrigger == null) {
            upTrigger = () -> {
                System.out.println("kernelEngine.isAvailableDirection(pacMan, Direction.UP) = " + kernelEngine.isAvailableDirection(pacMan, Direction.UP));
                if (!kernelEngine.isAvailableDirection(pacMan, Direction.UP)) return; // TODO

                pacMan.setDirection(Direction.UP);
                pacMan.setSpeed(PacManConfiguration.CONF_PACMAN_SPEED);
            };
        }

        return upTrigger;
    }

    private Trigger getDownTrigger(KernelEngine kernelEngine) {
        if (downTrigger == null) {
            downTrigger = () -> {
                if (!kernelEngine.isAvailableDirection(pacMan, Direction.DOWN)) return;

                pacMan.setDirection(Direction.DOWN);
                pacMan.setSpeed(PacManConfiguration.CONF_PACMAN_SPEED);
            };
        }

        return downTrigger;
    }

    private Trigger getLeftTrigger(KernelEngine kernelEngine) {
        if (leftTrigger == null) {
            leftTrigger = () -> {
                if (!kernelEngine.isAvailableDirection(pacMan, Direction.LEFT)) return;

                pacMan.setDirection(Direction.LEFT);
                pacMan.setSpeed(PacManConfiguration.CONF_PACMAN_SPEED);
            };
        }

        return leftTrigger;
    }

    private Trigger getRightTrigger(KernelEngine kernelEngine) {
        if (rightTrigger == null) {
            rightTrigger = () -> {
                if (!kernelEngine.isAvailableDirection(pacMan, Direction.RIGHT)) return;

                pacMan.setDirection(Direction.RIGHT);
                pacMan.setSpeed(PacManConfiguration.CONF_PACMAN_SPEED);
            };
        }

        return rightTrigger;
    }
}

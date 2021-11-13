package controller.elements.entities;

import fr.r1r0r0.deltaengine.exceptions.InputKeyStackingError;
import fr.r1r0r0.deltaengine.model.Direction;
import fr.r1r0r0.deltaengine.model.engines.KernelEngine;
import fr.r1r0r0.deltaengine.model.engines.utils.Key;
import fr.r1r0r0.deltaengine.model.events.InputEvent;
import fr.r1r0r0.deltaengine.model.events.Trigger;
import model.elements.entities.PacMan;
import model.loadables.LoadableInput;

public class PacManInputs implements LoadableInput {

    public final static double DEFAULT_SPEED = 5;

    private PacMan pacMan;
    private InputEvent upEvent, downEvent, leftEvent, rightEvent;
    private Trigger upTrigger, downTrigger, leftTrigger, rightTrigger;

    public PacManInputs(PacMan pacMan) {
        this.pacMan = pacMan;
    }

    @Override
    public void load(KernelEngine engine) {
        try {
            engine.setInput(Key.Z, getUpEvent());
            engine.setInput(Key.Q, getLeftEvent());
            engine.setInput(Key.S, getDownEvent());
            engine.setInput(Key.D, getRightEvent());

            engine.setInput(Key.ARROW_UP, getUpEvent());
            engine.setInput(Key.ARROW_DOWN, getDownEvent());
            engine.setInput(Key.ARROW_LEFT, getLeftEvent());
            engine.setInput(Key.ARROW_RIGHT, getRightEvent());
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

    private InputEvent getUpEvent() {
        if (upEvent == null) {
            upEvent = new InputEvent(getUpTrigger(), null);
        }

        return upEvent;
    }

    private InputEvent getDownEvent() {
        if (downEvent == null) {
            downEvent = new InputEvent(getDownTrigger(), null);
        }

        return downEvent;
    }

    private InputEvent getLeftEvent() {
        if (leftEvent == null) {
            leftEvent = new InputEvent(getLeftTrigger(), null);
        }

        return leftEvent;
    }

    private InputEvent getRightEvent() {
        if (rightEvent == null) {
            rightEvent = new InputEvent(getRightTrigger(), null);
        }

        return rightEvent;
    }

    private Trigger getUpTrigger() {
        if (upTrigger == null) {
            upTrigger = () -> {
                pacMan.setDirection(Direction.UP);
                pacMan.setSpeed(DEFAULT_SPEED);
            };
        }

        return upTrigger;
    }

    private Trigger getDownTrigger() {
        if (downTrigger == null) {
            downTrigger = () -> {
                pacMan.setDirection(Direction.DOWN);
                pacMan.setSpeed(DEFAULT_SPEED);
            };
        }

        return downTrigger;
    }

    private Trigger getLeftTrigger() {
        if (leftTrigger == null) {
            leftTrigger = () -> {
                pacMan.setDirection(Direction.LEFT);
                pacMan.setSpeed(DEFAULT_SPEED);
            };
        }

        return leftTrigger;
    }

    private Trigger getRightTrigger() {
        if (rightTrigger == null) {
            rightTrigger = () -> {
                pacMan.setDirection(Direction.RIGHT);
                pacMan.setSpeed(DEFAULT_SPEED);
            };
        }

        return rightTrigger;
    }
}

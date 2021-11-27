package model.events;

import fr.r1r0r0.deltaengine.model.events.Event;

/**
 * A timed event, who will trigger actions when times up.
 */
public final class TimedEvent extends Event {

    private long start, pauseTime, time;
    private boolean triggered;
    private boolean paused;

    /**
     * Default constructor. Trigger event when specified time is over.
     * @param time long time in milliseconds
     */
    public TimedEvent(long time) {
        this.time = time;
        this.triggered = false;
        this.paused = false;
    }

    @Override
    public void checkEvent() {
        if (start == 0) {
            start = System.currentTimeMillis();
            return;
        }

        if (paused) return;

        if (System.currentTimeMillis() - start >= time && !triggered) {
            triggered = true;
            runTriggers();
        }
    }

    /**
     * Resume the timer where it has stopped
     */
    public void unpause() {
        if (!paused) return;

        this.time += System.currentTimeMillis() - pauseTime;
        this.pauseTime = 0;
        this.paused = false;
    }

    /**
     * Pause the timer
     */
    public void pause() {
        if (paused) return;

        this.paused = true;
        this.pauseTime = System.currentTimeMillis();
    }

    /**
     * Returns remaining milliseconds before times up
     * @return long milliseconds before times up
     */
    public long remainingTime() {
        long remaining = -1 * (System.currentTimeMillis() - time - start);
        return (remaining > 0) ? remaining : 0;
    }
}

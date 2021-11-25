package model.events;

import fr.r1r0r0.deltaengine.model.events.Event;

/**
 * A timed event, who will trigger actions when times up.
 */
public final class TimedEvent extends Event {

    private final long time;
    private long start;
    private boolean triggered = false;

    /**
     * Default constructor. Trigger event when specified time is over.
     * @param time long time in milliseconds
     */
    public TimedEvent(long time) {
        this.time = time;
    }

    @Override
    public void checkEvent() {
        if (start == 0) {
            start = System.currentTimeMillis();
            return;
        }

        if (System.currentTimeMillis() - start >= time && !triggered) {
            triggered = true;
            runTriggers();
        }
    }
}

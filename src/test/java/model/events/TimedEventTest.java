package model.events;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

class TimedEventTest {

    @Test
    void timedEvent() throws InterruptedException {
        AtomicBoolean value = new AtomicBoolean(false);
        TimedEvent event = new TimedEvent(500);
        event.addTrigger(() -> value.set(true));
        event.checkEvent(); // Once to start timer (same as including it in engine)

        Thread.sleep(250);
        event.checkEvent();
        Assertions.assertFalse(value.get());

        Thread.sleep(250);
        event.checkEvent();
        Assertions.assertTrue(value.get());
    }
}
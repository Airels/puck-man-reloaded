package model.events;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

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

    @Test
    void pause() throws InterruptedException {
        TimedEvent event = new TimedEvent(500);
        AtomicLong start = new AtomicLong();
        AtomicLong time = new AtomicLong(0);
        event.addTrigger(() -> time.set(System.currentTimeMillis()));

        start.set(System.currentTimeMillis());
        event.checkEvent();
        Thread.sleep(250);
        event.checkEvent();
        event.pause();
        Assertions.assertEquals(0, time.get());

        Thread.sleep(500);
        event.checkEvent();
        Assertions.assertEquals(0, time.get());

        event.unpause();
        Thread.sleep(250);
        event.checkEvent();

        Assertions.assertNotEquals(0, time.get());
        Assertions.assertTrue(time.get() - start.get() > 500);
        Assertions.assertTrue(time.get() - start.get() < 1100);
    }

    @Test
    void remaining() throws InterruptedException {
        TimedEvent event = new TimedEvent(500);
        event.checkEvent();

        Thread.sleep(200);
        Assertions.assertTrue(event.remainingTime() < 300);

        Thread.sleep(100);
        Assertions.assertTrue(event.remainingTime() < 200);

        Thread.sleep(500);
        Assertions.assertEquals(event.remainingTime(), 0);
    }
}
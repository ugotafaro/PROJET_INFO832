package timer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PeriodicTimerTest {

    private PeriodicTimer periodicTimer;
    @BeforeEach
    void setUp() {
    }

    // ----CONSTRUCTOR----

    @Test // TEST 1
    void testPeriodicTimerConstructorWithPeriod() {
        int period = 1000;
        PeriodicTimer timer = new PeriodicTimer(period);
        assertEquals(period, timer.getPeriod());
    }

    @Test // TEST 2
    void testPerdiodicTimerConstructorWithNegativePeriod() {
        int period = -1000;
        PeriodicTimer timer = new PeriodicTimer(period);
        assertEquals(period, timer.getPeriod());
    }

    @Test // TEST 3
    void testPeriodicTimerConstructorWithNullPeriod() {
        int period = 0;
        PeriodicTimer periodicTimer = new PeriodicTimer(period);
        assertEquals(period, periodicTimer.getPeriod());
        assertEquals(period, periodicTimer.next());
    }
}
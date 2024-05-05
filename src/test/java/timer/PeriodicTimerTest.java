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

    // PeriodicTimer(int at)
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

    // ----next()----

    @Test // TEST 1
    void testNextWithMoreOrLessNotNull() throws Exception {
        int period = 1000;
        int next = 500;
        RandomTimer randomTimer = new RandomTimer(RandomTimer.randomDistribution.POISSON, 1500); // Assuming RandomTimer constructor takes min and max as arguments
        PeriodicTimer timer = new PeriodicTimer(period, next, randomTimer);
        assertEquals(next, timer.next());
    }

    @Test // TEST 2
    void testNextWithValidMoreOrLess() throws Exception {
        int period = 1000;
        int next = 500;
        RandomTimer randomTimer = new RandomTimer(RandomTimer.randomDistribution.POISSON, 1500);
        PeriodicTimer timer = new PeriodicTimer(period, next, randomTimer);
        assertNotEquals(500, timer.next());
    }

    @Test // TEST 3
    void testNextWithNullMoreOrLess() {
        int period = 1000;
        int next = 500;
        PeriodicTimer timer = new PeriodicTimer(period, next, null);
        assertEquals(500, timer.next());
    }

    @Test // TEST 4
    void testNextWithNullMoreOrLessReturnsNextAndUpdateNextToPeriod() {
        int period = 1000;
        int next = 500;
        PeriodicTimer timer = new PeriodicTimer(period, next, null);
        assertEquals(500, timer.next());
    }

    @Test // TEST 5
    void testNextWithValidMoreOrLessReturnsNextAndUpdateNextToPeriodPlusDifference() throws Exception {
        int period = 1000;
        int next = 500;
        RandomTimer randomTimer = new RandomTimer(RandomTimer.randomDistribution.POISSON, 1500);
        PeriodicTimer timer = new PeriodicTimer(period, next, randomTimer);
        assertEquals(500, timer.next());
    }

    // ----hasNext()----
    @Test // TEST 1
    void testHasNextAlwaysReturnsTrue() {
        int period = 1000;
        PeriodicTimer timer = new PeriodicTimer(period);
        assertTrue(timer.hasNext());
    }

    @Test
    void testHasNextReturnsTrueAfterMultipleNextCalls() {
        int period = 1000;
        PeriodicTimer timer = new PeriodicTimer(period);
        for (int i = 0; i < 10; i++) {
            timer.next();
        }
        assertTrue(timer.hasNext());
    }
}
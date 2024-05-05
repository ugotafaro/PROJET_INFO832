package timer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class MergedTimerTest {
    private Timer timer1;
    private Timer timer2;

    private MergedTimer mergedTimer;

    @BeforeEach
    void setUp() {
    }

    // ----CONSTRUCTOR----

    @Test // TEST 1
    void testMergedTimerWithSameTimer() {
        timer1 = new PeriodicTimer(5);
        mergedTimer = new MergedTimer(timer1, timer1);

        for (int i = 0; i < 10; i++) {
            assertTrue(mergedTimer.hasNext());
            assertEquals(10, mergedTimer.next());
        }

        assertFalse(mergedTimer.hasNext());
    }

    @Test // TEST 2
    void testMergedTimerWithDifferentTimers() {
        TreeSet<Integer> dates = new TreeSet<>();
        dates.add(1);
        dates.add(5);
        dates.add(10);

        timer1 = new PeriodicTimer(1);
        timer2 = new DateTimer(dates);

        mergedTimer = new MergedTimer(timer1, timer2);

        int expectedNext = 2;
        while (mergedTimer.hasNext()) {
            assertEquals(expectedNext, mergedTimer.next());
            expectedNext = (expectedNext == 2) ? 6 : 11;
        }
    }

    // ----HASNEXT----

    @Test // TEST 1
    void testHasNextWithNotEmptyTimers() {
        TreeSet<Integer> dates = new TreeSet<>();
        dates.add(1);
        dates.add(5);
        dates.add(10);

        timer1 = new PeriodicTimer(1);
        timer2 = new DateTimer(dates);

        mergedTimer = new MergedTimer(timer1, timer2);

        assertTrue(mergedTimer.hasNext());
    }

    @Test // Test 2
    void testHasNextWithOneEmptyTimer() {
        TreeSet<Integer> dates = new TreeSet<>();
        dates.add(1);

        timer1 = new PeriodicTimer(1);
        timer2 = new DateTimer(dates);

        mergedTimer = new MergedTimer(timer1, timer2);

        assertTrue(mergedTimer.hasNext());
        assertEquals(2, mergedTimer.next());
        assertFalse(mergedTimer.hasNext());
    }

    @Test // Test 3
    void testHasNextWithBothEmptyTimers() {
        TreeSet<Integer> dates = new TreeSet<>();
        dates.add(1);

        timer1 = new DateTimer(dates);
        timer2 = new DateTimer(dates);

        mergedTimer = new MergedTimer(timer1, timer2);

        assertTrue(mergedTimer.hasNext());
        assertEquals(2, mergedTimer.next());
        assertFalse(mergedTimer.hasNext());
    }

    // ----NEXT----

    @Test // TEST 1
    void testNextWithNotEmptyTimers() {
        TreeSet<Integer> dates = new TreeSet<>();
        dates.add(1);
        dates.add(5);
        dates.add(10);

        timer1 = new PeriodicTimer(1);
        timer2 = new DateTimer(dates);

        mergedTimer = new MergedTimer(timer1, timer2);

        int expectedNext = 2; // 1 (from PeriodicTimer) + 1 (from DateTimer)
        while (mergedTimer.hasNext()) {
            assertEquals(expectedNext, mergedTimer.next());
            expectedNext = (expectedNext == 2) ? 6 : 11; // Update expectedNext based on the dates in DateTimer
        }
    }

    @Test // TEST 2
    void testNextWithBothEmptyTimers() {
        TreeSet<Integer> dates = new TreeSet<>();
        dates.add(1);

        timer1 = new DateTimer(dates);
        timer2 = new DateTimer(dates);

        mergedTimer = new MergedTimer(timer1, timer2);

        assertEquals(2, mergedTimer.next());
        assertNull(mergedTimer.next());
    }

    @Test // Test 3
    void testNextWithOneEmptyTimer() {
        TreeSet<Integer> dates = new TreeSet<>();
        dates.add(1);

        timer1 = new PeriodicTimer(1);
        timer2 = new DateTimer(dates);

        mergedTimer = new MergedTimer(timer1, timer2);

        assertEquals(2, mergedTimer.next());
        assertNull(mergedTimer.next());
        // test
    }
}
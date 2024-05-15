package timer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class TimeBoundedTimerTest {
    public Timer timer;

    @BeforeEach
    public void setUp() {
        timer = new Timer() {
            private int[] values = {5, 10, 15};
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < values.length;
            }

            @Override
            public Integer next() {
                return values[index++];
            }
        };
    }

    @Test
    public void testConstructorWithTimerAndTimes() {

        int startTime = 10;
        int stopTime = 30;
        TimeBoundedTimer tbt = new TimeBoundedTimer(timer, startTime, stopTime);

        assertNotNull(tbt);
        assertNotNull(tbt.next());
        assertTrue(tbt.hasNext());
    }

    @Test
    public void testConstructorWithTimerAndStartTime() {

        TimeBoundedTimer tbt = new TimeBoundedTimer(timer, 5);

        assertNotNull(tbt);
        assertNotNull(tbt.next());
        assertTrue(tbt.hasNext());
        assertNotNull(tbt.next());
//        assertEquals(Integer.MAX_VALUE, tbt.getStopTime);
    }

    @Test
    public void testInit() {
        Timer simulatedTimer = new Timer() {
            private int count = 0;
            private int[] times = {2, 3, 5, 7, 11};

            @Override
            public boolean hasNext() {
                return count < times.length;
            }

            @Override
            public Integer next() {
                if (count < times.length) {
                    return times[count++];
                } else {
                    return null;
                }
            }

            @Override
            public void remove() {

            }
        };

        int startTime = 5;
        int stopTime = 15;

        TimeBoundedTimer timeBoundedTimer = new TimeBoundedTimer(simulatedTimer, startTime, stopTime);


        //assertTrue(timeBoundedTimer.getNext() >= startTime);


        //assertTrue(timeBoundedTimer.getNext() < stopTime);


        //assertTrue(timeBoundedTimer.isHasNext());
    }

    @Test
    public void testHasNextBeforeCallingNext() {

        int startTime = 3;
        int stopTime = 15;

        TimeBoundedTimer timeBoundedTimer = new TimeBoundedTimer(timer, startTime, stopTime);

        if (timeBoundedTimer.next()<stopTime) {
            assertTrue(timeBoundedTimer.hasNext());
        } else {
            assertFalse(timeBoundedTimer.hasNext());
        }
    }

    @Test
    public void testHasNextAfterCallingNext() {

        int startTime = 3;
        int stopTime = 15;

        TimeBoundedTimer timeBoundedTimer = new TimeBoundedTimer(timer, startTime, stopTime);

        while (timeBoundedTimer.hasNext()) {
            timeBoundedTimer.next();
        }

        assertFalse(timeBoundedTimer.hasNext());
    }

    @Test
    public void testHasNextAfterTimeExceedsStopTime(){

        TimeBoundedTimer tbt = new TimeBoundedTimer(timer, 5, 20);

        assertEquals(5, tbt.next());
        assertEquals(10, tbt.next());
        assertTrue(tbt.hasNext());
        assertEquals(null, tbt.next());
        assertFalse(tbt.hasNext());
    }

    @Test
    public void testNextOnFirstCall() {

        int startTime = 5;
        int stopTime = 15;

        TimeBoundedTimer timeBoundedTimer = new TimeBoundedTimer(timer, startTime, stopTime);

        Integer firstNext = timeBoundedTimer.next();

        assertEquals(5, firstNext);
        assertTrue(firstNext >= startTime);
        assertTrue(firstNext < stopTime);
    }

    @Test
    public void testNextOnSubsequentCalls() {
        Timer simulatedTimer = new Timer() {
            private int count = 0;
            private int[] times = {2, 3, 5, 7, 11};

            @Override
            public boolean hasNext() {
                return count < times.length;
            }

            @Override
            public Integer next() {
                if (count < times.length) {
                    return times[count++];
                } else {
                    return null;
                }
            }

            @Override
            public void remove() {

            }


        };

        int startTime = 3;
        int stopTime = 15;

        TimeBoundedTimer timeBoundedTimer = new TimeBoundedTimer(simulatedTimer, startTime, stopTime);

        while (timeBoundedTimer.hasNext()) {
            Integer value = timeBoundedTimer.next();

            if (value != null) {
                assertTrue(value >= startTime);
                assertTrue(value < stopTime);
            } else {
                assertNull(value);
            }
        }
    }

}
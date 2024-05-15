package timer;

import org.junit.jupiter.api.Test;

import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

class TimeBoundedTimerTest {


    @Test
    public void testConstructorWithTimerAndTimes() {

        Timer simulatedTimer = new Timer() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                return null;
            }

            @Override
            public void remove() {

            }
        };

        int startTime = 5;
        int stopTime = 15;

        TimeBoundedTimer timeBoundedTimer = new TimeBoundedTimer(simulatedTimer, startTime, stopTime);

        //assertSame(simulatedTimer, timeBoundedTimer.getTimer2bound());
        //assertEquals(startTime, timeBoundedTimer.getStartTime());
        //assertEquals(stopTime, timeBoundedTimer.getStopTime());

        assertTrue(timeBoundedTimer.hasNext());
    }

    @Test
    public void testConstructorWithTimerAndStartTime() {
        Timer simulatedTimer = new Timer() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                return null;
            }

            @Override
            public void remove() {

            }
        };

        int startTime = 5;

        TimeBoundedTimer timeBoundedTimer = new TimeBoundedTimer(simulatedTimer, startTime);

        //assertSame(simulatedTimer, timeBoundedTimer.getTimer2bound());
        //assertEquals(startTime, timeBoundedTimer.getStartTime());
        //assertEquals(Integer.MAX_VALUE, timeBoundedTimer.getStopTime());

        assertTrue(timeBoundedTimer.hasNext());
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

        assertTrue(timeBoundedTimer.hasNext());
    }

    @Test
    public void testHasNextAfterCallingNext() {
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
            timeBoundedTimer.next();
        }

        assertFalse(timeBoundedTimer.hasNext());
    }

    @Test
    public void testNextOnFirstCall() {
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

        Integer firstNext = timeBoundedTimer.next();

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

    @Test
    public void testHasNextAfterTimeExceedsStopTime() {
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
            timeBoundedTimer.next();
        }

        assertFalse(timeBoundedTimer.hasNext());
    }

}
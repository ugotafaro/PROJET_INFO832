package discreteBehaviorSimulator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class ClockTest {

    private Clock clock;
    private TestObserver observer;
    @BeforeEach
    void setUp() {
        clock = Clock.getInstance();
        clock.setVirtual(true);
        clock.setNextJump(10);
        observer = new TestObserver();
        clock.addObserver(observer);

    }


    @Test
    void getInstance() {
        Clock firstInstance = Clock.getInstance();
        assertNotNull(firstInstance, "First instance should not be null");

        Clock secondInstance = Clock.getInstance();
        assertNotNull(secondInstance, "Second instance should not be null");

        assertSame(firstInstance, secondInstance, "Both instances should be the same (singleton pattern)");

        Clock thirdInstance = Clock.getInstance();
        assertNotNull(thirdInstance, "Third instance should not be null");

        // Check if the third instance is also the same as the first and second instances
        assertSame(firstInstance, thirdInstance, "All instances should be the same (singleton pattern)");



    }

    @Test
    void addObserver() {

        // Create a new observer
        ClockObserver observer = new ClockObserver() {
            @Override
            public void clockChange(int time) {

            }

            @Override
            public void nextClockChange(int nextJump) {
                // Do nothing
            }
        };

        clock.addObserver(observer);

//        assertTrue(clock.getObservers().contains(observer), "The observer should be added to the observers list");
    }

    @Test
    void removeObserver() {

        ClockObserver observer = new ClockObserver() {
            @Override
            public void clockChange(int time) {

            }

            @Override
            public void nextClockChange(int nextJump) {
                // Do nothing
            }
        };

        clock.addObserver(observer);

        clock.removeObserver(observer);

        // assertFalse(clock.getObservers().contains(observer), "The observer should be removed from the observers list");


        Clock clock = Clock.getInstance();

        ClockObserver fakeObserver = new ClockObserver() {
            @Override
            public void nextClockChange(int nextJump) {
                // Implémentation factice
            }

            @Override
            public void clockChange(int time) {
                // Implémentation factice
            }
        };

        // int initialObserverCount = clock.getObservers().size();

        clock.removeObserver(fakeObserver);

        // int finalObserverCount = clock.getObservers().size();

        //assertEquals(initialObserverCount, finalObserverCount, "La taille de la liste des observateurs devrait rester la même après avoir tenté de supprimer un observer non présent.");

    }

    @Test
    void setVirtual() {

        clock.setVirtual(false);


        assertFalse(clock.isVirtual(), "The virtual attribute should be set to false");
    }

    @Test
    void isVirtual() {
        assertTrue(clock.isVirtual(), "The virtual attribute should be true by default");

        clock.setVirtual(false);

        assertFalse(clock.isVirtual(), "The virtual attribute should be false");
    }

    @Test
    void setNextJump() {

        clock.setNextJump(5);

        //assertEquals(5, clock.getNextJump(), "The nextJump attribute should be set to 5");


    }

    @Test
    void increase() throws Exception {
        assertEquals(0, clock.getTime(), "Initial time should be 0");

        clock.increase(10);

        assertEquals(10, clock.getTime(), "Time should be increased by 10");

        assertEquals(10, observer.getObservedTime(), "Observer should receive updated time of 10");

        clock.setNextJump(15);

        clock.increase(15);

        assertEquals(25, clock.getTime(), "Time should be increased by 15 more, making it 25");

        assertEquals(25, observer.getObservedTime(), "Observer should receive updated time of 25");

        Clock clock = Clock.getInstance();
        clock.setNextJump(10); // Set nextJump to 10

        try {
            clock.increase(5); // Try to increase time by 5
            fail("Should throw an exception when trying to increase time by a value different from nextJump");
        } catch (Exception e) {
            // Exception should be thrown
        }



    }

//    @Test
//    void getTime() throws Exception {
//
//
//        assertEquals(0, clock.getTime(), "Time should be 10");
//
//
//
//        clock.setVirtual(false);
//
//        long currentTime = System.currentTimeMillis();
//
//        assertEquals(currentTime, clock.getTime(), "Time should be the same as the current time");
//
//    }

    @Test
    void lockReadAccess() {
    }

    @Test
    void unlockReadAccess() {
    }

    @Test
    void lockWriteAccess() {
    }

    @Test
    void unlockWriteAccess() {
    }

    @Test
    void testToString() {
    }

    private static class TestObserver implements ClockObserver {
        private int observedTime;

        @Override
        public void clockChange(int time) {
            observedTime = time;
        }

        @Override
        public void nextClockChange(int nextJump) {
            // Not needed for this test case
        }

        public int getObservedTime() {
            return observedTime;
        }
    }
}
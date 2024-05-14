package discreteBehaviorSimulator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.Observer;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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

        // Add the observer to the clock
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
        //test si la valeur de virtual est bien retourné
        // Check if the virtual attribute is true by default
        assertTrue(clock.isVirtual(), "The virtual attribute should be true by default");

        // Set the virtual attribute to false
        clock.setVirtual(false);

        // Check if the virtual attribute is false
        assertFalse(clock.isVirtual(), "The virtual attribute should be false");
    }

    @Test
    void setNextJump() {
        //test si la valeur de nextJump est bien retourné
        // Set the nextJump attribute to 5
        clock.setNextJump(5);

        // Check if the nextJump attribute was set correctly
        //assertEquals(5, clock.getNextJump(), "The nextJump attribute should be set to 5");


    }

    @Test
    void increase() throws Exception {
        // Verify the initial time is 0
        assertEquals(0, clock.getTime(), "Initial time should be 0");

        // Increase the time by 10, which is the next jump
        clock.increase(10);

        // Verify the time has increased correctly
        assertEquals(10, clock.getTime(), "Time should be increased by 10");

        // Verify that the observer's `clockChange` method was called with the correct time
        assertEquals(10, observer.getObservedTime(), "Observer should receive updated time of 10");

        // Now set a new next jump
        clock.setNextJump(15);

        // Increase the time by 15, which is the next jump
        clock.increase(15);

        // Verify the time has increased correctly again
        assertEquals(25, clock.getTime(), "Time should be increased by 15 more, making it 25");

        // Verify that the observer's `clockChange` method was called with the correct time again
        assertEquals(25, observer.getObservedTime(), "Observer should receive updated time of 25");

        // Test to increase the time by a value that is not equal to the next jump
        Clock clock = Clock.getInstance();
        clock.setNextJump(10); // Set nextJump to 10
        assertThrows(Exception.class, () -> {
            clock.increase(5); // Trying to increase time by 5, but nextJump is 10
        });




    }

    @Test
    void getTime() throws Exception {


        assertEquals(0, clock.getTime(), "Time should be 10");



        clock.setVirtual(false);

        long currentTime = System.currentTimeMillis();

        assertEquals(currentTime, clock.getTime(), "Time should be the same as the current time");

    }

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
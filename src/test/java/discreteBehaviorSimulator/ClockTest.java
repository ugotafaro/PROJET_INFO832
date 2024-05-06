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
        clock.setVirtual(true); // Ensure the clock is in virtual mode for the test
        clock.setNextJump(10); // Set next jump to 10
        observer = new TestObserver();
        clock.addObserver(observer);

    }


    @Test
    void getInstance() {
        // First call to getInstance() should return a non-null instance of Clock
        Clock firstInstance = Clock.getInstance();
        assertNotNull(firstInstance, "First instance should not be null");

        // Second call to getInstance() should return the same instance as the first call
        Clock secondInstance = Clock.getInstance();
        assertNotNull(secondInstance, "Second instance should not be null");

        // Check if the two instances are the same (singleton pattern)
        assertSame(firstInstance, secondInstance, "Both instances should be the same (singleton pattern)");

        // Third call to getInstance() should also return the same instance
        Clock thirdInstance = Clock.getInstance();
        assertNotNull(thirdInstance, "Third instance should not be null");

        // Check if the third instance is also the same as the first and second instances
        assertSame(firstInstance, thirdInstance, "All instances should be the same (singleton pattern)");



    }

    @Test
    void addObserver() {
        // Test : Description: on test la fonction avec un observer et on regarde si il a bien était ajouté, Valeur entrée : addObserver(ClockObserver o), valeur attendue : this.observers(ClockObserver o)=True l’observer est bien dans les observers

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

        // Add the observer to the clock
        clock.addObserver(observer);

        // Check if the observer was added successfully
        assertTrue(clock.getObservers().contains(observer), "The observer should be added to the observers list");
    }

    @Test
    void removeObserver() {
        //test to remove an observer
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

        // Add the observer to the clock
        clock.addObserver(observer);

        // Remove the observer from the clock
        clock.removeObserver(observer);

        // Check if the observer was removed successfully
        assertFalse(clock.getObservers().contains(observer), "The observer should be removed from the observers list");

        //Test to remove an observer that is not in the list
        // Obtenir l'instance de Clock
        Clock clock = Clock.getInstance();

        // Observer factice qui implémente ClockObserver mais n'est pas dans la liste des observateurs
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

        // Obtenir la taille initiale de la liste des observateurs
        int initialObserverCount = clock.getObservers().size();

        // Appeler removeObserver() avec l'observer factice
        clock.removeObserver(fakeObserver);

        // Obtenir la taille finale de la liste des observateurs
        int finalObserverCount = clock.getObservers().size();

        // Vérifier que la taille de la liste des observateurs n'a pas changé
        assertEquals(initialObserverCount, finalObserverCount, "La taille de la liste des observateurs devrait rester la même après avoir tenté de supprimer un observer non présent.");

    }

    @Test
    void setVirtual() {
        //utiliser la méthode avec un boolean en entré et regarder si virtual est bien ce boolean
        // Set the virtual attribute to false
        clock.setVirtual(false);

        // Check if the virtual attribute was set correctly
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
        assertEquals(5, clock.getNextJump(), "The nextJump attribute should be set to 5");


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
        // test si virtual=true valeur attendue = temps actuel this.time


        // Check if the time is 10
        assertEquals(0, clock.getTime(), "Time should be 10");


        //test si virtual=false valeur attendue = nouveau time
        //new Date().getTime

        // Set the virtual attribute to false
        clock.setVirtual(false);

        // Get the current time in milliseconds
        long currentTime = System.currentTimeMillis();

        // check if the time is the same as the current time
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
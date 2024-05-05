package discreteBehaviorSimulator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.junit.jupiter.api.Assertions.*;
class ClockTest {

    @BeforeEach
    void setUp() {

    }


    @Test
    void getInstance() {
        //generate test for getInstance
        Clock clock = Clock.getInstance();
        assertNotNull(clock);
        // verify if clock is an instance of Clock
        assertTrue(clock instanceof Clock);
        //verify if time is 0
        assertEquals(0, clock.getTime());
        //verify if nextJump is 0
        assertEquals(0, clock.getTime());
        //verify virtual is true
        assertTrue(clock.isVirtual());


    }

    @Test
    void addObserver() {
        //implement test for addObserver
        Clock clock = Clock.getInstance();
        //create a new ClockObserver
        ClockObserver observer = new ClockObserver() {
            @Override
            public void clockChange(int time) {

            }

            @Override
            public void nextClockChange(int nextJump) {
                // TODO Auto-generated method stub
            }
        };

    }

    @Test
    void removeObserver() {
        //generate test for removeObserver
        Clock clock = Clock.getInstance();
        //create a new ClockObserver
        ClockObserver observer = new ClockObserver() {
            @Override
            public void clockChange(int time) {

            }

            @Override
            public void nextClockChange(int nextJump) {
                // TODO Auto-generated method stub
            }
        };


    }

    @Test
    void setVirtual() {
        //generate test for setVirtual
        Clock clock = Clock.getInstance();
        //verify if virtual is true
        assertTrue(clock.isVirtual());
        //set virtual to false
        clock.setVirtual(false);
        //verify if virtual is false
        assertFalse(clock.isVirtual());
    }

    @Test
    void isVirtual() {
    }

    @Test
    void setNextJump() {
    }

    @Test
    void increase() {
    }

    @Test
    void getTime() {
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
}
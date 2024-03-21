package timer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class OneShotTimerTest {

    private OneShotTimer timerPositive;
    private OneShotTimer timerNegative;
    private OneShotTimer timer0;
    private OneShotTimer timer;

    @BeforeEach
    void setUp() {
        timer = new OneShotTimer(100);
    }

    @Test // TEST 1
    void testConstructorAtPositive(){
        timerPositive = new OneShotTimer(10);
        assertTrue(timerPositive instanceof OneShotTimer);
    }

    @Test // TEST 2
    void testConstructorAtNegative(){
        timerNegative = new OneShotTimer(-10);
        assertTrue(timerNegative instanceof OneShotTimer);
    }

    @Test // TEST 3
    void testConstructorAt0(){
        timer0 = new OneShotTimer(0);
        assertTrue(timer0 instanceof OneShotTimer);
    }

    @Test // TEST 1
    void hasNextShouldReturnTrueWhenTimerHasNext() {
        assertTrue(timer.hasNext());
    }

    @Test // TEST 2
    void hasNextShouldReturnFalseWhenTimerDoesNotHaveNext() {
        timer.next();
        assertFalse(timer.hasNext());
    }

    @Test // TEST 1
    void nextShouldReturnAtWhenTimerHasNext() {
        Integer at = 100;
        assertEquals(at, timer.next());
    }

    @Test // TEST 2
    void nextShouldReturnNullWhenTimerDoesNotHaveNext() {
        timer.next();
        assertNull(timer.next());
    }
}
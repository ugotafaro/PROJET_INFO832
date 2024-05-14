package timer;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DateTimerTest {

    @Test
    void testConstructorWithTreeSet() {
        TreeSet<Integer> dates = new TreeSet<>(Arrays.asList(1, 2, 3));
        DateTimer dt = new DateTimer(dates);
        assertEquals(Arrays.asList(1, 1, 1), dt.lapsTimes);
        assertEquals(1, dt.lapsTimes.get(0));

    }

    @Test
    void testConstructorWithVector() {
        Vector<Integer> lapsTimes = new Vector<>(Arrays.asList(1, 2, 3));
        DateTimer dt = new DateTimer(lapsTimes);
        assertEquals(Arrays.asList(1, 2, 3), dt.lapsTimes);
        assertEquals(1, dt.lapsTimes.get(0));
    }

    @Test
    void testHasNextWhenIteratorHasElements() {
        TreeSet<Integer> dates = new TreeSet<>(Arrays.asList(1, 2, 3));
        DateTimer dt = new DateTimer(dates);
        assertTrue(dt.hasNext());
    }

    @Test
    void testHasNextWhenIteratorHasNoElements() {
        TreeSet<Integer> dates = new TreeSet<>(Arrays.asList(1, 2, 3));
        DateTimer dt = new DateTimer(dates);
        dt.next();
        dt.next();
        dt.next();
        assertFalse(dt.hasNext());
    }

    @Test
    void testNextWhenIteratorHasElements() {
        TreeSet<Integer> dates = new TreeSet<>(Arrays.asList(1, 2, 3));
        DateTimer dt = new DateTimer(dates);
        assertEquals(1, dt.next());
        assertEquals(1, dt.next());
        assertEquals(1, dt.next());
    }

    @Test
    void testNextWhenIteratorHasNoElements() {
        TreeSet<Integer> dates = new TreeSet<>(Arrays.asList(1, 2, 3));
        DateTimer dt = new DateTimer(dates);
        dt.next();
        dt.next();
        dt.next();
        assertThrows(NoSuchElementException.class, dt::next);
    }
}
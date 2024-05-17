package discrete_behavior_simulator;

import action.DiscreteAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timer.DateTimer;

import java.util.Arrays;
import java.util.TreeSet;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class DiscreteActionSimulatorTest {
    private DiscreteActionSimulator simulator;
    private String word;
    private Vector vector;
    private TreeSet<Integer> dates;
    private DateTimer dt;
    private DiscreteAction action1;
    private DiscreteAction action2;
    private DiscreteAction action3;
    @BeforeEach
    void setUp() {
        simulator = new DiscreteActionSimulator();
        word = "Bonjour";
        vector = new Vector();
        dates = new TreeSet<>(Arrays.asList(1, 2, 3));
        dt = new DateTimer(dates);
        action1 = new DiscreteAction(word, "toUpperCase", dt);
        action2 = new DiscreteAction(word, "toUpperCase", dt);
        action3 = new DiscreteAction(word, "toUpperCase", dt);

        simulator.addAction(action1);
        simulator.addAction(action2);
        simulator.addAction(action3);


    }

//    @Test
//    void testRunWithMultipleActions() {
//        simulator.addAction(action1);
//        simulator.addAction(action2);
//        simulator.addAction(action3);
//        simulator.start();
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        simulator.stop();
//        assertFalse(simulator.getRunning());
//    }

    @Test
    void testRunWithNoActions()  {
        DiscreteActionSimulator simulator2 = new DiscreteActionSimulator();
        assertThrows(Exception.class, () -> {
            simulator2.start();
        }, "No action to run");
    }

    @Test
    void testRunWithNegativeOrZeroNbLoop() {
        simulator.setNbLoop(-1);
        simulator.start();
        assertTrue(simulator.getRunning());
        simulator.stop();
        assertFalse(simulator.getRunning());
    }

    @Test // TEST 1
    void testStart() {
        simulator.start();
        assertTrue(simulator.getRunning());
    }

    @Test // TEST 2
    void testStartWhenRunningIsTrue() {
        simulator.start();
        assertThrows(IllegalThreadStateException.class, simulator::start);
    }
    @Test // TEST 1
    void testStopAfterStart() {
        simulator.start();
        simulator.stop();
        assertFalse(simulator.getRunning());
    }
}
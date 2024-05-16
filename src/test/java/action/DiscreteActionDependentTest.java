package action;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timer.DateTimer;
import timer.Timer;

import java.lang.reflect.Method;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class DiscreteActionDependentTest {

    private DiscreteActionDependent actionDependent;
    private String method = "toUpperCase";
    private final String word = "Bonjour";
    private final Vector vector1 = new Vector();
    private final Vector vector2 = new Vector();
    private Timer dt1;
    private Timer dt2;

    @BeforeEach
    void setUp() {
        vector1.add(10);
        dt1 = new DateTimer(vector1);
        dt2 = new DateTimer(vector2);
        vector2.add(20);
        this.actionDependent = new DiscreteActionDependent(word, method, dt1);
    }

    @Test // TEST 1
    void testConstructorBaseAction() {
        assertTrue(actionDependent.baseAction instanceof DiscreteAction);
    }

    @Test // TEST 2
    void testConstructorCurrentAction() {
        assertEquals(actionDependent.baseAction, actionDependent.currentAction);
    }

    @Test // TEST 1
    void addDependence() {
        actionDependent.addDependence(word, method, dt2);
        assertEquals(actionDependent.depedentActions.last(), new DiscreteAction(word, method, dt2));
        assertEquals(1, actionDependent.depedentActions.size());
    }

    @Test // TEST 1 & 2
    void testNextMethodAffirmation() {

        actionDependent.addDependence(word, method, dt2);
        actionDependent.nextMethod();
        DiscreteAction ds = new DiscreteAction(word, method, dt2);
        System.out.println(ds);
        System.out.println(actionDependent.currentAction);
        assertEquals(ds, actionDependent.currentAction);

        actionDependent.nextMethod();
        assertEquals(actionDependent.baseAction, actionDependent.currentAction);
    }

    @Test // TEST 3
    void testNextMethodLimit1() {
        actionDependent.depedentActions.clear();
        actionDependent.depedentActions.add(new DiscreteAction("h", method, dt2));
//        actionDependent.depedentActions.first().setLapsTime(10);
        DiscreteAction currentActionBefore = actionDependent.currentAction;
        actionDependent.nextMethod();
        assertEquals(currentActionBefore, actionDependent.currentAction);
    }

    @Test // TEST 4
    void testNextMethodLimit() {
        actionDependent.depedentActions.clear();
        actionDependent.nextMethod();
        assertEquals(actionDependent.baseAction, actionDependent.currentAction);
    }

    @Test // TEST 1
    void testSpendTimePositive() {
        actionDependent.depedentActions.add(new DiscreteAction("h", method, dt2));
        actionDependent.depedentActions.first().setLapsTime(10);
        actionDependent.addDependence(word, method, dt2);
        int initialTime = actionDependent.depedentActions.first().getCurrentLapsTime();

        actionDependent.spendTime(5);

        int expectedTime = initialTime - 5;
        int actualTime = actionDependent.depedentActions.first().getCurrentLapsTime();
        assertEquals(expectedTime, actualTime);
    }

    @Test // TEST 2
    void testSpendTimeZero() {
        actionDependent.depedentActions.add(new DiscreteAction("h", method, dt2));
        actionDependent.depedentActions.first().setLapsTime(10);
        actionDependent.addDependence(word, method, dt2);
        int initialTime = actionDependent.depedentActions.first().getCurrentLapsTime();

        actionDependent.spendTime(0);

        int actualTime = actionDependent.depedentActions.first().getCurrentLapsTime();
        assertEquals(initialTime, actualTime);
    }

    @Test // TEST 1
    void testHasNextBaseActionHasNext() {
        actionDependent.addDependence(word, method, dt2);
        boolean result = actionDependent.hasNext();
        assertTrue(result);
    }

    @Test // TEST 2
    void testHasNextBaseActionHasNoNextButNotEmpty() {
        actionDependent.addDependence(word, method, dt2);
        actionDependent.nextMethod();
        boolean result = actionDependent.hasNext();
        assertTrue(result);
    }

    @Test // TEST 3
    void testHasNextBaseActionHasNoNextAndEmpty() {
        boolean result = actionDependent.hasNext();
        assertFalse(result);
    }

    @Test // TEST 4
    void testHasNextWithLargeDependentActions() {
        for (int i = 0; i < 10000; i++) {
            actionDependent.addDependence(word, method, dt2);
        }
        boolean result = actionDependent.hasNext();
        assertTrue(result);
    }
}
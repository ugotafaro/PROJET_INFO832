package action;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import timer.RandomTimer;
import timer.Timer;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class DiscreteActionOnOffDependentTest {


    @Test
    public void testConstructorWithTimers() throws Exception {
        Timer timerOn = new RandomTimer(RandomTimer.randomDistribution.POISSON, 5);
        Timer timerOff = new RandomTimer(RandomTimer.randomDistribution.POISSON, 10);
        String word = "word";
        DiscreteActionOnOffDependent action = new DiscreteActionOnOffDependent(word, "toUpperCase", timerOn, "toLowerCase", timerOff);

        assertNotNull(action);

        assertEquals("toLowerCase", action.getMethod().getName());
    }

    @Test
    public void testConstructorWithDates() throws Exception {
        TreeSet<Integer> datesOn = new TreeSet<>();
        TreeSet<Integer> datesOff = new TreeSet<>();
        String word = "word";
        datesOn.add(10);
        datesOff.add(5);

        DiscreteActionOnOffDependent action = new DiscreteActionOnOffDependent(word, "toUpperCase", datesOn, "toLowerCase", datesOff);

        assertNotNull(action);

        assertEquals("toLowerCase", action.getMethod().getName());
    }




        @Test
        public void testNextActionSwitchesToOffAction() throws Exception {
            Timer timerOn = new RandomTimer(RandomTimer.randomDistribution.POISSON, 5);
            Timer timerOff = new RandomTimer(RandomTimer.randomDistribution.POISSON, 10);
            String word =  "word";

            DiscreteActionOnOffDependent action = new DiscreteActionOnOffDependent(word, "toUpperCase", timerOn, "toLowerCase", timerOff);

            action.nextAction();
            action.nextAction();

            assertEquals("toLowerCase", action.getMethod().getName());
        }

        @Test
        public void testNextActionSwitchesToOnAction() throws Exception {
            Timer timerOn = new RandomTimer(RandomTimer.randomDistribution.POISSON, 5);
            Timer timerOff = new RandomTimer(RandomTimer.randomDistribution.POISSON, 10);
            String word = "word";

            DiscreteActionOnOffDependent action = new DiscreteActionOnOffDependent(word, "toUpperCase", timerOn, "toLowerCase", timerOff);

            action.nextAction();


            assertEquals("toUpperCase", action.getMethod().getName());
        }

        @Test
        public void testNextActionSpendTimeOnOffAction() throws Exception {
            Timer timerOn = new RandomTimer(RandomTimer.randomDistribution.POISSON, 5);
            Timer timerOff = new RandomTimer(RandomTimer.randomDistribution.POISSON, 10);
            String word = "word";
            DiscreteActionOnOffDependent action = new DiscreteActionOnOffDependent(word, "toUpperCase", timerOn, "toLowerCase", timerOff);

            action.nextAction();
            action.nextAction();

            assertEquals("toLowerCase", action.getMethod().getName());


            action.spendTime(5);

            assertEquals("toLowerCase", action.getMethod().getName());
        }

    @Test
    public void testSpendTimeOnOffAction() throws Exception {
        Timer timerOn = new RandomTimer(RandomTimer.randomDistribution.POISSON, 5);
        Timer timerOff = new RandomTimer(RandomTimer.randomDistribution.POISSON, 10);
        Light light = new Light();

        DiscreteActionOnOffDependent action = new DiscreteActionOnOffDependent(light, "turnOn", timerOn, "turnOff", timerOff);

        action.nextAction();

        int currentLapsTime = action.getCurrentLapsTime();
        action.spendTime(5);

        assertNotEquals(currentLapsTime, action.getCurrentLapsTime());
    }

    @Test
    public void testSpendTimeOnOnAction() throws Exception {
        Timer timerOn = new RandomTimer(RandomTimer.randomDistribution.POISSON, 5);
        Timer timerOff = new RandomTimer(RandomTimer.randomDistribution.POISSON, 10);
        String word = "word";

        DiscreteActionOnOffDependent action = new DiscreteActionOnOffDependent(word, "toUpperCase", timerOn, "toLowerCase", timerOff);

        action.nextAction();
        action.nextAction();
        int currentLapsTime = action.getCurrentLapsTime();
        action.spendTime(7);

        assertNotEquals(currentLapsTime, action.getCurrentLapsTime());
    }

    @Test
    public void testSpendTimeBeforeNextAction() throws Exception {
        Timer timerOn = new RandomTimer(RandomTimer.randomDistribution.POISSON, 5);
        Timer timerOff = new RandomTimer(RandomTimer.randomDistribution.POISSON, 10);
        String word = "word";

        DiscreteActionOnOffDependent action = new DiscreteActionOnOffDependent(word, "toUpperCase", timerOn, "toLowerCase", timerOff);

        action.nextAction();

        int currentLapsTime = action.getCurrentLapsTime();
        action.spendTime(7);

        assertNotEquals(currentLapsTime, action.getCurrentLapsTime());


    }

    // Classe représentant une lumière
    private static class Light {
        public void turnOn() {
            System.out.println("Light is on");
        }

        public void turnOff() {
            System.out.println("Light is off");
        }
    }

}






//    @Test
//    public void testConstructorWithTimerOnLessThanTimerOff() {
//    }
//
//    @Test
//    public void testConstructorWithDatesOnAndDatesOff() {
//
//    }
//
//    @Test
//    public void testGetMethod() {
//
//    }
//
//    @Test
//    public void testCompareTo() {
//
//    }
//
//
//    @Test
//    public void testGetCurrentLapsTime() {
//
//    }
//
//
//    @Test
//    public void testNext() {
//
//    }
//
//    @Test
//    public void testHasNext() {
//
//    }


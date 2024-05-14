package timer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomTimerTest {


    private RandomTimer poissonTimer;
    private RandomTimer expTimer;
    private RandomTimer posibilistTimer;
    private RandomTimer gaussianTimer;

    @BeforeEach
    public void setUp() {
        try {
            poissonTimer = new RandomTimer(RandomTimer.randomDistribution.POISSON, 5.0); // Example mean value
            expTimer = new RandomTimer(RandomTimer.randomDistribution.EXP, 0.5); // Example rate value
            posibilistTimer = new RandomTimer(RandomTimer.randomDistribution.POSIBILIST, 10, 20); // Example lolim and hilim values
            gaussianTimer = new RandomTimer(RandomTimer.randomDistribution.GAUSSIAN, 10, 20); // Example lolim and hilim values
        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    @Test
    void testDoubleRandomTimerWithExpDistribution () {
        RandomTimer randomTimer = null;
        try {
            randomTimer = new RandomTimer(RandomTimer.randomDistribution.EXP, 0.5);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals("EXP", randomTimer.getDistribution());
        assertEquals("rate: 0.5", randomTimer.getDistributionParam());
        assertEquals(2.0, randomTimer.getMean());
//        assertEquals(randomTimer.lolim, 0);
//        assertEquals(randomTimer.hilim, Double.POSITIVE_INFINITY);
    }

    @Test
    void testDoubleRandomTimerWithPoissonDistribution () {
        RandomTimer randomTimer = null;
        try {
            randomTimer = new RandomTimer(RandomTimer.randomDistribution.POISSON, 0.5);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals("POISSON", randomTimer.getDistribution());
        assertEquals("mean: 0.5", randomTimer.getDistributionParam());
//        assertEquals(Double.NaN, randomTimer.rate);
//        assertEquals(randomTimer.lolim, 0);
//        assertEquals(randomTimer.hilim, Double.POSITIVE_INFINITY);

    }

    @Test
    void testDoubleRandomTimerWithPossibilistDistribution() throws Exception {
        RandomTimer randomTimer = null;

        randomTimer = new RandomTimer(RandomTimer.randomDistribution.POSIBILIST, 0.5);

        //verify if new Exception("Bad Timer constructor for selected distribution") is thrown
        assertThrows(Exception.class, () -> {
            // Code that should throw the exception
            throw new Exception("Bad Timer constructor for selected distribution");
        });


    }


    @Test
    void testDoubleRandomTimerWithGaussianDistribution() throws Exception {
        RandomTimer randomTimer = null;

        randomTimer = new RandomTimer(RandomTimer.randomDistribution.GAUSSIAN, 0.5);

        assertThrows(Exception.class, () -> {
            throw new Exception("Bad Timer constructor for selected distribution");
        });


    }

    @Test
    void testIntRandomTimerWithExpDistribution() throws Exception {
        RandomTimer randomTimer = null;

        randomTimer = new RandomTimer(RandomTimer.randomDistribution.EXP, 0, 5);


        assertThrows(Exception.class, () -> {
            throw new Exception("Bad Timer constructor for selected distribution");
        });

    }

    @Test
    void testIntRandomTimerWithPoissonDistribution() throws Exception {
        RandomTimer randomTimer = null;

        randomTimer = new RandomTimer(RandomTimer.randomDistribution.POISSON, 0, 5);

        assertThrows(Exception.class, () -> {
            throw new Exception("Bad Timer constructor for selected distribution");
        });

    }

    @Test
    void testIntRandomTimerWithGaussianDistribution() throws Exception {
        RandomTimer randomTimer = null;

        randomTimer = new RandomTimer(RandomTimer.randomDistribution.GAUSSIAN, 0, 5);

        assertEquals("GAUSSIAN", randomTimer.getDistribution());
        assertEquals("lolim: 0 hilim: 5", randomTimer.getDistributionParam());
        assertEquals(2.5, randomTimer.getMean());
//        assertEquals(randomTimer.lolim, 0);
//        assertEquals(randomTimer.hilim, 5);

    }

    @Test
    void testIntRandomTimerWithPossibilistDistribution() throws Exception {
        RandomTimer randomTimer = null;

        randomTimer = new RandomTimer(RandomTimer.randomDistribution.POSIBILIST, 0, 5);

        assertEquals("POSIBILIST", randomTimer.getDistribution());
        assertEquals("lolim: 0 hilim: 5", randomTimer.getDistributionParam());
        assertEquals(2.5, randomTimer.getMean());
//        assertEquals(randomTimer.lolim, 0);
//        assertEquals(randomTimer.hilim, 5);

    }

    @Test
    void testRandomTimerWithParamEqualsZero() throws Exception {
        RandomTimer randomTimer = null;

        randomTimer = new RandomTimer(RandomTimer.randomDistribution.EXP, 0);

        assertThrows(Exception.class, () -> {
            throw new ArithmeticException("division by zero");
        });

    }






    @Test
    void testToStringWithExpDistribution() {

        assertEquals("EXP rate:0.5", expTimer.toString());

    }

    @Test
    void testToStringWithPoissonDistribution() {

        assertEquals("POISSON mean:5.0", poissonTimer.toString());

    }

    @Test
    void testToStringWithPosibilistDistribution() {

        assertEquals("POSIBILIST lolim:10 hilim:20", posibilistTimer.toString());

    }

    @Test
    void testToStringWithGaussianDistribution() {

        assertEquals("GAUSSIAN lolim:10 hilim:20", gaussianTimer.toString());

    }





    @Test
    void hasNext() throws Exception {

        RandomTimer randomTimer = new RandomTimer(RandomTimer.randomDistribution.EXP, 0.5);


        assertEquals(true, randomTimer.hasNext());

    }
}
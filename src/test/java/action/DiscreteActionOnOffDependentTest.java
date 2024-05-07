package action;

import org.junit.jupiter.api.Test;
import timer.Timer;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class DiscreteActionOnOffDependentTest {


    @Test
    public void testConstructorWithTimerOnGreaterThanTimerOff() {
        Timer simulatedTimerOn = new Timer() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                return null;
            }
        };

        Timer simulatedTimerOff = new Timer() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                return null;
            }
        };

        DiscreteActionOnOffDependent discreteActionOnOffDependent = new DiscreteActionOnOffDependent(new Object(), "on", simulatedTimerOn, "off", simulatedTimerOff);

        assertThrows(IllegalArgumentException.class, () -> {
            new DiscreteActionOnOffDependent(new Object(), "on", simulatedTimerOn, "off", simulatedTimerOff);
        });
    }

    @Test
    public void testConstructorWithTimerOnLessThanTimerOff() {
        Timer simulatedTimerOn = new Timer() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                return null;
            }
        };

        Timer simulatedTimerOff = new Timer() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                return null;
            }
        };

        Object object = new Object();
        String on = "on";
        String off = "off";

        DiscreteActionOnOffDependent discreteActionOnOffDependent = new DiscreteActionOnOffDependent(object, on, simulatedTimerOn, off, simulatedTimerOff);

        assertSame(object, discreteActionOnOffDependent.getObject());
        assertEquals(on, discreteActionOnOffDependent.onAction.getMethod().getName());
        assertEquals(off, discreteActionOnOffDependent.offAction.getMethod().getName());
    }

    @Test
    public void testConstructorWithDatesOnAndDatesOff() {
        Timer simulatedTimerOn = new Timer() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                return null;
            }
        };

        Timer simulatedTimerOff = new Timer() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                return null;
            }
        };

        Object object = new Object();
        String on = "on";
        String off = "off";
        TreeSet<Integer> datesOn = new TreeSet<>();
        TreeSet<Integer> datesOff = new TreeSet<>();

        DiscreteActionOnOffDependent discreteActionOnOffDependent = new DiscreteActionOnOffDependent(object, on, datesOn, off, datesOff);

        assertSame(object, discreteActionOnOffDependent.getObject());
        assertEquals(on, discreteActionOnOffDependent.onAction.getMethod().getName());
        assertEquals(off, discreteActionOnOffDependent.offAction.getMethod().getName());
    }

    @Test
    public void testNextActionFromOnActionToOffAction() {
        // Créer un Timer simulé pour passer au constructeur.
        Timer simulatedTimerOn = new Timer() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                return null;
            }
        };

        Timer simulatedTimerOff = new Timer() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                return null;
            }
        };

        Object object = new Object();
        String on = "on";
        String off = "off";
        TreeSet<Integer> datesOn = new TreeSet<>();
        TreeSet<Integer> datesOff = new TreeSet<>();

        DiscreteActionOnOffDependent discreteActionOnOffDependent = new DiscreteActionOnOffDependent(object, on, datesOn, off, datesOff);

        discreteActionOnOffDependent.nextAction();

        assertSame(discreteActionOnOffDependent.offAction, discreteActionOnOffDependent.currentAction);
    }

    @Test
    public void testSpendTime() {
        Timer simulatedTimerOn = new Timer() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                return null;
            }
        };

        Timer simulatedTimerOff = new Timer() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                return null;
            }
        };

        Object object = new Object();
        String on = "on";
        String off = "off";
        TreeSet<Integer> datesOn = new TreeSet<>();
        TreeSet<Integer> datesOff = new TreeSet<>();

        DiscreteActionOnOffDependent discreteActionOnOffDependent = new DiscreteActionOnOffDependent(object, on, datesOn, off, datesOff);

        discreteActionOnOffDependent.spendTime(5);

        assertEquals(5, discreteActionOnOffDependent.currentAction.getCurrentLapsTime());
    }

    @Test
    public void testGetMethod() {
        Timer simulatedTimerOn = new Timer() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                return null;
            }
        };

        Timer simulatedTimerOff = new Timer() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                return null;
            }
        };

        Object object = new Object();
        String on = "on";
        String off = "off";
        TreeSet<Integer> datesOn = new TreeSet<>();
        TreeSet<Integer> datesOff = new TreeSet<>();

        DiscreteActionOnOffDependent discreteActionOnOffDependent = new DiscreteActionOnOffDependent(object, on, datesOn, off, datesOff);

        discreteActionOnOffDependent.getMethod();

        assertSame(discreteActionOnOffDependent.onAction.getMethod(), discreteActionOnOffDependent.getMethod());
    }

    @Test
    public void testCompareTo() {
        Timer simulatedTimerOn = new Timer() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                return null;
            }
        };

        Timer simulatedTimerOff = new Timer() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                return null;
            }
        };

        Object object = new Object();
        String on = "on";
        String off = "off";
        TreeSet<Integer> datesOn = new TreeSet<>();
        TreeSet<Integer> datesOff = new TreeSet<>();

        DiscreteActionOnOffDependent discreteActionOnOffDependent = new DiscreteActionOnOffDependent(object, on, datesOn, off, datesOff);

        discreteActionOnOffDependent.compareTo(discreteActionOnOffDependent);

        assertEquals(0, discreteActionOnOffDependent.compareTo(discreteActionOnOffDependent));
    }


    @Test
    public void testGetCurrentLapsTime() {
        Timer simulatedTimerOn = new Timer() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                return null;
            }
        };

        Timer simulatedTimerOff = new Timer() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                return null;
            }
        };

        Object object = new Object();
        String on = "on";
        String off = "off";
        TreeSet<Integer> datesOn = new TreeSet<>();
        TreeSet<Integer> datesOff = new TreeSet<>();

        DiscreteActionOnOffDependent discreteActionOnOffDependent = new DiscreteActionOnOffDependent(object, on, datesOn, off, datesOff);

        discreteActionOnOffDependent.getCurrentLapsTime();

        assertEquals(discreteActionOnOffDependent.currentAction.getCurrentLapsTime(), discreteActionOnOffDependent.getCurrentLapsTime());
    }


    @Test
    public void testNext() {
        Timer simulatedTimerOn = new Timer() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                return null;
            }
        };

        Timer simulatedTimerOff = new Timer() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                return null;
            }
        };

        Object object = new Object();
        String on = "on";
        String off = "off";
        TreeSet<Integer> datesOn = new TreeSet<>();
        TreeSet<Integer> datesOff = new TreeSet<>();

        DiscreteActionOnOffDependent discreteActionOnOffDependent = new DiscreteActionOnOffDependent(object, on, datesOn, off, datesOff);

        discreteActionOnOffDependent.next();

        assertSame(discreteActionOnOffDependent.offAction, discreteActionOnOffDependent.currentAction);
    }

    @Test
    public void testHasNext() {
        Timer simulatedTimerOn = new Timer() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                return null;
            }
        };

        Timer simulatedTimerOff = new Timer() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                return null;
            }
        };

        Object object = new Object();
        String on = "on";
        String off = "off";
        TreeSet<Integer> datesOn = new TreeSet<>();
        TreeSet<Integer> datesOff = new TreeSet<>();

        DiscreteActionOnOffDependent discreteActionOnOffDependent = new DiscreteActionOnOffDependent(object, on, datesOn, off, datesOff);

        discreteActionOnOffDependent.hasNext();

        assertTrue(discreteActionOnOffDependent.hasNext());
    }

}
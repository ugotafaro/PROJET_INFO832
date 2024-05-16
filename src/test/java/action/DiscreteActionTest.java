package action;

import org.junit.jupiter.api.Test;
import timer.DateTimer;
import timer.Timer;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class DiscreteActionTest {
     public void setUp() {

     }

    @Test // TEST 1 contructeur
    public void testConstructor() {

        Object object = "Hello";
        String methodName = "toUpperCase";
        Timer timer = new Timer() {
            @Override
            public Integer next() {
                return null;
            }

            @Override
            public boolean hasNext() {
                return false;
            }
        };
        DiscreteAction discreteAction = new DiscreteAction(object, methodName, timer);


        assertNotNull(discreteAction);
        assertEquals(object, discreteAction.getObject());
        assertEquals(methodName, discreteAction.getMethod().getName());
    }

    @Test // TEST 2.1 spendTime
    public void testSpendTime1() {
        // Créez un objet DiscreteAction avec une valeur de lapsTime égale à 4
        DiscreteAction discreteAction = new DiscreteAction("Hello", "length", new Timer() {
            @Override
            public Integer next() {
                return null;
            }

            @Override
            public boolean hasNext() {
                return false;
            }
        });

        discreteAction.setLapsTime(4);

        discreteAction.spendTime(1);
        assertEquals(3, discreteAction.getLapsTime());
    }
    @Test // TEST 2.2 spendTime limite si c'est null
    public void testSpendTime2() {

        DiscreteAction discreteAction = new DiscreteAction(new Object(), "exampleMethod", new Timer() {
            @Override
            public Integer next() {
                return null;
            }

            @Override
            public boolean hasNext() {
                return false;
            }
        });
        discreteAction.setLapsTime(4);


        discreteAction.spendTime(0);


        assertEquals(4, discreteAction.getLapsTime());
    }
    @Test // TEST 2.3 spendTime negation si c'est negatif
    public void testSpendTime3() {

        DiscreteAction discreteAction = new DiscreteAction(new Object(), "exampleMethod", new Timer() {
            @Override
            public Integer next() {
                return null;
            }

            @Override
            public boolean hasNext() {
                return false;
            }
        });
        discreteAction.setLapsTime(4);


        discreteAction.spendTime(-1);


    }



    @Test //test 3.1 compareTo
    void testcompareTo1() {
        DiscreteAction discreteAction = new DiscreteAction(new Object(), "exampleMethod", new Timer() {
            @Override
            public Integer next() {
                return null;
            }

            @Override
            public boolean hasNext() {
                return false;
            }
        });
        discreteAction.setLapsTime(null);
        discreteAction.compareTo(discreteAction);
        assertEquals(1, discreteAction.compareTo(discreteAction));
    }
    @Test //test 3.2 compareTo
    void testcompareTo32(){
        DiscreteAction discreteAction = new DiscreteAction("test", "length", new Timer() {
            @Override
            public Integer next() {
                return null;
            }

            @Override
            public boolean hasNext() {
                return false;
            }
        });
        discreteAction.setLapsTime(2);
        DiscreteAction discreteAction2 = new DiscreteAction("test", "length", new Timer() {
            @Override
            public Integer next() {
                return null;
            }

            @Override
            public boolean hasNext() {
                return false;
            }
        });
        discreteAction2.setLapsTime(null);
        discreteAction.compareTo(discreteAction2);
        assertEquals(-1, discreteAction.compareTo(discreteAction2));

    }
    @Test //test 3.3 compareTo
    void testcompareTo3() {
        DiscreteAction discreteAction = new DiscreteAction(new Object(), "exampleMethod", new Timer() {
            @Override
            public Integer next() {
                return null;
            }

            @Override
            public boolean hasNext() {
                return false;
            }
        });
        discreteAction.setLapsTime(2);
        DiscreteAction discreteAction2 = new DiscreteAction(new Object(), "exampleMethod", new Timer() {
            @Override
            public Integer next() {
                return null;
            }

            @Override
            public boolean hasNext() {
                return false;
            }
        });
        discreteAction2.setLapsTime(1);
        discreteAction.compareTo(discreteAction2);
        assertEquals(1, discreteAction.compareTo(discreteAction2));

    }
    @Test //test 3.4 compareTo
    void testcompareTo4() {
        DiscreteAction discreteAction = new DiscreteAction(new Object(), "exampleMethod", new Timer() {
            @Override
            public Integer next() {
                return null;
            }

            @Override
            public boolean hasNext() {
                return false;
            }
        });
        discreteAction.setLapsTime(1);

        DiscreteAction discreteAction2 = new DiscreteAction(new Object(), "exampleMethod", new Timer() {
            @Override
            public Integer next() {
                return null;
            }

            @Override
            public boolean hasNext() {
                return false;
            }
        });
        discreteAction2.setLapsTime(2);

        discreteAction.compareTo(discreteAction2);
        assertEquals(-1, discreteAction.compareTo(discreteAction2));

    }
    @Test //test 3.5 compareTo
    void testcompareTo5() {
        DiscreteAction discreteAction = new DiscreteAction(new Object(), "exampleMethod", new Timer() {
            @Override
            public Integer next() {
                return null;
            }

            @Override
            public boolean hasNext() {
                return false;
            }
        });
        discreteAction.setLapsTime(1);

        DiscreteAction discreteAction2 = new DiscreteAction(new Object(), "exampleMethod", new Timer() {
            @Override
            public Integer next() {
                return null;
            }

            @Override
            public boolean hasNext() {
                return false;
            }
        });
        discreteAction2.setLapsTime(1);
        discreteAction.compareTo(discreteAction2);
        assertEquals(0, discreteAction.compareTo(discreteAction2));

    }
    @Test //test 3.6 compareTo limite si c'est null
    void testcompareTo2() {
        DiscreteAction discreteAction = new DiscreteAction(new Object(), "exampleMethod", new Timer() {
            @Override
            public Integer next() {
                return null;
            }

            @Override
            public boolean hasNext() {
                return false;
            }
        });


        discreteAction.compareTo(discreteAction);

    }


    @Test //test 4.1 next
    void next() {
        Object o = new Object();
        Vector<Integer> monVector = new Vector<>();
        monVector.add(10);

        Timer datetimer = new DateTimer(monVector);

        DiscreteAction da1 = new DiscreteAction(o, "test", datetimer);
        da1.setLapsTime(4);

        da1.next();
        assertEquals(10, da1.getLapsTime().intValue());
    }

    @Test // test 5.1 hasNext
    void testHasNext1() {
        Object o = new Object();
        Vector<Integer> monVector = new Vector<>();
        monVector.add(10);
        monVector.add(20);

        Timer datetimer = new DateTimer(monVector);


        DiscreteAction da1 = new DiscreteAction(o, "test", datetimer);
        da1.setLapsTime(4);


        boolean result = da1.hasNext();


        assertTrue(result);
    }
    @Test // test 5.2 hasNext
    void testHasNext2() {
        Object o = new Object();
        Vector<Integer> monVector = new Vector<>();
        monVector.add(10);
        monVector.add(20);

        Timer datetimer = new DateTimer(monVector);


        DiscreteAction da1 = new DiscreteAction(o, "test", datetimer);
        da1.setLapsTime(4);

        da1.next();
        boolean result = da1.hasNext();


        assertTrue(result);
    }
    @Test // test 5.3 hasNext
    void testHasNext3() {
        Object o = new Object();
        Vector<Integer> monVector = new Vector<>();
        monVector.add(10);
        monVector.add(20);

        Timer datetimer = new DateTimer(monVector);


        DiscreteAction da1 = new DiscreteAction(o, "test", datetimer);
        da1.setLapsTime(4);

        da1.next();
        da1.next();
        boolean result = da1.hasNext();


        assertFalse(result);
    }
}
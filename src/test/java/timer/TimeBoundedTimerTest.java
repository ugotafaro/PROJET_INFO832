package timer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeBoundedTimerTest {


    @Test
    public void testConstructorWithTimerAndTimes() {
        // Créons un Timer simulé pour passer au constructeur.
        Timer simulatedTimer = new Timer() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                return null;
            }
        };

        // Valeurs d'entrée.
        int startTime = 5;
        int stopTime = 15;

        // Instancier TimeBoundedTimer avec le constructeur.
        TimeBoundedTimer timeBoundedTimer = new TimeBoundedTimer(simulatedTimer, startTime, stopTime);

        // Vérifiez que les attributs sont correctement initialisés.
        assertSame(simulatedTimer, timeBoundedTimer.getTimer2bound());
        assertEquals(startTime, timeBoundedTimer.getStartTime());
        assertEquals(stopTime, timeBoundedTimer.getStopTime());

        // Nous attendons que init() ait été appelée, donc hasNext devrait être défini correctement.
        assertTrue(timeBoundedTimer.hasNext());
    }

    @Test
    public void testConstructorWithTimerAndStartTime() {
        // Créons un Timer simulé pour passer au constructeur.
        Timer simulatedTimer = new Timer() {
            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                return null;
            }
        };

        // Valeur d'entrée.
        int startTime = 5;

        // Instancier TimeBoundedTimer avec le constructeur.
        TimeBoundedTimer timeBoundedTimer = new TimeBoundedTimer(simulatedTimer, startTime);

        // Vérifiez que les attributs sont correctement initialisés.
        assertSame(simulatedTimer, timeBoundedTimer.getTimer2bound());
        assertEquals(startTime, timeBoundedTimer.getStartTime());
        assertEquals(Integer.MAX_VALUE, timeBoundedTimer.getStopTime());

        // Nous attendons que init() ait été appelée, donc hasNext devrait être défini correctement.
        assertTrue(timeBoundedTimer.hasNext());
    }

    @Test
    public void testInit() {
        // Créons un Timer simulé qui fournit une séquence de valeurs.
        Timer simulatedTimer = new Timer() {
            private int count = 0;
            private int[] times = {2, 3, 5, 7, 11};

            @Override
            public boolean hasNext() {
                return count < times.length;
            }

            @Override
            public Integer next() {
                if (count < times.length) {
                    return times[count++];
                } else {
                    return null;
                }
            }
        };

        // Valeurs d'entrée pour le TimeBoundedTimer.
        int startTime = 5;
        int stopTime = 15;

        // Créer une instance de TimeBoundedTimer.
        TimeBoundedTimer timeBoundedTimer = new TimeBoundedTimer(simulatedTimer, startTime, stopTime);

        // Vérifiez que next est initialisé correctement.
        // next doit être supérieur ou égal à startTime.
        assertTrue(timeBoundedTimer.getNext() >= startTime);

        // next doit être inférieur à stopTime.
        assertTrue(timeBoundedTimer.getNext() < stopTime);

        // Vérifiez que hasNext est configuré correctement.
        // hasNext doit être vrai si next est inférieur à stopTime.
        assertTrue(timeBoundedTimer.isHasNext());
    }

    @Test
    public void testHasNextBeforeCallingNext() {
        // Créons un Timer simulé pour passer au constructeur.
        Timer simulatedTimer = new Timer() {
            private int count = 0;
            private int[] times = {2, 3, 5, 7, 11};

            @Override
            public boolean hasNext() {
                return count < times.length;
            }

            @Override
            public Integer next() {
                if (count < times.length) {
                    return times[count++];
                } else {
                    return null;
                }
            }
        };

        // Valeurs d'entrée pour TimeBoundedTimer.
        int startTime = 3;
        int stopTime = 15;

        // Créer une instance de TimeBoundedTimer.
        TimeBoundedTimer timeBoundedTimer = new TimeBoundedTimer(simulatedTimer, startTime, stopTime);

        // Vérifiez que hasNext() renvoie true avant d'appeler next().
        assertTrue(timeBoundedTimer.hasNext());
    }

    @Test
    public void testHasNextAfterCallingNext() {
        // Créons un Timer simulé pour passer au constructeur.
        Timer simulatedTimer = new Timer() {
            private int count = 0;
            private int[] times = {2, 3, 5, 7, 11};

            @Override
            public boolean hasNext() {
                return count < times.length;
            }

            @Override
            public Integer next() {
                if (count < times.length) {
                    return times[count++];
                } else {
                    return null;
                }
            }
        };

        // Valeurs d'entrée pour TimeBoundedTimer.
        int startTime = 3;
        int stopTime = 15;

        // Créer une instance de TimeBoundedTimer.
        TimeBoundedTimer timeBoundedTimer = new TimeBoundedTimer(simulatedTimer, startTime, stopTime);

        // Appelez next() plusieurs fois jusqu'à dépasser stopTime.
        while (timeBoundedTimer.hasNext()) {
            timeBoundedTimer.next();
        }

        // Vérifiez que hasNext() renvoie false après avoir appelé next() jusqu'à la fin.
        assertFalse(timeBoundedTimer.hasNext());
    }

    @Test
    public void testNextOnFirstCall() {
        // Créons un Timer simulé qui fournit une séquence de valeurs.
        Timer simulatedTimer = new Timer() {
            private int count = 0;
            private int[] times = {2, 3, 5, 7, 11};

            @Override
            public boolean hasNext() {
                return count < times.length;
            }

            @Override
            public Integer next() {
                if (count < times.length) {
                    return times[count++];
                } else {
                    return null;
                }
            }
        };

        // Valeurs d'entrée pour TimeBoundedTimer.
        int startTime = 5;
        int stopTime = 15;

        // Créer une instance de TimeBoundedTimer.
        TimeBoundedTimer timeBoundedTimer = new TimeBoundedTimer(simulatedTimer, startTime, stopTime);

        // Appel initial de next().
        Integer firstNext = timeBoundedTimer.next();

        // Vérifiez que la valeur de next() est supérieure ou égale à startTime et inférieure à stopTime.
        assertTrue(firstNext >= startTime);
        assertTrue(firstNext < stopTime);
    }

    @Test
    public void testNextOnSubsequentCalls() {
        // Créons un Timer simulé qui fournit une séquence de valeurs.
        Timer simulatedTimer = new Timer() {
            private int count = 0;
            private int[] times = {2, 3, 5, 7, 11};

            @Override
            public boolean hasNext() {
                return count < times.length;
            }

            @Override
            public Integer next() {
                if (count < times.length) {
                    return times[count++];
                } else {
                    return null;
                }
            }
        };

        // Valeurs d'entrée pour TimeBoundedTimer.
        int startTime = 3;
        int stopTime = 15;

        // Créer une instance de TimeBoundedTimer.
        TimeBoundedTimer timeBoundedTimer = new TimeBoundedTimer(simulatedTimer, startTime, stopTime);

        // Appel initial de next().
        while (timeBoundedTimer.hasNext()) {
            Integer value = timeBoundedTimer.next();

            if (value != null) {
                // Vérifiez que la valeur est supérieure ou égale à startTime et inférieure à stopTime.
                assertTrue(value >= startTime);
                assertTrue(value < stopTime);
            } else {
                // Vérifiez que la valeur est null lorsque le temps est supérieur ou égal à stopTime.
                assertNull(value);
            }
        }
    }

    @Test
    public void testHasNextAfterTimeExceedsStopTime() {
        // Créons un Timer simulé qui fournit une séquence de valeurs.
        Timer simulatedTimer = new Timer() {
            private int count = 0;
            private int[] times = {2, 3, 5, 7, 11};

            @Override
            public boolean hasNext() {
                return count < times.length;
            }

            @Override
            public Integer next() {
                if (count < times.length) {
                    return times[count++];
                } else {
                    return null;
                }
            }
        };

        // Valeurs d'entrée pour TimeBoundedTimer.
        int startTime = 3;
        int stopTime = 15;

        // Créer une instance de TimeBoundedTimer.
        TimeBoundedTimer timeBoundedTimer = new TimeBoundedTimer(simulatedTimer, startTime, stopTime);

        // Appel initial de next() jusqu'à ce que time atteigne ou dépasse stopTime.
        while (timeBoundedTimer.hasNext()) {
            timeBoundedTimer.next();
        }

        // Vérifiez que hasNext() renvoie false après que time ait atteint ou dépassé stopTime.
        assertFalse(timeBoundedTimer.hasNext());
    }

}
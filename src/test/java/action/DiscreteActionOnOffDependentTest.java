package action;

import org.junit.jupiter.api.Test;
import timer.Timer;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class DiscreteActionOnOffDependentTest {

    //Vérifier l'erreur quand timerOn > timerOff, valeur attendue : Erreur

    @Test
    public void testConstructorWithTimerOnGreaterThanTimerOff() {
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

        // Instancier DiscreteActionOnOffDependent avec le constructeur.
        DiscreteActionOnOffDependent discreteActionOnOffDependent = new DiscreteActionOnOffDependent(new Object(), "on", simulatedTimerOn, "off", simulatedTimerOff);

        // Vérifier que l'erreur est levée
        assertThrows(IllegalArgumentException.class, () -> {
            new DiscreteActionOnOffDependent(new Object(), "on", simulatedTimerOn, "off", simulatedTimerOff);
        });
    }

    //Pour ce test, vérifiez que la classe est correctement créée avec les valeurs d'entrée données. Vous pouvez vérifier les attributs et les méthodes de la classe nouvellement créée
    @Test
    public void testConstructorWithTimerOnLessThanTimerOff() {
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

        // Valeurs d'entrée.
        Object object = new Object();
        String on = "on";
        String off = "off";

        // Instancier DiscreteActionOnOffDependent avec le constructeur.
        DiscreteActionOnOffDependent discreteActionOnOffDependent = new DiscreteActionOnOffDependent(object, on, simulatedTimerOn, off, simulatedTimerOff);

        // Vérifier que les attributs sont correctement initialisés.
        assertSame(object, discreteActionOnOffDependent.getObject());
        assertEquals(on, discreteActionOnOffDependent.onAction.getMethod().getName());
        assertEquals(off, discreteActionOnOffDependent.offAction.getMethod().getName());
    }

    //Pour ce test, vérifiez la création de l'objet DiscreteActionOnOffDependent avec des TreeSet<Integer> donnés. Vérifiez que les attributs et les méthodes sont correctement initialisé
    @Test
    public void testConstructorWithDatesOnAndDatesOff() {
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

        // Valeurs d'entrée.
        Object object = new Object();
        String on = "on";
        String off = "off";
        TreeSet<Integer> datesOn = new TreeSet<>();
        TreeSet<Integer> datesOff = new TreeSet<>();

        // Instancier DiscreteActionOnOffDependent avec le constructeur.
        DiscreteActionOnOffDependent discreteActionOnOffDependent = new DiscreteActionOnOffDependent(object, on, datesOn, off, datesOff);

        // Vérifier que les attributs sont correctement initialisés.
        assertSame(object, discreteActionOnOffDependent.getObject());
        assertEquals(on, discreteActionOnOffDependent.onAction.getMethod().getName());
        assertEquals(off, discreteActionOnOffDependent.offAction.getMethod().getName());
    }

    //nextAction()	 Testez cette méthode et vérifiez que l'action courante passe correctement de onAction à offAction
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

        // Valeurs d'entrée.
        Object object = new Object();
        String on = "on";
        String off = "off";
        TreeSet<Integer> datesOn = new TreeSet<>();
        TreeSet<Integer> datesOff = new TreeSet<>();

        // Instancier DiscreteActionOnOffDependent avec le constructeur.
        DiscreteActionOnOffDependent discreteActionOnOffDependent = new DiscreteActionOnOffDependent(object, on, datesOn, off, datesOff);

        // Appeler la méthode nextAction().
        discreteActionOnOffDependent.nextAction();

        // Vérifier que l'action courante est offAction.
        assertSame(discreteActionOnOffDependent.offAction, discreteActionOnOffDependent.currentAction);
    }

    //spendTime(int t)	Testez cette méthode avec différentes entrées et vérifiez que le temps de l'action en cours est correctement dépensé.
    @Test
    public void testSpendTime() {
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

        // Valeurs d'entrée.
        Object object = new Object();
        String on = "on";
        String off = "off";
        TreeSet<Integer> datesOn = new TreeSet<>();
        TreeSet<Integer> datesOff = new TreeSet<>();

        // Instancier DiscreteActionOnOffDependent avec le constructeur.
        DiscreteActionOnOffDependent discreteActionOnOffDependent = new DiscreteActionOnOffDependent(object, on, datesOn, off, datesOff);

        // Appeler la méthode spendTime().
        discreteActionOnOffDependent.spendTime(5);

        // Vérifier que le temps de l'action en cours est correctement dépensé.
        assertEquals(5, discreteActionOnOffDependent.currentAction.getCurrentLapsTime());
    }

    //getMethod()
    //Testez cette méthode et vérifiez qu'elle renvoie la bonne méthode de l'action en cours.
    @Test
    public void testGetMethod() {
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

        // Valeurs d'entrée.
        Object object = new Object();
        String on = "on";
        String off = "off";
        TreeSet<Integer> datesOn = new TreeSet<>();
        TreeSet<Integer> datesOff = new TreeSet<>();

        // Instancier DiscreteActionOnOffDependent avec le constructeur.
        DiscreteActionOnOffDependent discreteActionOnOffDependent = new DiscreteActionOnOffDependent(object, on, datesOn, off, datesOff);

        // Appeler la méthode getMethod().
        discreteActionOnOffDependent.getMethod();

        // Vérifier que la bonne méthode de l'action en cours est renvoyée.
        assertSame(discreteActionOnOffDependent.onAction.getMethod(), discreteActionOnOffDependent.getMethod());
    }

    //compareTo(DiscreteActionInterface c)	 Testez cette méthode avec différentes entrées et vérifiez qu'elle compare correctement l'action courante avec une autre action.
    @Test
    public void testCompareTo() {
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

        // Valeurs d'entrée.
        Object object = new Object();
        String on = "on";
        String off = "off";
        TreeSet<Integer> datesOn = new TreeSet<>();
        TreeSet<Integer> datesOff = new TreeSet<>();

        // Instancier DiscreteActionOnOffDependent avec le constructeur.
        DiscreteActionOnOffDependent discreteActionOnOffDependent = new DiscreteActionOnOffDependent(object, on, datesOn, off, datesOff);

        // Appeler la méthode compareTo().
        discreteActionOnOffDependent.compareTo(discreteActionOnOffDependent);

        // Vérifier que l'action courante est correctement comparée avec une autre action.
        assertEquals(0, discreteActionOnOffDependent.compareTo(discreteActionOnOffDependent));
    }

    //getCurrentLapsTime()
    //Testez cette méthode et vérifiez qu'elle renvoie la durée correcte de l'action en cours.
    @Test
    public void testGetCurrentLapsTime() {
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

        // Valeurs d'entrée.
        Object object = new Object();
        String on = "on";
        String off = "off";
        TreeSet<Integer> datesOn = new TreeSet<>();
        TreeSet<Integer> datesOff = new TreeSet<>();

        // Instancier DiscreteActionOnOffDependent avec le constructeur.
        DiscreteActionOnOffDependent discreteActionOnOffDependent = new DiscreteActionOnOffDependent(object, on, datesOn, off, datesOff);

        // Appeler la méthode getCurrentLapsTime().
        discreteActionOnOffDependent.getCurrentLapsTime();

        // Vérifier que la durée correcte de l'action en cours est renvoyée.
        assertEquals(discreteActionOnOffDependent.currentAction.getCurrentLapsTime(), discreteActionOnOffDependent.getCurrentLapsTime());
    }

    //next()
    // Testez cette méthode et vérifiez qu'elle passe correctement à l'action suivante.
    @Test
    public void testNext() {
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

        // Valeurs d'entrée.
        Object object = new Object();
        String on = "on";
        String off = "off";
        TreeSet<Integer> datesOn = new TreeSet<>();
        TreeSet<Integer> datesOff = new TreeSet<>();

        // Instancier DiscreteActionOnOffDependent avec le constructeur.
        DiscreteActionOnOffDependent discreteActionOnOffDependent = new DiscreteActionOnOffDependent(object, on, datesOn, off, datesOff);

        // Appeler la méthode next().
        discreteActionOnOffDependent.next();

        // Vérifier que l'action suivante est correctement passée.
        assertSame(discreteActionOnOffDependent.offAction, discreteActionOnOffDependent.currentAction);
    }

    //hasNext()	Testez cette méthode et vérifiez qu'elle renvoie la bonne valeur booléenne selon qu'il y a d'autres actions ou non.
    @Test
    public void testHasNext() {
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

        // Valeurs d'entrée.
        Object object = new Object();
        String on = "on";
        String off = "off";
        TreeSet<Integer> datesOn = new TreeSet<>();
        TreeSet<Integer> datesOff = new TreeSet<>();

        // Instancier DiscreteActionOnOffDependent avec le constructeur.
        DiscreteActionOnOffDependent discreteActionOnOffDependent = new DiscreteActionOnOffDependent(object, on, datesOn, off, datesOff);

        // Appeler la méthode hasNext().
        discreteActionOnOffDependent.hasNext();

        // Vérifier que la bonne valeur booléenne est renvoyée.
        assertTrue(discreteActionOnOffDependent.hasNext());
    }

}
package action;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.TreeSet;

import timer.Timer;

/**
 * @author flver
 *
 */
//TODO Must be refactored to be generic
public class DiscreteActionDependent implements DiscreteActionInterface {
	
	protected DiscreteAction baseAction;
	protected TreeSet<DiscreteAction> depedentActions;
	private Iterator<DiscreteAction> it;
	protected DiscreteAction currentAction;


	/**
	 * Constructs a DiscreteActionDependent object. This object represents a series of dependent actions.
	 * The first action (method) called is the baseMethodName, then the method nextMethod() is called to select the next action.
	 *
	 * @param object The object on which the base method will be invoked.
	 * @param baseMethodName The name of the base method to be invoked on the object 'o'.
	 * @param timerBase The Timer object associated with the base action. This is used to manage the timing of the base action.
	 */
	public DiscreteActionDependent(Object object, String baseMethodName, Timer timerBase){
		this.baseAction = new DiscreteAction(object, baseMethodName, timerBase);
		this.depedentActions = new TreeSet<>();
		this.depedentActions.add(this.baseAction);
		this.it = this.depedentActions.iterator();
		this.currentAction = this.baseAction;
	}

	/**
	 * Adds a dependent action to the DiscreteActionDependent object.
	 * This method creates a new DiscreteAction with the provided parameters and adds it to the set of dependent actions.
	 *
	 * @param object The object on which the dependent method will be invoked.
	 * @param depentMethodName The name of the dependent method to be invoked on the object 'o'.
	 * @param timerDependence The Timer object associated with the dependent action. This is used to manage the timing of the dependent action.
	 */
	public void addDependence(Object object, String depentMethodName, Timer timerDependence) {
		this.depedentActions.add(new DiscreteAction(object, depentMethodName, timerDependence));
	}

	/**
	 * Reinitializes the DiscreteActionDependent object.
	 * This method iterates over all dependent actions and updates their time lapses.
	 */
	private void reInit() {
		//this.baseAction.updateTimeLaps();
		for (Iterator<DiscreteAction> iter = this.depedentActions.iterator(); iter.hasNext(); ) {
		    DiscreteAction element = iter.next();
		    //element.updateTimeLaps();
		}

	}

	/**
	 * Determines the next action to be executed in the sequence of dependent actions.
	 * If the current action is the base action, it sets the iterator to the beginning of the dependent actions and sets the current action to the next action in the set.
	 * If the current action is the last action in the dependent actions set, it resets the current action back to the base action and reinitializes the DiscreteActionDependent object.
	 * If the current action is neither the base action nor the last action in the set, it simply moves the current action to the next action in the set.
	 */
	public void nextMethod() {
		if (this.currentAction == this.baseAction) {
			this.it = this.depedentActions.iterator();
			this.currentAction = this.it.next();
		} else if (this.currentAction == this.depedentActions.last()) {
			this.currentAction = this.baseAction;
			this.reInit();
		} else {
			this.currentAction = this.it.next();
		}
	}

	/**
	 * Reduces the time lapses of all dependent actions by a specified amount.
	 * This method iterates over all dependent actions and calls their spendTime method with the provided time 't'.
	 *
	 * @param t The amount of time to be subtracted from the time lapses of all dependent actions.
	 */
	public void spendTime(int t) {
		for (Iterator<DiscreteAction> iter = this.depedentActions.iterator(); iter.hasNext(); ) {
		    DiscreteAction element = iter.next();
		    element.spendTime(t);
		}
	}

	public void updateTimeLaps() {
		// time laps is updated at the re-initialization
		// this.currentAction.updateTimeLaps();
		this.nextMethod();	
	}

	public Method getMethod() {
		return this.currentAction.getMethod();
	}

	public Integer getCurrentLapsTime() {
		return this.currentAction.getCurrentLapsTime();
	}

	public Object getObject() {
		return this.currentAction.getObject();
	}

	public int compareTo(DiscreteActionInterface c) {
		return this.currentAction.compareTo(c);
	}

	public Boolean isEmpty() {
		return !this.hasNext();
	}

	public DiscreteActionInterface next() {
		//Integer lapsTime = this.getNextLapsTime();
		Method method = this.getMethod();
		Object object = this.getObject();
		return this;
	}

	public boolean hasNext() {
		return !this.depedentActions.isEmpty();
	}

}

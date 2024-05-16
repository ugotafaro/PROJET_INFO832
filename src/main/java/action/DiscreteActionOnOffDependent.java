package action;

import java.lang.reflect.Method;
import java.util.TreeSet;
import java.util.Vector;

import timer.DateTimer;
import timer.Timer;

/**
 * Represents a discrete action with On-Off dependence.
 * This class allows switching between two actions (On and Off) based on certain conditions.
 * The switching behavior can be customized by overriding the nextMethod() method.
 *
 * @author flver
 */
public class DiscreteActionOnOffDependent implements DiscreteActionInterface {

	protected DiscreteActionInterface onAction;
	protected DiscreteActionInterface offAction;
	protected DiscreteActionInterface currentAction;

	private Integer currentLapsTime;
	private Integer lastOffDelay=0;

	/**
	 * Constructs an On Off dependence where the first action called is On,
	 * then the next action is determined by the nextMethod() method.
	 * The default behavior of nextMethod() is to switch between On and Off actions.
	 * This behavior can be changed by overloading nextMethod().
	 *
	 * @param o Object on which actions are performed.
	 * @param on Name of the method for On action.
	 * @param timerOn Timer for the On action.
	 * @param off Name of the method for Off action.
	 * @param timerOff Timer for the Off action.
	 */
	public DiscreteActionOnOffDependent(Object o, String on, Timer timerOn, String off, Timer timerOff){
		this.onAction = new DiscreteAction(o, on, timerOn);
		this.offAction = new DiscreteAction(o, off, timerOff);
		this.currentAction = this.offAction;
		this.currentLapsTime = 0;
	}

	/**
	 * Constructs an On Off dependence based on specified dates for On and Off actions.
	 *
	 * @param o Object on which actions are performed.
	 * @param on Name of the method for On action.
	 * @param datesOn TreeSet of dates for On action.
	 * @param off Name of the method for Off action.
	 * @param datesOff TreeSet of dates for Off action.
	 */
	public DiscreteActionOnOffDependent(Object o, String on, TreeSet<Integer> datesOn, String off, TreeSet<Integer> datesOff){
		this.onAction = new DiscreteAction(o, on, new DateTimer(datesOn));
		this.offAction = new DiscreteAction(o, off, new DateTimer(datesOff));

		if(datesOn.first() < datesOff.first()){
			this.currentAction = this.onAction;
		}else{
			this.currentAction = this.offAction;
		}
	}

	/**
	 * Determines the time lapse between two sets of dates.
	 *
	 * @param datesOn TreeSet of dates for On action.
	 * @param datesOff TreeSet of dates for Off action.
	 * @param timeLapseOn Vector to store time lapse for On action.
	 * @param timeLapseOff Vector to store time lapse for Off action.
	 */
	private void dates2Timalapse(TreeSet<Integer> datesOn, TreeSet<Integer> datesOff, Vector<Integer> timeLapseOn, Vector<Integer> timeLapseOff) {
		Vector<Integer> currentTimeLapse;
		TreeSet<Integer> currentDates;
		Integer date=0;
		if(datesOn.first()<datesOff.first()) {
			currentTimeLapse = timeLapseOn;
			currentDates = datesOn;
		}else {
			currentTimeLapse = timeLapseOff;
			currentDates = datesOff;
		}
		Integer nextDate;

		while(datesOn.size()>0 || datesOff.size()>0) {
			nextDate = currentDates.first();

			currentTimeLapse.add(nextDate - date);
			currentDates.remove(nextDate);

			date = nextDate;

			if(currentDates == datesOn) {
				currentDates = datesOff;
				currentTimeLapse = timeLapseOff;
			}else {
				currentDates = datesOn;
				currentTimeLapse = timeLapseOn;
			}
		}

	}

	/**
	 * Switches to the next action (On or Off).
	 */
	public void nextAction(){
		if (this.currentAction == this.onAction){
			this.currentAction = this.offAction;
			this.currentAction = this.currentAction.next();
			this.lastOffDelay = this.currentAction.getCurrentLapsTime();
		}else{
			this.currentAction = this.onAction;
			this.currentAction = this.currentAction.next();
			this.currentAction.spendTime(this.lastOffDelay);
		}
	}

	/**
	 * Spends time on the current action.
	 *
	 * @param t Time to spend.
	 */
	public void spendTime(int t) {
		this.currentLapsTime = t;
		this.currentAction.spendTime(t);
	}

	/**
	 * Gets the method of the current action.
	 *
	 * @return Method of the current action.
	 */
	public Method getMethod() {
		return this.currentAction.getMethod();
	}

	/**
	 * Gets the current laps time.
	 *
	 * @return Current laps time.
	 */
	public Integer getCurrentLapsTime() {
		return this.currentAction.getCurrentLapsTime();
	}

	/**
	 * Gets the object on which actions are performed.
	 *
	 * @return Object on which actions are performed.
	 */
	public Object getObject() {
		return this.currentAction.getObject();
	}

	/**
	 * Compares this action to another action.
	 *
	 * @param c DiscreteActionInterface to compare.
	 * @return 0 if equal, a value less than 0 if this action is less than the specified action, and a value greater than 0 if this action is greater.
	 */
	public int compareTo(DiscreteActionInterface c) {
		return this.currentAction.compareTo(c);
	}

	/**
	 * Moves to the next action and returns it.
	 *
	 * @return Next action.
	 */
	public DiscreteActionInterface next() {
		this.nextAction();
		return this;
	}

	/**
	 * Checks if there is a next action available.
	 *
	 * @return True if there is a next action, otherwise false.
	 */
	public boolean hasNext() {
		return this.onAction.hasNext() || this.offAction.hasNext();
	}
}

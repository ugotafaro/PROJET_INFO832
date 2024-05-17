package action;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import timer.Timer;

/**
 * @author Tiphaine Bulou (2016)
 * @author Flavien Vernier
 *
 */

public class DiscreteAction implements DiscreteActionInterface {
	private Object object;
	private Method method;
	
	private Timer timer;
	private Integer lapsTime;
	
	private Logger logger;

	private DiscreteAction() {
			this.logger = Logger.getLogger("DAS");
			this.logger.setLevel(Level.ALL);
			this.logger.setUseParentHandlers(true);
	}

	/**
	 * Constructs a new DiscreteAction with the specified object, method, and timer.
	 *
	 * @param object The object on which the method will be invoked.
	 * @param method The name of the method to be invoked on the object.
	 * @param timer  The timer that provides the next lapsTime for this DiscreteAction.
	 *
	 * The constructor attempts to get the declared method from the object's class using reflection.
	 * If the method does not exist, it prints the stack trace of the exception.
	 */
	public DiscreteAction(Object object, String method, Timer timer){
		this();
		this.object = object;
		try{	
			this.method = object.getClass().getDeclaredMethod(method);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		this.timer = timer;
	}

	/**
	 * Reduces the current lapsTime of this DiscreteAction by the specified amount of time.
	 *
	 * @param timeSpent The amount of time to be subtracted from the current lapsTime.
	 *
	 * If the current lapsTime is not null, it is reduced by the amount of timeSpent.
	 * After the operation, a log message is generated with the class name, hash code of the object,
	 * old lapsTime and new lapsTime.
	 */
	public void spendTime(int timeSpent) {
		Integer old = this.lapsTime;
		if(this.lapsTime != null) {
			this.lapsTime -= timeSpent;
		}
		String logMessage = String.format("[DA] operate spendTime on  %s:%d: old time %d new time %d",
				this.getObject().getClass().getName(),
				this.getObject().hashCode(),
				old,
				this.getCurrentLapsTime());

		this.logger.log(Level.FINE, logMessage);	}

	/**
	 * Compares this discrete action to another discrete action.
	 *
	 * @param c The discrete action to be compared with this discrete action.
	 * @return A negative integer if the waiting time of this discrete action is less than that of the passed discrete action,
	 * a positive integer if the waiting time of this discrete action is greater than that of the passed discrete action,
	 * and zero if both waiting times are equal.
	 */
	public int compareTo(DiscreteActionInterface c) {
		if (this.lapsTime == null) { // no lapstime is equivalent to infinity 
			return 1;
		}
		if (c.getCurrentLapsTime() == null) {// no lapstime is equivalent to infinity 
			return -1;
		}
    	if(this.lapsTime > c.getCurrentLapsTime()){
    		return 1;
    	}
    	if(this.lapsTime < c.getCurrentLapsTime()){
    		return -1;
    	}
		if(this.lapsTime == c.getCurrentLapsTime()){
			return 0;
		}
		return 0;
	}


	/**
	 * Updates the current lapsTime of this DiscreteAction to the next value provided by the timer.
	 * Logs the old and new lapsTime values, and returns the updated DiscreteAction.
	 *
	 * @return The updated DiscreteAction with the new lapsTime.
	 */
	public DiscreteActionInterface next() {
		Integer old = this.lapsTime;
		this.lapsTime = this.timer.next();
		String logMessage = String.format("[DA] operate next on  %s:%d: old time %d new time %d",
				this.getObject().getClass().getName(),
				this.getObject().hashCode(),
				old,
				this.getCurrentLapsTime());

		this.logger.log(Level.FINE, logMessage);
		return this;
	}

	/**
	 * Checks if there is a next timelapse available for this DiscreteAction.
	 *
	 * @return true if the timer is not null and has a next timelapse, false otherwise.
	 */
	public boolean hasNext() {
		Boolean hasNext = false;
		if (this.timer != null && this.timer.hasNext()) {
			hasNext = true;
		}
		return hasNext;
	}

	public void setLapsTime(Integer timer){
		this.lapsTime = timer;
	}

	public Integer getLapsTime() {
		return lapsTime;
	}

	public Method getMethod(){
		return method;
	}
	public Integer getCurrentLapsTime(){
		return lapsTime;
	}
	public Object getObject(){
		return object;
	}

	public String toString(){
		return String.format("Object : %s %n Method : %s %n Stat. : %s %n delay: %s",
				this.object.getClass().getName(),
				this.method.getName(),
				this.timer,
				this.lapsTime);
	}

	@Override
	public int hashCode() {
		return Objects.hash(object, method, timer);
	}

	/**
	 * Compares this DiscreteAction to the specified object.
	 * The result is true if and only if the argument is not null and is a DiscreteAction object that has the same object, method, and timer as this object.
	 *
	 * @param obj The object to compare this DiscreteAction against.
	 * @return true if the given object represents a DiscreteAction equivalent to this DiscreteAction, false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		DiscreteAction that = (DiscreteAction) obj;
		return Objects.equals(object, that.object) &&
				Objects.equals(method, that.method) &&
				Objects.equals(timer, that.timer);
	}
}

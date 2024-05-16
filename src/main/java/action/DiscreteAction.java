package action;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import timer.Timer;

/**
 * @author Tiphaine Bulou (2016)
 * @author Flavien Vernier
 *
 */

// TODO must inherit from Action
public class DiscreteAction implements DiscreteActionInterface {
	private Object object;
	private Method method;
	
	private Timer timer;
	private Integer lapsTime;
	
	private Logger logger;

	// Constructor
	
	private DiscreteAction() {
			this.logger = Logger.getLogger("DAS");
			this.logger.setLevel(Level.ALL);
			this.logger.setUseParentHandlers(true);
	}
	
	public DiscreteAction(Object o, String m, Timer timmer){
		this();
		this.object = o;
		try{	
			this.method = o.getClass().getDeclaredMethod(m, new Class<?>[0]);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		this.timmer = timmer;
		//this.updateTimeLaps();
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
			this.lapsTime -= t;
		}
		this.logger.log(Level.FINE, "[DA] operate spendTime on  " + this.getObject().getClass().getName() + ":" + this.getObject().hashCode() + ": old time " + old + " new time " + this.getCurrentLapsTime());
	}

	/**
	 * Compare cette action discrète à une autre action discrète.
	 *
	 * @param c L'action discrète à comparer avec cette action discrète.
	 * @return Un entier négatif si le temps d'attente de cette action discrète est inférieur à celui de l'action discrète passée en paramètre,
	 * un entier positif si le temps d'attente de cette action discrète est supérieur à celui de l'action discrète passée en paramètre,
	 * et zéro si les deux temps d'attente sont égaux.
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


	}

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

	public boolean hasNext() {
		Boolean more=false;
		if (this.timmer != null && this.timmer.hasNext()) {
			more = true;
		}/*else if (this.dates != null) {
			more = !this.dates.isEmpty();
		}else if (this.lapsTimes != null) {
			more = !this.lapsTimes.isEmpty();
		}*/
		return more;		
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
		return String.format("Object : %s\n Method : %s\n Stat. : %s\n delay: %s",
				this.object.getClass().getName(),
				this.method.getName(),
				this.timer,
				this.lapsTime);
	}

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

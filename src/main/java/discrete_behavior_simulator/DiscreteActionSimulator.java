
package discrete_behavior_simulator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import action.DiscreteActionInterface;


/**
 * @author Tiphaine Bulou (2016)
 * @author Flavien Vernier
 *
 */
public class DiscreteActionSimulator implements Runnable {
	private static final String TIME_UNITS = " time units%n";
	private static final String RESET_ACTION = "[DAS] reset action ";


	private static final String ACTION_LOG_MESSAGE = "[DAS] run action ";
	private Thread t;
	private boolean running = false;
	
	private Clock globalTime;

	private List<DiscreteActionInterface> actionsList = new ArrayList<>();
	
	private int nbLoop;
	private int step;
	
	private Logger logger;
	private FileHandler logFile; 
	private ConsoleHandler logConsole; 

	public DiscreteActionSimulator(){
		

		this.logger = Logger.getLogger("DAS");

		this.logger.setLevel(Level.ALL);
		this.logger.setUseParentHandlers(true);
		try{
			this.logFile = new FileHandler(this.getClass().getName() + ".log");

			this.logFile.setFormatter(new LogFormatter());
			this.logConsole = new ConsoleHandler();
		}catch(Exception e){
			e.printStackTrace();
		}
		this.logger.addHandler(logFile);
		this.logger.addHandler(logConsole);
		

		this.globalTime = Clock.getInstance();
		
		this.t = new Thread(this);
		this.t.setName("Discrete Action Simulator");
	}
	
	/**
	 * @param nbLoop defines the number of loop for the simulation, the simulation is infinite if nbLoop is negative or 0.
	 */
	public void setNbLoop(int nbLoop){
		if(nbLoop>0){
			this.nbLoop = nbLoop;
			this.step = 1;
		}else{ // running infinitely
			this.nbLoop = 0;
			this.step = -1;
		}
	}


	/**
	 * Adds a new action to the list of actions to be executed.
	 * The action is only added if it has a next action in its sequence.
	 * After adding the action, the list of actions is sorted for ordered execution.
	 *
	 * @param c The action to be added to the list.
	 */
	public void addAction(DiscreteActionInterface c){

		if(c.hasNext()) {
			// add to list of actions, next is call to the action exist at the first time
			this.actionsList.add(c.next());

			// sort the list for ordered execution 
			Collections.sort(this.actionsList);
		}
	}
	


	/**
	 * @return the laps time before the next action
	 */
	private int nextLapsTime() {
		DiscreteActionInterface currentAction = this.actionsList.get(0);
		return currentAction.getCurrentLapsTime();
	}
	/**
	 * @return laps time of the running action
	 */
	private int runAction() {
		int sleepTime = 0;

		if (!this.actionsList.isEmpty()) {
			DiscreteActionInterface currentAction = this.actionsList.get(0);
			Object o = currentAction.getObject();
			Method m = currentAction.getMethod();
			sleepTime = currentAction.getCurrentLapsTime();

			try {
				if (m != null) {
					Thread.yield();
					if (this.globalTime != null) {
						this.globalTime.increase(sleepTime);
					}
					m.invoke(o);
					if (this.globalTime != null) {
						String message = String.format("%s%s on %s:%d at %d after %d time units%n",
								ACTION_LOG_MESSAGE,
								m.getName(),
								o.getClass().getName(),
								o.hashCode(),
								this.globalTime.getTime(),
								sleepTime);
						this.logger.log(Level.FINE, message);
					} else {
						String message = String.format("%s%s on %s:%d after %d time units%n",
								ACTION_LOG_MESSAGE,
								m.getName(),
								o.getClass().getName(),
								o.hashCode(),
								sleepTime);
						this.logger.log(Level.FINE, message);
					}
				} else {
					this.logger.log(Level.SEVERE, "[DAS] run action: method is null");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			this.logger.log(Level.INFO, "NOTHING TO DO%n");
			this.stop();
		}

		return sleepTime;
	}

	/**
	 * Update the time of all actions
	 * @param runningTimeOf1stCapsul
	 */
	private void updateTimes(int runningTimeOf1stCapsul){
		// update time laps off all actions
		for(int i=1 ; i < this.actionsList.size(); i++){
			this.actionsList.get(i).spendTime(runningTimeOf1stCapsul);
		}

		DiscreteActionInterface a = this.actionsList.remove(0);
		if(a.hasNext()) {
			a = a.next();
			this.actionsList.add(a);
			if(this.globalTime!=null) {
				String message = String.format("%s%s on %s:%d at %d after %d%s",
						RESET_ACTION,
						a.getMethod().getName(),
						a.getObject().getClass().getName(),
						a.getObject().hashCode(),
						this.globalTime.getTime(),
						a.getCurrentLapsTime(),
						TIME_UNITS);
				this.logger.log(Level.FINE, message);
			}else {
				String message = String.format("%s%s on %s:%d after %d%s",
						RESET_ACTION,
						a.getMethod().getName(),
						a.getObject().getClass().getName(),
						a.getObject().hashCode(),
						a.getCurrentLapsTime(),
						TIME_UNITS);
				this.logger.log(Level.FINE, message);
			}
			Collections.sort(this.actionsList);
		}
	}


	/**
	 * Executes the simulation of discrete actions.
	 * The simulation runs in a loop until it is stopped or until the specified number of loops is reached.
	 * In each loop, the next action is executed and the times of all actions are updated.
	 * If there are no actions to execute, the simulation is stopped.
	 */
	public void run() {
		int count = this.nbLoop;
		boolean finished = false;

		logThreadStart();

		while(running && !finished){
			processActions();

			count -= this.step;
			if(count<=0) {
				finished = true;
			}
		}
		this.running = false;
		logThreadEnd(count);
	}

	private void logThreadStart() {
		if (logger.isLoggable(Level.INFO)) {
			String message = String.format("LANCEMENT DU THREAD %s %n", t.getName());
			this.logger.log(Level.INFO, message);
		}
	}

	private void processActions() {
		if(!this.actionsList.isEmpty()){
			if (logger.isLoggable(Level.INFO)) {
				this.logger.log(Level.INFO, this.toString());
			}
			this.globalTime.setNextJump(this.nextLapsTime());

			this.globalTime.lockWriteAccess();
			int runningTime = this.runAction();
			this.updateTimes(runningTime);
			this.globalTime.unlockWriteAccess();
			sleepThread();
		}else{
			if (logger.isLoggable(Level.INFO)) {
				this.logger.log(Level.INFO, "NOTHING TO DO%n");
			}
			this.stop();
		}
	}

	private void sleepThread() {
		try {
			Thread.sleep(100);
		}catch(InterruptedException e) {
			Thread.currentThread().interrupt(); // Re-interrupt the thread
		}
	}

	private void logThreadEnd(int count) {
		if(this.step>0) {
			if (logger.isLoggable(Level.INFO)) {
				String messageEnd = String.format("DAS: %d actions done for %d turns asked.%n", (this.nbLoop - count), this.nbLoop);
				this.logger.log(Level.INFO, messageEnd);
			}
		}else {
			if (logger.isLoggable(Level.INFO)) {
				String messageEnd = String.format("DAS: %d actions done!%n", count);
				this.logger.log(Level.INFO, messageEnd);
			}
		}
	}

	/**
	 * Starts the simulation of discrete actions.
	 * This method checks if there are actions to be executed in the actionsList.
	 * If the actionsList is empty, it throws an IllegalStateException.
	 * Otherwise, it sets the running flag to true and starts the simulation thread.
	 *
	 * @throws IllegalStateException if there are no actions to run.
	 */
	public void start(){
		if (actionsList.isEmpty()) {
			throw new IllegalStateException("No action to run");
		}
		this.running = true;
		this.t.start();
	}

	/**
	 * Stops the simulation of discrete actions.
	 * This method sets the running flag to false, which stops the simulation thread.
	 */
	public void stop(){
		String message = "STOP THREAD " + t.getName() + "obj " + this;
		this.logger.log(Level.INFO, message);
		this.running = false;
	}

	/**
	 * @return the running status of the simulation
	 */
	public String toString(){
		StringBuilder toS = new StringBuilder("------------------\nTestAuto :" + this.actionsList.size());
		for(DiscreteActionInterface c : this.actionsList){
			toS.append(c.toString()).append("\n");
		}
		toS.append("---------------------\n");
		return toS.toString();
	}

	public boolean getRunning() {
		return this.running;
	}

}

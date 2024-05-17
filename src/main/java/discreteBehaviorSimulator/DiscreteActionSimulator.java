
package discreteBehaviorSimulator;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Vector;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import action.DiscreteAction;
import action.DiscreteActionInterface;


/**
 * @author Tiphaine Bulou (2016)
 * @author Flavien Vernier
 *
 */
public class DiscreteActionSimulator implements Runnable {


	private Thread t;
	private boolean running = false;
	
	private Clock globalTime;
	
	private Vector<DiscreteActionInterface> actionsList = new Vector<>();
	
	private int nbLoop;
	private int step;
	
	private Logger logger;
	private FileHandler logFile; 
	private ConsoleHandler logConsole; 

	public DiscreteActionSimulator(){
		
		// Start logger
		this.logger = Logger.getLogger("DAS");
		//this.logger = Logger.getLogger("APP");
		this.logger.setLevel(Level.ALL);
		this.logger.setUseParentHandlers(true);
		try{
			this.logFile = new FileHandler(this.getClass().getName() + ".log");
			//this.logFile.setFormatter(new SimpleFormatter());
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

		// Vérification que la liste d'actions n'est pas vide
		if (!this.actionsList.isEmpty()) {
			// Récupération de l'action en cours
			DiscreteActionInterface currentAction = this.actionsList.get(0);
			Object o = currentAction.getObject();
			Method m = currentAction.getMethod();
			sleepTime = currentAction.getCurrentLapsTime();

			try {
				// Exécution de l'action si la méthode n'est pas null
				if (m != null) {
					//Thread.sleep(sleepTime); // Real time can be very slow
					Thread.yield();
					//Thread.sleep(1000); // Wait message bus sends
					if (this.globalTime != null) {
						this.globalTime.increase(sleepTime);
					}
					m.invoke(o);
					if (this.globalTime != null) {
						// Logging de l'exécution de l'action
						this.logger.log(Level.FINE, "[DAS] run action " + m.getName() + " on " + o.getClass().getName() + ":" + o.hashCode() + " at " + this.globalTime.getTime() + " after " + sleepTime + " time units\n");
						System.out.println("[DAS] run action " + m.getName() + " on " + o.getClass().getName() + ":" + o.hashCode() + " at " + this.globalTime.getTime() + " after " + sleepTime + " time units\n");
					} else {
						this.logger.log(Level.FINE, "[DAS] run action " + m.getName() + " on " + o.getClass().getName() + ":" + o.hashCode() + " after " + sleepTime + " time units\n");
						System.out.println("[DAS] run action " + m.getName() + " on " + o.getClass().getName() + ":" + o.hashCode() + " after " + sleepTime + " time units\n");
					}
				} else {
					// Gestion du cas où la méthode est null
					System.err.println("[DAS] run action: method is null");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// Gestion du cas où il n'y a pas d'action à exécuter
			System.out.println("NOTHING TO DO\n");
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
			//int d = this.actionsList.get(i).getLapsTime();
			//this.actionsList.get(i).setLapsTemps(d- runningTimeOf1stCapsul);
			this.actionsList.get(i).spendTime(runningTimeOf1stCapsul);
		}



		DiscreteActionInterface a = this.actionsList.remove(0);
		if(a.hasNext()) {
			a = a.next();
			this.actionsList.addElement(a);
			if(this.globalTime!=null) {
				this.logger.log(Level.FINE, "[DAS] reset action " + a.getMethod().getName() + " on " + a.getObject().getClass().getName() + ":" + a.getObject().hashCode() + " at " + this.globalTime.getTime() + " to " + a.getCurrentLapsTime() + " time units\n");
				System.out.println("[DAS] reset action " + a.getMethod().getName() + " on " + a.getObject().getClass().getName() + ":" + a.getObject().hashCode() + " at " + this.globalTime.getTime() + " to " + a.getCurrentLapsTime() + " time units\n");
			}else {
				this.logger.log(Level.FINE, "[DAS] reset action " + a.getMethod().getName() + " on " + a.getObject().getClass().getName() + ":" + a.getObject().hashCode() + " to " + a.getCurrentLapsTime() + " time units\n");
				System.out.println("[DAS] reset action " + a.getMethod().getName() + " on " + a.getObject().getClass().getName() + ":" + a.getObject().hashCode() + " to " + a.getCurrentLapsTime() + " time units\n");
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

		System.out.println("LANCEMENT DU THREAD " + t.getName() + " \n");

		while(running && !finished){

			if(!this.actionsList.isEmpty()){
				System.out.println(this);
				this.globalTime.setNextJump(this.nextLapsTime());
				
				this.globalTime.lockWriteAccess();
				int runningTime = this.runAction();
				this.updateTimes(runningTime);
				this.globalTime.unlockWriteAccess();
				try {
					Thread.sleep(100);
				}catch(Exception e) {
					e.printStackTrace();
				}
				//TODO add global time synchronizer for action with list of date and avoid drift 
			}else{
				System.out.println("NOTHING TO DO\n");
				this.stop();
			}

			count -= this.step;
			if(count<=0) {
				finished = true;
			}
		}
		this.running = false;
		if(this.step>0) {
			System.out.println("DAS: " + (this.nbLoop - count) + " actions done for " + this.nbLoop + " turns asked.");
		}else {
			System.out.println("DAS: " + (count) + " actions done!");			
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
		System.out.println("STOP THREAD " + t.getName() + "obj " + this);
		this.running = false;
	}

	/**
	 * @return the running status of the simulation
	 */
	public String toString(){
		StringBuffer toS = new StringBuffer("------------------\nTestAuto :" + this.actionsList.size());
		for(DiscreteActionInterface c : this.actionsList){
			toS.append(c.toString() + "\n");
		}
		toS.append("---------------------\n");
		return toS.toString();
	}

	public boolean getRunning() {
		return this.running;
	}

}

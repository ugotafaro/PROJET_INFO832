package discrete_behavior_simulator;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Clock {
	private static Clock instance = null;
	
	private int time;

	public int getNextJump() {
		return nextJump;
	}

	private int nextJump;

	public Set<ClockObserver> getObservers() {
		return observers;
	}

	private ReentrantReadWriteLock lock;
	private boolean virtual;
	
	
	private Set<ClockObserver> observers;

	private Clock() {
		this.time = 0;
		this.nextJump=0;
		this.lock = new ReentrantReadWriteLock();
		this.virtual = true;
		this.observers = new HashSet<>();
	}
	
	public static Clock getInstance() {
		if (Clock.instance == null) {
			Clock.instance = new Clock();
		}
		return Clock.instance;
	}
	
	public void addObserver(ClockObserver o) {
		this.observers.add(o);
	}
	public void removeObserver(ClockObserver o) {
		this.observers.remove(o);
	}
	
	public void setVirtual(boolean virtual) {
		this.virtual = virtual;
	}
	public boolean isVirtual() {
		return this.virtual;
	}
	
	public void setNextJump(int nextJump) {
		this.nextJump = nextJump;
		for(ClockObserver o:this.observers) {
			o.nextClockChange(this.nextJump);
		}
	}
	public class UnexpectedTimeChangeException extends Exception {
		public UnexpectedTimeChangeException(String message) {
			super(message);
		}
	}
	public void increase(int time) throws UnexpectedTimeChangeException {

		this.lockWriteAccess();

		if(time != this.nextJump) {
			throw new UnexpectedTimeChangeException("Unexpected time change");
		}
		this.time += time;
		for(ClockObserver o:this.observers) {
			o.clockChange(this.time);
		}
		this.unlockWriteAccess();
	}
	public long getTime() {
		if(this.virtual) {
			return this.time;
		}else {
			return new Date().getTime();
		}
	}
	
	public void lockReadAccess() {
		this.lock.readLock().lock();
	}
	
	public void unlockReadAccess() {
		this.lock.readLock().unlock();
	}
	
	public void lockWriteAccess() {
		this.lock.writeLock().lock();
	}
	public void unlockWriteAccess() {
		this.lock.writeLock().unlock();		
	}
	
	public String toString() {
		return ""+this.time;
	}
}

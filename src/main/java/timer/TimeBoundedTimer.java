package timer;

public class TimeBoundedTimer implements Timer {
	
	private Timer timer2bound;
	private Integer startTime;
	private Integer stopTime;
	
	private Integer next=0;
	private int time=0;
	private boolean hasNext;

	/**
	 * Constructs a new TimeBoundedTimer with the given timer, start time, and stop time.
	 * This constructor initializes the timer2bound, startTime, and stopTime fields with the provided values.
	 * It then calls the init() method to initialize the next and hasNext fields.
	 *
	 * @param timer2bound The timer to be bounded.
	 * @param startTime The start time of the bounded timer.
	 * @param stopTime The stop time of the bounded timer.
	 */
	public TimeBoundedTimer(Timer timer2bound, int startTime, int stopTime) {
		this.timer2bound = timer2bound;
		this.startTime = startTime;
		this.stopTime = stopTime;
		this.init();
	}

	/**
	 * Constructs a new TimeBoundedTimer with the given timer and start time.
	 * This constructor initializes the timer2bound and startTime fields with the provided values.
	 * The stopTime field is set to Integer.MAX_VALUE, representing an infinite stop time.
	 * It then calls the init() method to initialize the next and hasNext fields.
	 *
	 * @param timer2bound The timer to be bounded.
	 * @param startTime The start time of the bounded timer.
	 */
	public TimeBoundedTimer(Timer timer2bound, int startTime) {
		this.timer2bound = timer2bound;
		this.startTime = startTime;
		this.stopTime = Integer.MAX_VALUE;
		this.init();
	}

	/**
	 * Initializes the TimeBoundedTimer.
	 * This method sets the next time from the bounded timer and increments it until it is greater than or equal to the start time.
	 * It then checks if the next time is less than the stop time. If it is, it sets hasNext to true, otherwise it sets hasNext to false.
	 */
	private void init() {
		this.next = this.timer2bound.next();
		while (this.next < this.startTime) {
			this.next += this.timer2bound.next();
		}
		if(this.next<this.stopTime) {
			this.hasNext = true;
		}else {
			this.hasNext = false;
		}
	}

	/**
	 * Checks if there is a next time for the TimeBoundedTimer.
	 *
	 * @return true if there is a next time, false otherwise.
	 */
	@Override
	public boolean hasNext() {
		return this.hasNext;
	}

	/**
	 * Returns the next time from the bounded timer and updates the internal state.
	 * This method first saves the current next time in a local variable.
	 * It then increments the internal time by the value of the next time.
	 * If the internal time is less than the stop time, it sets the next time to the next time from the bounded timer.
	 * Otherwise, it sets the next time to null and sets hasNext to false.
	 * Finally, it returns the saved next time.
	 *
	 * @return The next time from the bounded timer, or null if the stop time has been reached.
	 */
	@Override
	public Integer next() {
		Integer next = this.next;
		this.time+=this.next;
		if(this.time < this.stopTime) {
			this.next = this.timer2bound.next();
		}else {
			next = null;
			this.hasNext=false;
		}
		return next;
	}

}

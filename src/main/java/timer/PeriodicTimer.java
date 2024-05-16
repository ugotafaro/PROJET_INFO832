package timer;

public class PeriodicTimer implements Timer {

	private int period;
	private int next;
	private RandomTimer moreOrLess = null;

	/**
	 * Constructs a new PeriodicTimer with the specified period.
	 *
	 * @param at the period of the timer
	 */
	public PeriodicTimer(int at) {
		this.period = at;
		this.next = at;
	}

	/**
	 * Constructs a new PeriodicTimer with the specified period and a RandomTimer.
	 * This constructor is deprecated and it's recommended to use MergedTimer instead.
	 *
	 * @param at the period of the timer
	 * @param moreOrLess the RandomTimer instance used to adjust the period
	 * @deprecated Use {@link MergedTimer} instead.
	 */
	@Deprecated
	public PeriodicTimer(int at, RandomTimer moreOrLess) {
		this.period = at;
		this.moreOrLess = moreOrLess;
		if (this.moreOrLess != null) {
			this.next = at + (int)(this.moreOrLess.next() - this.moreOrLess.getMean());
		} else {
			this.next = at;
		}
	}

	/**
	 * Constructs a new PeriodicTimer with the specified period and initial time.
	 *
	 * @param period the period of the timer
	 * @param at the initial time of the timer
	 */
	public PeriodicTimer(int period, int at) {
		this.period = period;
		this.next = at;
	}

	/**
	 * Constructs a new PeriodicTimer with the specified period, initial time, and a RandomTimer.
	 * This constructor is deprecated and it's recommended to use MergedTimer instead.
	 *
	 * @param period the period of the timer
	 * @param at the initial time of the timer
	 * @param moreOrLess the RandomTimer instance used to adjust the period
	 * @deprecated Use {@link MergedTimer} instead.
	 */
	@Deprecated
	public PeriodicTimer(int period, int at, RandomTimer moreOrLess) {
		this.period = period;
		this.moreOrLess = moreOrLess;
		if (this.moreOrLess != null) {
			this.next = at + (int)(this.moreOrLess.next() - this.moreOrLess.getMean());
		} else {
			this.next = at;
		}
	}


	public int getPeriod() {
		return this.period;
	}


	/**
	 * Returns the next time and updates the next time for the next call.
	 * If a RandomTimer instance is set, it adjusts the next time based on the RandomTimer's next time and mean.
	 * Otherwise, the next time is set to the period of the timer.
	 *
	 * @return the next time before the update
	 */
	@Override
	public Integer next() {
		int next =  this.next;

		if(this.moreOrLess != null) {
			this.next = this.period + Math.abs((int)(this.moreOrLess.next() - this.moreOrLess.getMean()));
		} else {
			this.next = this.period;
		}

		return next;
	}

	/*@Override
	public Integer next(int since) {
		
		int next = (this.at - (since % this.period) + this.period) % this.period;
		
		if(this.moreOrLess != null) {
			next += this.moreOrLess.next() - this.moreOrLess.getMean();
			this.next = this.period * 2 - next;
		}else {
			this.next = this.period;
		}
		
		return next;
	}*/


	/**
	 * Checks if there is a next time for the PeriodicTimer.
	 * As the PeriodicTimer is periodic, it will always have a next time.
	 *
	 * @return true as there is always a next time
	 */
	@Override
	public boolean hasNext() {
		return true;
	}

}

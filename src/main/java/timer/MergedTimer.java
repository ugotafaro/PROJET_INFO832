package timer;

/**
 * The MergedTimer class implements the Timer interface.
 * It represents a timer that is the fusion of two other timers.
 * The next() method of this class returns the sum of the next values of the two timers if they exist.
 * The hasNext() method of this class checks if the two timers have next values.
 */
public class MergedTimer implements Timer{
	
	private Timer timer1;
	private Timer timer2;

	public MergedTimer(Timer timer1, Timer timer2) {
		this.timer1 = timer1;
		this.timer2 = timer2;
	}

	/**
	 * Checks if the two timers have next values.
	 *
	 * @return true if the two timers have next values, false otherwise.
	 */
	@Override
	public boolean hasNext() {
		return (this.timer1.hasNext() && this.timer2.hasNext());
	}

	/**
	 * Returns the sum of the next values of the two timers if they exist.
	 *
	 * @return The sum of the next values of the two timers if they exist, otherwise null.
	 */
	@Override
	public Integer next() {
		if(this.hasNext()) {
			return this.timer1.next() + this.timer2.next();
		}
		return null;
	}

}

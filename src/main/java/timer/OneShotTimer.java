package timer;

public class OneShotTimer  implements Timer {
	
	private Integer at;
	private boolean hasNext;

	/**
	 * Constructs a new OneShotTimer with the given date.
	 * This constructor initializes the date and sets the hasNext flag to true.
	 *
	 * @param at The date to be used for initializing the OneShotTimer.
	 */
	public OneShotTimer(int at) {
		this.at = at;
		this.hasNext = true;
	}

	@Override
	public boolean hasNext() {
		return this.hasNext;
	}

	@Override
	public Integer next() {
		Integer next=this.at;
		this.at=null;
		this.hasNext = false;
		return next;
	}

}

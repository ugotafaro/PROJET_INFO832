package timer;

import java.util.*;

public class DateTimer  implements Timer {


	List<Integer> lapsTimes;
	Iterator<Integer> it;

	/**
	 * Constructs a new DateTimer with the given dates.
	 * This constructor initializes the lapsTimes vector and sets the current time to 0.
	 * It then iterates over the given dates, calculating the difference between the current and last date, and adds this difference to the lapsTimes vector.
	 * Finally, it initializes the iterator for the lapsTimes vector.
	 *
	 * @param dates The TreeSet of dates to be used for initializing the DateTimer.
	 */
	public DateTimer(SortedSet<Integer> dates) {
		this.lapsTimes = new Vector<>();
		Integer last;
		Integer current = 0;

		Iterator<Integer> itr = dates.iterator();
		while (itr.hasNext()) {
			last = current;
			current = itr.next();
			this.lapsTimes.add(current - last);
		}
		this.it = this.lapsTimes.iterator();
	}
	/**
	 * Constructs a new DateTimer with the given lapsTimes.
	 * This constructor initializes the lapsTimes vector and sets the current time to 0.
	 * It then iterates over the given lapsTimes, adding each time to the lapsTimes vector.
	 * Finally, it initializes the iterator for the lapsTimes vector.
	 *
	 * @param lapsTimes The Vector of lapsTimes to be used for initializing the DateTimer.
	 */
	public DateTimer(List<Integer> lapsTimes) {
		this.lapsTimes = new ArrayList<>(lapsTimes);
		this.it = this.lapsTimes.iterator();
	}

	@Override
	public boolean hasNext() {
		return it.hasNext();
	}

	@Override
	public Integer next() {
		return it.next();
	}

}

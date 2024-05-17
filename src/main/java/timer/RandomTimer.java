package timer;

import java.util.Random;


import static java.lang.Enum.valueOf;

/**
 * @author Flavien Vernier
 *
 */



public class RandomTimer implements Timer {

	/**
	 * The randomDistribution enumeration defines the types of random distributions that the RandomTimer can use.
	 * The available distribution types are:
	 * - POISSON: A Poisson distribution, typically used to model the number of events occurring in a fixed time interval.
	 * - EXP: An exponential distribution, often used to model the time between events in a Poisson process.
	 * - POSIBILIST: A posibilist distribution, used to model situations where outcomes are evenly spread between a lower and upper limit.
	 * - GAUSSIAN: A Gaussian (or normal) distribution, used to model random variables that are the result of many independent factors.
	 */
	public enum randomDistribution {
		POISSON, EXP, POSIBILIST, GAUSSIAN;
	}
	

	
	private Random r = new Random();
	private randomDistribution distribution;
	private double rate;
	private double mean;
	private double lolim;
	private double hilim; 



	/**
	 * Converts a string representation of a distribution type into a randomDistribution enum value.
	 * The string is converted to uppercase before comparison to ensure case-insensitive matching.
	 *
	 * @param distributionName the string representation of the distribution type
	 * @return the corresponding randomDistribution enum value
	 * @throws IllegalArgumentException if the provided string does not match any randomDistribution value
	 */
	public static randomDistribution string2Distribution(String distributionName){
		return valueOf(RandomTimer.randomDistribution.class, distributionName.toUpperCase());
	}

	/**
	 * Converts a randomDistribution enum value into a string representation (name).
	 *
	 * @param distribution the randomDistribution enum value
	 * @return the string representation of the distribution type
	 */
	public static String distribution2String(randomDistribution distribution){
		return distribution.name();
	}
	public class InvalidDistributionException extends Exception {
		public InvalidDistributionException(String message) {
			super(message);
		}
	}
	/**
	 * Constructs a RandomTimer with a specific distribution and a parameter.
	 * If the distribution is EXP, the rate is set to the provided parameter and the mean is calculated as the reciprocal of the rate.
	 * If the distribution is POISSON, the mean is set to the provided parameter.
	 * In both cases, the lower limit (lolim) is set to 0 and the upper limit (hilim) is set to positive infinity.
	 * If a distribution other than EXP or POISSON is provided, an exception is thrown.
	 *
	 * @param distribution the distribution type
	 * @param param the parameter for the distribution
	 * @throws Exception if the provided distribution is not EXP or POISSON
	 */
	public RandomTimer(randomDistribution distribution, double param) throws InvalidDistributionException {
		if(distribution == randomDistribution.EXP ){
			this.distribution = distribution;
			this.rate = param;
			this.mean = 1/param;
			this.lolim = 0;
			this.hilim = Double.POSITIVE_INFINITY;
		} else if(distribution == randomDistribution.POISSON){
			this.distribution = distribution;
			this.rate = Double.NaN;
			this.mean = param;
			this.lolim = 0;
			this.hilim = Double.POSITIVE_INFINITY;
		} else {
			throw new InvalidDistributionException("Bad Timer constructor for selected distribution");
		}
	}

	/**
	 * Constructs a RandomTimer with a specific distribution and two parameters, lolim and hilim.
	 * If the distribution is POSIBILIST or GAUSSIAN, the mean is calculated as the average of lolim and hilim.
	 * The lower limit (lolim) and the upper limit (hilim) are set to the provided parameters.
	 * If a distribution other than POSIBILIST or GAUSSIAN is provided, an exception is thrown.
	 *
	 * @param distribution the distribution type
	 * @param lolim the lower limit for the distribution
	 * @param hilim the upper limit for the distribution
	 * @throws Exception if the provided distribution is not POSIBILIST or GAUSSIAN
	 */
	public RandomTimer(randomDistribution distribution, double lolim, double hilim) throws InvalidDistributionException {
		if(distribution == randomDistribution.POSIBILIST || distribution == randomDistribution.GAUSSIAN){
			this.distribution = distribution;
			this.mean = lolim + (hilim - lolim)/2;
			this.rate = Double.NaN;
			this.lolim = lolim;
			this.hilim = hilim;
		} else {
			throw new InvalidDistributionException("Bad Timer constructor for selected distribution");
		}
	}

	/**
	 * Returns the distribution type of the RandomTimer as a string.
	 *
	 * @return the string representation of the distribution type
	 */
	public String getDistribution(){
		return this.distribution.name();
	}

	/**
	 * Returns the parameters of the distribution type of the RandomTimer as a string.
	 * If the distribution is EXP, it returns the rate.
	 * If the distribution is POISSON, it returns the mean.
	 * If the distribution is POSIBILIST or GAUSSIAN, it returns the lower limit (lolim) and the upper limit (hilim).
	 *
	 * @return the string representation of the distribution parameters
	 */
	public String getDistributionParam() {
		if(distribution == randomDistribution.EXP ){
			return "rate: " + this.rate;
		}else if(distribution == randomDistribution.POISSON){
			return "mean: " + this.mean;
		}else if(distribution == randomDistribution.POSIBILIST || distribution == randomDistribution.GAUSSIAN){
			return "lolim: " + this.lolim + " hilim: " + this.hilim;
		}
		
		return "null";
	}

	/**
	 * Returns the mean of the distribution type of the RandomTimer.
	 *
	 * @return the mean of the distribution
	 */
	public double getMean(){
		return this.mean;
	}

	/**
	 * @return the string representation of the RandomTimer
	 */
	public String toString(){
		String s = this.getDistribution();
		switch (this.distribution){
		case POSIBILIST :
			s += " LoLim:" + this.lolim + " HiLim:" + this.hilim;
			break;
		case POISSON :
			s += " mean:" + this.mean;
			break;
		case EXP :
			s += " rate:" + this.rate;
			break;
		case GAUSSIAN :
			s += " LoLim:" + this.lolim + " HiLim:" + this.hilim;
			break;
		}
		
		return s;
	}
	

	/*
	 * Returns the next random time according to the distribution type of the RandomTimer.
	 * @see methodInvocator.Timer#next()
	 * @return the next random time
	 */
	@Override
	public Integer next(){
		switch (this.distribution){
		case POSIBILIST :
			return this.nextTimePosibilist();
		case POISSON :
			return this.nextTimePoisson();
		case EXP :
			return this.nextTimeExp();
		case GAUSSIAN :
			return this.nextTimeGaussian();
		}
		return -1; // Theoretically impossible !!!
	}
	

	/**
	 * Returns the next random time according to the posibilist distribution.
	 * The posibilist distribution is used to model situations where outcomes are evenly spread between a lower and upper limit.
	 * The method generates a random number between the lower limit (lolim) and the upper limit (hilim).
	 *
	 * @return the next random time
	 */
	private int nextTimePosibilist(){
	    return (int)this.lolim + (int)(this.r.nextDouble() * (this.hilim - this.lolim));
	}

	/**
	 * Returns the next random time according to the exponential distribution.
	 * The exponential distribution is often used to model the time between events in a Poisson process.
	 * The method generates a random number based on the rate of the distribution.
	 *
	 * @return the next random time
	 */
	private int nextTimeExp(){
	    return (int)(-Math.log(1.0 - this.r.nextDouble()) / this.rate);
	}


	/**
	 * Returns the next random time according to the Poisson distribution.
	 * The Poisson distribution is used to model the number of events occurring in a fixed time interval.
	 * The method generates a random number based on the mean of the distribution.
	 *
	 * @return the next random time
	 */
	private int nextTimePoisson() {
		double lambda = Math.exp(-this.mean);
		int k = 0;
		double p = 1.0;
		do {
			p = p * this.r.nextDouble();
			k++;
		} while (p > lambda);
		return k - 1;
	}


	/**
	 * Returns the next random time according to the Gaussian distribution.
	 * The Gaussian distribution, also known as the normal distribution, is used to model random variables that are the result of many independent factors.
	 * The method generates a random number based on the lower limit (lolim) and the upper limit (hilim).
	 *
	 * @return the next random time
	 */
	private int nextTimeGaussian(){
		return (int)this.lolim + (int)((this.r.nextGaussian() + 1.0)/2.0 * (this.hilim - this.lolim));
	}

	/**
	 * Checks if there is a next random time according to the distribution type of the RandomTimer.
	 * As the RandomTimer generates random times based on a distribution, it will always have a next time.
	 *
	 * @return true as there is always a next random time
	 */
	@Override
	public boolean hasNext() {
		return true;
	}
}

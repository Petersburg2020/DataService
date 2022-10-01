package nx.peter.java.util;

import nx.peter.java.util.param.FactorPair;
import nx.peter.java.util.param.IntPair;
import nx.peter.java.util.param.LongPair;

import java.util.ArrayList;
import java.util.List;

public class Factor {
	protected List<Long> factors, sumFactors;
	protected long number;

	public Factor(long number) {
		set(number);
	}

	public void reset() {
		factors = new ArrayList<>();
		number = 0;
	}

	public void set(long number) {
		reset();
		this.number = number;
		sumFactors = new ArrayList<>();
		factors = new ArrayList<>();
		
		// Factors
		if (Math.abs(number) > 1) 
			for (long n = 1; n <= Math.abs(number); n++) {
				// System.out.println(n);
				if (n > 0 && Math.abs(number) % n == 0) 
					factors.add(n);	// Product factor
				if (n < Math.abs(number))
					sumFactors.add(n);	// Sum factor
			}
		else if (Math.abs(number) == 1) {
			factors.add(1L);
			sumFactors.add(1L);
		}
	}

	public long getNumber() {
		return number;
	}

	public List<Long> getFactors() {
		return factors;
	}

	public List<Long> getSumFactors() {
		return factors;
	}
	
	public boolean isFactor(long value) {
		return factors.contains(value);
	}

	public boolean isNotFactor(int value) {
		return !isFactor(value);
	}

	public FactorPair getFactorPairs() {
		List<LongPair> pairs = new ArrayList<>();
		for (long factor : getFactors())
			if (notContains(factor, pairs))
				pairs.add(new LongPair(factor, number/factor));
		return new FactorPair(number, pairs);
	}

	public FactorPair getSumFactorPairs() {
		List<LongPair> pairs = new ArrayList<>();
		for (long factor : getSumFactors())
			if (notContains(factor, pairs))
				pairs.add(new LongPair(factor, number - factor));
		return new FactorPair(number, pairs);
	}
	
	private boolean notContains(long factor, List<LongPair> pairs) {
		for (LongPair pair : pairs)
			if (pair.contains(factor))
				return false;
		return true;
	}

	public boolean areFactors(int... values) {
		for (int value : values)
		 	if (!isFactor(value))
				return false;
		return true;
	}

	public HCF getHCF(long... others) {
		long[] values = new long[others.length + 1];
		int index = 0;
		for (long val : others)
			values[index++] = val;
		values[index] = number;
		return new HCF(values);
	}

	@Override
	public String toString() {
		return "Factors of " + number + " are: " + factors.toString().replace("[", "").replace("]", "");
	}

}


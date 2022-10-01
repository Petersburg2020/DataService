package nx.peter.java.util;

import java.util.ArrayList;
import java.util.List;

public class Multiple {
	protected long number;
	protected long range;

	public Multiple(long number) {
		reset();
		setNumber(number);
	}

	public Multiple(long number, long range) {
		reset();
		set(number, range);
	}

	public void set(long number, long range) {
		this.number = number;
		setRange(range);
	}

	public void reset() {
		number = 0;
		range = 10;
	}

	public void setNumber(long number) {
		set(number, range);
	}

	public long getNumber() {
		return number;
	}

	public void setRange(long range) {
		this.range = range >= 5 ? Math.abs(range) : 5;
	}

	public long getRange() {
		return range;
	}

	public boolean isMultiple(long value) {
		return getMultiples().contains(value);
	}

	public List<Long> getMultiples() {
		List<Long> multiples = new ArrayList<>();
		long number = Math.abs(this.number);
		if (number > 0 && range > 0) 
			for (long n = 1; n <= range; n++)
				multiples.add(number * n);
		return multiples;
	}

	@Override
	public String toString() {
		return "First " + range + " multiples of '" + number + "'\n\t= " + getMultiples().toString().replace("[", "").replace("]", "");
	}

}


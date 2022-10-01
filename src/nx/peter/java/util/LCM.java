package nx.peter.java.util;

import java.util.ArrayList;
import java.util.List;

public class LCM {
	// protected long value1, value2;
	protected List<Long> values;

	public LCM(long... values) {
		set(values);
	}

	public void reset() {
		values = new ArrayList<>();
	}

	public void set(long... values) {
		reset();
		for (long value : values)
			this.values.add(value);
	}

	/*public void set(int value1, int value2) {
	 this.value1 = value1;
	 this.value2 = value2;
	 }*/

	public List<Long> getValues() {
		return new ArrayList<>(values);
	}

	public long getLowestCommonMultiple() {
		if (values.contains(0L) || valueCount() <= 1)
			return 0;
		int range = 100;
		Multiple multiple0 = new Multiple(values.get(0), range);

		for (long multiple : multiple0.getMultiples()) {
			boolean temp = true;

			for (int n = 1; n < valueCount(); n++) {
				Multiple multiple1 = new Multiple(values.get(n), range);
				if (!multiple1.isMultiple(multiple)) 
					temp = false;
			}
			if (temp) return multiple;
		}
		/*Multiple multiple1 = new Multiple(value1, 100);
		 Multiple multiple2 = new Multiple(value2, 100);

		 for (int multiple : multiple1.getMultiples())
		 if (multiple2.isMultiple(multiple))
		 return multiple;*/
		return 1;
	}

	protected long maxRange(List<Long> values) {
		long max = 0;
		for (long value : values)
			max = Math.max(max, value);
		return max;
	}

	public int valueCount() {
		return values.size();
	}

	protected List<Long> deValues() {
		List<Long> de = getValues();
		de.remove(valueCount() - 1);
		return de;
	}

	@Override
	public String toString() {
		return "L.C.M. of " + deValues().toString().replace("[", "").replace("]", "") + " & " + getValues().get(valueCount() - 1) + " = " + getLowestCommonMultiple();
	}

}


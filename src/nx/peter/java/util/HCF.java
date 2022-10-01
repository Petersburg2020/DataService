package nx.peter.java.util;

import java.util.ArrayList;
import java.util.List;

public class HCF {
	// protected int value1, value2;
	protected List<Long> values;

	public HCF(long... values) {
		set(values);
	}

	/*public HCF(int value1, int value2) {
	 set(value1, value2);
	 }*/

	public void reset() {
		values = new ArrayList<>();
	}

	/*public void set(int value1, int value2) {
	 this.value1 = value1;
	 this.value2 = value2;
	 }*/

	public void set(long... values) {
		reset();
		for (long value : values)
			this.values.add(value);
	}

	/*
	 public int getValue1() {
	 return value1;
	 }

	 public int getValue2() {
	 return value2;
	 }*/

	public int valueCount() {
		return values.size();
	}

	public List<Long> getValues() {
		return new ArrayList<>(values);
	}

	public long getHighestCommonFactor() {
		long hcf = 0;
		if (valueCount() > 1) {
			Factor factor0 = new Factor(values.get(0));
			boolean temp;
			for (long factor : factor0.getFactors()) {
				temp = true;
				for (int n = 1; n < valueCount(); n++) {
					Factor factor1 = new Factor(values.get(n));
					if (!factor1.isFactor(factor) && factor > 1L) {
						temp = false;
					}
				}
				if (temp) hcf = factor;
			}
		}
		/*Factor factor1 = new Factor(value1);
		 Factor factor2 = new Factor(value2);
		 for (int factor : factor2.getFactors())
		 if (factor1.isFactor(factor) && factor > 1)
		 hcf = factor;*/
		return hcf;
	}

	protected List<Long> deValues() {
		List<Long> de = getValues();
		de.remove(valueCount() - 1);
		return de;
	}

	@Override
	public String toString() {
		return "H.C.F. of " + deValues().toString().replace("[", "").replace("]", "") + " & " + getValues().get(valueCount() - 1) + " = " + getHighestCommonFactor();
	}


}


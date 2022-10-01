package nx.peter.java.util.param;

public class IntPair extends NumPair<Integer> {
	public IntPair() {
		super();
	}
	
	public IntPair(int value1, int value2) {
		super(value1, value2);
	}

	@Override
	public void reset() {
		value1 = 0;
		value2 = 0;
	}

	public boolean isDifference(int diff) {
		return Math.abs(value1 - value2) == Math.abs(diff);
	}
	
	public boolean isProduct(long product) {
		return (long) value1 * value2 == product;
	}

	public boolean isDivision(double div) {
		return 1.0 * value1/value2 == div || 1.0 * value2/value1 == div;
	}

	@Override
	public boolean isSum(Integer sum) {
		return value1 + value2 == sum;
	}

	@Override
	public boolean isDifference(Integer diff) {
		return Math.abs(value1 - value2) == Math.abs(diff);
	}

	@Override
	public boolean isProduct(Integer product) {
		return value1 * value2 == product;
	}

	@Override
	public boolean isDivision(Integer div) {
		return value1/value2 == div || 1.0 * value2/value1 == div;
	}
}

package nx.peter.java.util.param;

import nx.peter.java.util.Fraction;

public class DoublePair extends Pair<Double> {
	public DoublePair() {
		super();
	}
	
	public DoublePair(double value1, double value2) {
		super(value1, value2);
	}

	@Override
	public void reset() {
		value1 = 0.0;
		value2 = 0.0;
	}
	
	public void setValue1(Fraction value1) {
		setValue1(value1 != null ? value1.getDecimal() : getValue1());
	}
	
	public Fraction getFraction1() {
		return new Fraction(value1);
	}
	
	public Fraction getFraction2() {
		return new Fraction(value2);
	}
	
}

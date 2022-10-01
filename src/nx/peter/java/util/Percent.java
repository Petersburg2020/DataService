package nx.peter.java.util;

public class Percent {
	protected double percent;

	public Percent(double percent) {
		set(percent);
	}

	public Percent(double numerator, double denominator) {
		set(numerator, denominator);
	}

	public void set(double percent) {
		this.percent = percent;
	}

	public void set(double numerator, double denominator) {
		set(numerator/denominator);
	}

	public double getPercent() {
		return percent;
	}

	public double getPercentageOf(double value) {
		return percent * value /100;
	}

	public double getTotalFrom(double value) {
		return (100 * value)/percent;
	}

	public Fraction getFraction() {
		return new Fraction(percent, 100);
	}

	@Override
	public String toString() {
		return Util.toNDecimalPlace(percent, 4) + "%";
	}
}


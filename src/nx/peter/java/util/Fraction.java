package nx.peter.java.util;

import nx.peter.java.util.data.Number;

public class Fraction {
	protected long numerator, denominator;
	protected double decimal;

	public Fraction(double decimal) {
		set(decimal);
	}

	public Fraction(double numerator, double denominator) {
		set(numerator, denominator);
	}

	public Fraction(long numerator, long denominator) {
		set(numerator, denominator);
	}

	public Fraction reset() {
		numerator = 0;
		denominator = 0;
		decimal = 0;
		return this;
	}

	public Fraction set(double decimal) {
		this.decimal = decimal;
		// System.out.println("Dec: " + decimal);
		long d = Util.getDecimalPortion(decimal);
		if (d > 0) {
			long i = Util.getWholePortion(decimal);
			int digits = Util.digitCount(d);
			denominator = (long) Math.pow(10, digits);
			numerator = (long) (decimal * Math.pow(10, digits));
			toLowestForm(numerator, denominator);
			if (i > 0)
				numerator += i * denominator;
		} else {
			numerator = Util.getWholePortion(decimal);
			denominator = 1;
		}
		if (decimal < 0 && numerator > 0)
			numerator *= -1;
		return set(numerator, denominator);
	}

	public Fraction set(Fraction fraction) {
		if (fraction != null)
			return set(fraction.decimal);
		return this;
	}

	public void setDenominator(long denominator) {
		this.denominator = denominator;
	}

	public void setNumerator(long numerator) {
		this.numerator = numerator;
	}

	protected void toLowestForm(long numerator, long denominator) {
		// divide through by their common factors till there isn't anymore
		long num = numerator;
		long den = denominator;
		Factor factor = new Factor(num); // extract the numerator's factors
		long hcf = factor.getHCF(den).getHighestCommonFactor(); // Get their highest common factor

		while (hcf > 1) {
			num = num / hcf;
			den = den / hcf;

			factor = new Factor(num);
			hcf = factor.getHCF(den).getHighestCommonFactor();
			if (hcf == 1L)
				break;
		}
		this.numerator = num;
		this.denominator = den;
	}

	protected double abs(double number) {
		return Math.abs(number);
	}

	protected long abs(long number) {
		return Math.abs(number);
	}

	public Fraction set(double numerator, double denominator) {
		int tens = (int) Math.max(Util.getDecimalPortion(abs(numerator)), Util.getDecimalPortion(abs(denominator)));
		return set((long) (numerator * Math.pow(10, tens)), (long) (denominator * Math.pow(10, tens)));
	}

	public Fraction set(long numerator, long denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
		decimal = numerator/(denominator * 1.0);
		toLowestForm(this.numerator, this.denominator);
		if (Util.getWholePortion(denominator) > 0)
			this.numerator += Util.getWholePortion(decimal) * denominator;
		if (decimal < 0) {
			this.numerator *= -1;
			this.denominator = abs(this.denominator);
		}
		return this;
	}

	public String get() {
		return getFraction();
	}

	public LCM getLCM(Fraction... others) {
		long[] other = new long[others.length + 1];
		for (int index = 0; index < others.length; index++)
			if (others[index] != null)
			other[index] = others[index].getDenominator();
		other[others.length] = denominator; // include this fraction's denominator
		return new LCM(other);
	}

	public Fraction add(Fraction another) {
		return set(another.decimal + decimal);
	}

	public Fraction add(double decimal) {
		return add(new Fraction(decimal));
	}
	
	public Fraction add(Number number) {
		return add(number.getDouble());
	}

	public Fraction subtract(Fraction another) {
		return set(decimal - another.decimal);
	}

	public Fraction subtract(double decimal) {
		return subtract(new Fraction(decimal));
	}

	public Fraction subtract(Number number) {
		return subtract(number.getDouble());
	}

	public Fraction multiply(Fraction another) {
		return set(decimal * another.decimal);
	}

	public Fraction multiply(double decimal) {
		return multiply(new Fraction(decimal));
	}

	public Fraction multiply(Number number) {
		return multiply(number.getDouble());
	}

	public Fraction divide(Fraction another) {
		return set(decimal/another.decimal);
	}

	public Fraction divide(double decimal) {
		return divide(new Fraction(decimal));
	}

	public Fraction divide(Number number) {
		return divide(number.getDouble());
	}

	public Fraction inverse() {
		set(denominator, numerator);
		return this;
	}

	public Percent toPercent() {
		return new Percent(numerator * 100, denominator);
	}

	public long getNumerator() {
		return (isNegative() ? -1 : 1) * numerator;
	}

	public long getDenominator() {
		return denominator;
	}

	public double getDecimal() {
		return decimal;
	}
	
	public int toInteger() {
		return (int) (numerator * denominator);
	}

	public boolean isZero() {
		return decimal == 0;
	}

	public boolean isNegative() {
		return decimal < 0;
	}

	public boolean isPositive() {
		return decimal >= 0;
	}
	
	public boolean isFraction() {
		return !isInteger() && denominator != 0;
	}
	
	public boolean isImproper() {
		return isFraction() && numerator > denominator;
	}
	
	public boolean isMixed() {
		return isImproper();
	}
	
	public boolean isProper() {
		return isFraction() && numerator < denominator;
	}
	
	public boolean isInteger() {
		return Math.abs(denominator) == 1;
	}

	public boolean equals(Fraction another) {
		return equals(another.getDecimal());
	}

	public boolean equals(double decimal) {
		return this.decimal == decimal;
	}
	
	public String getFraction() {
		return numerator + (denominator == 1 ? "" : "/" + denominator);
	}
	
	public Mixed getMixed() {
		if (isMixed())
			return new Mixed(decimal);
		return new Mixed(0);
	}
	
	public Improper getImproper() {
		if (isProper())
			return new Improper(decimal);
		return new Improper(0);
	}
	
	public Proper getProper() {
		if (isProper())
			return new Proper(decimal);
		return new Proper(0);
	}

	@Override
	public String toString() {
		return Util.toNDecimalPlace(decimal, 4) + " = " + getFraction();
	}

	public String trueValue() {
		return isInteger() ? getNumerator() + "" : getDecimal() + "";
	}
	
	public Type getType() {
		if (isZero())
			return Type.Zero;
		else if (numerator != 0 && denominator == 0)
			return Type.Infinity;
		else if (isImproper())
			return Type.ImproperFraction;
		else if (isProper())
			return Type.ProperFraction;
		else
			return Type.WholeNumber;
	}

	
	
	
	public enum Type {
		ImproperFraction,
		Infinity,
		MixedFraction,
		ProperFraction,
		WholeNumber,
		Zero
	}
	
	
	protected static class Mixed extends Fraction {
		protected long whole;
		
		public Mixed(double number) {
			super(number);
		}
		
		public Mixed(double numerator, double denominator) {
			super(numerator, denominator);
		}

		@Override
		public Mixed set(double decimal) {
			if (new Fraction(decimal).isImproper())
				super.set(decimal);
			return this;
		}

		@Override
		public Mixed set(double numerator, double denominator) {
			if (new Fraction(numerator, denominator).isImproper())
				super.set(numerator, denominator);
			return this;
		}

		@Override
		public Mixed set(long numerator, long denominator) {
			if (new Fraction(numerator, denominator).isImproper()) {
				super.set(numerator, denominator);
				whole = Util.getWholePortion(decimal);
				this.numerator -= (whole * this.denominator);
			}
			return this;
		}
		
		public long getWhole() {
			return whole;
		}

		public Improper toImproper() {
			return new Improper(decimal);
		}

		@Override
		public String toString() {
			return whole + "(" + super.toString() + ")";
		}
	}
	
	protected static class Proper extends Fraction {
		public Proper(double number) {
			super(number);
		}

		public Proper(double numerator, double denominator) {
			super(numerator, denominator);
		}

		@Override
		public Fraction set(double decimal) {
			if (new Fraction(decimal).isProper())
				super.set(decimal);
			return this;
		}

		@Override
		public Proper set(long numerator, long denominator) {
			if (new Fraction(numerator, denominator).isProper())
				super.set(numerator, denominator);
			return this;
		}

		@Override
		public Proper set(double numerator, double denominator) {
			if (new Fraction(numerator, denominator).isProper())
				super.set(numerator, denominator);
			return this;
		}
		
	}
	
	protected static class Improper extends Fraction {
		public Improper(double number) {
			super(number);
		}

		public Improper(double numerator, double denominator) {
			super(numerator, denominator);
		}
		
		public Mixed toMixed() {
			return new Mixed(decimal);
		}

		@Override
		public Improper set(double decimal) {
			if (new Fraction(decimal).isImproper())
				super.set(decimal);
			return this;
		}

		@Override
		public Improper set(long numerator, long denominator) {
			if (new Fraction(numerator, denominator).isImproper())
				super.set(numerator, denominator);
			return this;
		}

		@Override
		public Improper set(double numerator, double denominator) {
			if (new Fraction(numerator, denominator).isImproper())
				super.set(numerator, denominator);
			return this;
		}
		
	}
}



package nx.peter.java.util.data;

import nx.peter.java.util.Fraction;

public class Number extends Data<Number> {
	public Number(){
		super();
	}

	public Number(int number) {
		super(String.valueOf(number));
	}
	
	public Number(double number) {
		super(String.valueOf(number));
	}

	public Number(char number){
		super(number);
	}
	
	public Number(Number number) {
		super(number);
	}

	public Number(CharSequence number){
		super(number);
	}

	@Override
	public Number reset() {
		super.reset();
		data = "0";
		return this;
	}

	public void set(int number) {
		set(number + "");
	}
	
	public void set(double number) {
		set(number + "");
	}

	public void set(Fraction number) {
		set(number.trueValue() + "");
	}
	
	@Override
	public Number set(CharSequence number){
		if(DataManager.isNumberOnly(number))
			super.set(num(number.toString()));
		return this;
	}
	
	protected String num(String n) {
		return DataManager.isInteger(n) ? DataManager.extractIntegers(n).get(0) + "" : n;
	}
	
	public long getLong() {
		return (long) getDouble();
	}
	
	public float getFloat() {
		return (float) getDouble();
	}
	
	public double getDouble() {
		return !isEmpty() ? Double.parseDouble(data) : 0.0;
	}
	
	public int getInteger() {
		return (int) getDouble();
	}
	
	public boolean isInteger() {
		return getFraction().isInteger();
	}
	
	public boolean isDecimal() {
		return getFraction().isFraction();
	}
	
	public boolean isNegative() {
		return getFraction().isNegative();
	}
	
	public boolean isPositive() {
		return getFraction().isPositive();
	}
	
	public boolean isZero() {
		return getFraction().isZero();
	}
	
	public Number add(double data) {
		set(((isInteger() ? getInteger() : getDouble()) + (new Fraction(data).isInteger() ? (int) data : data)) + "");
		return this;
	}
	
	public Number add(Fraction data) {
		return add(data != null ? data.getDecimal() : 0);
	}
	
	public Number add(Number data) {
		return add(data != null ? data.getDouble() : 0);
	}

	public Number sub(double data) {
		set(((isInteger() ? getInteger() : getDouble()) - (new Fraction(data).isInteger() ? (int) data : data)) + "");
		return this;
	}

	public Number sub(Fraction data) {
		return sub(data != null ? data.getDecimal() : 0);
	}

	public Number sub(Number data) {
		return sub(data != null ? data.getDouble() : 0);
	}
	
	public Number mul(double data) {
		set(((isInteger() ? getInteger() : getDouble()) * (new Fraction(data).isInteger() ? (int) data : data)) + "");
		return this;
	}

	public Number mul(Fraction data) {
		return mul(data != null ? data.getDecimal() : 1);
	}

	public Number mul(Number data) {
		return mul(data != null ? data.getDouble() : 1);
	}
	
	public Number div(double data) {
		set(((isInteger() ? getInteger() : getDouble()) / (new Fraction(data).isInteger() ? (int) data : data)) + "");
		return this;
	}

	public Number div(Fraction data) {
		return div(data != null ? data.getDecimal() : 1);
	}

	public Number div(Number data) {
		return div(data != null ? data.getDouble() : 1);
	}

	public Number sqrt() {
		set((new Fraction(Math.sqrt(getDouble())).isInteger() ? (int) Math.sqrt(getDouble()) : Math.sqrt(getDouble())));
		return this;
	}
	
	public Fraction getFraction() {
		return new Fraction(getDouble());
	}
	
	public FractionData toFraction() {
		return new FractionData(getFraction());
	}

	@Override
	public DataType getType(){
		return DataType.Number;
	}

	public boolean equals(double data) {
		return getDouble() == data;
	}

	@Override
	public String toString() {
		return get();
	}
}

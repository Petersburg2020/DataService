package nx.peter.java.util.data;

import nx.peter.java.util.Util;
import nx.peter.java.util.Fraction;

public class Index extends Data<Index> {
	protected double value;

	public Index() {
		super();
	}

	public Index(char index) {
		this(index + "");
	}

	public Index(double index) {
		this(index + "");
		set(index);
	}

	public Index(int index) {
		this((double) index);
	}

	public Index(CharSequence index) {
		super(index);
	}

	public Index(CharSequence index, double value) {
		super(index);
		setValue(value);
	}

	public Index(int start, char index) {
		this(start, index + "");
	}

	public Index(int start, CharSequence index) {
		super(start, index);
	}

	public Index(int start, CharSequence index, double value) {
		super(start, index);
		setValue(value);
	}

	@Override
	public Index reset() {
		super.reset();
		value = 1;
		return this;
	}

	public Index setValue(double value) {
		this.value = value;
		return this;
	}

	public double getValue() {
		return value;
	}

	public Index set(double data) {
		return set(toIndex(data));
	}

	public Index set(int data) {
		return set(toIndex((double) data));
	}

	@Override
	public Index set(Index data) {
		if (data != null)
			set(data.index, data.get(), data.getValue());
		return this;
	}

	@Override
	public Index set(int index, CharSequence data) {
		if (DataManager.isIndexes(data))
			super.set(index, data);
		return this;
	}

	public Index set(int index, CharSequence data, double value) {
		super.set(data);
		this.index = index;
		return setValue(value);
	}

	public Number simplify() {
		return new Number(Math.pow(value, getIndex()));
	}

	public Index add(double another) {
		return add(new Index(another));
	}

	public Index add(int another) {
		return add(new Index(another));
	}

	public Index add(Index another) {
		return set(another.getIndex() + getIndex());
	}

	public Index sub(double another) {
		return sub(new Index(another));
	}

	public Index sub(int another) {
		return sub(new Index(another));
	}

	public Index sub(Index another) {
		return set(getIndex() - another.getIndex());
	}

	public Index mul(double another) {
		return mul(new Index(another));
	}

	public Index mul(int another) {
		return mul(new Index(another));
	}

	public Index mul(Index another) {
		return set(getIndex() * another.getIndex());
	}

	public Index div(double another) {
		return div(new Index(another));
	}

	public Index div(int another) {
		return div(new Index(another));
	}

	public Index div(Index another) {
		return set(getIndex() / another.getIndex());
	}

	public double sqrt() {
		return Math.sqrt(simplify().getDouble());
	}

	public Index abs() {
		return new Index(data, Math.abs(value));
	}

	public double getIndex() {
		StringBuilder ind = new StringBuilder();
		int index = 0;
		if (new Word().set(get()).getLetterCount("°") > 1)
			return 0;
		if (data.startsWith("-"))
			index = 1;
		for (char c : data.substring(index).toCharArray())
			if (c == '°')
				ind.append('.');
			else
				ind.append(getIndex(c));
		return ind.isEmpty() ? 0 : (index > 0 ? -1 : 1) * Double.parseDouble(ind.toString());
	}

	public Fraction toFraction() {
		return new Fraction(getIndex());
	}


	protected static int getIndex(char index) {
		switch (index + "") {
			case "¹":
				return 1;
			case "²":
				return 2;
			case "³":
				return 3;
			case "⁴":
				return 4;
			case "⁵":
				return 5;
			case "⁶":
				return 6;
			case "⁷":
				return 7;
			case "⁸":
				return 8;
			case "⁹":
				return 9;
			default:
				return 0;
		}
	}

	protected static String toIndex(int index) {
		switch (index) {
			case 1:
				return "¹";
			case 2:
				return "²";
			case 3:
				return "³";
			case 4:
				return "⁴";
			case 5:
				return "⁵";
			case 6:
				return "⁶";
			case 7:
				return "⁷";
			case 8:
				return "⁸";
			case 9:
				return "⁹";
			default:
				return "⁰";
		}
	}

	protected static String toIndex(char index) {
		if (index == '.')
			return "°";
		if (DataManager.isInteger(index))
			return toIndex(DataManager.extractIntegers(index + "").get(0));
		return "";
	}

	protected static String num(double num) {
		return num == (int) num ? (int) num + "" : Util.toNDecimalPlace(num, 2) + "";
	}

	protected static double abs(double num) {
		return Math.abs(num);
	}


	public static Index toIndex(Fraction value) {
		return value != null ? toIndex(value.getDecimal()) : new Index();
	}

	public static Index toIndex(double value) {
		String temp = value > 0 ? "" : "-";
		for (char c : num(abs(value)).toCharArray())
			temp += toIndex(c);
		return new Index(temp);
	}

	@Override
	public DataType getType() {
		return DataType.Index;
	}

}

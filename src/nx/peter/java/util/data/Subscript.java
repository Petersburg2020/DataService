package nx.peter.java.util.data;


import nx.peter.java.util.Util;

public class Subscript extends Data<Subscript> {

	public Subscript(double data) {
		super(toSubscript(data));
	}

	public Subscript(CharSequence data) {
		super(data);
		set(data);
	}

	public Subscript(char... data) {
		super(data);
	}
	
	public Subscript(Subscript data) {
		super(data);
	}
	
	public Subscript set(int data) {
		String sub = "";
		for (char c : (data + "").toCharArray())
			sub += toSubscript(c);
		return super.set(sub);
	}
	
	public Subscript set(double data) {
		return super.set(toSubscript(data));
	}

	@Override
	public Subscript set(CharSequence data) {
		if (data == null)
			return this;
		for (char c : data.toString().toCharArray())
			if (!isSubscript(c) && c != ' ')
				return this;
		return super.set(data);
	}
	
	public boolean isSubscript() {
		return isSubscript(data);
	}

	public static boolean isSubscript(char data) {
		return DataManager.isSubscript(data);
	}
	
	public static boolean isSubscript(CharSequence data) {
		if (data == null || !data.toString().isEmpty()) 
			return false;
		int index = 0, count = 0;
		for (char c : data.toString().toCharArray()) {
			if (c == '.' && count == 0 && data.length() > 1 && index > 0 && index < data.length() - 1) {
				count++;
				continue;
			} else if (!isSubscript(c))
				return false;
			index++;
		}
		return true;
	}


	@Override
	public DataType getType() {
		return DataType.Subscript;
	}


	protected static String toSubscript(double data) {
		StringBuilder sub = new StringBuilder();
		if ((data + "").startsWith("-"))
			sub.append("-");
		for (char c : (num(abs(data))).toCharArray())
			if (c == '.')
				sub.append(".");
			else
				sub.append(toSubscript(c));
		return sub.toString();
	}

	protected static String num(double num) {
		return num == (int) num ? (int) num + "" : Util.toNDecimalPlace(num, 2) + "";
	}

	protected static double abs(double num) {
		return Math.abs(num);
	}

	protected static String toSubscript(int data) {
		switch (data) {
			case 0:
				return "₀";
			case 1:
				return "₁";
			case 2:
				return "₂";
			case 3:
				return "₃";
			case 4:
				return "₄";
			case 5:
				return "₅";
			case 6:
				return "₆";
			case 7:
				return "₇";
			case 8:
				return "₈";
			case 9:
				return "₉";
			default:
				return "";
		}
	}
	
	public static Subscript parseSubscript(CharSequence data) {
		return new Subscript(toSubscript(data));
	}
	
	public boolean isNumber() {
		return isSubscriptNumber(data);
	}
	
	public static boolean isSubscriptNumber(CharSequence data) {
		if (data == null || data.toString().isEmpty())
			return false;
		for (char c : data.toString().toCharArray())
			if (DataManager.isSubscript(c))
				return false;
		return true;
	}

	public boolean isVariable() {
		return isSubscriptVariable(data);
	}

	public static boolean isSubscriptVariable(CharSequence data) {
		return DataManager.isSubscriptVariable(data);
	}
	
	public boolean isOperator() {
		return isSubscriptOperator(data);
	}

	public static boolean isSubscriptOperator(CharSequence data) {
		return DataManager.isSubscriptCharacter(data);
	}

	public boolean isCharacter() {
		return isSubscriptCharacter(data);
	}

	public static boolean isSubscriptCharacter(CharSequence data) {
		return DataManager.isSubscriptCharacter(data);
	}
	
	
	protected static String toSubscript(char data) {
		if (DataManager.isAlphabet(data) && !"aeox".contains((data + "").toLowerCase()))
			return "ₔ";
		if (DataManager.isNumber(data))
			return toSubscript(DataManager.extractIntegers(data + "").get(0));
		switch ((data + "").toLowerCase()) {
			case ")":
				return "₎";
			case "(":
				return "₍";
			case "+":
				return "₊";
			case "=":
				return "₌";
			case "-":
				return "₋";
			case "a":
				return "ₐ";
			case "e":
				return "ₑ";
			case "o":
				return "ₒ";
			case "x":
				return "ₓ";
			case " ":
				return " ";
			default:
				return "_";
		}
	}

	protected static String toSubscript(CharSequence data) {
		StringBuilder sub = new StringBuilder();
		if (data != null && !data.isEmpty())
			for (char c : data.toString().toCharArray())
				if (!isSubscript(c)) {
					sub.append(toSubscript(c));
				} else {
					sub.append(c);
				}
		return sub.toString();
	}
	
}

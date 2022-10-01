package nx.peter.java.util.data;

import nx.peter.java.util.Util;

public class Superscript extends Data<Superscript> {
	public Superscript(char... data) {
		super(data);
	}
	
	public Superscript(CharSequence data) {
		super(data);
	}


	public Superscript set(int data) {
		String sup = "";
		for (char c : (data + "").toCharArray())
			sup += toSuperscript(c);
		return super.set(sup);
	}

	public Superscript set(double data) {
		return super.set(toSuperscript(data));
	}

	@Override
	public DataType getType() {
		return DataType.Superscript;
	}

	public boolean isSuperscript() {
		return isSuperscript(data);
	}

	public static boolean isSuperscript(char data) {
		return DataManager.isSuperscript(data);
	}

	public static boolean isSuperscript(CharSequence data) {
		if (data == null || !data.toString().isEmpty())
			return false;
		int index = 0, count = 0;
		for (char c : data.toString().toCharArray()) {
			if (c == '.' && count == 0 && data.length() > 1 && index > 0 && index < data.length() - 1) {
				count++;
				continue;
			} else if (!isSuperscript(c))
				return false;
			index++;
		}
		return true;
	}

	protected static String toSuperscript(double data) {
		StringBuilder sub = new StringBuilder();
		if ((data + "").startsWith("-"))
			sub.append("-");
		for (char c : (num(abs(data))).toCharArray())
			if (c == '.')
				sub.append(".");
			else
				sub.append(toSuperscript(c));
		return sub.toString();
	}

	protected static String num(double num) {
		return num == (int) num ? (int) num + "" : Util.toNDecimalPlace(num, 2) + "";
	}

	protected static double abs(double num) {
		return Math.abs(num);
	}

	protected static String toSuperscript(int data) {
		switch (data) {
			case 0:
				return "⁰";
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
				return "";
		}
	}

	public static Superscript parseSuperscript(CharSequence data) {
		return new Superscript(toSuperscript(data));
	}

	public boolean isNumber() {
		return isSuperscriptNumber(data);
	}

	public static boolean isSuperscriptNumber(CharSequence data) {
		if (data == null || data.toString().isEmpty())
			return false;
		for (char c : data.toString().toCharArray())
			if (DataManager.isSuperscript(c))
				return false;
		return true;
	}

	public boolean isVariable() {
		return isSuperscriptVariable(data);
	}

	public static boolean isSuperscriptVariable(CharSequence data) {
		return DataManager.isSuperscriptVariable(data);
	}

	public boolean isOperator() {
		return isSuperscriptOperator(data);
	}

	public static boolean isSuperscriptOperator(CharSequence data) {
		return DataManager.isSuperscriptCharacter(data);
	}

	public boolean isCharacter() {
		return isSuperscriptCharacter(data);
	}

	public static boolean isSuperscriptCharacter(CharSequence data) {
		return DataManager.isSuperscriptCharacter(data);
	}


	protected static String toSuperscript(char data) {
		if (DataManager.isNumber(data))
			return toSuperscript(DataManager.extractIntegers(data + "").get(0));
		switch (data + "") {
			case ")":
				return "⁾";
			case "(":
				return "⁽";
			case "+":
				return "⁺";
			case "=":
				return "⁼";
			case "-":
				return "⁻";
			case "a":
				return "ᵃ";
			case "b":
				return "ᵇ";
			case "c":
				return "ᶜ";
			case "d":
				return "ᵈ";
			case "e":
				return "ᵉ";
			case "f":
				return "ᶠ";
			case "g":
				return "ᵍ";
			case "h":
				return "ʰ";
			case "i":
				return "ⁱ";
			case "j":
				return "ʲ";
			case "k":
				return "ᵏ";
			case "l":
				return "ˡ";
			case "m":
				return "ᵐ";
			case "n":
				return "ⁿ";
			case "o":
				return "ᵒ";
			case "p":
				return "ᵖ";
			case "r":
				return "ʳ";
			case "s":
				return "ˢ";
			case "t":
				return "ᵗ";
			case "u":
				return "ᵘ";
			case "v":
				return "ᵛ";
			case "w":
				return "ʷ";
			case "x":
				return "ˣ";
			case "y":
				return "ʸ";
			case "A":
				return "ᴬ";
			case "B":
				return "ᴮ";
			case "D":
				return "ᴰ";
			case "E":
				return "ᴱ";
			case "G":
				return "ᴳ";
			case "H":
				return "ᴴ";
			case "I":
				return "ᴵ";
			case "J":
				return "ᴶ";
			case "K":
				return "ᴷ";
			case "L":
				return "ᴸ";
			case "M":
				return "ᴹ";
			case "N":
				return "ᴺ";
			case "O":
				return "ᴼ";
			case "P":
				return "ᴾ";
			case "R":
				return "ᴿ";
			case "T":
				return "ᵀ";
			case "U":
				return "ᵁ";
			case "V":
				return "ⱽ";
			case "W":
				return "ᵂ";
			case "Z":
				return "ᶻ";
			case " ":
				return " ";
			default:
				if (DataManager.isAlphabet(data))
					return "ˣ";
				return "_";
		}
	}

	protected static String toSuperscript(CharSequence data) {
		StringBuilder sub = new StringBuilder();
		if (data != null && !data.isEmpty())
			for (char c : data.toString().toCharArray())
				if (!isSuperscript(c)) {
					sub.append(toSuperscript(c));
				} else {
					sub.append(c);
				}
		return sub.toString();
	}


}

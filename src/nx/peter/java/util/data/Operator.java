package nx.peter.java.util.data;

import nx.peter.java.util.Random;

public class Operator extends Letter<Operator> {
	public Operator() {
		super();
	}

	public Operator(char operator) {
		super(operator);
	}

	public Operator(CharSequence operator) {
		super(operator);
	}

	@Override
	public Operator set(CharSequence operator) {
		if (DataManager.isOperator(operator))
			data = operator.toString();
		return this;
	}

	public boolean isOperator() {
		return DataManager.isOperator(data);
	}

	public static Operator generate() {
		return new Operator(DataManager.OPERATORS.charAt(Random.nextInt(DataManager.OPERATORS.length() - 1)));
	}

	@Override
	public DataType getType() {
		return DataType.Operator;
	}

}

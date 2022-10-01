package nx.peter.java.util.param;

public abstract class Pair<T> {
	protected T value1, value2;
	
	public Pair() {
		reset();
	}

	public Pair(T value1, T value2) {
		this.value1 = value1;
		this.value2 = value2;
	}
	
	public abstract void reset();

	public void setValue1(T value1) {
		this.value1 = value1;
	}

	public T getValue1() {
		return value1;
	}

	public void setValue2(T value2) {
		this.value2 = value2;
	}

	public T getValue2() {
		return value2;
	}
	
	public boolean isEqual() {
		return value1 != null && value1.equals(value2);
	}

	public boolean isNotEqual() {
		return !isEqual();
	}
	
	public boolean contains(T value) {
		return value != null && (value.equals(value1) || value.equals(value2));
	}
	
	public boolean equals(Pair<T> another) {
		return another != null 
				&& ((another.getValue1().equals(value1) && another.getValue2().equals(value2)) 
				|| (another.getValue1().equals(value2) && another.getValue2().equals(value1)));
	}

	@Override
	public String toString() {
		return value1 + "-" + value2;
	}
}

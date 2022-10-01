package nx.peter.java.util.param;

public class StringPair extends Pair<String> {
	public StringPair() {
		super();
	}
	
	public StringPair(String value1, String value2) {
		super(value1, value2);
	}

	@Override
	public void reset() {
		value1 = "";
		value2 = "";
	}
	
}

package nx.peter.java.util.param;
import java.util.List;

public class FactorPair {
	private final long number;
	private final List<LongPair> pairs;

	public FactorPair(long number, List<LongPair> pairs) {
		this.number = number;
		this.pairs = pairs;
	}

	public List<LongPair> getPairs() {
		return pairs;
	}

	public long getNumber() {
		return number;
	}
	
	public boolean isEmpty() {
		return pairs.isEmpty();
	}
	
	public boolean hasPair(long value1, long value2) {
		return hasPair(new LongPair(value1, value2));
	}
	
	public boolean hasPair(LongPair pair) {
		for (LongPair iPair : pairs)
			if (iPair.equals(pair))
				return true;
		return false;
	}
	
	public LongPair getSumPair(long value) {
		for (LongPair pair : pairs)
			if (pair.isSum(value))
				return pair;
		return null;
	}
	
	public boolean hasSumPair(int value) {
		return getSumPair(value) != null;
	}

	public LongPair getProductPair(long value) {
		for (LongPair pair : pairs)
			if (pair.isProduct(value))
				return pair;
		return null;
	}

	public boolean hasProductPair(long value) {
		return getProductPair(value) != null;
	}
	
	public LongPair getEqualPair(FactorPair fPair) {
		if (fPair != null)
			for (LongPair pair : fPair.getPairs())
				if (hasPair(pair))
					return pair;
		return null;
	}
	
	public boolean hasEqualPair(FactorPair pair) {
		return getEqualPair(pair) != null;
	}

	@Override
	public String toString() {
		return "Factor Pairs of " + number + " are: " + pairs.toString().replace("[", "").replace("]", "");
	}
}

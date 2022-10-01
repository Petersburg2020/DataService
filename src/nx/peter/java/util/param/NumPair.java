package nx.peter.java.util.param;

import nx.peter.java.util.Util;

public abstract class NumPair<N extends Number> extends Pair<N> {

    public NumPair() {
        super();
    }

    public NumPair(N value1, N value2) {
        super(value1, value2);
    }

    public abstract boolean isSum(N sum);

    public abstract boolean isDifference(N diff);

    public abstract boolean isProduct(N product);

    public abstract boolean isDivision(N div);

    public N getMin() {
        return getMinMax().min();
    }

    public N getMax() {
        return getMinMax().max();
    }

    public Util.MinMax<N> getMinMax() {
        return new Util.MinMax<>(value1, value2);
    }

}

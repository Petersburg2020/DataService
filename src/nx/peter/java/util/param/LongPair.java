package nx.peter.java.util.param;

public class LongPair extends NumPair<Long> {

    public LongPair() {
        super();
    }

    public LongPair(Long value1, Long value2) {
        super(value1, value2);
    }

    @Override
    public void reset() {
        value1 = 0L;
        value2 = 0L;
    }

    @Override
    public boolean isSum(Long sum) {
        return false;
    }

    @Override
    public boolean isDifference(Long diff) {
        return false;
    }

    @Override
    public boolean isProduct(Long product) {
        return false;
    }

    @Override
    public boolean isDivision(Long div) {
        return false;
    }
}

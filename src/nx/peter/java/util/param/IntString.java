package nx.peter.java.util.param;

public class IntString implements Param<IntString, Integer, String> {
    public final String letter;
    public final int index;

    public IntString(char letter, int index) {
        this(letter + "", index);
    }

    public IntString(CharSequence letter, int index) {
        this.letter = letter.toString();
        this.index = index;
    }

    @Override
    public Integer getFirst() {
        return index;
    }

    @Override
    public String getSecond() {
        return letter;
    }

    @Override
    public IntString getParam() {
        return this;
    }

    @Override
    public boolean equals(IntString param) {
        return param != null && param.letter.contentEquals(letter) && param.index == index;
    }
}

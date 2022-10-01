package nx.peter.java.util.data;

import nx.peter.java.util.Random;

public class Alphabet extends Letter<Alphabet> {
    public Alphabet() {
        super();
    }

    public Alphabet(char alphabet) {
        super(alphabet);
    }

    public Alphabet(CharSequence alphabet) {
        super(alphabet);
    }

    @Override
    public Alphabet reset() {
        return (Alphabet) super.reset();
    }

    @Override
    public Alphabet set(CharSequence alphabet) {
        if (DataManager.isAlphabet(alphabet))
            this.data = alphabet.toString();
        return this;
    }

    public boolean isAlphabet() {
        return DataManager.isAlphabet(data);
    }

    public int toNumber() {
        return DataManager.toNumber(data);
    }

    public static Alphabet generate() {
        return new Alphabet(DataManager.ALPHABETS.charAt(Random.nextInt(DataManager.ALPHABETS.length() - 1)));
    }

    @Override
    public DataType getType() {
        return DataType.Alphabet;
    }

}

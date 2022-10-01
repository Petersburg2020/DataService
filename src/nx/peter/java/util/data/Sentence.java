package nx.peter.java.util.data;

public class Sentence extends ISentence<Sentence> {

	public Sentence() {
		super();
	}

	public Sentence(CharSequence sentence) {
		super(sentence);
	}

	@Override
	public DataType getType() {
		return DataType.Sentence;
	}

}

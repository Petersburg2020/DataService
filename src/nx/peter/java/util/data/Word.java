package nx.peter.java.util.data;

public class Word extends Letters<Word> {
    public enum PartOfSpeech {
        Adjective,
        Adverb,
        Conjunction,
        Interjection,
        Noun,
        Pronoun,
        Unknown,
        Verb
    }

    protected PartOfSpeech partOfSpeech;

    public Word(){
        super();
    }

    public Word(char... word) {
        super(word);
    }

    public Word(CharSequence word){
        this(word, PartOfSpeech.Unknown);
    }

    public Word(CharSequence word, PartOfSpeech pos) {
        super(word);
        setPartOfSpeech(pos);
    }

    @Override
    public Word reset() {
        super.reset();
        partOfSpeech = PartOfSpeech.Unknown;
        return this;
    }

    @Override
    public Word set(CharSequence data) {
        return set(data, null);
    }

    public Word set(CharSequence word, PartOfSpeech pos) {
        super.set(word);
        return setPartOfSpeech(pos != null ? pos : PartOfSpeech.Unknown);
    }


    public Word setPartOfSpeech(PartOfSpeech partOfSpeech) {
        if (partOfSpeech != null)
            this.partOfSpeech = partOfSpeech;
        return this;
    }

    public PartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }

    @Override
    public DataType getType() {
        return DataType.Word;
    }

    @Override
    public String toString() {
        return "(" + (!partOfSpeech.equals(PartOfSpeech.Unknown) && partOfSpeech.toString().length() > 4 ? partOfSpeech.toString().substring(0, 3) : partOfSpeech) + ") " + super.toString();
    }


}

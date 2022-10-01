package nx.peter.java.util.data.word;

import nx.peter.java.util.data.Word;

public class Noun extends Word {

    public Noun(){
        super();
    }

    public Noun(char... noun){
        super(noun);
    }

    public Noun(CharSequence noun){
        super(noun);
    }

    public Noun(Word noun) {
        this(noun.get());
    }

    @Override
    public Noun set(CharSequence noun) {
        return (Noun) super.set(noun);
    }

    @Override
    public Noun set(char... noun) {
        return (Noun) super.set(noun);
    }

    @Override
    public Word set(Word noun) {
        return super.set(noun);
    }

    @Override
    public PartOfSpeech getPartOfSpeech() {
        return PartOfSpeech.Noun;
    }

}

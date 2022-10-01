package nx.peter.java.util.data.word;

import nx.peter.java.util.data.Word;

public class Adverb extends Word {

    public Adverb(){
        super();
    }

    public Adverb(char... adverb){
        super(adverb);
    }

    public Adverb(CharSequence adverb){
        super(adverb);
    }

    @Override
    public PartOfSpeech getPartOfSpeech() {
        return PartOfSpeech.Adverb;
    }
}

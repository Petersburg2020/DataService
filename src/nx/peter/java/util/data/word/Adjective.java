package nx.peter.java.util.data.word;

import nx.peter.java.util.data.Word;

public class Adjective extends Word {

    public Adjective(){
        super();
    }

    public Adjective(char... adjective){
        super(adjective);
    }

    public Adjective(CharSequence adjective){
        super(adjective);
    }

    public Adjective(Word adjective) {
        this(adjective.get());
    }

    @Override
    public PartOfSpeech getPartOfSpeech() {
        return PartOfSpeech.Adjective;
    }

}

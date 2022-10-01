package nx.peter.java.util.data.word;

import nx.peter.java.util.data.Word;

public class Verb extends Word {

    public Verb(){
        super();
    }

    public Verb(char... verb){
        super(verb);
    }

    public Verb(CharSequence verb){
        super(verb);
    }

    public Verb(Word verb) {
        this(verb.get());
    }

    @Override
    public PartOfSpeech getPartOfSpeech() {
        return PartOfSpeech.Verb;
    }

}

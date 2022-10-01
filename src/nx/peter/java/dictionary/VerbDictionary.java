package nx.peter.java.dictionary;

import nx.peter.java.util.data.Word;
import nx.peter.java.util.data.word.Verb;

import java.util.ArrayList;
import java.util.List;

public class VerbDictionary extends Dictionary.Builder {
    public VerbDictionary(){
        super(Type.Verb);
    }

    public List<Verb> getAllVerbs() {
        List<Verb> verbs = new ArrayList<>();
        for (Word word : super.getWords())
            verbs.add(new Verb(word));
        return verbs;
    }

}
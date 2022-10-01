package nx.peter.java.dictionary;


import nx.peter.java.util.data.Word;
import nx.peter.java.util.data.word.Noun;

import java.util.ArrayList;
import java.util.List;

public class NounDictionary extends Dictionary.Builder {

    public NounDictionary(){
        super(Type.Noun);
    }

    public List<Noun> getNouns() {
        List<Noun> nouns = new ArrayList<>();
        for (Word word : super.getWords())
            nouns.add(new Noun(word));
        return nouns;
    }

}

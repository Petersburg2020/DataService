package nx.peter.java.dictionary;

import nx.peter.java.util.data.Word;
import nx.peter.java.util.data.word.Adverb;

import java.util.ArrayList;
import java.util.List;

public class AdverbDictionary  extends Dictionary.Builder {
    public AdverbDictionary(){
        super(Type.Adverb);
    }

    public List<Adverb> getAdverbs() {
        List<Adverb> adverbs = new ArrayList<>();
        for (Word word : super.getWords())
            adverbs.add(new Adverb(word.get()));
        return adverbs;
    }

}
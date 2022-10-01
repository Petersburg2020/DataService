package nx.peter.java.dictionary;

import nx.peter.java.util.data.Word;
import nx.peter.java.util.data.word.Adjective;

import java.util.ArrayList;
import java.util.List;

public class AdjectiveDictionary extends Dictionary.Builder {

    public AdjectiveDictionary(){
        super(Type.Adjective);
    }

    public List<Adjective> getAllAdjectives() {
        List<Adjective> adjectives = new ArrayList<>();
        for (Word word : super.getWords())
            adjectives.add(new Adjective(word));
        return adjectives;
    }

}
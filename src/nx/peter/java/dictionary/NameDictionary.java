package nx.peter.java.dictionary;

import nx.peter.java.util.data.Word;
import nx.peter.java.util.data.word.Name;

import java.util.ArrayList;
import java.util.List;

public class NameDictionary extends Dictionary.Builder {
    public NameDictionary() {
        super(Type.Name);
    }

    public List<Name> getAllNames() {
        List<Name> names = new ArrayList<>();
        for (Word word : super.getWords())
            names.add(new Name(word));
        return names;
    }

}

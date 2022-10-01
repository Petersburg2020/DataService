package nx.peter.java.dictionary;

import nx.peter.java.util.data.Word;
import nx.peter.java.util.data.word.Country;

import java.util.ArrayList;
import java.util.List;

public class CountryDictionary extends Dictionary.Builder {
    public CountryDictionary() {
        super(Type.Country);
    }

    public List<Country> getCountries() {
        List<Country> verbs = new ArrayList<>();
        for (Word word : super.getWords())
            verbs.add(new Country(word));
        return verbs;
    }

}

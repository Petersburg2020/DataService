package nx.peter.java.dictionary;

import nx.peter.java.util.data.Word;
import nx.peter.java.util.data.word.Bank;

import java.util.ArrayList;
import java.util.List;

public class BankDictionary extends Dictionary.Builder {

    public BankDictionary() {
        super(Type.Bank);
    }

    public List<Bank> getAllBanks() {
        List<Bank> banks = new ArrayList<>();
        for (Word word : super.getWords())
            banks.add(new Bank(word));
        return banks;
    }

}

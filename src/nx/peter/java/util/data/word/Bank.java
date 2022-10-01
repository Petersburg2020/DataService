package nx.peter.java.util.data.word;

import nx.peter.java.util.data.Word;

public class Bank extends Noun {

    public Bank(){
        super();
    }

    public Bank(char... bank){
        super(bank);
    }

    public Bank(CharSequence bank){
        super(bank);
    }

    public Bank(Word bank) {
        this(bank.get());
    }

}

package nx.peter.java.util.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Texts extends ISentence<Texts> {

    public Texts() {
        this("");
    }

    public Texts(CharSequence paragraph) {
        super(paragraph);
    }

    @Override
    public DataType getType() {
        return DataType.Texts;
    }

}

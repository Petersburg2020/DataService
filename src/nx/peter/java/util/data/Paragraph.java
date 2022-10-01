package nx.peter.java.util.data;

public class Paragraph extends ISentence<Paragraph> {

    public Paragraph() {
        this("");
    }

    public Paragraph(CharSequence paragraph) {
        super(paragraph);
    }


    @Override
    public DataType getType() {
        return DataType.Paragraph;
    }
}

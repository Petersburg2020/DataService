package nx.peter.java.document.type;

import nx.peter.java.document.Document;
import nx.peter.java.document.core.Page;

import java.io.File;
import java.util.List;

/**
 * A {@link Document} class that represents a Thesis.<br>
 * It extends the {@link Document} class
 */
public class Thesis extends Document<Thesis> {
    public static final String START_ENCODE = "<THESIS>";
    public static final String END_ENCODE = "</THESIS>";
    public static final String PIS_TAG = "THESIS";
    public static final String JSON_TAG = "thesis";


    /**
     * Constructs an Instance of {@link Document} with {@link Type}<b>.Thesis "Type"</b> and empty pages
     */
    public Thesis() {
        super(Type.Thesis);
    }

    /**
     * Constructs an Instance of {@link Document} with {@link Type}<b>.Thesis "Type"</b> and {@link List} of {@link Page}
     * @param pages the pages of document
     */
    public Thesis(List<Page> pages) {
        super(Type.Thesis, pages);
    }

    /**
     * Constructs an Instance of {@link Document} with {@link Type}<b>.Thesis "Type"</b> and <b>filePath</b>
     * @param filePath the path to document {@link File}
     */
    public Thesis(CharSequence filePath) {
        super(Type.Thesis, filePath);
    }

}

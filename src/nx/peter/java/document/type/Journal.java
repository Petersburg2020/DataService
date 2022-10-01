package nx.peter.java.document.type;

import nx.peter.java.document.Document;
import nx.peter.java.document.core.Page;

import java.io.File;
import java.util.List;

/**
 * A {@link Document} class that represents a Journal.<br>
 * It extends the {@link Document} class
 */
public class Journal extends Document<Journal> {
    public static final String PIS_TAG = "JOURNAL";
    public static final String JSON_TAG = "journal";


    /**
     * Constructs an Instance of {@link Document} with {@link Type}<b>.Journal "Type"</b> and empty pages
     */
    public Journal() {
        super(Type.Journal);
    }

    /**
     * Constructs an Instance of {@link Document} with {@link Type}<b>.Journal "Type"</b> and {@link List} of {@link Page}
     * @param pages the pages of document
     */
    public Journal(List<Page> pages) {
        super(Type.Journal, pages);
    }

    /**
     * Constructs an Instance of {@link Document} with {@link Type}<b>.Journal "Type"</b> and <b>filePath</b>
     * @param filePath the path to document {@link File}
     */
    public Journal(CharSequence filePath) {
        super(Type.Journal, filePath);
    }


}

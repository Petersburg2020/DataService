package nx.peter.java.document.type;

import nx.peter.java.document.Document;
import nx.peter.java.document.core.Page;

import java.io.File;
import java.util.List;

/**
 * A {@link Document} class that represents a Magazine.<br>
 * It extends the {@link Document} class
 */
public class Magazine extends Document<Magazine> {
    public static final String START_ENCODE = "<MAGAZINE>";
    public static final String END_ENCODE = "</MAGAZINE>";
    public static final String PIS_TAG = "MAGAZINE";
    public static final String JSON_TAG = "magazine";


    /**
     * Constructs an Instance of {@link Document} with {@link Type}<b>.Magazine "Type"</b> and empty pages
     */
    public Magazine() {
        super(Type.Magazine);
    }

    /**
     * Constructs an Instance of {@link Document} with {@link Type}<b>.Magazine "Type"</b> and {@link List} of {@link Page}
     * @param pages the pages of document
     */
    public Magazine(List<Page> pages) {
        super(Type.Magazine, pages);
    }

    /**
     * Constructs an Instance of {@link Document} with {@link Type}<b>.Magazine "Type"</b> and <b>filePath</b>
     * @param filePath the path to document {@link File}
     */
    public Magazine(CharSequence filePath) {
        super(Type.Magazine, filePath);
    }

}

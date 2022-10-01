package nx.peter.java.document.type;

import nx.peter.java.document.Document;
import nx.peter.java.document.core.Page;

import java.io.File;
import java.util.List;

/**
 * A {@link Document} class that represents a NewsPaper.<br>
 * It extends the {@link Document} class
 */
public class NewsPaper extends Document<NewsPaper> {
    public static final String START_ENCODE = "<NEWSPAPER>";
    public static final String END_ENCODE = "</NEWSPAPER>";
    public static final String PIS_TAG = "NEWSPAPER";
    public static final String JSON_TAG = "newspaper";


    /**
     * Constructs an Instance of {@link Document} with {@link Type}<b>.NewsPaper "Type"</b> and empty pages
     */
    public NewsPaper() {
        super(Type.NewsPaper);
    }

    /**
     * Constructs an Instance of {@link Document} with {@link Type}<b>.NewsPaper "Type"</b> and {@link List} of {@link Page}
     * @param pages the pages of document
     */
    public NewsPaper(List<Page> pages) {
        super(Type.NewsPaper, pages);
    }

    /**
     * Constructs an Instance of {@link Document} with {@link Type}<b>.NewsPaper "Type"</b> and <b>filePath</b>
     * @param filePath the path to document {@link File}
     */
    public NewsPaper(CharSequence filePath) {
        super(Type.NewsPaper, filePath);
    }

}

package nx.peter.java.document.type;

import nx.peter.java.document.Document;
import nx.peter.java.document.core.Page;

import java.io.File;
import java.util.List;

/**
 * A {@link Document} class that represents a Book.<br>
 * It extends the {@link Document} class
 */
public class Book extends Document<Book> {
    public static final String PIS_TAG = "BOOK";
    public static final String JSON_TAG = "book";


    /**
     * Constructs an Instance of {@link Document} with {@link Type}<b>.Book "Type"</b> and empty pages
     */
    public Book() {
        super(Type.Book);
    }

    /**
     * Constructs an Instance of {@link Document} with {@link Type}<b>.Book "Type"</b> and {@link List} of {@link Page}
     * @param pages the pages of document
     */
    public Book(List<Page> pages) {
        super(Type.Book, pages);
    }

    /**
     * Constructs an Instance of {@link Document} with {@link Type}<b>.Book "Type"</b> and <b>filePath</b>
     * @param filePath the path to document {@link File}
     */
    public Book(CharSequence filePath) {
        super(Type.Book, filePath);
    }

}

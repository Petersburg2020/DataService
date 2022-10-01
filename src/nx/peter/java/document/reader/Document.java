package nx.peter.java.document.reader;

import nx.peter.java.document.core.PrettyPrinter;
import nx.peter.java.json.reader.JsonObject;
import nx.peter.java.pis.reader.Node;
import nx.peter.java.util.data.Sentence;
import nx.peter.java.util.data.Texts;

import java.util.List;

public interface Document<D extends Document, P extends Page> extends nx.peter.java.document.core.Document<D, P> {

    /**
     * Gets the title of the {@link Document}
     *
     * @return the title of the document
     */
    String getTitle();

    /**
     * Gets the author of the {@link Document}
     *
     * @return the author of the document
     */
    String getAuthor();

    /**
     * Gets the summary of the {@link Document}
     *
     * @return the summary of the document
     */
    String getSummary();

    /**
     * Gets the date of publication of the {@link Document}
     *
     * @return the date of publication of the document
     */
    String getDateOfPublication();

    /**
     * Gets the serial number of the {@link Document}
     *
     * @return the serial number of the document
     */
    String getSerialNumber();

    /**
     * Gets the file path of the {@link Document}
     *
     * @return the file path of the document
     */
    String getFilePath();

    /**
     * Gets the {@link Page} with page number <b>"pageNumber"</b> from the {@link Document}
     *
     * @param pageNumber the page number of {@link Page} to get
     * @return page with pageNumber in document
     */
    Page getPage(int pageNumber);

    /**
     * Generates a random {@link Page} from the {@link Document}
     *
     * @return generated page from document
     */
    Page generatePage();

    /**
     * Selects and returns the {@link List} of {@link Page}s from <b>startPage</b> to <b>endPage</b> off all pages in the {@link Document}
     *
     * @param startPage the page number where selection starts
     * @param endPage   the page number where selection ends
     * @return pages from startPage to endPage in document
     */
    List<Page> getPages(int startPage, int endPage);

    /**
     * Generates and returns a random {@link List} of {@link Page}s from the {@link Document}
     *
     * @return pages generated from document
     */
    List<Page> generatePages();

    /**
     * Gets the {@link List} of all {@link Page} in the {@link Document}
     *
     * @return all pages in document
     */
    List<Page> getPages();

    /**
     * Gets the {@link JsonObject} format of this {@link Document}
     *
     * @return json object format of this document
     */
    JsonObject toJson();

    /**
     * Gets the {@link Node} format of this {@link Document}
     *
     * @return pis node format of this document
     */
    Node toPis();

    /**
     * Gets the data ({@link Texts}) format
     * @return data format of document
     */
    Texts getData();

    /**
     * Gets the data ({@link Texts}) format with line length
     * @return data format of document
     */
    Texts getData(int lineLength);

    /**
     * Combines all properties into a string that can easily be printed as a document
     *
     * @return a string that can easily be printed as a document
     */
    String toDocumentFormat();

    /**
     * Gets a pretty printer that prints this document in a pretty form
     * by randomly choosing whether to print in json or in pis
     * @return a pretty printer for this document
     */
    PrettyPrinter<D, Node, JsonObject> getPrettyPrinter();



    /**
     * A readable root source of {@link Document}
     * @param <S> type of source
     */
    interface Source<S extends Source> extends nx.peter.java.document.core.Document.Source<S> {

        /**
         * Gets a copy of this source
         * @return copy of this source
         */
        S copy();

        /**
         * Combines all properties into a string that can easily be printed as {@link JsonObject}
         * @return Json format of source
         */
        JsonObject toJson();

        /**
         * Combines all properties into a string that can easily be printed as {@link Node}
         * @return Pis format of source
         */
        Node toPis();

        /**
         * Combines all properties into a string that can easily be printed as a document
         * @return a string that can easily be printed as a document
         */
        String toDocumentFormat();

        /**
         * Gets the step of source
         * @return step of source
         */
        int getStep();

        /**
         * Gets source data ({@link Texts}) format
         * @return data format of source
         */
        Texts getData();

        /**
         * Gets the data ({@link Texts}) format with line length
         * @return data format of source
         */
        Texts getData(int lineLength);

        /**
         * Get the source's encoding
         * @return source's encoding
         */
        String getEncoding();

        /**
         * Scan for errors within this source
         * @param error error type to scan for
         * @param listener scanning listener
         */
        void scanForErrors(Sentence.SentenceError error, Sentence.OnScanErrorListener listener);

    }
}

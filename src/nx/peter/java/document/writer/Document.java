package nx.peter.java.document.writer;

import nx.peter.java.document.core.Page;
import nx.peter.java.document.exception.NotItemException;
import nx.peter.java.json.reader.JsonObject;
import nx.peter.java.pis.reader.Node;

import java.io.File;
import java.util.List;


public interface Document<D extends Document, P extends nx.peter.java.document.writer.Page> extends
        nx.peter.java.document.core.Document<D, P> {

    /**
     * Resets the {@link Document}
     */
    D reset();

    /**
     * Sets this {@link Document} from a {@link File} with <b>filePath</b>
     *
     * @param filePath the path to the file
     */
    D setFromFile(CharSequence filePath);

    /**
     * Sets the {@link Document}'s <b>filePath</b>
     *
     * @param filePath the document's file path
     */
    D setFilePath(CharSequence filePath);

    /**
     * Sets the {@link Document}'s <b>title</b>
     *
     * @param title the document's title
     */
    D setTitle(CharSequence title);

    /**
     * Sets the {@link Document}'s <b>author</b>
     *
     * @param author the document's author
     */
    D setAuthor(CharSequence author);

    /**
     * Sets the {@link Document}'s <b>summary</b>
     *
     * @param summary the document's summary
     */
    D setSummary(CharSequence summary);

    /**
     * Sets the {@link Document}'s <b>date</b> of publication
     *
     * @param date the document's date of publication
     */
    D setDateOfPublication(CharSequence date);

    /**
     * Sets the {@link Document}'s <b>serialNumber</b>
     *
     * @param serialNumber the document's serial number
     */
    D setSerialNumber(CharSequence serialNumber);

    /**
     * Sets the list of <b>"pages"</b> provides
     *
     * @param pages the list of page provided
     */
    D setPages(Page... pages);

    /**
     * Sets the list of <b>"pages"</b> provides
     *
     * @param pages the list of page provided
     */
    D setPages(List<Page> pages);

    /**
     * Adds the list of <b>"pages"</b> provides
     *
     * @param pages the list of page provided
     */
    D addPages(Page... pages);

    /**
     * Adds the list of <b>"pages"</b> provides
     *
     * @param pages the list of page provided
     */
    D addPages(List<Page> pages);

    /**
     * Removes the list of <b>"pages"</b> provides
     *
     * @param pages the list of page provided
     */
    D removePages(Page... pages);

    /**
     * Removes the list of <b>"pages"</b> provides
     *
     * @param pages the list of page provided
     */
    D removePages(List<Page> pages);

    /**
     * Removes the <b>"pageNumbers"</b> provides
     *
     * @param pageNumbers the page numbers provided
     */
    D removePages(int... pageNumbers);

    /**
     * Removes all the pages in the {@link Document}
     */
    D removeAllPages();



    /**
     * A writable root source of {@link Document}
     * @param <S> type of source
     */
    interface Source<S extends Source> extends nx.peter.java.document.core.Document.Source<S> {

        /**
         * Resets this root source
         */
        S reset();

        /**
         * Resets this root source's step to zero
         */
        S resetStep();

        /**
         * Auto set steps of this root source and it's children
         */
        S autoStep();

        /**
         * Sets this root
         * @param source the given source
         * @return this source
         */
        S set(S source) throws NotItemException;

        /**
         * Sets the steps of tabs for source
         * @param step number of tabs
         * @return this source
         */
        S setStep(int step);

        /**
         * Create this source from Json
         * @param json json source
         * @return this source
         */
        S fromJson(JsonObject json);

        /**
         * Create this source from Pis
         * @param node pis source
         * @return this source
         */
        S fromPis(Node node);
    }
}

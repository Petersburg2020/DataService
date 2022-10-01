package nx.peter.java.document.core;

import nx.peter.java.context.DataFile;
import nx.peter.java.document.core.page.Body;
import nx.peter.java.util.data.IData;
import nx.peter.java.util.data.Sentence;
import nx.peter.java.util.data.Texts;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * This is an {@link Object} interface that represents a real life document<br>
 * such as:<blockquote><pre>
 *     <i>- PDFs</i>,
 *     <i>- docs</i>,
 *     <i>- magazines</i>,
 *     <i>- books</i>,
 *     <i>- news paper,<br>e.t.c</i></pre></blockquote>
 * Some of the features of the document can be manipulated here. These are:<blockquote><pre>
 *     <i>- gets pages with its features</i>,
 *     <i>- sets pages with its features</i>,
 *     <i>- adds pages with its features</i>,
 *     <i>- remove pages with its features</i>,
 *     <i>- gets document's title</i>,
 *     <i>- gets document's author</i>,
 *     <i>- gets document's date of publication</i>,
 *     <i>- gets document's serial number</i>,
 *     <i>- gets document's summary</i>,
 *     <i>- sets document's title</i>,
 *     <i>- sets document's author</i>,
 *     <i>- sets document's date of publication</i>,
 *     <i>- sets document's serial number</i>,
 *     <i>- sets document's summary</i>,
 *     <i>- gets the document type,<br>e.t.c</i></pre></blockquote>
 */
public interface Document<D extends Document, P extends Page> extends DataFile<D> {
    /**
     * The [.json] {@link Document} format Key for the {@link Document}'s title
     */
    String JSON_TITLE = "title";

    /**
     * The [.json] {@link Document} format Key for the {@link Document}'s author
     */
    String JSON_AUTHOR = "author";

    /**
     * The [.json] {@link Document} format Key for the {@link Document}'s summary
     */
    String JSON_SUMMARY = "summary";

    /**
     * The [.json] {@link Document} format Key for the {@link Document}'s serial number
     */
    String JSON_SERIAL_NUMBER = "serial-number";

    /**
     * The [.json] {@link Document} format Key for the {@link Document}'s date of publication
     */
    String JSON_DATE_OF_PUBLICATION = "date-of-publication";

    /**
     * The [.json] {@link Document} format Key for the {@link Document} Entity's steps
     */
    String JSON_STEP = "step";

    /**
     * The [.json] {@link Document} format Key for the {@link Document} Entity's position in body
     */
    String JSON_POSITION_IN_BODY = "position-in-body";

    /**
     * The [.json] {@link Document} format Key for the {@link Document}'s pages
     */
    String JSON_PAGES = "pages";

    /**
     * The [.json] {@link Document} format Key for the {@link Document}'s type
     */
    String JSON_DOC_TYPE = "type";



    /**
     * The [.pis] {@link Document} format for the {@link Document}'s summary<br>
     * Like a Mark-up Language.
     */
    String PIS_SUMMARY = "summary";

    /**
     * The [.pis] {@link Document} format for the {@link Document}'s title<br>
     * Like a Mark-up Language.
     */
    String PIS_TITLE = "title";

    /**
     * The [.pis] {@link Document} format for the {@link Document}'s author<br>
     * Like a Mark-up Language.
     */
    String PIS_AUTHOR = "author";

    /**
     * The [.pis] {@link Document} format for the {@link Document}'s pages<br>
     * Like a Mark-up Language.
     */
    String PIS_PAGES = "PAGES";

    /**
     * The [.pis] {@link Document} format for the {@link Document}'s serial number<br>
     * Like a Mark-up Language.
     */
    String PIS_SERIAL_NUMBER = "serial-number";

    /**
     * The [.pis] {@link Document} format for the {@link Document}'s date of publication<br>
     * Like a Mark-up Language.
     */
    String PIS_DATE_OF_PUBLICATION = "date-of-publication";

    /**
     * The [.pis] {@link Document} format for the {@link Document} Entity's step<br>
     * Like a Mark-up Language.
     */
    String PIS_STEP = "step";

    /**
     * The [.pis] {@link Document} format for the {@link Document} Entity's position in body<br>
     * Like a Mark-up Language.
     */
    String PIS_POSITION_IN_BODY = "position";




    /**
     * Gets the {@link Document}'s <b>type</b> ({@link Type})
     *
     * @return the document's type
     */
    Type getType();

    /**
     * Counts the number of pages {@link Page} in {@link Document}
     *
     * @return the number of pages in document
     */
    int pageCount();

    /**
     * Checks if {@link Document} has been provided with a file path
     *
     * @return true if it has been provided with a valid file path
     */
    boolean hasFilePath();

    /**
     * Checks if {@link Document} has been provided with a title
     *
     * @return true if it has been provided with a valid title
     */
    boolean hasTitle();

    /**
     * Checks if {@link Document} has been provided with a author
     *
     * @return true if it has been provided with a valid author
     */
    boolean hasAuthor();

    /**
     * Checks if {@link Document} has been provided with a summary
     *
     * @return true if it has been provided with a valid summary
     */
    boolean hasSummary();

    /**
     * Checks if {@link Document} has been provided with a serial number
     *
     * @return true if it has been provided with a valid serial number
     */
    boolean hasSerialNumber();

    /**
     * Checks if {@link Document} has been provided with a date of publication
     *
     * @return true if it has been provided with a valid date of publication
     */
    boolean hasDateOfPublication();

    /**
     * Checks if {@link Document} has at least one {@link Page}
     *
     * @return true if it has been provided with at least one page
     */
    boolean hasPages();


    /**
     * Document's Type.<br>
     * An {@link Enum} that represents the document's type
     */
    enum Type {
        /**
         * Represents {@link nx.peter.java.document.type.Book}, a type of {@link Document}
         */
        Book,

        /**
         * Represents {@link nx.peter.java.document.type.Journal}, a type of {@link Document}
         */
        Journal,

        /**
         * Represents {@link nx.peter.java.document.type.Magazine}, a type of {@link Document}
         */
        Magazine,

        /**
         * Represents {@link nx.peter.java.document.type.NewsPaper}, a type of {@link Document}
         */
        NewsPaper,

        /**
         * Represents {@link nx.peter.java.document.type.Thesis}, a type of {@link Document}
         */
        Thesis,

        /**
         * Represents Unknown {@link Document}, an unknown type of {@link Document}
         */
        Unknown
    }


    /**
     * An array of items
     * @param <I> item type
     */
    class Array<I> implements Iterable<I> {
        protected List<I> items;

        public Array(List<I> items) {
            this.items = new ArrayList<>();
            for (I item : items) if (item != null) this.items.add(item);
        }

        public int size() {
            return items.size();
        }

        public I get(int index) {
            return index >= 0 && index < size() ? items.get(index) : null;
        }

        public boolean contains(I item) {
            return items.contains(item);
        }

        public int indexOf(I item) {
            return items.indexOf(item);
        }

        public boolean isEmpty() {
            return items.isEmpty();
        }

        public boolean isNotEmpty() {
            return !isEmpty();
        }

        @Override
        public Iterator<I> iterator() {
            return items.iterator();
        }

        public boolean equals(Array<?> array) {
            return array != null && items.equals(array.items);
        }

        public boolean equalsIgnoreOrder(Array<?> array) {
            if (array == null) return false;
            List<I> arr = new ArrayList<>(items);
            arr.sort(Comparator.comparing(String::valueOf));
            List<?> brr = new ArrayList<>(array.items);
            brr.sort(Comparator.comparing(String::valueOf));
            return arr.equals(brr);
        }

        @Override
        public String toString() {
            return items.toString().replace("[", "").replace("]", "");
        }
    }


    /**
     * A root source of {@link Document}
     * @param <S> type of source
     */
    interface Source<S extends Source> {
        /**
         * A base encoding format for root json
         */
        String ENCODE = "encode";

        /**
         * Source's tag
         */
        String TAG = "tag";

        /**
         * Checks if source is valid
         * @return true if it's valid
         */
        boolean isValid();

        /**
         * Checks if source is empty
         * @return true if it's empty
         */
        boolean isEmpty();

        /**
         * Checks if source is not empty
         * @return true if it's not empty
         */
        boolean isNotEmpty();

        /**
         * Checks if provided source is equal to this source
         * @param source provided comparing source
         * @return true if it's equal
         */
        boolean equals(S source);

        /**
         * Checks if data is in this source
         * @param data provided data
         * @param <O> type of data
         * @return true if data is in source
         */
        <O> boolean contains(O data);

        /**
         * Checks if data is in this source
         * @param data provided data
         * @return true if data is in source
         */
        boolean contains(CharSequence data);

        /**
         * Checks if data is in this source
         * @param data provided data
         * @param <D> type of data
         * @return true if data is in source
         */
        <D extends IData> boolean contains(D data);

        /**
         * Checks if data is in this source ignoring its case
         * @param data provided data
         * @param <O> type of data
         * @return true if data is in source
         */
        <O> boolean containsIgnoreCase(O data);

        /**
         * Checks if data is in this source ignoring its case
         * @param data provided data
         * @return true if data is in source
         */
        boolean containsIgnoreCase(CharSequence data);

        /**
         * Checks if data is in this source ignoring its case
         * @param data provided data
         * @param <D> type of data
         * @return true if data is in source
         */
        <D extends IData> boolean containsIgnoreCase(D data);


        abstract class ISource<I extends ISource> implements
                nx.peter.java.document.writer.Document.Source<I>,
                nx.peter.java.document.reader.Document.Source<I> {

            protected int step;
            protected Body.Creator body;

            public ISource() {
                this(0);
            }

            public ISource(int step) {
                this.step = step;
            }

            public I setBody(Body body) {
                this.body = (Body.Creator) body;
                return (I) this;
            }

            public Body.Creator getBody() {
                return body;
            }

            public boolean hasBody() {
                return body != null && body.isNotEmpty();
            }

            @Override
            public I autoStep() {
                if (hasBody()) {
                    body.setStep(step + 1);
                    body.autoStep();
                }
                return (I) this;
            }

            @Override
            public boolean isValid() {
                return step > 0;
            }

            @Override
            public boolean isNotEmpty() {
                return !isEmpty();
            }

            @Override
            public I copy() {
                return (I) this;
            }

            @Override
            public int getStep() {
                return step;
            }

            @Override
            public Texts getData() {
                return getData("********************************************".length());
            }

            @Override
            public String getEncoding() {
                String name = getClass().getName().substring(getClass().getName().lastIndexOf(".") + 1);
                return name.contains("$") ? name.substring(0, name.indexOf("$")) : name;
            }

            @Override
            public void scanForErrors(Sentence.SentenceError error, Sentence.OnScanErrorListener listener) {
                getData().scanForError(error, listener);
            }

            @Override
            public String toDocumentFormat() {
                return getData().get();
            }

            @Override
            public I reset() {
                body = null;
                return resetStep();
            }

            @Override
            public I resetStep() {
                setStep(0);
                return (I) this;
            }

            @Override
            public I setStep(int step) {
                this.step = step;
                return (I) this;
            }

            @Override
            public <D extends IData> boolean contains(D data) {
                return getData().contains(data);
            }

            @Override
            public <O> boolean contains(O data) {
                return getData().contains(data);
            }

            @Override
            public boolean contains(CharSequence data) {
                return getData().contains(data);
            }

            @Override
            public <O> boolean containsIgnoreCase(O data) {
                return getData().containsIgnoreCase(data);
            }

            @Override
            public <D extends IData> boolean containsIgnoreCase(D data) {
                return getData().containsIgnoreCase(data);
            }

            @Override
            public boolean containsIgnoreCase(CharSequence data) {
                return getData().containsIgnoreCase(data);
            }

            @Override
            public String toString() {
                return toDocumentFormat();
            }
        }
    }

}

package nx.peter.java.document;

import nx.peter.java.document.core.PrettyPrinter;
import nx.peter.java.document.type.*;
import nx.peter.java.json.JsonArray;
import nx.peter.java.document.core.Page;
import nx.peter.java.json.reader.JsonObject;
import nx.peter.java.json.reader.JsonReader;
import nx.peter.java.pis.reader.Node;
import nx.peter.java.pis.reader.PisReader;
import nx.peter.java.util.Random;
import nx.peter.java.util.Util;
import nx.peter.java.util.data.Texts;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An abstract class that implements the methods in {@link Document}
 */
public abstract class Document<D extends Document> implements
        nx.peter.java.document.writer.Document<D, Page.Creator>,
        nx.peter.java.document.reader.Document<D, Page.Creator> {

    /**
     * The type of {@link Document}
     */
    protected Type type;

    /**
     * The author of {@link Document}
     */
    protected String author;

    /**
     * The title of {@link Document}
     */
    protected String title;

    /**
     * The summary of {@link Document}
     */
    protected String summary;

    /**
     * The date of publication of {@link Document}
     */
    protected String dateOfPublication;

    /**
     * The serial number of {@link Document}
     */
    protected String serialNumber;

    /**
     * The decoded [pis] format of the {@link Document} from a {@link File} with the <b>filePath</b> provided
     */
    protected Node decodedPis;

    /**
     * The decoded [json] {@link JsonObject} format of the {@link Document} from a {@link File} with the <b>filePath</b> provided
     */
    protected JsonObject decodedJson;

    /**
     * The encoded [pet] format of the {@link Document} that will be stored to a {@link File} with the <b>filePath</b> provided
     */
    protected nx.peter.java.pis.Node encodedPis;

    /**
     * The encoded [json] {@link JsonObject} format of the {@link Document} that will be stored to a {@link File} with the <b>filePath</b> provided
     */
    protected nx.peter.java.json.JsonObject encodedJson;

    /**
     * The filePath to this {@link Document}'s {@link File} with the <b>encodedDocument</b>
     */
    protected String filePath;

    /**
     * {@link List} of {@link Page}s in {@link Document}
     */
    protected List<Page.Creator> pages;


    /**
     * Constructs an Instance of {@link Document} with an unKnown <b>"type"</b> and no <b>pages</b>
     */
    public Document() {
        this(Type.Unknown);
    }

    /**
     * Constructs an Instance of {@link Document} with {@link Type} <b>"type"</b> and empty pages
     *
     * @param type the type of document
     */
    public Document(Type type) {
        reset();
        this.type = type;
    }

    /**
     * Constructs an Instance of {@link Document} with {@link Type} <b>"type"</b> and {@link List} of {@link Page}s
     *
     * @param type  the type of document
     * @param pages the pages of document
     */
    public Document(Type type, List<Page> pages) {
        this(type);
        setPages(pages);
    }

    /**
     * Constructs an Instance of {@link Document} with {@link Type} <b>"type"</b> and <b>filePath</b>
     *
     * @param type     the type of document
     * @param filePath the path to the file to be read <b><em>(".json" or ".pet")</em></b>
     */
    public Document(Type type, CharSequence filePath) {
        this(type);
        setFromFile(filePath);
    }


    @Override
    public D reset() {
        pages = new ArrayList<>();
        encodedJson = new nx.peter.java.json.JsonObject();
        encodedPis = new nx.peter.java.pis.Node();
        setFilePath("");
        setTitle("");
        setAuthor("");
        setSummary("");
        setSerialNumber("");
        setDateOfPublication("");
        return (D) this;
    }

    @Override
    public D setFromFile(CharSequence filePath) {
        this.filePath = filePath != null ? filePath.toString() : "";
        decodedPis = null;
        if (this.filePath.endsWith(".pis"))
            decodedPis = new PisReader(this.filePath).getSource().getNode();
        decodedJson = null;
        if (this.filePath.endsWith(".json"))
            decodedJson = new JsonReader(this.filePath).getRoot().getObject();
        setFilePath(this.filePath);
        decode();
        return (D) this;
    }

    @Override
    public D setFilePath(CharSequence filePath) {
        this.filePath = filePath != null ? filePath.toString() : "";

        // remove extension
        this.filePath = this.filePath.contains(".") ? this.filePath.substring(0, this.filePath.lastIndexOf(".")) : this.filePath;
        return (D) this;
    }


    /* ******************************************************************************** */
    /* *************************** PRIVATE CODES BEGINS ******************************* */
    /* Pieces of codes that helps our code efficient (they only work in the background) */

    /**
     * It encodes the {@link Document} to a store-able format, a {@link List} of {@link String} that can be stored into a {@link File} with the <b>filePath</b> provided
     */
    protected void encode() {
        encodedPis = new nx.peter.java.pis.Node();
        encodedJson = new nx.peter.java.json.JsonObject();

        String tag = switch (getType()) {
            case Book -> Book.PIS_TAG;
            case Journal -> Journal.PIS_TAG;
            case Magazine -> Magazine.PIS_TAG;
            case NewsPaper -> NewsPaper.PIS_TAG;
            case Thesis -> Thesis.PIS_TAG;
            default -> "UNKNOWN";
        };

        // Populate encoded Pis
        encodedPis.setTag(tag);
        encodedPis.addAttr(PIS_TITLE, title);
        encodedPis.addAttr(PIS_AUTHOR, author);
        encodedPis.addAttr(PIS_SUMMARY, summary);
        encodedPis.addAttr(PIS_SERIAL_NUMBER, serialNumber);
        encodedPis.addAttr(PIS_DATE_OF_PUBLICATION, dateOfPublication);
        nx.peter.java.pis.Node pisPages = new nx.peter.java.pis.Node(PIS_PAGES);


        // Populate encoded Json
        encodedJson.add(JSON_DOC_TYPE, type.toString());
        encodedJson.add(JSON_TITLE, title);
        encodedJson.add(JSON_AUTHOR, author);
        encodedJson.add(JSON_SUMMARY, summary);
        encodedJson.add(JSON_SERIAL_NUMBER, serialNumber);
        encodedJson.add(JSON_DATE_OF_PUBLICATION, dateOfPublication);
        JsonArray jsonPages = new JsonArray();

        for (Page.Creator page : this.pages) {
            jsonPages.add(page.toJson());
            pisPages.addNode(page.toPis());
            // System.out.println("Page: " + page);
        }

        encodedPis.addNode(pisPages);
        encodedJson.add(JSON_PAGES, jsonPages);
    }

    /**
     * It decodes the <b>decodedPis</b> (a {@link Node}) or <b>decodedJson</b> (a {@link JsonObject})
     * to this {@link Document}
     */
    protected void decode() {
        if (decodedPis != null && decodedPis.isNotEmpty()) {
            type = switch (decodedPis.getTag()) {
                case Book.PIS_TAG -> Type.Book;
                case Journal.PIS_TAG -> Type.Journal;
                case Magazine.PIS_TAG -> Type.Magazine;
                case NewsPaper.PIS_TAG -> Type.NewsPaper;
                case Thesis.PIS_TAG -> Type.Thesis;
                default -> Type.Unknown;
            };


            if (!type.equals(Type.Unknown)) {
                setTitle(decodedPis.containsAttr(PIS_TITLE) ? decodedPis.getAttribute(PIS_TITLE).getString() : "");
                setAuthor(decodedPis.containsAttr(PIS_AUTHOR) ? decodedPis.getAttribute(PIS_AUTHOR).getString() : "");
                setSummary(decodedPis.containsAttr(PIS_SUMMARY) ? decodedPis.getAttribute(PIS_SUMMARY).getString() : "");
                setSerialNumber(decodedPis.containsAttr(PIS_SERIAL_NUMBER) ? decodedPis.getAttribute(PIS_SERIAL_NUMBER).getString() : "");
                setDateOfPublication(decodedPis.containsAttr(PIS_DATE_OF_PUBLICATION) ? decodedPis.getAttribute(PIS_DATE_OF_PUBLICATION).getString() : "");

                pages = new ArrayList<>();
                if (decodedPis.containsChild(PIS_PAGES)) {
                    Node pisPage = (Node) decodedPis.getChildByTag(PIS_PAGES).get(0);
                    for (Object o : pisPage.getChildren()) {
                        Node page = (Node) o;
                        addPages(new Page.Creator().fromPis(page));
                    }
                }
            }
        } else if (decodedJson != null && decodedJson.isNotEmpty()) {
            JsonObject json = null;
            if (decodedJson.containsKey(JSON_DOC_TYPE)) {
                json = decodedJson;
                switch (decodedJson.getString(JSON_DOC_TYPE)) {
                    case Book.JSON_TAG -> type = Type.Book;
                    case Journal.JSON_TAG -> type = Type.Journal;
                    case Magazine.JSON_TAG -> type = Type.Magazine;
                    case NewsPaper.JSON_TAG -> type = Type.NewsPaper;
                    case Thesis.JSON_TAG -> type = Type.Thesis;
                }
            } else type = Type.Unknown;

            if (json != null) {
                setTitle(json.containsKey(JSON_TITLE) ? json.getString(JSON_TITLE) : "");
                setAuthor(json.containsKey(JSON_AUTHOR) ? json.getString(JSON_AUTHOR) : "");
                setSummary(json.containsKey(JSON_SUMMARY) ? json.getString(JSON_SUMMARY) : "");
                setSerialNumber(json.containsKey(JSON_SERIAL_NUMBER) ? json.getString(JSON_SERIAL_NUMBER) : "");
                setDateOfPublication(json.containsKey(JSON_DATE_OF_PUBLICATION) ? json.getString(JSON_DATE_OF_PUBLICATION) : "");

                pages = new ArrayList<>();
                JsonArray jsonPages = json.containsKey(JSON_PAGES) ? (JsonArray) json.getArray(JSON_PAGES) : new JsonArray();
                if (jsonPages.size() > 0)
                    for (int index = 0; index < jsonPages.size(); index++)
                        addPages(new Page.Creator().fromJson(jsonPages.getObject(index)));
            }
        }
    }


    /* ***************************** PRIVATE CODES ENDS ******************************* */
    /* ******************************************************************************** */


    @Override
    public JsonObject toJson() {
        encode();
        return encodedJson;
    }

    @Override
    public Node toPis() {
        encode();
        return encodedPis;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public D setTitle(CharSequence title) {
        this.title = title != null ? title.toString() : "";
        return (D) this;
    }

    @Override
    public D setAuthor(CharSequence author) {
        this.author = author != null ? author.toString() : "";
        return (D) this;
    }

    @Override
    public D setSummary(CharSequence summary) {
        this.summary = summary != null ? summary.toString() : "";
        return (D) this;
    }

    @Override
    public D setDateOfPublication(CharSequence date) {
        this.dateOfPublication = date != null ? date.toString() : "";
        return (D) this;
    }

    @Override
    public D setSerialNumber(CharSequence serialNumber) {
        this.serialNumber = serialNumber != null ? serialNumber.toString() : "";
        return (D) this;
    }

    @Override
    public D setPages(Page... pages) {
        return setPages(Arrays.asList(pages));
    }

    @Override
    public D setPages(List<Page> pages) {
        this.pages = new ArrayList<>();
        return addPages(pages);
    }

    @Override
    public D addPages(Page... pages) {
        return addPages(Arrays.asList(pages));
    }

    @Override
    public D addPages(List<Page> pages) {
        if (pages != null)
            for (Page page : pages)
                if (page != null && page.hasNumber())
                    this.pages.add((Page.Creator) page);
        return (D) this;
    }

    @Override
    public D removePages(Page... pages) {
        if (pages != null)
            removePages(Arrays.asList(pages));
        return (D) this;
    }

    @Override
    public D removePages(List<Page> pages) {
        if (pages != null)
            for (Page page : pages)
                if (page != null)
                    this.pages.remove(page);
        return (D) this;
    }

    @Override
    public D removePages(int... pageNumbers) {
        for (int page : pageNumbers)
            if (page > 0 && page <= pageCount())
                pages.remove(page - 1);
        return (D) this;
    }

    @Override
    public D removeAllPages() {
        pages.clear();
        return (D) this;
    }

    @Override
    public boolean hasFilePath() {
        return !filePath.isEmpty();
    }

    @Override
    public boolean hasTitle() {
        return title != null && !title.isEmpty();
    }

    @Override
    public boolean hasAuthor() {
        return author != null && !author.isEmpty();
    }

    @Override
    public boolean hasSummary() {
        return summary != null && !summary.isEmpty();
    }

    @Override
    public boolean hasSerialNumber() {
        return serialNumber != null && !serialNumber.isEmpty();
    }

    @Override
    public boolean hasDateOfPublication() {
        return dateOfPublication != null && !dateOfPublication.isEmpty();
    }

    @Override
    public boolean hasPages() {
        return pages != null && !pages.isEmpty();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getSummary() {
        return summary;
    }

    @Override
    public String getDateOfPublication() {
        return dateOfPublication;
    }

    @Override
    public String getSerialNumber() {
        return serialNumber;
    }

    @Override
    public String getFilePath() {
        return filePath;
    }

    @Override
    public Page.Creator getPage(int pageNumber) {
        if (pageNumber > 0)
            return pages.get(pageNumber - 1);
        return new Page.Creator();
    }

    @Override
    public Page.Creator generatePage() {
        return getPage(Random.nextInt(1, pageCount()));
    }

    @Override
    public List<nx.peter.java.document.reader.Page> getPages(int startPage, int endPage) {
        List<nx.peter.java.document.reader.Page> pages = new ArrayList<>();
        if (startPage < endPage)
            for (Page.Creator page : this.pages)
                if (page.getNumber() >= startPage && page.getNumber() <= endPage)
                    pages.add(page);
        return pages;
    }

    @Override
    public List<nx.peter.java.document.reader.Page> generatePages() {
        int start = Random.nextInt(1, pageCount()); // Generates random page between 1 and pageCount()
        int end = Random.nextInt(1, pageCount()); // Generates random page between 1 and pageCount()

        // Check if start equals end and generate a page number that is different from start
        while (start == end)
            end = Random.nextInt(1, pageCount());

        // Return the minimum page as startPage and maximum page as endPage
        return getPages(Math.min(start, end), Math.max(start, end));
    }

    @Override
    public List<nx.peter.java.document.reader.Page> getPages() {
        return new ArrayList<>(pages);
    }

    @Override
    public int pageCount() {
        return pages.size();
    }

    @Override
    public String toString() {
        return toDocumentFormat();
    }

    @Override
    public Texts getData() {
        return getData("********************************************".length());
    }

    @Override
    public Texts getData(int lineLength) {
        Texts data = new Texts();
        String line = "*".repeat(lineLength);
        int length = line.length();
        data.append(line)
                .append("\n")
                .append(Util.centerLine(type.equals(Type.Unknown) ? "An Unknown Document" : "A " + type, length))
                .append("\n" + line)
                .append("\n\n")
                .append(hasTitle() ? Util.formatLine("Titled:" + Util.tab(1) + title, length) + "\n" : "")
                .append(hasAuthor() ? Util.formatLine("Written by: " + author, length) + "\n" : "")
                .append(hasDateOfPublication() ? "Published on: " + dateOfPublication + "\n" : "")
                .append(hasSerialNumber() ? "Serial No. " + serialNumber + "\n" : "")
                .append("\n");

        for (Page.Creator page : pages)
            data.append(line + "\n")
                    .append(Util.centerLine("PAGE " + page.getNumber(), length) + "\n")
                    .append(line + "\n" + page.getData(lineLength));

        data.append(hasSummary() ? line + "\n" + Util.centerLine("SUMMARY", length) + "\n" + line + "\n" + Util.centerLine(summary, length) + "\n" + line + "\n" : "");
        return data;
    }

    @Override
    public String toDocumentFormat() {
        return getData().get();
    }

    @Override
    public D getDataFile() {
        return (D) this;
    }

    @Override
    public int size() {
        return getPages().size();
    }

    @Override
    public boolean isEmpty() {
        return getPages().isEmpty();
    }

    @Override
    public boolean isNotEmpty() {
        return !isEmpty();
    }

    @Override
    public PrettyPrinter<D, Node, JsonObject> getPrettyPrinter() {
        return new PrettyPrinter<>() {
            @Override
            public Node toPis() {
                return Document.this.toPis();
            }

            @Override
            public JsonObject toJson() {
                return Document.this.toJson();
            }

            @Override
            public D getDocument() {
                return (D) Document.this;
            }

            @Override
            public String print() {
                return Random.nextInt(1, 2) == 1 ? toJson().getPrettyPrinter().print() :
                        toPis().getPrettyPrinter().print();
            }

            @Override
            public String toString() {
                return print();
            }
        };
    }
}

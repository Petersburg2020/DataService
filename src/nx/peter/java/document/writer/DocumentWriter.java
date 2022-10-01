package nx.peter.java.document.writer;

import nx.peter.java.context.Writer;
import nx.peter.java.document.core.DocumentCore;
import nx.peter.java.document.core.Source;
import nx.peter.java.json.writer.JsonObject;
import nx.peter.java.json.writer.JsonWriter;
import nx.peter.java.pis.writer.Node;
import nx.peter.java.pis.writer.PisWriter;

import java.io.File;

public class DocumentWriter extends DocumentCore<DocumentWriter, Document, Node, JsonObject>
        implements Writer<DocumentWriter, Source<Document, Node, JsonObject>> {

    public DocumentWriter(nx.peter.java.document.core.Document document) {
        super(document);
    }

    public DocumentWriter(Document.Type type) {
        super(type);
    }

    public DocumentWriter(CharSequence path, Document.Type type) {
        super(path, type);
    }

    public DocumentWriter setDocument(nx.peter.java.document.core.Document document) {
        this.document = (Document) document;
        return this;
    }

    /**
     * Stores the {@link Document} to a {@link File} with the <b>filePath</b>.json provided.<br>
     * It will not store if no/an invalid file was provided.
     *
     * @return True if document was stored, and false if otherwise
     */
    public boolean storeAsJson() {
        nx.peter.java.json.reader.JsonObject encodedJson = ((nx.peter.java.document.Document) document).toJson();
        if (encodedJson != null && !encodedJson.isEmpty() && document.hasFilePath() && !document.getType().equals(Document.Type.Unknown)) {
            JsonWriter writer = new JsonWriter(getPath() + ".json", true)
                    .setRoot(encodedJson);
            return writer.store();
        }
        return false;
    }

    /**
     * Stores the {@link Document} to a {@link File} with the <b>filePath</b>.pis provided.<br>
     * It will not store if no/an invalid file was provided.
     *
     * @return True if document was stored, and false if otherwise
     */
    public boolean storeAsPis() {
        nx.peter.java.pis.reader.Node<?, ?> encodedPis = ((nx.peter.java.document.Document<?>) document).toPis();
        if (encodedPis != null && !encodedPis.isEmpty() && document.hasFilePath() && !document.getType().equals(nx.peter.java.document.core.Document.Type.Unknown)) {
            PisWriter writer = new PisWriter(getPath() + ".pis")
                    .setRoot(encodedPis);
            return writer.store();
        }
        return false;
    }

    public boolean store(CharSequence path) {
        nx.peter.java.storage.File file = new nx.peter.java.storage.File(path);
        DocumentWriter writer = new DocumentWriter(path, document.getType());
        return path != null &&
                file.getExtension().contentEquals(".pis") ?
                writer.storeAsPis() :
                file.getExtension().contentEquals(".json") &&
                        writer.storeAsJson();
    }

    @Override
    public boolean store() {
        return storeAsJson() && storeAsPis();
    }
}

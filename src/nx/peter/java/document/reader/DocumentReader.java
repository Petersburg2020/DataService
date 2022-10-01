package nx.peter.java.document.reader;

import nx.peter.java.context.Reader;
import nx.peter.java.document.core.DocumentCore;
import nx.peter.java.document.core.Source;
import nx.peter.java.json.reader.JsonObject;
import nx.peter.java.pis.reader.Node;

public class DocumentReader extends DocumentCore<DocumentReader, Document, Node, JsonObject> implements Reader<DocumentReader, Source<Document, Node, JsonObject>> {

    public DocumentReader(Document.Type type) {
        super(type);
    }

    public DocumentReader(CharSequence path, Document.Type type) {
        super(path, type);
    }

    public DocumentReader(nx.peter.java.document.core.Document document) {
        super(document);
    }

}

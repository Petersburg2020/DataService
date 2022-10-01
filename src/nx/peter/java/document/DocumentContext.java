package nx.peter.java.document;

import nx.peter.java.context.Context;
import nx.peter.java.document.core.Document;
import nx.peter.java.document.core.PrettyPrinter;
import nx.peter.java.document.core.Source;
import nx.peter.java.document.reader.DocumentReader;
import nx.peter.java.document.writer.DocumentWriter;

/**
 * <pre><h1>A project created by:<br> Uareime Peter Ezekiel</pre></h1>
 * <h2>********* DocumentContext **********</h2>
 * <p>This is the base shortcut for document manager functions.
 * <br>Here, you can access some details from a document.<br></p>
 */
public interface DocumentContext extends
        Context<DocumentContext, DocumentWriter,
                DocumentReader, Source, Document,
                nx.peter.java.document.writer.Document,
                nx.peter.java.document.reader.Document,
                PrettyPrinter> {
    /**
     * Gets the root document
      * @return root document
     */
    Document getDocument();

    /**
     * Gets this document's type
     * @return document type
     */
    Document.Type getType();
}

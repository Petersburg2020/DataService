package nx.peter.java.document.writer.page.body;

import nx.peter.java.document.writer.Document;
import nx.peter.java.document.writer.page.Body;

public interface Element<E extends Element, B extends Body> extends
        Document.Source<E>,
        nx.peter.java.document.core.page.body.Element<E, B> {

    /**
     * Sets the body in element
     * @param body provided body
     * @return this element
     */
    E setBody(nx.peter.java.document.core.page.Body body);

}

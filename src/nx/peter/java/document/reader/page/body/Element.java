package nx.peter.java.document.reader.page.body;

import nx.peter.java.document.reader.Document;
import nx.peter.java.document.reader.page.Body;

public interface Element<E extends Element, B extends Body> extends
        Document.Source<E>,
        nx.peter.java.document.core.page.body.Element<E, B> {

    /**
     * Gets the body source in this element
     * @return body in element
     */
    B getBody();

    /**
     * Gets entity of element
     * @return entity of element
     */
    Entity getEntity();

}

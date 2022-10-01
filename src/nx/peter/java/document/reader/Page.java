package nx.peter.java.document.reader;

import nx.peter.java.document.reader.page.Body;
import nx.peter.java.document.reader.page.body.Heading;

public interface Page<P extends Page, B extends Body, H extends Heading> extends
        Document.Source<P>,
        nx.peter.java.document.core.Page<P, B, H> {
    /**
     * Gets this page's number
     * @return page number
     */
    int getNumber();
}

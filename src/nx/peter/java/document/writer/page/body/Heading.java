package nx.peter.java.document.writer.page.body;

import nx.peter.java.document.writer.page.Body;

public interface Heading<H extends Heading, B extends Body> extends
        Element<H, B>,
        nx.peter.java.document.core.page.body.Heading<H, B> {
    /**
     * Sets the heading
     * @return this heading
     */
    H setHeading(CharSequence heading);
}

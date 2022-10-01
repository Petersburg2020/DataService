package nx.peter.java.document.reader.page.body;

import nx.peter.java.document.reader.page.Body;

public interface Heading<H extends Heading, B extends Body> extends
        Element<H, B>, nx.peter.java.document.core.page.body.Heading<H, B> {

    /**
     * Gets the heading
     * @return heading
     */
    String getHeading();
}

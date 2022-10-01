package nx.peter.java.document.reader.page.body;

import nx.peter.java.document.reader.page.Body;

public interface Text<T extends Text, B extends Body> extends
        Element<T, B>,
        nx.peter.java.document.core.page.body.Text<T, B> {
    /**
     * Gets this text in String
     * @return String text
     */
    String getText();
}

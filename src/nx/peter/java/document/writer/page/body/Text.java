package nx.peter.java.document.writer.page.body;

import nx.peter.java.document.writer.page.Body;
import nx.peter.java.util.data.IData;

public interface Text<T extends Text, B extends Body> extends
        nx.peter.java.document.core.page.body.Text<T, B>,
        Element<T, B> {

    /**
     * Sets text for this element
     * @param text given text
     * @return this text
     */
    T setText(CharSequence text);

    /**
     * Sets text for this element
     * @param text given text
     * @return this text
     */
    T setText(IData text);

    /**
     * Sets text for this element
     * @param text given text
     * @return this text
     */
    T setText(Object text);
}

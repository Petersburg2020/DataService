package nx.peter.java.document.writer.page;

import nx.peter.java.document.core.Document;
import nx.peter.java.document.core.page.body.Element;
import nx.peter.java.document.writer.page.body.Heading;
import nx.peter.java.document.writer.page.body.List;
import nx.peter.java.document.writer.page.body.Text;

public interface Body<B extends Body>
        extends Document.Source<B>,
        nx.peter.java.document.core.page.Body<B> {

    /**
     * Clear all elements in body
     * @return this body
     */
    B clear();

    /**
     * Sets the elements in this body
     * @param elements provided elements
     * @return this body
     */
    B set(java.util.List<Element> elements);

    B set(nx.peter.java.document.core.page.Body body);

    /**
     * Sets the elements in this body
     * @param elements provided elements
     * @return this body
     */
    B set(Element... elements);

    /**
     * Adds the elements in this body
     * @param elements provided elements
     * @return true if at least an element was added
     */
    boolean add(Element... elements);

    /**
     * Adds the elements in this body
     * @param elements provided elements
     * @return true if at least an element was added
     */
    boolean add(java.util.List<Element> elements);

    /**
     * Adds a {@link Text} entity to this body
     * @param text text to add
     * @return true if it was added
     */
    boolean addText(CharSequence text);

    /**
     * Adds a {@link Heading} entity to this body
     * @param heading heading to add
     * @return true if it was added
     */
    boolean addHeading(CharSequence heading);

    /**
     * Adds a list of items of a {@link List} to this body
     * @param items items to add
     * @return true if at least an item was added
     */
    boolean addList(Object... items);

    /**
     * Removes element at index
     * @param index given index
     * @return true if element was removed at index
     */
    boolean removeByIndex(int index);
}

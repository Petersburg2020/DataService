package nx.peter.java.pis.reader;

import nx.peter.java.pis.core.PrettyPrinter;

/**
 * A read only Node; that allows user to read data from the PIS document
 *
 * @param <N> the type of node
 * @param <A> the attribute type
 */
public interface Node<N extends Node, A extends Node.Attr>
        extends nx.peter.java.pis.core.Node<N, A> {
    Object get();

    <V> V get(Class<V> clazz);

    String getString();

    int getInt(int defaultValue);

    long getLong(long defaultValue);

    float getFloat(float defaultValue);

    double getDouble(double defaultValue);

    boolean getBoolean(boolean defaultValue);

    Object get(CharSequence tag);

    /**
     * Gets all child nodes' tag
     * @return child node tags
     */
    Tags getTags();

    /**
     * Gets the list of child nodes with unique tags
     * @return list of unique child nodes
     */
    Nodes<N> getUniqueChildren();

    Nodes<N> getChildByTag(CharSequence tag);

    Nodes<N> getChildByValue(Object value);

    String getString(CharSequence tag);

    int getInt(CharSequence tag, int defaultValue);

    long getLong(CharSequence tag, long defaultValue);

    float getFloat(CharSequence tag, float defaultValue);

    double getDouble(CharSequence tag, double defaultValue);

    boolean getBoolean(CharSequence tag, boolean defaultValue);

    /**
     * Gets attribute with provided name if any
     *
     * @param name provided name of attribute to search
     * @return attribute with name if any
     */
    A getAttribute(CharSequence name);

    /**
     * Gets all attributes
     *
     * @return all attributes
     */
    Attrs<A> getAttributes();

    /**
     * Gets the parent node if any exists
     *
     * @return the parent node if any
     */
    N getParent();

    /**
     * A list of nodes in this node
     *
     * @return list of nodes
     */
    Nodes<N> getChildren();

    /**
     * Gets child Node at index
     * @param index given index
     * @return a node at index
     */
    N getChildAt(int index);

    /**
     * Gets the pretty printer for node
     *
     * @return pretty printer
     */
    PrettyPrinter<Node, Attr> getPrettyPrinter();



    interface Attr<A extends Attr> extends nx.peter.java.pis.core.Node.Attr<A> {
        /**
         * Gets the name for this attribute
         *
         * @return the name for this attribute
         */
        String getName();

        <V> V get(Class<V> clazz);

        Object get();

        String getString();

        int getInt(int defaultValue);

        long getLong(long defaultValue);

        float getFloat(float defaultValue);

        double getDouble(double defaultValue);

        boolean getBoolean(boolean defaultValue);
    }
}

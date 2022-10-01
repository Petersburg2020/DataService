package nx.peter.java.pis.writer;

import java.util.List;

/**
 * A write only Node; that allows user to write data from the PIS document
 * @param <N> the type of node
 * @param <A> the attribute type
 */
public interface Node<N extends Node, A extends Node.Attr> extends nx.peter.java.pis.core.Node<N, A> {
    /**
     * Set this node's tag
     * @param tag provided tag
     */
    void setTag(CharSequence tag);

    /**
     * Set this node's value
     * @param value provided value
     * @param <V> type of value
     */
    <V> void setValue(V value);

    /**
     * Sets the value of this node
     * @param node provided node
     */
    void setValue(nx.peter.java.pis.core.Node node);

    /**
     * Adds an attribute to this node if attribute name does
     * not exist
     * @param name attribute name
     * @param value attribute value
     * @return true if it was added
     */
    boolean addAttr(CharSequence name, Object value);

    /**
     * Adds an attribute to this node if attribute name does
     * not exist
     * @param attr provided attribute
     * @return true if it was added
     */
    boolean addAttr(nx.peter.java.pis.core.Node.Attr attr);

    /**
     * Adds new node(s) to this node
     * @param tag unique tag for all nodes
     * @param values values of nodes
     * @param <V> type of values
     * @return true if node(s) was added
     */
    <V> boolean addAll(CharSequence tag, V... values);

    /**
     * Adds new node(s) to this node
     * @param tag unique tag for all nodes
     * @param values values of nodes
     * @param <V> type of values
     * @return true if node(s) was added
     */
    <V> boolean addAll(CharSequence tag, List<V> values);

    /**
     * Adds a new node to this node
     * @param tag unique tag for node
     * @param value value of node
     * @param <V> type of value
     * @return true if it was added
     */
    <V> boolean addNode(CharSequence tag, V value);

    /**
     * Adds node to this node
     * @param node provided node
     * @return true if it was added
     */
    boolean addNode(nx.peter.java.pis.core.Node node);

    /**
     * Adds new nodes to this node
     * @param nodes provided nodes
     * @param <N> node type
     * @return true if nodes were added
     */
    <N extends nx.peter.java.pis.core.Node> boolean addNodes(N... nodes);

    /**
     * Adds new nodes to this node
     * @param nodes provided nodes
     * @return true if nodes were added
     */
    boolean addNodes(Nodes<? extends nx.peter.java.pis.core.Node> nodes);

    /**
     * Adds new nodes to this node
     * @param nodes provided nodes
     * @return true if nodes were added
     */
    boolean addNodes(List<? extends nx.peter.java.pis.core.Node> nodes);

    /**
     * Removes node from this node's children
     * @param node provided node
     * @return true if node was removed
     */
    boolean removeNode(nx.peter.java.pis.core.Node node);

    /**
     * Removes nodes from this node's children
     * @param nodes provided nodes
     * @return true if nodes were removed
     */
    boolean removeAll(Nodes<? extends nx.peter.java.pis.core.Node> nodes);

    /**
     * Removes the node(s) with the given tag
     * @param tag provided tag
     * @return true if node was removed
     */
    boolean removeTag(CharSequence tag);

    /**
     * Removes the node(s) with the given value
     * @param value provided value
     * @return true if node was removed
     */
    boolean removeValue(Object value);

    /**
     * Removes attribute with name from this node
     * @param name provided attribute name
     * @return true if attribute was removed
     */
    boolean removeAttribute(CharSequence name);

    /**
     * Removes attribute from this node
     * @param attribute provided attribute
     * @return true if attribute was removed
     */
    boolean removeAttribute(nx.peter.java.pis.core.Node.Attr attribute);


    /**
     * A writable Attribute for creating attributes
     * @param <A> the specific type
     */
    interface Attr<A extends Attr> extends nx.peter.java.pis.core.Node.Attr<A> {
        /**
         * Set the name of attribute
         * @param name provided name
         */
        void setName(CharSequence name);

        /**
         * Sets the value of attribute
         * @param value provided value
         * @param <V> object type of value
         */
        <V> void setValue(V value);
    }
}

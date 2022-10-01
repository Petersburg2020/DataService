package nx.peter.java.pis.core;

import nx.peter.java.context.DataFile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Node is an object that represents each tagged reference in a PIS document
 * @param <N> the type of Node
 * @param <A> the type Attr
 */
public interface Node<N extends Node, A extends Node.Attr> extends DataFile<N> {
    /**
     * Gets the size of elements in this node
     * @return size of elements in node
     */
    int size();

    /**
     * Checks if it's an integer node
     * @return true if it's an integer
     */
    boolean isInt();

    /**
     * Checks if it's a long node
     * @return true if it's a long
     */
    boolean isLong();

    /**
     * Checks if it's a float node
     * @return true if it's a float
     */
    boolean isFloat();

    /**
     * Checks if it's a double node
     * @return true if it's a double
     */
    boolean isDouble();

    /**
     * Checks if it's a string node
     * @return true if it's a string
     */
    boolean isString();

    /**
     * Checks if it's a boolean node
     * @return true if it's a boolean
     */
    boolean isBoolean();

    /**
     * Checks if element with tag is an integer node
     * @param tag the tag to check
     * @return true if it's an integer
     */
    boolean isInt(CharSequence tag);

    /**
     * Checks if element with tag is a long node
     * @param tag the tag to check
     * @return true if it's a long
     */
    boolean isLong(CharSequence tag);

    /**
     * Checks if element with tag is a float node
     * @param tag the tag to check
     * @return true if it's a float
     */
    boolean isFloat(CharSequence tag);

    /**
     * Checks if element with tag is a double node
     * @param tag the tag to check
     * @return true if it's a double
     */
    boolean isDouble(CharSequence tag);

    /**
     * Checks if element with tag is a string node
     * @param tag the tag to check
     * @return true if it's a string
     */
    boolean isString(CharSequence tag);

    /**
     * Checks if element with tag is a boolean node
     * @param tag the tag to check
     * @return true if it's a boolean
     */
    boolean isBoolean(CharSequence tag);

    /**
     * Gets the tag for this node
     * @return the tag for this node
     */
    String getTag();

    /**
     * Checks if node has no element
     * @return false if element exists and true if otherwise
     */
    boolean isEmpty();

    /**
     * Gets attribute size
     * @return attribute size
     */
    int attributeCount();

    /**
     * Checks if node has element
     * @return true if element exists and false if otherwise
     */
    boolean isNotEmpty();

    /**
     * Checks if this node contains any attribute
     * @return true if attribute exists
     */
    boolean hasAttribute();

    /**
     * Checks if this node has a parent node
     * @return true if it's in parent
     */
    boolean hasParent();

    /**
     * Checks if value exists within this node
     * @param value provided value
     * @return true if value exists and false if otherwise
     */
    <V> boolean containsValue(V value);

    /**
     * Checks if child node exists within this node
     * @param node provided node
     * @return true if node exists and false if otherwise
     */
    boolean containsChild(Node node);

    /**
     * Checks if child node with tag exists within this node
     * @param tag provided tag
     * @return true if node exists and false if otherwise
     */
    boolean containsChild(CharSequence tag);

    /**
     * Checks if attribute exists within this node
     * @param attr provided attribute
     * @return true if attribute exists and false if otherwise
     */
    boolean containsAttr(A attr);

    /**
     * Checks if attribute with provided name exists within this node
     * @param name provided attribute name
     * @return true if attribute exists and false if otherwise
     */
    boolean containsAttr(CharSequence name);

    /**
     * Checks if it contains only a non-node objects
     * @return true if it contains only a non-node object
     */
    boolean isSingleValue();

    /**
     * Checks if it contains numerous multiple elements
     * @return true if it contains numerous multiple elements
     */
    boolean hasChildren();

    /**
     * Checks if this node equals the provided node
     * @param node the provided node
     * @return true if this node equals the provided node
     */
    boolean equals(Node<? extends Node, ? extends Attr> node);



    /**
     * Attr is an object that represents the
     * various attributes of a node in a PIS document
     * @param <A> the type of Attr
     */
    interface Attr<A extends Attr> extends Serializable {

        /**
         * Checks if attribute has no value
         * @return false if value exists and true if otherwise
         */
        boolean isEmpty();

        /**
         * Checks if it's an integer attribute
         * @return true if it's an integer
         */
        boolean isInt();

        /**
         * Checks if it's a long attribute
         * @return true if it's a long
         */
        boolean isLong();

        /**
         * Checks if it's a float attribute
         * @return true if it's a float
         */
        boolean isFloat();

        /**
         * Checks if it's a double attribute
         * @return true if it's a double
         */
        boolean isDouble();

        /**
         * Checks if it's a string attribute
         * @return true if it's a string
         */
        boolean isString();

        /**
         * Checks if it's a boolean attribute
         * @return true if it's a boolean
         */
        boolean isBoolean();

        /**
         * Checks if attribute has no value
         * @return true if value exists and false if otherwise
         */
        boolean isNotEmpty();

        /**
         * Checks if the given attribute equals this
         * @param attr given attribute
         * @return true if they are equal
         */
        boolean equals(Attr<?> attr);
    }

    /**
     * A list of Attr (attributes in a Node)
     * @param <A> the type of Attr
     */
    class Attrs<A extends Attr> implements Iterable<A> {
        /**
         * The list of attributes
         */
        protected List<A> attrs;

        /**
         * List of attributes constructor that takes a list of attribute
         * @param attrs provided list of attribute
         */
        public Attrs(List<A> attrs) {
            this.attrs = attrs;
        }

        /**
         * Checks if provided attribute is in this attribute
         * @param attribute provided attribute
         * @return true if attribute exists
         */
        public boolean contains(A attribute) {
            return attrs.contains(attribute);
        }

        /**
         * Checks if this attribute list contains an attribute with the given name
         * @param name the provided name
         * @return true if attribute exists
         */
        public boolean contains(CharSequence name) {
            for (A attr : attrs)
                if (attr instanceof nx.peter.java.pis.Node.Attr && ((nx.peter.java.pis.Node.Attr) attr).getName().contentEquals(name))
                    return true;
            return false;
        }

        /**
         * Checks if list is empty
         * @return true if it's empty
         */
        public boolean isEmpty() {
            return attrs.isEmpty();
        }

        /**
         * Checks if list is not empty
         * @return true if it's not empty
         */
        public boolean isNotEmpty() {
            return !isEmpty();
        }

        /**
         * Gets the number of attributes in this list
         * @return number of attributes in list
         */
        public int size() {
            return attrs.size();
        }

        /**
         * Gets all attributes names
         * @return all attributes names
         */
        public Names getNames() {
            List<String> names = new ArrayList<>();
            for (A attr : attrs)
                names.add(((nx.peter.java.pis.reader.Node.Attr<?>) attr).getName());
            return new Names(names);
        }

        @Override
        public Iterator<A> iterator() {
            return attrs.iterator();
        }

        /**
         * Gets a sequential {@code Stream} with this list of attributes as its source.
         * @return a sequential stream of this attributes
         */
        public Stream<A> stream() {
            return attrs.stream();
        }

        /**
         * Returns a stream consisting of the results of applying the given
         * function to the elements of this stream.
         * @param mapper a <a href="package-summary.html#NonInterference">non-interfering</a>,
         *              <a href="package-summary.html#Statelessness">stateless</a>
         *              function to apply to each element
         * @param <R> The element type of the new stream
         * @return the new stream
         */
        public <R> Stream<R> map(Function<A, R> mapper) {
            return stream().map(mapper);
        }

        /**
         * Checks if provided list of attributes equals this list
         * @param attrs provided list of attributes
         * @return true if they are equal
         */
        public boolean equals(Attrs<? extends Attr> attrs) {
            return attrs != null && attrs.attrs.equals(this.attrs);
        }

    }

    /**
     * Node represents a list of Node. <br>
     * Here you get access to:<br><br>
     * <code>
     *     <b>int</b> size();<br>
     *     <b>boolean</b> isEmpty();<br>
     *     <b>Node</b> get(int index);<br>
     *     <b>boolean</b> isNotEmpty();<br>
     *     <b>boolean</b> contains(Node node);<br>
     *     <b>Nodes<Node></b> get(CharSequence tag);
     * </code><br><br>
     * <pre><b>int</b> size() :</pre><i>returns the number of nodes in the class</i><br><br>
     * <pre><b>boolean</b> isEmpty() :</pre><i>returns true if size of nodes is zero else false</i>
     * @param <N> a type of Node
     */
    class Nodes<N extends Node> implements Iterable<N> {
        protected List<N> nodes;

        /**
         * A constructor that creates an empty list of nodes
         */
        public Nodes() {
            this(new ArrayList<>());
        }

        /**
         * A constructor that creates an instance
         * of Nodes from the provided list of nodes
         * @param nodes list of nodes
         */
        public Nodes(List<N> nodes) {
            this.nodes = new ArrayList<>();
            if (nodes != null)
                for (N node : nodes)
                    if (node != null)
                        this.nodes.add(node);
        }

        /**
         * Gets the node in the list at the position provided
         * @param index position of node provided
         * @return a node at the position provided
         */
        public N get(int index) {
            return isNotEmpty() && index < size() && index >= 0 ? nodes.get(index) : null;
        }

        /**
         * Gets the list of nodes with the tag provided
         * @param tag the provided tag of node
         * @return list of nodes with the provided tag
         */
        public Nodes<N> get(CharSequence tag) {
            List<N> nodes = new ArrayList<>();
            for (N node : this.nodes)
                if (node.getTag().contentEquals(tag))
                    nodes.add(node);
            return new Nodes<>(nodes);
        }

        /**
         * Gets the number of nodes in the list
         * @return number of nodes
         */
        public int size() {
            return nodes.size();
        }

        /**
         * Checks if node exists in this list
         * @param node the provided node
         * @return true if node exists else false
         */
        public boolean contains(N node) {
            return nodes.contains(node);
        }

        /**
         * Checks if there is no node in the list
         * @return true if no node is in list
         */
        public boolean isEmpty() {
            return nodes.isEmpty();
        }

        /**
         * Checks if there is node in this list
         * @return true if list has at least one node
         */
        public boolean isNotEmpty() {
            return !isEmpty();
        }

        /**
         * Gets tags for nodes, ignoring duplicates
         * @return gets nodes tags
         */
        public Tags getTags() {
            List<String> tags = new ArrayList<>();
            for (N node : nodes)
                if (!tags.contains(node.getTag()))
                    tags.add(node.getTag());
            return new Tags(tags);
        }


        /**
         * An Iterator over a collection of nodes
         * @return iterator of nodes
         */
        @Override
        public Iterator<N> iterator() {
            return nodes.iterator();
        }

        @Override
        public String toString() {
            return nodes.toString();
        }
    }


    /**
     * A list of node tags
     */
    class Tags implements Iterable<String> {
        protected List<String> tags;

        public Tags(List<String> tags) {
            this.tags = tags != null ? tags : new ArrayList<>();
        }

        public String getTag(int index) {
            return index >= 0 && index < size() ? tags.get(index) : null;
        }

        public int size() {
            return tags.size();
        }

        public boolean contains(CharSequence tag) {
            return tag != null && tags.contains(tag.toString());
        }

        @Override
        public Iterator<String> iterator() {
            return tags.iterator();
        }
    }

    /**
     * A list of attribute names
     */
    class Names implements Iterable<String> {
        protected final List<String> names;

        public Names(List<String> names) {
            this.names = names;
        }

        public String getName(int index) {
            return index >= 0 && index < size() ? names.get(index) : null;
        }

        public int size() {
            return names.size();
        }

        public boolean contains(CharSequence name) {
            return name != null && names.contains(name.toString());
        }

        @Override
        public Iterator<String> iterator() {
            return names.iterator();
        }
    }

}


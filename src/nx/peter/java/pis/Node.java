package nx.peter.java.pis;

import nx.peter.java.pis.core.PrettyPrinter;
import nx.peter.java.pis.core.Source;
import nx.peter.java.util.data.DataChecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Node extends DataChecker
        implements nx.peter.java.pis.reader.Node<Node, Node.Attr>,
        nx.peter.java.pis.writer.Node<Node, Node.Attr> {
    protected Pis<Node, Attr> root;

    public Node() {
        this("");
    }

    public Node(nx.peter.java.pis.core.Node node) {
        this();
        if (node instanceof Node)
            root = ((Node) node).root;
    }

    public Node(CharSequence tag) {
        this(tag, null);
    }

    public Node(CharSequence tag, Object value) {
        root = new Pis<>(tag, value);
    }

    public Node(CharSequence tag, Pis.TagValueList node) {
        root = new Pis<>(tag, node);
    }

    public Node(CharSequence tag, Object value, nx.peter.java.pis.core.Node parent) {
        this(tag, value);
        if (parent instanceof Node)
            root.setParent(parent.getTag(), ((Node) parent).root.node);
    }

    public Node(CharSequence tag, Pis.TagValueList node, nx.peter.java.pis.core.Node parent) {
        this(tag, node);
        if (parent instanceof Node)
            root.setParent(parent.getTag(), ((Node) parent).root.node);
    }

    public Node(CharSequence tag, Pis.TagValueList node, CharSequence parentTag, Pis.TagValueList parent) {
        this(tag, node);
        root.setParent(parentTag, parent);
    }

    @Override
    public int size() {
        return root.size();
    }

    @Override
    public boolean isInt() {
        return root.isInt();
    }

    @Override
    public boolean isLong() {
        return root.isLong();
    }

    @Override
    public boolean isFloat() {
        return root.isFloat();
    }

    @Override
    public boolean isDouble() {
        return root.isDouble();
    }

    @Override
    public boolean isString() {
        return root.isString();
    }

    @Override
    public boolean isBoolean() {
        return root.isBoolean();
    }

    @Override
    public boolean isInt(CharSequence tag) {
        return isInt(get(tag));
    }

    @Override
    public boolean isLong(CharSequence tag) {
        return isLong(get(tag));
    }

    @Override
    public boolean isFloat(CharSequence tag) {
        return isFloat(get(tag));
    }

    @Override
    public boolean isDouble(CharSequence tag) {
        return isDouble(get(tag));
    }

    @Override
    public boolean isString(CharSequence tag) {
        return isString(get(tag));
    }

    @Override
    public boolean isBoolean(CharSequence tag) {
        return isBoolean(get(tag));
    }

    @Override
    public Object get() {
        return root.get();
    }

    @Override
    public <V> V get(Class<V> clazz) {
        return (V) get();
    }

    @Override
    public String getString() {
        return isString() ? (String) get() : null;
    }

    @Override
    public int getInt(int defaultValue) {
        return isInt() ? (int) get() : defaultValue;
    }

    @Override
    public long getLong(long defaultValue) {
        return isLong() ? (long) get() : defaultValue;
    }

    @Override
    public float getFloat(float defaultValue) {
        return isFloat() ? (float) get() : defaultValue;
    }

    @Override
    public double getDouble(double defaultValue) {
        return isDouble() ? (double) get() : defaultValue;
    }

    @Override
    public boolean getBoolean(boolean defaultValue) {
        return isBoolean() ? (boolean) get() : defaultValue;
    }

    @Override
    public String getTag() {
        return root.tag;
    }

    @Override
    public boolean isEmpty() {
        return root.isEmpty();
    }

    @Override
    public int attributeCount() {
        return getAttributes().size();
    }

    @Override
    public boolean isNotEmpty() {
        return root.isNotEmpty();
    }

    @Override
    public Node getDataFile() {
        return this;
    }

    @Override
    public boolean hasAttribute() {
        return root.hasAttribute();
    }

    @Override
    public boolean hasParent() {
        return root.hasParent();
    }

    @Override
    public <V> boolean containsValue(V value) {
        return value instanceof Node ? containsChild((Node) value) : root.containsValue(value);
    }

    @Override
    public boolean containsChild(nx.peter.java.pis.core.Node node) {
        return node instanceof Node && root.containsChild(node.getTag(), ((Node) node).root.node);
    }

    @Override
    public boolean containsChild(CharSequence tag) {
        return root.containsChild(tag);
    }

    @Override
    public boolean containsAttr(Attr attr) {
        return getAttributes().contains(attr);
    }

    @Override
    public boolean containsAttr(CharSequence name) {
        return getAttributes().contains(name);
    }

    @Override
    public Attr getAttribute(CharSequence name) {
        return root.getAttribute(name);
    }

    @Override
    public Attrs<Attr> getAttributes() {
        return root.getAttributes();
    }

    @Override
    public Node getParent() {
        return root.getParent();
    }

    @Override
    public Nodes<Node> getChildren() {
        return root.getChildren();
    }

    @Override
    public Node getChildAt(int index) {
        Nodes<Node> nodes = getChildren();
        return nodes.get(index);
    }

    public PrettyPrinter<nx.peter.java.pis.reader.Node, nx.peter.java.pis.reader.Node.Attr> getPrettyPrinter() {
        return new PrettyPrinter<>() {
            @Override
            public nx.peter.java.pis.reader.Node getNode() {
                return Node.this;
            }

            @Override
            public String print() {
                return root.getPrettyPrinter().print();
            }

            @Override
            public Source<? extends Source, ? extends nx.peter.java.pis.reader.Node, ? extends nx.peter.java.pis.reader.Node.Attr> getSource() {
                return root;
            }

            @Override
            public PrettyPrinter<nx.peter.java.pis.reader.Node, nx.peter.java.pis.reader.Node.Attr> printer(nx.peter.java.pis.core.Node node) {
                return ((Node) node).getPrettyPrinter();
            }

            @Override
            public boolean equals(PrettyPrinter<? extends nx.peter.java.pis.core.Node, ? extends nx.peter.java.pis.core.Node.Attr> printer) {
                return root.getPrettyPrinter().equals(printer);
            }

            @Override
            public String toString() {
                return print();
            }
        };
    }

    @Override
    public boolean isSingleValue() {
        return root.isSingleValue();
    }

    @Override
    public boolean hasChildren() {
        return root.hasChildren();
    }

    @Override
    public boolean equals(nx.peter.java.pis.core.Node<? extends nx.peter.java.pis.core.Node, ? extends nx.peter.java.pis.core.Node.Attr> node) {
        return node instanceof Node && root.equals(((Node) node).root);
    }

    @Override
    public Object get(CharSequence tag) {
        return root.getChild(tag);
    }

    @Override
    public Tags getTags() {
        return getChildren().getTags();
    }

    @Override
    public Nodes<Node> getUniqueChildren() {
        List<Node> nodes = new ArrayList<>();
        for (String tag : getTags()) {
            Nodes<Node> child = getChildByTag(tag);
            if (child.size() == 1)
                nodes.add(child.get(0));
        }
        return new Nodes<>(nodes);
    }

    @Override
    public Nodes<Node> getChildByTag(CharSequence tag) {
        return root.getChild(tag);
    }

    @Override
    public Nodes<Node> getChildByValue(Object value) {
        if (hasChildren() && value != null && !(value instanceof Node) && !(value instanceof Pis.TagValueList)) {
            List<Node> nodes = new ArrayList<>();
            for (Node child : getChildren())
                if (child.isSingleValue() && child.get().equals(value))
                    nodes.add(child);
            return new Nodes<>(nodes);
        }
        return new Nodes<>();
    }

    @Override
    public String getString(CharSequence tag) {
        return isString(tag) ? (String) get(tag) : null;
    }

    @Override
    public int getInt(CharSequence tag, int defaultValue) {
        return isInt(tag) ? (int) get(tag) : defaultValue;
    }

    @Override
    public long getLong(CharSequence tag, long defaultValue) {
        return isLong(tag) ? (long) get(tag) : defaultValue;
    }

    @Override
    public float getFloat(CharSequence tag, float defaultValue) {
        return isFloat(tag) ? (float) get(tag) : defaultValue;
    }

    @Override
    public double getDouble(CharSequence tag, double defaultValue) {
        return isDouble(tag) ? (double) get(tag) : defaultValue;
    }

    @Override
    public boolean getBoolean(CharSequence tag, boolean defaultValue) {
        return isBoolean(tag) ? (boolean) get(tag) : defaultValue;
    }

    @Override
    public void setTag(CharSequence tag) {
        root.setTag(tag);
    }

    @Override
    public <V> void setValue(V value) {
        if (value instanceof Pis.TagValueList)
            root.setNode((Pis.TagValueList) value);
        else if (value != null)
            root.setValue(value);
    }

    @Override
    public boolean addAttr(CharSequence name, Object value) {
        return root.addAttr(name, value);
    }

    @Override
    public boolean addAttr(nx.peter.java.pis.core.Node.Attr attr) {
        return attr instanceof Attr && addAttr(((Attr) attr).name, ((Attr) attr).value);
    }

    @SafeVarargs
    @Override
    public final <V> boolean addAll(CharSequence tag, V... values) {
        return addAll(tag, Arrays.asList(values));
    }

    @Override
    public <V> boolean addAll(CharSequence tag, List<V> values) {
        if (values != null && tag != null && !values.isEmpty()) {
            int count = 0;
            for (V value : values) {
                boolean added = addNode(tag, value);
                count = added ? 1 : 0;
            }
            return count > 0;
        }
        return false;
    }

    @Override
    public <V> boolean addNode(CharSequence tag, V value) {
        if (tag != null)
            if (value instanceof Pis.TagValueList)
                return addNode(new Node(tag, (Pis.TagValueList) value));
            else if (value instanceof Node) {
                ((Node) value).setTag(tag);
                return addNode((Node) value);
            }
        return tag != null && addNode(new Node(tag, value));
    }

    @Override
    public void setValue(nx.peter.java.pis.core.Node node) {
        if (node instanceof Node)
            root.set(node.getTag(), ((Node) node).root.node);
    }

    @Override
    public boolean addNode(nx.peter.java.pis.core.Node node) {
        // System.out.println("Add Node: " + (node instanceof Node));
        return node instanceof Node && root.add(node.getTag(), ((Node) node).root.node);
    }

    @SafeVarargs
    @Override
    public final <N extends nx.peter.java.pis.core.Node> boolean addNodes(N... nodes) {
        return addNodes(Arrays.asList(nodes));
    }

    @Override
    public boolean addNodes(Nodes<? extends nx.peter.java.pis.core.Node> nodes) {
        if (nodes != null && nodes.isNotEmpty()) {
            for (nx.peter.java.pis.core.Node node : nodes)
                addNode(node);
            return true;
        }
        return false;
    }

    @Override
    public boolean addNodes(List<? extends nx.peter.java.pis.core.Node> nodes) {
        return addNodes(new Nodes<>(nodes));
    }

    @Override
    public boolean removeNode(nx.peter.java.pis.core.Node node) {
        return node instanceof Node && root.removeNode(((Node) node).root.node);
    }

    @Override
    public boolean removeAll(Nodes<? extends nx.peter.java.pis.core.Node> nodes) {
        if (nodes != null) {
            boolean removed = false;
            for (nx.peter.java.pis.core.Node node : nodes)
                if (removed) removeNode(node);
                else removed = removeNode(node);
            return removed;
        }
        return false;
    }

    @Override
    public boolean removeTag(CharSequence tag) {
        return tag != null && removeAll(getChildByTag(tag));
    }

    @Override
    public boolean removeValue(Object value) {
        return value != null && removeAll(getChildByValue(value));
    }

    @Override
    public boolean removeAttribute(CharSequence name) {
        return root.removeAttr(name);
    }

    @Override
    public boolean removeAttribute(nx.peter.java.pis.core.Node.Attr attribute) {
        return attribute != null && root.removeAttr(((Attr) attribute).getName());
    }

    @Override
    public String toString() {
        return root.toString();
    }

    public static class Attr extends DataChecker
            implements nx.peter.java.pis.reader.Node.Attr<Attr>,
            nx.peter.java.pis.writer.Node.Attr<Attr> {
        protected String name;
        protected Object value;

        public Attr(Map.Entry<String, Object> entry) {
            this(entry.getKey(), entry.getValue());
        }

        public Attr(CharSequence name, Object value) {
            this.name = name != null ? name.toString() : "";
            this.value = value;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public boolean isEmpty() {
            return value.toString().isEmpty();
        }

        @Override
        public boolean isInt() {
            return isInt(value);
        }

        @Override
        public boolean isLong() {
            return isLong(value);
        }

        @Override
        public boolean isFloat() {
            return isFloat(value);
        }

        @Override
        public boolean isDouble() {
            return isDouble(value);
        }

        @Override
        public boolean isString() {
            return isString(value);
        }

        @Override
        public boolean isBoolean() {
            return isBoolean(value);
        }

        @Override
        public boolean isNotEmpty() {
            return !isEmpty();
        }

        @Override
        public boolean equals(nx.peter.java.pis.core.Node.Attr<?> attr) {
            return attr instanceof Attr && name.contentEquals(((Attr) attr).getName()) && get().equals(((Attr) attr).name);
        }

        @Override
        public Object get() {
            return value;
        }

        @Override
        public <V> V get(Class<V> clazz) {
            return value.getClass().equals(clazz) ? (V) value : null;
        }

        @Override
        public String getString() {
            return isString() ? (String) get() : null;
        }

        @Override
        public int getInt(int defaultValue) {
            return isInt() ? (int) get() : defaultValue;
        }

        @Override
        public long getLong(long defaultValue) {
            return isLong() ? (long) get() : defaultValue;
        }

        @Override
        public float getFloat(float defaultValue) {
            return isFloat() ? (float) get() : defaultValue;
        }

        @Override
        public double getDouble(double defaultValue) {
            return isDouble() ? (double) get() : defaultValue;
        }

        @Override
        public boolean getBoolean(boolean defaultValue) {
            return isBoolean() ? (boolean) get() : defaultValue;
        }

        @Override
        public void setName(CharSequence name) {
            this.name = name != null ? name.toString() : null;
        }

        @Override
        public <V> void setValue(V value) {
            this.value = value;
        }
    }
}

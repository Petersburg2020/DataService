package nx.peter.java.pis;

import nx.peter.java.context.model.Model;
import nx.peter.java.service.Service;
import nx.peter.java.storage.FileManager;
import nx.peter.java.pis.core.Node;
import nx.peter.java.pis.core.PrettyPrinter;
import nx.peter.java.pis.core.Source;
import nx.peter.java.pis.reader.PisReader;
import nx.peter.java.pis.writer.PisWriter;
import nx.peter.java.util.Random;
import nx.peter.java.util.Util;
import nx.peter.java.util.data.DataChecker;
import nx.peter.java.util.data.DataManager;

import java.util.*;

public class Pis<N extends Node, A extends Node.Attr> extends DataChecker implements Source<Pis, N, A> {
    public static final String ATTRS_NAME = "attrs";
    public static final String SINGLE_CHILD_TAG = "single-child-tag";


    public TagValueList node, parent;
    public String tag, parentTag;

    public Pis(CharSequence tag, Object value) {
        parent = null;
        parentTag = null;
        node = new TagValueList();
        set(tag, value);
    }

    public Pis(CharSequence tag, List<TagValue> node) {
        this(tag, new TagValueList(node));
    }

    public Pis(CharSequence tag, TagValueList node) {
        parent = null;
        parentTag = null;
        this.node = new TagValueList();
        set(tag, node);
    }

    public void setNode(TagValueList node) {
        if (hasParent())
            parent.removeTag(tag);
        Map<String, Object> attrs = getAttrs();
        this.node = node != null ? node : new TagValueList();
        if (!attrs.isEmpty()) this.node.setAttr(attrs);
        if (hasParent())
            parent.add(tag, node);
    }

    public void set(CharSequence tag, Object value) {
        setTag(tag);
        setValue(value);
    }


    public void setValue(Object value) {
        if (hasParent())
            parent.removeTag(tag);
        Map<String, Object> attrs = getAttrs();
        node.set(value);
        if (!attrs.isEmpty()) this.node.setAttr(attrs);
        if (hasParent())
            parent.add(tag, value);
    }

    public void set(CharSequence tag, TagValueList node) {
        setTag(tag);
        setNode(node);
    }

    public void set(CharSequence tag, Node node) {
        if (node != null)
            set(tag, ((nx.peter.java.pis.Node) node).root.node);
    }

    public boolean add(CharSequence tag, Object value) {
        // System.out.println("Is Single Value: " + node.isSingleValue());
        if (!node.isSingleValue() && tag != null && !tag.equals(ATTRS_NAME) && value != null) {
            node.add(tag.toString(), value);
            if (hasParent())
                parent.add(this.tag, node);
            return true;
        }
        return false;
    }

    public boolean add(CharSequence tag, TagValue... node) {
        return add(tag, Arrays.asList(node));
    }

    public boolean add(CharSequence tag, List<TagValue> node) {
        return add(tag, node != null ? new TagValueList(node) : null);
    }

    public boolean add(CharSequence tag, TagValueList node) {
        // System.out.println("Add Pis: " + (!isSingleValue() && tag != null && !tag.equals(ATTRS_NAME) && node != null));
        if (!isSingleValue() && tag != null && !tag.equals(ATTRS_NAME) && node != null) {
            boolean added = this.node.add(tag.toString(), node);
            // System.out.println("Added: " + added);
            if (hasParent() && added)
                parent.replace(this.tag, new TagValue(this.tag, this.node));
            return added;
        }
        return false;
    }

    public boolean removeTag(CharSequence tag) {
        if (hasChildren() && tag != null)
            return node.removeTag(tag.toString());
        return false;
    }

    public void setTag(CharSequence tag) {
        if (hasParent() && parent.containsTag(this.tag)) {
            parent.removeTag(this.tag);
            if (node.isNotEmpty())
                parent.add(this.tag, node.getNodes());
            else if (node.isSingleValue())
                parent.add(this.tag, node.getSingleValue());
        }
        this.tag = tag != null ? tag.toString() : "";
    }

    public boolean addAttr(CharSequence name, Object value) {
        return node.addAttr(name, value);
    }

    public void setAttr(Node.Attr... attrs) {
        setAttr(Arrays.asList(attrs));
    }

    public void setAttr(Node.Attrs<? extends Node.Attr> attrs) {
        node.setAttrs(attrs);
    }

    public void setAttr(List<? extends Node.Attr> attrs) {
        if (attrs != null)
            for (Node.Attr attr : attrs)
                if (attr instanceof nx.peter.java.pis.Node.Attr)
                    node.addAttr(((nx.peter.java.pis.Node.Attr) attr).getName(), ((nx.peter.java.pis.Node.Attr) attr).value);
    }

    public void setAttr(Map<String, Object> attrs) {
        node.setAttr(attrs);
    }

    public boolean removeAttr(CharSequence name) {
        return node.removeAttr(name);
    }

    @Override
    public boolean isEmpty() {
        return !isSingleValue() && !hasChildren();
    }

    @Override
    public boolean isFloat() {
        return isFloat(get());
    }

    @Override
    public boolean isString() {
        return isString(get());
    }

    @Override
    public boolean isDouble() {
        return isDouble(get());
    }

    @Override
    public boolean isBoolean() {
        return isBoolean(get());
    }

    @Override
    public boolean hasChildren() {
        return node != null && node.hasChildren();
    }

    @Override
    public boolean hasAttribute() {
        return node.hasAttribute() && !node.getAttrs().isEmpty();
    }

    @Override
    public boolean isSingleValue() {
        return node.isSingleValue();
    }

    @Override
    public A getAttribute(CharSequence name) {
        return node.getAttribute(name);
    }

    public void setParent(CharSequence tag, TagValueList parent) {
        this.parentTag = tag != null ? tag.toString() : null;
        this.parent = parent;
    }

    public boolean hasParent() {
        return parentTag != null && !parentTag.isEmpty() && parent != null && parent.containsTag(tag) && parent.containsValue(node);
    }

    @Override
    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public Map<String, Object> getAttrs() {
        return node != null ? node.getAttrs() : new LinkedHashMap<>();
    }

    @Override
    public Node.Attrs<A> getAttributes() {
        List<A> attrs = new ArrayList<>();
        if (hasAttribute())
            for (String name : node.getAttrs().keySet())
                attrs.add(getAttribute(name));
        return new Node.Attrs<>(attrs);
    }

    public boolean isValid() {
        return !tag.isEmpty() && !isEmpty();
    }

    @Override
    public N getNode() {
        return (N) new nx.peter.java.pis.Node(tag, node);
    }

    @Override
    public Object get() {
        return node.getSingleValue();
    }

    @Override
    public N getParent() {
        return hasParent() ? (N) new nx.peter.java.pis.Node(parentTag, parent) : null;
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public boolean isInt() {
        return isInt(get());
    }

    @Override
    public boolean isLong() {
        return isLong(get());
    }

    @Override
    public <V> V get(Class<V> clazz) {
        return node.getSingleValue(clazz);
    }

    @Override
    public Node.Nodes<N> getChildren() {
        List<N> nodes = new ArrayList<>();
        for (TagValue v : node)
            if (!v.equalTag(ATTRS_NAME) && !v.equalTag(SINGLE_CHILD_TAG)) {
                Node node = v.value instanceof TagValueList ? new nx.peter.java.pis.Node(v.tag, (TagValueList) v.value) : new nx.peter.java.pis.Node(v.tag, v.value, getNode());
                nodes.add((N) node);
            }
        return new Node.Nodes<>(nodes);
    }

    @Override
    public Node.Nodes<N> getChild(CharSequence tag) {
        List<N> nodes = new ArrayList<>();
        if (tag != null && hasChildren() && !ATTRS_NAME.contentEquals(tag) && !SINGLE_CHILD_TAG.contentEquals(tag))
            for (TagValue v : node)
                if (v.equalTag(tag))
                    nodes.add((N) new nx.peter.java.pis.Node(v.tag, v.value));
        return new Node.Nodes<>(nodes);
    }

    @Override
    public int size() {
        return node.size();
    }

    public String prettyPrint() {
        return Util.toPrettyPis(this, 0);
    }

    @Override
    public String toString() {
        return Util.toPis(this, 0);
    }

    @Override
    public PrettyPrinter<N, A> getPrettyPrinter() {
        return new PrettyPrinter<>() {
            @Override
            public N getNode() {
                return Pis.this.getNode();
            }

            @Override
            public String print() {
                return prettyPrint();
            }

            @Override
            public Source<? extends Source, N, A> getSource() {
                return Pis.this;
            }

            @Override
            public PrettyPrinter<? extends Node, ? extends Node.Attr> printer(Node node) {
                return node instanceof nx.peter.java.pis.Node ? ((nx.peter.java.pis.Node) node).getPrettyPrinter() : null;
            }

            @Override
            public boolean equals(PrettyPrinter<? extends Node, ? extends Node.Attr> printer) {
                return printer.getNode().equals(getNode());
            }

            @Override
            public String toString() {
                return print();
            }
        };
    }

    @Override
    public PisContext getContext() {
        return new Context(getNode());
    }

    @Override
    public byte[] encode() {
        return toString().getBytes();
    }

    @Override
    public boolean equals(Source<? extends Source, ? extends Node, ? extends Node.Attr> source) {
        return source instanceof Pis && node.equals(((Pis) source).node) && tag.contentEquals(((Pis) source).tag);
    }

    @Override
    public boolean containsChild(CharSequence tag) {
        return node.containsTag(tag);
    }

    @Override
    public boolean containsChild(CharSequence tag, TagValueList node) {
        return this.node.contains(new TagValue(tag, node));
    }

    @Override
    public <V> boolean containsValue(V value) {
        return node.containsValue(value);
    }

    public boolean removeNode(TagValueList node) {
        if (hasChildren())
            return this.node.remove(node);
        return false;
    }





    public static class TagValue {
        public String tag;
        public Object value;

        public TagValue(CharSequence tag, Object value) {
            this.tag = tag != null ? tag.toString() : "";
            this.value = value;
        }

        public boolean isValid() {
            return tag != null && !tag.isEmpty() && value != null;
        }

        public boolean equalTag(CharSequence tag) {
            return tag != null && this.tag.contentEquals(tag);
        }

        public boolean equalValue(Object value) {
            return this.value.equals(value);
        }

        public boolean equals(TagValue tV) {
            return tV != null && equalTag(tV.tag) && equalValue(tV.value);
        }

        @Override
        public String toString() {
            return "{tag='" + tag + "'" + ", value=" + (value instanceof String ? "'" + value + "'" : value) + "}";
        }
    }

    public static class TagValueList implements Iterable<TagValue> {
        protected List<TagValue> tagValues;

        public TagValueList() {
            this(new ArrayList<>());
        }

        public TagValueList(TagValue... tagValues) {
            this(Arrays.asList(tagValues));
        }

        public TagValueList(List<TagValue> tagValues) {
            set(tagValues);
        }

        public void set(Object value) {
            if (value instanceof String || value instanceof Double || value instanceof Integer
                    || value instanceof Long || value instanceof Float || value instanceof Boolean) {
                tagValues = new ArrayList<>();
                tagValues.add(new TagValue(SINGLE_CHILD_TAG, value));
            } else if (value instanceof TagValueList)
                tagValues = ((TagValueList) value).tagValues;
        }

        public void set(TagValue value) {
            if (value != null)
                set(new TagValue[]{value});
        }

        public void set(TagValue... tagValues) {
            set(Arrays.asList(tagValues));
        }

        public void set(List<TagValue> tagValues) {
            this.tagValues = new ArrayList<>();
            TagValue attr = null, single = null;
            if (tagValues != null)
                for (TagValue tagValue : tagValues)
                    if (tagValue.equalTag(ATTRS_NAME))
                        attr = tagValue;
                    else if (tagValue.equalTag(SINGLE_CHILD_TAG))
                        single = tagValue;
            addAll(tagValues);
            if (attr != null)
                this.tagValues.add(attr);
            if (single != null)
                this.tagValues.add(single);
        }

        public boolean add(CharSequence tag, Object... values) {
            if (tag != null && values != null && !ATTRS_NAME.contentEquals(tag) && !SINGLE_CHILD_TAG.contentEquals(tag)) {
                int count = 0;
                for (Object value : values)
                    if (value != null) {
                        boolean added = add(new TagValue(tag, value));
                        count += added ? 1 : 0;
                    }
                return count > 0;
            }
            return false;
        }

        public boolean add(CharSequence tag, Object value) {
            // System.out.println("Add Tag & Value: " + (tag != null && value != null && !ATTRS_NAME.contentEquals(tag) && !SINGLE_CHILD_TAG.contentEquals(tag)));
            if (tag != null && value != null && !ATTRS_NAME.contentEquals(tag) && !SINGLE_CHILD_TAG.contentEquals(tag))
                return add(new TagValue(tag, value));
            return false;
        }

        public boolean add(TagValue value) {
            /*System.out.println("Add TagValue: " + (value != null && !value.equalTag(SINGLE_CHILD_TAG) &&
                    !value.equalTag(ATTRS_NAME) && value.isValid() *//*&& !contains(value)*//*
             *//*&& (value.value instanceof String || value.value instanceof Double ||
                    value.value instanceof Integer || value.value instanceof Long ||
                    value.value instanceof TagValueList || value.value instanceof Float ||
                    value.value instanceof Boolean))*//*));*/
            if (value != null && !value.equalTag(SINGLE_CHILD_TAG) &&
                    !value.equalTag(ATTRS_NAME) && value.isValid() /*&& !contains(value)*/
                    && (value.value instanceof String || value.value instanceof Double ||
                    value.value instanceof Integer || value.value instanceof Long ||
                    value.value instanceof TagValueList || value.value instanceof Float ||
                    value.value instanceof Boolean))
                return this.tagValues.add(value);
            return false;
        }

        public boolean addAll(TagValue... tagValues) {
            return addAll(Arrays.asList(tagValues));
        }

        public boolean addAll(List<TagValue> tagValues) {
            if (tagValues != null) {
                int count = 0;
                for (TagValue value : tagValues) {
                    boolean added = add(value);
                    count += added ? 1 : 0;
                }
                return count > 0;
            }
            return false;
        }

        public Map<String, Object> getAttrs() {
            for (TagValue v : tagValues)
                if (v.equalTag(ATTRS_NAME) && v.value instanceof Map)
                    return (Map<String, Object>) v.value;
            return new LinkedHashMap<>();
        }

        public boolean hasAttribute() {
            return !getAttrs().isEmpty();
        }

        public <A extends Node.Attr> A getAttribute(CharSequence name) {
            Map<String, Object> attrs = getAttrs();
            for (String key : attrs.keySet())
                if (key.contentEquals(name))
                    return (A) new nx.peter.java.pis.Node.Attr(name, attrs.get(key));
            return null;
        }

        public boolean contains(TagValue value) {
            return tagValues.contains(value);
        }

        public boolean containsValue(Object value) {
            if (value != null)
                for (TagValue v : tagValues)
                    if (v.equalValue(value))
                        return true;
            return false;
        }

        public boolean containsTag(CharSequence tag) {
            if (tag != null && !tag.equals(ATTRS_NAME) && !tag.equals(SINGLE_CHILD_TAG))
                for (TagValue v : tagValues)
                    if (v.equalTag(tag))
                        return true;
            return false;
        }

        public int getTagCount(CharSequence tag) {
            int count = 0;
            for (TagValue v : tagValues)
                if (v.equalTag(tag))
                    count++;
            return count;
        }

        public boolean removeTag(CharSequence tag) {
            int count = 0;
            for (TagValue v : tagValues)
                if (v.equalTag(tag)) {
                    remove(v);
                    count++;
                }
            return count > 0;
        }

        public boolean remove(TagValue value) {
            return tagValues.remove(value);
        }

        public boolean remove(TagValueList list) {
            TagValue value = get(list);
            return value != null && remove(value);
        }

        public TagValue get(TagValueList list) {
            for (TagValue v : tagValues)
                if (v.equalValue(list))
                    return v;
            return null;
        }

        public boolean replace(CharSequence tag, TagValue value) {
            if (tag != null && value != null && containsTag(tag)) {
                int count = getTagCount(tag);
                removeTag(tag);
                if (count > 0)
                    for (int i = 0; i < count; i++)
                        add(tag, value.value);
                return count > 0;
            }
            return false;
        }

        public boolean isSingleValue() {
            if (size() == 1)
                for (TagValue v : tagValues)
                    if (v.equalTag(SINGLE_CHILD_TAG))
                        return true;
            return false;
        }

        public boolean hasChildren() {
            return size() > 0 && !isSingleValue();
        }

        public int size() {
            // System.out.println("Size: " + tagValues.size());
            return tagValues.size() - (hasAttribute() ? 1 : 0);
        }

        public TagValue get(int index) {
            return index < size() && index >= 0 ? tagValues.get(index) : null;
        }

        public TagValueList getNodes() {
            List<TagValue> nodes = new ArrayList<>();
            for (TagValue tv : tagValues)
                if (!tv.equalTag(ATTRS_NAME) && !tv.equalTag(SINGLE_CHILD_TAG))
                    nodes.add(tv);
            return new TagValueList(nodes);
        }

        public Object getSingleValue() {
            if (size() == 1)
                for (TagValue v : tagValues)
                    if (v.equalTag(SINGLE_CHILD_TAG))
                        return v.value;
            return null;
        }

        public <V> V getSingleValue(Class<V> clazz) {
            Object value = getSingleValue();
            return value.getClass().equals(clazz) ? (V) value : null;
        }

        public TagValue getByTag(CharSequence tag) {
            if (tag != null && !ATTRS_NAME.contentEquals(tag) && !SINGLE_CHILD_TAG.contentEquals(tag))
                for (TagValue v : tagValues)
                    if (v.equalTag(tag))
                        return v;
            return null;
        }

        public void clear() {
            if (isNotEmpty())
                tagValues.clear();
        }

        public boolean isEmpty() {
            return size() == 0;
        }

        public boolean isNotEmpty() {
            return !isEmpty();
        }

        @Override
        public Iterator<TagValue> iterator() {
            return tagValues.iterator();
        }

        public boolean equals(TagValueList list) {
            return list != null && list.tagValues.equals(tagValues);
        }

        public boolean removeAttr(CharSequence name) {
            for (TagValue v : tagValues)
                if (v.equalTag(ATTRS_NAME) && v.value instanceof Map)
                    return ((Map<String, Object>) v.value).remove(name.toString()) != null;
            return false;
        }

        public boolean removeAllAttributes() {
            for (TagValue v : tagValues)
                if (v.equalTag(ATTRS_NAME) && v.value instanceof Map) {
                    int size = ((Map<String, Object>) v.value).size();
                    ((Map<String, Object>) v.value).clear();
                    return size > 0;
                }
            return false;
        }

        public void setAttr(Map<String, Object> attrs) {
            Map<String, Object> temp = attrs != null ? attrs : new LinkedHashMap<>();
            if (attrs != null)
                for (String name : temp.keySet())
                    addAttr(name, temp.get(name));
        }

        public boolean addAttr(CharSequence name, Object value) {
            if (name != null &&
                    (value instanceof String || value instanceof Double || value instanceof Integer
                            || value instanceof Long || value instanceof Float || value instanceof Boolean)) {
                if (hasAttribute()) {
                    for (TagValue v : tagValues)
                        if (v.value instanceof Map)
                            if (v.equalTag(ATTRS_NAME) && !((Map<String, Object>) v.value).containsKey(name.toString()))
                                ((Map<String, Object>) v.value).put(name.toString(), value);
                } else {
                    Map<String, Object> attrs = new LinkedHashMap<>();
                    attrs.put(name.toString(), value);
                    tagValues.add(new TagValue(ATTRS_NAME, attrs));
                }
                return true;
            }
            return false;
        }

        public void setAttrs(Node.Attrs<? extends Node.Attr> attrs) {
            if (attrs != null)
                for (Node.Attr attr : attrs)
                    if (attr instanceof nx.peter.java.pis.Node.Attr)
                        addAttr(((nx.peter.java.pis.Node.Attr) attr).getName(), ((nx.peter.java.pis.Node.Attr) attr).value);
        }

        @Override
        public String toString() {
            return tagValues.toString();
        }
    }

    public static <N extends Node, A extends Node.Attr> Pis<N, A> toPis(CharSequence source, boolean isPath) {
        return DataManager.extractPis(isPath ? FileManager.readString(source) : source != null ? source : "");
    }


    protected static class Context implements PisContext {
        protected nx.peter.java.pis.Node node;

        public Context(Node node) {
            set(node);
        }

        @Override
        public PrettyPrinter getPrettyPrinter(Node source) {
            return new Context(source).getPrettyPrinter();
        }

        @Override
        public PisWriter getWriter(CharSequence path) {
            return new PisWriter(path);
        }

        @Override
        public PisReader getReader(CharSequence path) {
            return new PisReader(path);
        }

        @Override
        public PisWriter getWriter() {
            return getWriter("").setRoot(node);
        }

        @Override
        public PisReader getReader() {
            return new PisReader(node.getPrettyPrinter().print(), false);
        }

        @Override
        public nx.peter.java.pis.writer.Node getWritable() {
            return node;
        }

        @Override
        public nx.peter.java.pis.reader.Node getReadable() {
            return node;
        }

        @Override
        public PrettyPrinter getPrettyPrinter() {
            return node.getPrettyPrinter();
        }

        @Override
        public Source getSource() {
            return Random.nextInt(1, 2) == 1 ? getReader().getSource() : getWriter().getSource();
        }

        @Override
        public PisContext getContext() {
            return this;
        }

        @Override
        public PisContext set(Node source) {
            this.node = source != null ? (nx.peter.java.pis.Node) source : new nx.peter.java.pis.Node();
            return this;
        }

        @Override
        public <M extends Model> Service<PisContext, PisWriter, PisReader, Source, PrettyPrinter, nx.peter.java.pis.writer.Node, nx.peter.java.pis.reader.Node, Node, M> getService(M model) {
            return null;
        }

        @Override
        public Node getNode() {
            return node;
        }

        @Override
        public String toString() {
            return node.toString();
        }
    }

}

package nx.peter.java.json.core;

import nx.peter.java.context.DataFile;

public interface JsonElement<J extends JsonElement> extends Iterable<Object>, DataFile<J> {
    int size();
    boolean isEmpty();
    boolean isNotEmpty();
    boolean isArray();
    boolean isObject();
    boolean containsValue(Object value);
    ElementType getElementType();
    String getElementName();

    enum ElementType {
        ARRAY("JsonArray"),
        OBJECT("JsonObject");

        public final String name;
        ElementType(String name) { this.name = name; }
    }

    interface Keys extends Iterable<String> {
        String get(int index);
        boolean isEmpty();
        boolean isNotEmpty();
        boolean contains(Object key);
        int size();
    }

}

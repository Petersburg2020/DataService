package nx.peter.java.json;

import nx.peter.java.json.core.Root;
import nx.peter.java.util.data.DataChecker;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class JsonElement<J extends JsonElement> extends DataChecker implements
        nx.peter.java.json.writer.JsonElement<J>, nx.peter.java.json.reader.JsonElement<J> {
    protected Json<JsonArray, JsonObject, JsonElement> root;

    public JsonElement(List<Object> array) {
        root = new Json<>(array);
    }

    public JsonElement(Map<String, Object> object) {
        root = new Json<>(object);
    }

    @Override
    public int size() {
        return root.size();
    }

    @Override
    public boolean isArray() {
        return root.isArray();
    }

    @Override
    public boolean isObject() {
        return root.isObject();
    }

    @Override
    public boolean isEmpty() {
        return root.isEmpty();
    }

    @Override
    public boolean isNotEmpty() {
        return !root.isEmpty();
    }

    @Override
    public boolean containsValue(Object value) {
        return root.containsValue(value);
    }

    @Override
    public String getElementName() {
        return getElementType().name;
    }

    @Override
    public Iterator<Object> iterator() {
        return root.iterator();
    }

    public Root<JsonArray, JsonObject, JsonElement> getRoot() {
        return root;
    }

    protected J setIndent(int indent) {
        root.indent = indent;
        return (J) this;
    }

    @Override
    public J getDataFile() {
        return (J) this;
    }

    @Override
    public String toString() {
        return root.toString();
    }

}

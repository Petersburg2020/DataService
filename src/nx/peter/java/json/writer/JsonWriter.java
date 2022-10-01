package nx.peter.java.json.writer;

import nx.peter.java.context.Writer;
import nx.peter.java.storage.FileManager;
import nx.peter.java.json.Json;
import nx.peter.java.json.core.JsonCore;
import nx.peter.java.json.core.Root;
import nx.peter.java.json.core.Source;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class JsonWriter extends
        JsonCore<JsonWriter, JsonArray, JsonObject, JsonElement> implements
        Writer<JsonWriter, Source<JsonArray, JsonObject, JsonElement>> {
    public JsonWriter() {
        super();
    }

    public JsonWriter(CharSequence path) {
        super(path, false);
    }

    public JsonWriter(CharSequence jsonSource, boolean isPath) {
        super(jsonSource, isPath, false);
    }

    public boolean store() {
        return store(path);
    }

    public boolean store(CharSequence path) {
        return store(path, true);
    }

    public boolean store(CharSequence path, boolean prettyPrint) {
        System.out.println("Path: " + path);
        if (path != null)
            return FileManager.writeFile(path, prettyPrint ? getRoot().getPrettyPrinter().print() : getRoot().toString(), false);
        return false;
    }

    public JsonWriter createObject() {
        root.set(new LinkedHashMap<>());
        return this;
    }

    public JsonWriter createArray() {
        root.set(new ArrayList<>());
        return this;
    }

    public JsonWriter setRoot(nx.peter.java.json.core.JsonElement root) {
        if (root != null) {
            if (root.isObject())
                this.root.set(((Json)((nx.peter.java.json.JsonElement) root).getRoot()).object);
            else if (root.isArray())
                this.root.set(((Json)((nx.peter.java.json.JsonElement) root).getRoot()).array);
        }
        return this;
    }

    public JsonWriter setRoot(Root root) {
        return setRoot(root != null ? root.getElement() : null);
    }

}

package nx.peter.java.json;

import nx.peter.java.context.model.Model;
import nx.peter.java.service.Service;
import nx.peter.java.storage.FileManager;
import nx.peter.java.json.core.*;
import nx.peter.java.json.core.JsonElement;
import nx.peter.java.json.reader.JsonReader;
import nx.peter.java.json.writer.JsonWriter;
import nx.peter.java.util.Random;
import nx.peter.java.util.Util;
import nx.peter.java.util.advanced.Advanced;
import nx.peter.java.util.data.DataManager;

import java.util.*;

public class Json<JA extends JsonElement, JO extends JsonElement, JE extends JsonElement> implements Source<JA, JO, JE>, Iterable<Object> {
    public Map<String, Object> object;
    public List<Object> array;
    protected int indent;

    public Json() {
        reset();
    }

    public Json(Map<String, Object> object) {
        set(object);
    }

    public Json(List<Object> array) {
        set(array);
    }

    public void reset() {
        object = null;
        array = null;
        indent = 0;
    }

    public void set(Map<String, Object> object) {
        array = null;
        this.object = object;
    }

    public void set(List<Object> array) {
        object = null;
        this.array = array;
    }

    public boolean isEmpty() {
        return isArray() ? array.isEmpty() : !isObject() || object.isEmpty();
    }

    @Override
    public boolean isNotEmpty() {
        return !isEmpty();
    }

    @Override
    public boolean isUndefined() {
        return !isArray() && !isObject();
    }

    @Override
    public boolean isDefined() {
        return !isUndefined();
    }

    @Override
    public JsonContext getContext() {
        return openContext(getElement());
    }

    @Override
    public PrettyPrinter<JA, JO, JE> getPrettyPrinter() {
        return new PrettyPrinter<>() {
            @Override
            public String print() {
                return prettyPrint();
            }

            @Override
            public String print(JsonElement element) {
                return element.isArray() ? ((JsonArray) element).getPrettyPrinter().print()
                        : element.isObject() ? ((JsonObject) element).getPrettyPrinter().print() : "";
            }

            @Override
            public Source<JA, JO, JE> getSource() {
                return Json.this;
            }

            @Override
            public JE getElement() {
                return isArray() ? (JE) getArray() : isObject() ? (JE) getObject() : null;
            }

            @Override
            public String toString() {
                return print();
            }
        };
    }

    public boolean isArray() {
        return array != null;
    }

    public boolean isObject() {
        return object != null;
    }

    public boolean isNull() {
        return !isArray() && !isObject();
    }

    @Override
    public Iterator<Object> iterator() {
        return isArray() ? array.iterator() : isObject() ? object.values().iterator() : new ArrayList<>().iterator();
    }

    @Override
    public JA getArray() {
        return isArray() ? (JA) new JsonArray(array) : null;
    }

    @Override
    public JO getObject() {
        return isObject() ? (JO) new JsonObject(object) : null;
    }

    @Override
    public JE getElement() {
        return isArray() ? (JE) getArray() : isObject() ? (JE) getObject() : null;
    }

    @Override
    public int size() {
        return isArray() ? array.size() : isObject() ? object.size() : 0;
    }

    @Override
    public byte[] encode() {
        return prettyPrint().getBytes();
    }

    @Override
    public Source<JA, JO, JE> decode(byte[] code) {
        return fromString(new String(code));
    }

    @Override
    public boolean containsValue(Object value) {
        return isArray() ? array.contains(value) : isObject() && object.containsValue(value);
    }



    public String prettyPrint() {
        return isArray() ? Util.toPrettyJson(array, indent) : isObject() ? Util.toPrettyJson(object, indent) : "";
    }

    @Override
    public String toString() {
        return isArray() ? Util.toJson(array, indent) : isObject() ? Util.toJson(object, indent) : "";
    }




    public static <JA extends JsonElement, JO extends JsonElement, JE extends JsonElement> Json<JA, JO, JE> fromPath(CharSequence path) {
        return fromString(FileManager.readString(path));
    }

    public static  <JA extends JsonElement, JO extends JsonElement, JE extends JsonElement> Json<JA, JO, JE>  fromString(CharSequence source) {
        return DataManager.extractJson(source);
    }

    /**
     * Creates an instance of JsonContext
     * @return instance of JsonContext
     */
    public static JsonContext openContext(JsonElement json) {
        return new Context(json);
    }



    protected static class Context implements JsonContext {
        protected nx.peter.java.json.JsonElement json;

        public Context(JsonElement json) {
            set(json);
        }

        @Override
        public JsonReader openReader() {
            return new JsonReader();
        }

        @Override
        public JsonWriter openWriter() {
            return new JsonWriter();
        }

        @Override
        public JsonReader openReader(CharSequence source, boolean isPath) {
            return new JsonReader(source, isPath);
        }

        @Override
        public JsonWriter openWriter(CharSequence source, boolean isPath) {
            return new JsonWriter(source, true, isPath);
        }

        @Override
        public nx.peter.java.json.writer.JsonObject<?> createJsonObject() {
            return new JsonObject();
        }

        @Override
        public nx.peter.java.json.writer.JsonArray<?> createJsonArray() {
            return new JsonArray();
        }

        @Override
        public PrettyPrinter<nx.peter.java.json.reader.JsonArray, nx.peter.java.json.reader.JsonObject, nx.peter.java.json.reader.JsonElement> getPrettyPrinter(CharSequence source, boolean isPath) {
            return openReader(source, isPath).getRoot().getPrettyPrinter();
        }

        @Override
        public Elements<nx.peter.java.json.reader.JsonElement> getAllElements(CharSequence source, boolean isPath) {
            return openReader(source, isPath).getRoot().getElement().getAllElements();
        }

        @Override
        public Elements<nx.peter.java.json.reader.JsonObject> getAllObjects(CharSequence source, boolean isPath) {
            return openReader(source, isPath).getRoot().getElement().getAllObjects();
        }

        @Override
        public Elements<nx.peter.java.json.reader.JsonArray> getAllArrays(CharSequence source, boolean isPath) {
            return openReader(source, isPath).getRoot().getElement().getAllArrays();
        }

        @Override
        public <T> T fromJson(CharSequence source, boolean isPath, Class<T> clazz) {
            JsonReader reader = openReader(source, isPath);
            if (reader.getRoot().isObject())
                return fromJson(reader.getRoot().getObject(), clazz);
            return null;
        }

        @Override
        public <T> T fromJson(nx.peter.java.json.reader.JsonObject<?> json, Class<T> clazz) {
            return Advanced.fromJson(json, clazz);
        }

        @Override
        public <T> nx.peter.java.json.reader.JsonObject<?> toJson(T model) {
            return Advanced.toJson(model);
        }

        @Override
        public Source<nx.peter.java.json.reader.JsonArray, nx.peter.java.json.reader.JsonObject, nx.peter.java.json.reader.JsonElement> getSource(CharSequence source, boolean isPath) {
            return openReader(source, isPath).getRoot().getPrettyPrinter().getSource();
        }

        @Override
        public Source<nx.peter.java.json.writer.JsonArray, nx.peter.java.json.writer.JsonObject, nx.peter.java.json.writer.JsonElement> openSource(CharSequence source, boolean isPath) {
            return openWriter(source, isPath).getRoot().getPrettyPrinter().getSource();
        }

        @Override
        public PrettyPrinter getPrettyPrinter(JsonElement source) {
            return new Context(source).getPrettyPrinter();
        }

        @Override
        public JsonWriter getWriter(CharSequence path) {
            return getWriter().setPath(path);
        }

        @Override
        public JsonReader getReader(CharSequence path) {
            return openReader(path, true);
        }

        @Override
        public JsonWriter getWriter() {
            return new JsonWriter().setRoot(json);
        }

        @Override
        public JsonReader getReader() {
            return new JsonReader();
        }

        @Override
        public nx.peter.java.json.writer.JsonElement getWritable() {
            return json;
        }

        @Override
        public nx.peter.java.json.reader.JsonElement getReadable() {
            return json;
        }

        @Override
        public PrettyPrinter getPrettyPrinter() {
            return json.getPrettyPrinter();
        }

        @Override
        public Source getSource() {
            return Random.nextInt(1, 2) == 1 ? getReader().getSource() : getWriter().getSource();
        }

        @Override
        public JsonContext getContext() {
            return this;
        }

        @Override
        public JsonContext set(JsonElement json) {
            this.json = json != null ? (nx.peter.java.json.JsonElement) json : new JsonObject();
            return this;
        }

        @Override
        public <M extends Model> Service<JsonContext, JsonWriter, JsonReader, Source, PrettyPrinter, nx.peter.java.json.writer.JsonElement, nx.peter.java.json.reader.JsonElement, JsonElement, M> getService(M model) {
            return null;
        }

        @Override
        public String toString() {
            return json.toString();
        }
    }

}

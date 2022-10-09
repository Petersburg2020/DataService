package nx.peter.java.json;

import nx.peter.java.json.core.Elements;
import nx.peter.java.json.core.PrettyPrinter;
import nx.peter.java.util.data.Word;

import java.util.*;
import java.util.stream.Collectors;

public class JsonObject extends JsonElement<JsonObject>
        implements nx.peter.java.json.reader.JsonObject<JsonObject>,
        nx.peter.java.json.writer.JsonObject<JsonObject> {

    public JsonObject() {
        this(new LinkedHashMap<>());
    }

    public JsonObject(Map<String, Object> object) {
        super(object != null ? object : new LinkedHashMap<>());
    }

    @Override
    public ElementType getElementType() {
        return ElementType.OBJECT;
    }

    @Override
    public Object get(CharSequence key) {
        return root.object.get(key.toString());
    }

    @Override
    public double getDouble(CharSequence key, double defaultValue) {
        Object value = get(key);
        return isDouble(value) ? (double) value : defaultValue;
    }

    @Override
    public int getInt(CharSequence key, int defaultValue) {
        Object value = get(key);
        return isInt(value) ? (int) value : defaultValue;
    }

    @Override
    public float getFloat(CharSequence key, float defaultValue) {
        Object value = get(key);
        return isFloat(value) ? (float) value : defaultValue;
    }

    @Override
    public long getLong(CharSequence key, long defaultValue) {
        Object value = get(key);
        return isLong(value) ? (long) value : defaultValue;
    }

    @Override
    public boolean getBoolean(CharSequence key, boolean defaultValue) {
        Object value = get(key);
        return isBoolean(value) ? (boolean) value : defaultValue;
    }

    @Override
    public nx.peter.java.json.reader.JsonElement getElement(CharSequence key) {
        Object value = get(key);
        return isArray(value) ? new JsonArray((List<Object>) value) : isObject(value) ? new JsonObject((Map<String, Object>) value) : null;
    }

    @Override
    public nx.peter.java.json.reader.JsonObject getObject(CharSequence key) {
        Object value = get(key);
        return isObject(value) ? new JsonObject((Map<String, Object>) value) : null;
    }

    @Override
    public JsonArray getArray(CharSequence key) {
        Object value = get(key);
        return isArray(value) ? new JsonArray((List<Object>) value) : null;
    }

    @Override
    public String getString(CharSequence key) {
        Object value = get(key);
        return isString(value) ? new Word((String) value).replaceAll("\n", System.lineSeparator()).replaceAll("@10", System.lineSeparator()).replaceAll("@09", '\t').get() : null;
    }

    @Override
    public Keys getKeys() {
        return new IKeys(root.object.keySet());
    }

    @Override
    public boolean containsKey(CharSequence key) {
        return root.object.containsKey(key.toString());
    }

    @Override
    public JsonObject clear() {
        return null;
    }

    @Override
    public boolean removeValue(Object value) {
        return root.object.values().remove(value);
    }

    @Override
    public boolean removeValue(Object... value) {
        return removeValue(Arrays.asList(value));
    }

    @Override
    public boolean removeValue(nx.peter.java.json.core.JsonElement value) {
        return value != null && (value.isArray() ? removeValue(((JsonElement<?>) value).root.array) : value.isObject() && removeValue(((JsonElement<?>) value).root.object));
    }

    @Override
    public boolean removeValue(List<Object> value) {
        return root.object.values().remove(value);
    }

    @Override
    public boolean removeValue(Map<Object, Object> value) {
        return root.object.values().remove(value);
    }

    @Override
    public JsonObject add(CharSequence key, Object value) {
        if (value != null) {
            if (isString(value))
                value = new Word((String) value).replaceAll("\n", "@10").replaceAll("\t", "@09").get();
            root.object.put(key.toString(), value);
        }
        return this;
    }

    @Override
    public JsonObject add(CharSequence key, Object... value) {
        return add(key, Arrays.asList(value));
    }

    @Override
    public JsonObject add(CharSequence key, nx.peter.java.json.core.JsonElement value) {
        return value != null ? value.isArray() ? add(key, ((JsonElement<?>) value).root.array) : add(key, ((JsonElement<?>) value).root.object) : this;
    }

    @Override
    public JsonObject add(CharSequence key, List<Object> value) {
        value = value.stream().map(value1 -> isString(value1) ? new Word((String) value1).replaceAll("\n", "@10").replaceAll("\t", "@09").get() : value1).collect(Collectors.toList());
        root.object.put(key.toString(), value);
        return this;
    }

    @Override
    public JsonObject add(CharSequence key, Map<String, Object> value) {
        Map<String, Object> temp = new LinkedHashMap<>();
        value.forEach((s, value1) -> {
            if (isString(value1))
                value1 = new Word((String) value1).replaceAll("\n", "@10").replaceAll("\t", "@09").get();
            temp.put(s, value1);
        });
        root.object.put(key.toString(), temp);
        return this;
    }

    @Override
    public boolean removeKey(CharSequence key) {
        return root.object.keySet().remove(key.toString());
    }

    @Override
    public Elements<nx.peter.java.json.reader.JsonArray> getAllArrays() {
        List<nx.peter.java.json.reader.JsonArray> elements = new ArrayList<>();
        for (Object obj : getAll())
            if (isArray(obj))
                elements.add(new JsonArray((List<Object>) obj));
        return new Elements<>(elements);
    }

    @Override
    public Elements<nx.peter.java.json.reader.JsonObject> getAllObjects() {
        List<nx.peter.java.json.reader.JsonObject> elements = new ArrayList<>();
        for (Object obj : getAll())
            if (isObject(obj))
                elements.add(new JsonObject((Map<String, Object>) obj));
        return new Elements<>(elements);
    }

    @Override
    public Elements<nx.peter.java.json.core.JsonElement> getAllElements() {
        List<nx.peter.java.json.core.JsonElement> elements = new ArrayList<>();
        for (Object obj : getAll())
            if (isArray(obj))
                elements.add(new JsonArray((List<Object>) obj));
            else if (isObject(obj))
                elements.add(new JsonObject((Map<String, Object>) obj));
        return new Elements<>(elements);
    }

    @Override
    public Elements<Boolean> getAllBooleans() {
        List<Boolean> elements = new ArrayList<>();
        for (Object obj : getAll())
            if (isBoolean(obj))
                elements.add((boolean) obj);
        return new Elements<>(elements);
    }

    @Override
    public Elements<Double> getAllDoubles() {
        List<Double> elements = new ArrayList<>();
        for (Object obj : getAll())
            if (isDouble(obj))
                elements.add((double) obj);
        return new Elements<>(elements);
    }

    @Override
    public Elements<Float> getAllFloats() {
        List<Float> elements = new ArrayList<>();
        for (Object obj : getAll())
            if (isFloat(obj))
                elements.add((float) obj);
        return new Elements<>(elements);
    }

    @Override
    public Elements<Long> getAllLongs() {
        List<Long> elements = new ArrayList<>();
        for (Object obj : getAll())
            if (isLong(obj))
                elements.add((long) obj);
        return new Elements<>(elements);
    }

    @Override
    public Elements<Integer> getAllIntegers() {
        List<Integer> elements = new ArrayList<>();
        for (Object obj : getAll())
            if (isInt(obj))
                elements.add((int) obj);
        return new Elements<>(elements);
    }

    @Override
    public Elements<Object> getAll() {
        return new Elements<>(root.object.values());
    }

    @Override
    public PrettyPrinter<JsonArray, JsonObject, JsonElement> getPrettyPrinter() {
        return root.getPrettyPrinter();
    }

    // Implementing Keys
    private static class IKeys implements Keys {
        List<String> keys;

        public IKeys(Collection<String> keys) {
            this.keys = new ArrayList<>(keys);
        }

        @Override
        public String get(int index) {
            return keys.get(index);
        }

        @Override
        public boolean isEmpty() {
            return keys.isEmpty();
        }

        @Override
        public boolean isNotEmpty() {
            return !isEmpty();
        }

        @Override
        public boolean contains(Object key) {
            return keys.contains(key.toString());
        }

        @Override
        public int size() {
            return keys.size();
        }

        @Override
        public Iterator<String> iterator() {
            return keys.iterator();
        }
    }
}

package nx.peter.java.json;

import nx.peter.java.json.core.Elements;
import nx.peter.java.json.core.PrettyPrinter;
import nx.peter.java.json.core.Source;
import nx.peter.java.util.data.Word;

import java.util.*;
import java.util.stream.Collectors;

public class JsonArray extends JsonElement<JsonArray>
        implements nx.peter.java.json.reader.JsonArray<JsonArray>,
        nx.peter.java.json.writer.JsonArray<JsonArray> {

    public JsonArray() {
        this(new ArrayList<>());
    }

    public JsonArray(List<Object> array) {
        super(array != null ? array : new ArrayList<>());
    }

    @Override
    public JsonArray clear() {
        root.array.clear();
        return this;
    }

    @Override
    public boolean removeValue(Object value) {
        return root.array.remove(value);
    }

    @Override
    public boolean removeValue(Object... value) {
        return removeValue(Arrays.asList(value));
    }

    @Override
    public boolean removeValue(nx.peter.java.json.core.JsonElement value) {
        return value.isArray() ? removeValue(((JsonElement<?>) value).root.array) : value.isObject() && removeValue(((JsonElement<?>) value).root.object);
    }

    @Override
    public boolean removeValue(List<Object> value) {
        return false;
    }

    @Override
    public boolean removeValue(Map<Object, Object> value) {
        return false;
    }

    @Override
    public ElementType getElementType() {
        return ElementType.ARRAY;
    }

    @Override
    public Object get(int index) {
        return index >= 0 && index < size() ? root.array.get(index) : null;
    }

    @Override
    public String getString(int index) {
        Object value = get(index);
        return isString(value) ? new Word((String) value).replaceAll("\n", System.lineSeparator()).replaceAll("@10", System.lineSeparator()).replaceAll("@09", '\t').get() : null;
    }

    @Override
    public JsonElement<?> getElement(int index) {
        Object value = get(index);
        return isArray(value) ? new JsonArray((List<Object>) value) : isObject(value) ? new JsonObject((Map<String, Object>) value) : null;
    }

    @Override
    public JsonArray getArray(int index) {
        Object value = get(index);
        return isArray(value) ? new JsonArray((List<Object>) value) : null;
    }

    @Override
    public JsonObject getObject(int index) {
        Object value = get(index);
        return isObject(value) ? new JsonObject((Map<String, Object>) value) : null;
    }

    @Override
    public boolean getBoolean(int index, boolean defaultValue) {
        Object value = get(index);
        return isBoolean(value) ? (boolean) value : defaultValue;
    }

    @Override
    public double getDouble(int index, double defaultValue) {
        Object value = get(index);
        return isDouble(value) ? (double) value : defaultValue;
    }

    @Override
    public float getFloat(int index, float defaultValue) {
        Object value = get(index);
        return isFloat(value) ? (float) value : defaultValue;
    }

    @Override
    public long getLong(int index, long defaultValue) {
        Object value = get(index);
        return isLong(value) ? (long) value : defaultValue;
    }

    @Override
    public int getInt(int index, int defaultValue) {
        Object value = get(index);
        return isInt(value) ? (int) value : defaultValue;
    }

    @Override
    public boolean hasElement(int index) {
        return hasArray(index) || hasObject(index);
    }

    @Override
    public boolean hasArray(int index) {
        return isArray(get(index));
    }

    @Override
    public boolean hasObject(int index) {
        return isObject(get(index));
    }

    @Override
    public boolean hasString(int index) {
        return isString(get(index));
    }

    @Override
    public boolean hasBoolean(int index) {
        return isBoolean(get(index));
    }

    @Override
    public boolean hasDouble(int index) {
        return isDouble(get(index));
    }

    @Override
    public boolean hasFloat(int index) {
        return isFloat(get(index));
    }

    @Override
    public boolean hasLong(int index) {
        return isLong(get(index));
    }

    @Override
    public boolean hasInt(int index) {
        return isInt(get(index));
    }

    @Override
    public boolean hasIndex(int index) {
        return isNotEmpty() && index >= 0 && index < size();
    }

    @Override
    public JsonArray add(Object value) {
        if (value != null) {
            if (isString(value))
                value = new Word((String) value).replaceAll("\n", "@10").replaceAll("\t", "@09").get();
            root.array.add(value);
        }
        return this;
    }

    @Override
    public JsonArray add(Object... value) {
        return value != null ? add(Arrays.asList(value)) : this;
    }

    @Override
    public JsonArray add(List<Object> value) {
        if (value != null) {
            value = value.stream().map(value1 -> isString(value1) ? new Word((String) value1).replaceAll("\n", "@10").replaceAll("\t", "@09").get() : value1).collect(Collectors.toList());
            root.array.add(value);
        }
        return this;
    }

    @Override
    public JsonArray add(Map<String, Object> value) {
        if (value != null) {
            Map<String, Object> temp = new LinkedHashMap<>();
            value.forEach((s, value1) -> {
                if (isString(value1))
                    value1 = new Word((String) value1).replaceAll("\n", "@10").replaceAll("\t", "@09").get();
                temp.put(s, value1);
            });
            root.array.add(temp);
        }
        return this;
    }

    @Override
    public JsonArray add(nx.peter.java.json.core.JsonElement value) {
        return value instanceof JsonArray ? add(((JsonArray) value).root.array) :
                value instanceof JsonObject ? add(((JsonElement<?>) value).root.object) : this;
    }

    @Override
    public boolean removeIndex(int index) {
        return hasIndex(index) && removeValue(get(index));
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
        return new Elements<>(root.array);
    }

    @Override
    public PrettyPrinter<JsonArray, JsonObject, JsonElement> getPrettyPrinter() {
        return root.getPrettyPrinter();
    }

}

package nx.peter.java.json.reader;

public interface JsonArray<J extends JsonArray> extends JsonElement<J> {
    Object get(int index);
    String getString(int index);
    nx.peter.java.json.core.JsonElement<?> getElement(int index);
    JsonArray<?> getArray(int index);
    JsonObject<?> getObject(int index);
    boolean getBoolean(int index, boolean defaultValue);
    double getDouble(int index, double defaultValue);
    float getFloat(int index, float defaultValue);
    long getLong(int index, long defaultValue);
    int getInt(int index, int defaultValue);
    boolean hasElement(int index);
    boolean hasArray(int index);
    boolean hasObject(int index);
    boolean hasString(int index);
    boolean hasBoolean(int index);
    boolean hasDouble(int index);
    boolean hasFloat(int index);
    boolean hasLong(int index);
    boolean hasInt(int index);
    boolean hasIndex(int index);
}

package nx.peter.java.json.reader;

public interface JsonObject<J extends JsonObject> extends JsonElement<J> {
    Object get(CharSequence key);
    double getDouble(CharSequence key, double defaultValue);
    int getInt(CharSequence key, int defaultValue);
    float getFloat(CharSequence key, float defaultValue);
    long getLong(CharSequence key, long defaultValue);
    boolean getBoolean(CharSequence key, boolean defaultValue);
    JsonElement getElement(CharSequence key);
    JsonObject getObject(CharSequence key);
    JsonArray getArray(CharSequence key);
    String getString(CharSequence key);
    Keys getKeys();
    boolean containsKey(CharSequence key);
}

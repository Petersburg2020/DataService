package nx.peter.java.json.writer;

import java.util.List;
import java.util.Map;

public interface JsonObject<J extends JsonObject> extends JsonElement<J> {
    J add(CharSequence key, Object value);
    J add(CharSequence key, Object... value);
    J add(CharSequence key, nx.peter.java.json.core.JsonElement value);
    J add(CharSequence key, List<Object> value);
    J add(CharSequence key, Map<String, Object> value);
    boolean removeKey(CharSequence key);
    boolean containsKey(CharSequence key);
}

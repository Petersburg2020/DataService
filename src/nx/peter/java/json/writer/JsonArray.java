package nx.peter.java.json.writer;

import java.util.List;
import java.util.Map;

public interface JsonArray<J extends JsonArray> extends JsonElement<J> {
    J add(Object value);
    J add(Object... value);
    J add(List<Object> value);
    J add(Map<String, Object> value);
    J add(nx.peter.java.json.core.JsonElement<?> value);
    boolean removeIndex(int index);
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

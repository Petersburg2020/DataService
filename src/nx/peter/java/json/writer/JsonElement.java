package nx.peter.java.json.writer;

import java.util.List;
import java.util.Map;

public interface JsonElement<J extends JsonElement> extends nx.peter.java.json.core.JsonElement<J> {
    J clear();
    boolean removeValue(Object value);
    boolean removeValue(Object... value);
    boolean removeValue(nx.peter.java.json.core.JsonElement value);
    boolean removeValue(List<Object> value);
    boolean removeValue(Map<Object, Object> value);
}

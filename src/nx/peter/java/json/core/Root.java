package nx.peter.java.json.core;

import nx.peter.java.context.Source;
import nx.peter.java.json.JsonContext;

public interface Root<JA extends JsonElement, JO extends JsonElement, JE extends JsonElement>
        extends Source<PrettyPrinter<JA, JO, JE>, JsonContext> {
    JA getArray();
    JO getObject();
    JE getElement();
    int size();
    boolean containsValue(Object value);
    boolean isArray();
    boolean isObject();
    boolean isEmpty();
    boolean isNotEmpty();
    boolean isUndefined();
    boolean isDefined();
}

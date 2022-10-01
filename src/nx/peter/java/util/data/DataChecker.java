package nx.peter.java.util.data;

import java.util.List;
import java.util.Map;

public abstract class DataChecker {

    protected boolean isInt(Object value) {
        return value instanceof Integer;
    }

    protected boolean isLong(Object value) {
        return value instanceof Long;
    }

    protected boolean isBoolean(Object value) {
        return value instanceof Boolean;
    }

    protected boolean isFloat(Object value) {
        return value instanceof Float;
    }

    protected boolean isDouble(Object value) {
        return value instanceof Double;
    }

    protected boolean isString(Object value) {
        return value instanceof String;
    }

    protected boolean isArray(Object value) {
        return value instanceof List;
    }

    protected boolean isObject(Object value) {
        return value instanceof Map;
    }

}

package nx.peter.java.json.reader;

import nx.peter.java.context.Reader;
import nx.peter.java.json.core.JsonCore;
import nx.peter.java.json.core.Source;

public class JsonReader extends JsonCore<JsonReader, JsonArray, JsonObject, JsonElement>
        implements Reader<JsonReader, Source<JsonArray, JsonObject, JsonElement>> {
    public JsonReader() {
        super();
    }

    public JsonReader(CharSequence path) {
        super(path, true);
    }

    public JsonReader(CharSequence jsonSource, boolean isPath) {
        super(jsonSource, isPath, true);
    }

}

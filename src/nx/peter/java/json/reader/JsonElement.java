package nx.peter.java.json.reader;

import nx.peter.java.json.core.Elements;
import nx.peter.java.json.core.PrettyPrinter;

public interface JsonElement<J extends JsonElement> extends nx.peter.java.json.core.JsonElement<J> {
    Elements<JsonArray> getAllArrays();
    Elements<JsonObject> getAllObjects();
    Elements<nx.peter.java.json.core.JsonElement> getAllElements();
    Elements<Boolean> getAllBooleans();
    Elements<Double> getAllDoubles();
    Elements<Float> getAllFloats();
    Elements<Long> getAllLongs();
    Elements<Integer> getAllIntegers();
    Elements<Object> getAll();
    PrettyPrinter getPrettyPrinter();
}

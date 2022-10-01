package nx.peter.java.json;

import nx.peter.java.context.Context;
import nx.peter.java.json.core.Elements;
import nx.peter.java.json.core.PrettyPrinter;
import nx.peter.java.json.core.Source;
import nx.peter.java.json.reader.JsonElement;
import nx.peter.java.json.reader.JsonReader;
import nx.peter.java.json.writer.JsonArray;
import nx.peter.java.json.writer.JsonObject;
import nx.peter.java.json.writer.JsonWriter;

/**
 * <pre><h1>A project created by:<br> Uareime Peter Ezekiel</pre></h1>
 * <h2>*********** JsonContext **************</h2>
 * <p>This is the base shortcut for json manager functions.
 * <br>Here, you can access some details from a json element.<br></p>
 */
public interface JsonContext extends
        Context<JsonContext, JsonWriter, JsonReader, Source,
                nx.peter.java.json.core.JsonElement,
                nx.peter.java.json.writer.JsonElement,
                JsonElement, PrettyPrinter> {

    /**
     * Opens an instance of json reader without
     * a json formatting
     * @return an empty instance of Json Reader
     */
    JsonReader openReader();

    /**
     * Opens an instance of json writer without
     * a json formatting
     * @return an empty instance of Json Writer
     */
    JsonWriter openWriter();

    /**
     * Opens an instance of json reader from
     * a string format or path of json
     * @param source the string format or path of json
     * @param isPath indicates whether it's a path if true or a string if false
     * @return instance of json reader
     */
    JsonReader openReader(CharSequence source, boolean isPath);

    /**
     * Opens an instance of json writer from
     * a string format or path of json
     * @param source the string format or path of json
     * @param isPath indicates whether it's a path if true or a string if false
     * @return instance of json writer
     */
    JsonWriter openWriter(CharSequence source, boolean isPath);

    /**
     * Creates an instance of writable Json Object
     * @return an instance of JsonObject
     */
    JsonObject<?> createJsonObject();

    /**
     * Creates an instance of writable Json Array
     * @return an instance of JsonArray
     */
    JsonArray<?> createJsonArray();

    /**
     * Gets a pretty printer for the json.
     * @param source the string format or path of json
     * @param isPath indicates whether it's a path if true or a string if false
     * @return the pretty printer for the json
     */
    PrettyPrinter<nx.peter.java.json.reader.JsonArray, nx.peter.java.json.reader.JsonObject, JsonElement> getPrettyPrinter(CharSequence source, boolean isPath);

    /**
     * Gets all json elements in this json
     * @param source the string format or path of json
     * @param isPath indicates whether it's a path if true or a string if false
     * @return all the json elements in this json
     */
    Elements<JsonElement> getAllElements(CharSequence source, boolean isPath);

    /**
     * Gets all json objects in this json
     * @param source the string format or path of json
     * @param isPath indicates whether it's a path if true or a string if false
     * @return all the json objects in this json
     */
    Elements<nx.peter.java.json.reader.JsonObject> getAllObjects(CharSequence source, boolean isPath);

    /**
     * Gets all json arrays in this json
     * @param source the string format or path of json
     * @param isPath indicates whether it's a path if true or a string if false
     * @return all the json arrays in this json
     */
    Elements<nx.peter.java.json.reader.JsonArray> getAllArrays(CharSequence source, boolean isPath);

    /**
     * Converts json to object model of the provided object class
     * @param source the path or string format of json
     * @param isPath checks if it's a path or string source
     * @param clazz the class of the model
     * @param <T> the type of the model
     * @return the object model of the provided class populated by the json elements
     */
    <T> T fromJson(CharSequence source, boolean isPath, Class<T> clazz);

    /**
     * Converts json to object model of the provided object class
     * @param json the json object representation of the model
     * @param clazz the class of the model
     * @param <T> the type of the model
     * @return the object model of the provided class populated by the json elements
     */
    <T> T fromJson(nx.peter.java.json.reader.JsonObject<?> json, Class<T> clazz);

    /**
     * Converts an object model to json
     * @param model the object provided
     * @param <T> the class prototype of the object
     * @return the json format of the model
     */
    <T> nx.peter.java.json.reader.JsonObject<?> toJson(T model);

    /**
     * Gets the readable Source of Json provided.
     * @param source a path or string form of json
     * @param isPath specify if it's a path or a string
     * @return the readable source of the json provided if it's valid
     */
    Source<nx.peter.java.json.reader.JsonArray, nx.peter.java.json.reader.JsonObject, JsonElement> getSource(CharSequence source, boolean isPath);

    /**
     * Opens the writable Source of Json provided.
     * @param source a path or string form of json
     * @param isPath specify if it's a path or a string
     * @return the writable source of the json provided if it's valid
     */
    Source<JsonArray, JsonObject, nx.peter.java.json.writer.JsonElement> openSource(CharSequence source, boolean isPath);
}

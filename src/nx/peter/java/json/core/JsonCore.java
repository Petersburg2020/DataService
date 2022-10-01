package nx.peter.java.json.core;

import nx.peter.java.json.Json;
import nx.peter.java.json.JsonContext;

import java.io.File;

/**
 * <pre><h1>A project created by:<br> Uareime Peter Ezekiel</pre></h1>
 * <h2>************* JsonCore **********</h2>
 * <p>This is the base shortcut for json reader and writer.
 * <br>Here, you can read or write a json element.<br></p>
 */
public abstract class JsonCore<J extends JsonCore, JA extends JsonElement, JO extends JsonElement, JE extends JsonElement>  {
    protected Json<JA, JO, JE> root;
    protected CharSequence path;

    public JsonCore() {
        path = "";
        root = new Json<>();
    }

    public JsonCore(CharSequence path, boolean isReader) {
        this(path, true, isReader);
    }

    public JsonCore(CharSequence jsonSource, boolean isPath, boolean isReader) {
        path = isPath && jsonSource != null ? jsonSource : "";
        if (isReader) {
            if (isPath) {
                root = Json.fromPath(jsonSource);
            } else root = Json.fromString(jsonSource);
        } else root = new Json<>();
    }

    public boolean isUndefined() {
        return root.isNull();
    }

    public CharSequence getPath() {
        return path;
    }

    public J setPath(CharSequence path) {
        this.path = path;
        return (J) this;
    }

    public JsonContext getContext() {
        return root.getContext();
    }

    public File getFile() {
        return hasPath() ? new File(path.toString()) : null;
    }

    public boolean hasPath() {
        return path != null;
    }

    public Root<JA, JO, JE> getRoot() {
        return root;
    }

    public Source<JA, JO, JE> getSource() {
        return root;
    }


}

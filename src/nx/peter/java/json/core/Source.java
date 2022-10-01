package nx.peter.java.json.core;

public interface Source<JA extends JsonElement, JO extends JsonElement, JE extends JsonElement>
        extends Root<JA, JO, JE> {
    Source<JA, JO, JE> decode(byte[] code);
}

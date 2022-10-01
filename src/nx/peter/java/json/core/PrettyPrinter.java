package nx.peter.java.json.core;

import nx.peter.java.context.Printer;

public interface PrettyPrinter<JA extends JsonElement, JO extends JsonElement, JE extends JsonElement>
        extends Printer {
    String print();
    String print(JsonElement element);
    Source<JA, JO, JE> getSource();
    JE getElement();
}

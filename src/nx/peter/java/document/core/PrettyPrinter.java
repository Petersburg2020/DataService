package nx.peter.java.document.core;

import nx.peter.java.context.Printer;
import nx.peter.java.json.core.JsonElement;
import nx.peter.java.pis.core.Node;

public interface PrettyPrinter<D extends Document, N extends Node, J extends JsonElement> extends Printer {
    N toPis();
    J toJson();
    D getDocument();
}

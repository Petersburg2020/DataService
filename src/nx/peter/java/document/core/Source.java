package nx.peter.java.document.core;

import nx.peter.java.document.DocumentContext;
import nx.peter.java.json.core.JsonElement;
import nx.peter.java.pis.core.Node;

public interface Source<D extends Document, N extends Node, J extends JsonElement>
        extends nx.peter.java.context.Source<PrettyPrinter<D, N, J>, DocumentContext> {
    N toPis();
    J toJson();
    D getDocument();
}

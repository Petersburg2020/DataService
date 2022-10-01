package nx.peter.java.pis;

import nx.peter.java.context.Context;
import nx.peter.java.pis.core.Node;
import nx.peter.java.pis.core.PrettyPrinter;
import nx.peter.java.pis.core.Source;
import nx.peter.java.pis.reader.PisReader;
import nx.peter.java.pis.writer.PisWriter;

public interface PisContext extends
        Context<PisContext, PisWriter, PisReader,
                Source, Node, nx.peter.java.pis.writer.Node,
                nx.peter.java.pis.reader.Node, PrettyPrinter> {
    Node getNode();
}

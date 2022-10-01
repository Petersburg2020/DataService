package nx.peter.java.context;

import java.io.Serializable;

public interface Source<P extends Printer, C extends Context> extends Serializable {
    P getPrettyPrinter();
    C getContext();
    byte[] encode();
}

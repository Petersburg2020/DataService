package nx.peter.java.context;

import java.io.Serializable;

public interface Reader<R extends Reader, S extends Source> extends Serializable {
    S getSource();
}

package nx.peter.java.context;

import java.io.Serializable;

public interface Writer<W extends Writer, S extends Source> extends Serializable {
    S getSource();
    boolean store();
    boolean store(CharSequence path);
}

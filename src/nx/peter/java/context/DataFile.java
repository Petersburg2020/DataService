package nx.peter.java.context;

import java.io.Serializable;

public interface DataFile<F extends DataFile> extends Serializable {
    int size();
    boolean isEmpty();
    boolean isNotEmpty();
    F getDataFile();
}

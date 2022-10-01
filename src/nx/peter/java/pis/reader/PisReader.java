package nx.peter.java.pis.reader;

import nx.peter.java.context.Reader;
import nx.peter.java.pis.core.PisCore;
import nx.peter.java.pis.core.Source;

public class PisReader extends PisCore<Node, Node.Attr> implements
        Reader<PisReader, Source<? extends Source, Node, Node.Attr>> {
    public PisReader(CharSequence path) {
        super(path);
    }

    public PisReader(CharSequence source, boolean isPath) {
        super(source, isPath);
    }

}

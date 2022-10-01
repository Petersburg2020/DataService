package nx.peter.java.pis.core;

import nx.peter.java.pis.Pis;

public abstract class PisCore<N extends Node, A extends Node.Attr> {
    protected Pis<N, A> root;
    protected String path;

    public PisCore(CharSequence path) {
        this(path, true);
    }

    public PisCore(CharSequence source, boolean isPath) {
        path = source != null && isPath ? source.toString() : "";
        root = Pis.toPis(source != null ? source : "", isPath);
    }

    public String getPath() {
        return path;
    }

    public Source<? extends Source, N, A> getSource() {
        return root;
    }

}

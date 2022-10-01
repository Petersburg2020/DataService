package nx.peter.java.util.param;

public interface Param<P extends Param, F, S> {
    F getFirst();
    S getSecond();
    P getParam();
    boolean equals(P param);
}

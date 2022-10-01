package nx.peter.java.pis.core;

import nx.peter.java.pis.Pis;
import nx.peter.java.pis.PisContext;

public interface Source<S extends Source, N extends Node, A extends Node.Attr> extends
        nx.peter.java.context.Source<PrettyPrinter<N, A>, PisContext> {
    int size();
    N getNode();
    Object get();
    N getParent();
    String getTag();
    boolean isInt();
    boolean isLong();
    boolean isEmpty();
    boolean isFloat();
    boolean isString();
    boolean isDouble();
    boolean isBoolean();
    boolean hasParent();
    boolean isNotEmpty();
    boolean hasChildren();
    boolean hasAttribute();
    boolean isSingleValue();
    <V> V get(Class<V> clazz);
    Node.Nodes<N> getChildren();
    Node.Nodes<N> getChild(CharSequence tag);
    Node.Attrs<A> getAttributes();
    A getAttribute(CharSequence name);
    boolean equals(Source<? extends Source, ? extends Node, ? extends Node.Attr> source);
    boolean containsChild(CharSequence tag);
    boolean containsChild(CharSequence tag, Pis.TagValueList node);
    <V> boolean containsValue(V value);
}

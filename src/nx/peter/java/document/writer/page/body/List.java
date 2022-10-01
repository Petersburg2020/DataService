package nx.peter.java.document.writer.page.body;

import nx.peter.java.document.exception.NotItemException;
import nx.peter.java.document.writer.page.Body;

public interface List<I, L extends List, B extends Body> extends
        Element<L, B>,
        nx.peter.java.document.core.page.body.List<I, L, B> {

    L set(nx.peter.java.document.core.page.body.List list) throws NotItemException;

    L set(java.util.List<I> items);

    L set(I... items);

    boolean add(I... items);

    boolean add(java.util.List<I> items);

    boolean remove(I item);

    boolean removeAt(int index);
}

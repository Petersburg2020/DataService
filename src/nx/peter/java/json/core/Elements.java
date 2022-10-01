package nx.peter.java.json.core;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class Elements<J> implements Iterable<J> {
    protected final List<J> elements;

    public Elements(List<J> elements) {
        this.elements = elements;
    }

    @SafeVarargs
    public Elements(J... elements) {
        this(Arrays.asList(elements));
    }

    public boolean contains(J element) {
        return elements.contains(element);
    }

    public J get(int index) {
        return index >= 0 && index < size() ? elements.get(index) : null;
    }

    public int size() {
        return elements.size();
    }

    public void sort(Comparator<J> comparator) {
        elements.sort(comparator);
    }

    public Stream<J> stream() {
        return elements.stream();
    }

    public J[] toArray(J[] array) {
        return elements.toArray(array);
    }

    public boolean equals(Elements<J> elements) {
        return elements != null && elements.elements.equals(this.elements);
    }

    @Override
    public Iterator<J> iterator() {
        return elements.iterator();
    }

    @Override
    public String toString() {
        return String.valueOf(elements);
    }
}

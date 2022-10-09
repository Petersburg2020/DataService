package nx.peter.java.api;

import nx.peter.java.json.core.Source;
import nx.peter.java.json.reader.JsonArray;
import nx.peter.java.json.reader.JsonElement;
import nx.peter.java.json.reader.JsonObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public interface Api {
    String getUrl();
    String getHost();
    Response getResponse();

    enum Status {
        FAILURE,
        SUCCESS
    }

    interface Response {
        boolean isUnsuccessful();
        boolean isSuccessful();
        String getMessage();
        Body getBody();

        Status getStatus();
        interface Body {
            Source<JsonArray, JsonObject, JsonElement> getSource();
            JsonArray<?> getArray();
            JsonObject<?> getObject();
            JsonElement<?> getJson();
            boolean isArray();
            boolean isObject();
            boolean isUndefined();

        }
    }

    abstract class Items<A extends Items, I> implements Iterable<I> {
        protected final List<I> items;

        public Items(List<I> items) {
            this.items = items != null ? items : new ArrayList<>();
        }

        public int size() {
            return items.size();
        }

        public Stream<I> stream() {
            return items.stream();
        }

        public void sort(Comparator<I> comparator) {
            items.sort(comparator);
        }

        public boolean contains(I item) {
            return item != null && items.contains(item);
        }

        public boolean isEmpty() {
            return items.isEmpty();
        }

        public boolean isNotEmpty() {
            return !isEmpty();
        }

        public I get(int index) {
            return index > 0 && index <= size() ? items.get(index - 1) : null;
        }

        public List<I> toList() {
            return new ArrayList<>(items);
        }

        @Override
        public Iterator<I> iterator() {
            return items.iterator();
        }

        public boolean equals(A another) {
            return another != null && another.items.equals(items);
        }

        @Override
        public String toString() {
            return items.toString();
        }
    }
}

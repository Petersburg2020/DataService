package nx.peter.java.document.core.page.body;

import nx.peter.java.document.core.Document;
import nx.peter.java.document.core.page.Body;

import java.util.ArrayList;

public interface Element<E extends Element, B extends Body> extends Document.Source<E> {
    /**
     * The [.json] {@link Document} format Key for the {@link List}'s body
     */
    String JSON_BODY = "body";

    /**
     * The [.pis] {@link Document} format Key for the {@link List}'s body
     */
    String PIS_BODY = "BODY";

    /**
     * Checks if body exist
     * @return true if exist
     */
    boolean hasBody();


    /**
     * The enum type of element
     */
    enum Entity {
        Heading("Heading", Heading.class),
        Image("Image", Image.class),
        List("List", List.class),
        Text("Text", Text.class);

        public final String name;
        public final Class<? extends Element> element;

        Entity(String name, Class<? extends Element> element) {
            this.name = name;
            this.element = element;
        }
    }

    /**
     * A collection of elements
     * @param <E> element type
     */
    class Elements<E extends Element> extends Document.Array<E> {
        public Elements(java.util.List<E> items) {
            super(items);
        }

        /**
         * Gets collection of element by class type
         * @param clazz element class
         * @param <T> type of element
         * @return collection of element
         */
        public <T extends Element> Elements<T> getByClass(Class<T> clazz) {
            java.util.List<T> elements = new ArrayList<>();
            for (E element : items)
                if (element.getClass().equals(clazz))
                    elements.add((T) element);
            return new Elements<>(elements);
        }

        /**
         * Gets collection of element by entity
         * @param entity element entity
         * @return collection of element
         */
        public Elements getByEntity(Entity entity) {
            return getByClass(entity.element);
        }
    }
}

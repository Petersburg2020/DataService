package nx.peter.java.document.core.page;

import nx.peter.java.document.core.Document;
import nx.peter.java.document.core.page.body.Element;
import nx.peter.java.document.core.page.body.Heading;
import nx.peter.java.document.core.page.body.Image;
import nx.peter.java.document.core.page.body.Text;
import nx.peter.java.json.JsonArray;
import nx.peter.java.json.reader.JsonObject;
import nx.peter.java.pis.reader.Node;
import nx.peter.java.util.data.Texts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public interface Body<B extends Body> extends Document.Source<B> {
    String JSON_ELEMENTS = "elements";
    String JSON_TAG = "body";
    String PIS_TAG = "BODY";
    String PIS_ELEMENTS = "ELEMENTS";

    /**
     * The [.pis | .json] {@link Document} format Key for the {@link Body}'s elements' size
     */
    String ELEMENT_SIZE = "element-size";

    /**
     * Checks if body has element
     *
     * @return true if this body has at least an element
     */
    boolean hasElement();

    /**
     * Checks if this body and the provided body is equal
     *
     * @param body provided body
     * @return true if they are equal
     */
    boolean equals(Body body);


    class Creator extends ISource<Creator> implements
            nx.peter.java.document.writer.page.Body<Creator>,
            nx.peter.java.document.reader.page.Body<Creator> {

        protected List<Element> elements;

        public Creator() {
            this(new ArrayList<>());
        }

        public Creator(Element... elements) {
            set(elements);
        }

        public Creator(List<Element> elements) {
            set(elements);
        }

        @Override
        public boolean hasElement() {
            return !isEmpty();
        }

        @Override
        public boolean equals(Body body) {
            return equals((Creator) body);
        }

        @Override
        public boolean isEmpty() {
            return elements.isEmpty();
        }

        @Override
        public boolean equals(Creator source) {
            return source != null && Objects.equals(elements, source.elements);
        }

        @Override
        public JsonObject toJson() {
            nx.peter.java.json.JsonObject json = new nx.peter.java.json.JsonObject();
            json.add(Document.JSON_STEP, step);
            json.add(ENCODE, getEncoding());
            json.add(ELEMENT_SIZE, elements.size());

            JsonArray elements = new JsonArray();
            for (Element element : this.elements)
                elements.add(((nx.peter.java.document.reader.page.body.Element) element).toJson());
            json.add(JSON_ELEMENTS, elements);
            return json;
        }

        @Override
        public Node toPis() {
            nx.peter.java.pis.Node pis = new nx.peter.java.pis.Node(PIS_TAG);
            pis.addAttr(Document.PIS_STEP, step);
            pis.addAttr(ELEMENT_SIZE, elements.size());

            nx.peter.java.pis.Node node = new nx.peter.java.pis.Node(PIS_ELEMENTS);
            for (Element element : elements)
                node.addNode(((nx.peter.java.document.reader.page.body.Element) element).toPis());
            pis.addNode(node);
            return pis;
        }

        @Override
        public Texts getData(int lineLength) {
            Texts data = new Texts();
            int count = 0;
            for (Element element : elements)
                data.append(((nx.peter.java.document.reader.page.body.Element) element).getData(lineLength)).
                        append("\n").append((++count < elements.size() ? "\n" : ""));
            // System.out.println("Body: " + elements.size() + " " + data.get());
            return data;
        }

        @Override
        public Creator autoStep() {
            for (Element element : elements) {
                nx.peter.java.document.writer.page.body.Element e = (nx.peter.java.document.writer.page.body.Element) element;
                e.setStep(step);
                e.autoStep();
            }
            return this;
        }

        @Override
        public Creator set(Creator source) {
            if (source != null) {
                setStep(source.step);
                set(source.elements);
            }
            return this;
        }

        @Override
        public Creator fromJson(JsonObject json) {
            if (json != null) {
                setStep(json.getInt(Document.JSON_STEP, 0));
                if (json.containsKey(JSON_ELEMENTS)) {
                    nx.peter.java.json.reader.JsonArray elements = json.getArray(JSON_ELEMENTS);
                    for (Object o : elements.getAllObjects()) {
                        JsonObject element = (JsonObject) o;
                        if (element.containsKey(ENCODE))
                            add(switch (element.getString(ENCODE)) {
                                case "Heading" -> new Heading.Creator().fromJson(element);
                                case "Image" -> new Image.Creator().fromJson(element);
                                case "List" -> new nx.peter.java.document.core.page.body.List.Creator<>().fromJson(element);
                                case "Text" -> new Text.Creator().fromJson(element);
                                default -> null;
                            });
                    }
                }
            }
            return this;
        }

        @Override
        public Creator fromPis(Node node) {

            return null;
        }

        @Override
        public Creator clear() {
            if (isNotEmpty())
                elements.clear();
            return this;
        }

        @Override
        public Creator set(List<Element> elements) {
            this.elements = new ArrayList<>();
            add(elements);
            return this;
        }

        @Override
        public Creator set(Body body) {
            return set((Creator) body);
        }

        @Override
        public Creator set(Element... elements) {
            return set(Arrays.asList(elements));
        }

        @Override
        public boolean add(Element... elements) {
            return add(Arrays.asList(elements));
        }

        @Override
        public boolean add(List<Element> elements) {
            if (elements != null) {
                int count = 0;
                for (Element element : elements)
                    if (element != null && element.isNotEmpty()) {
                        this.elements.add(element);
                        count++;
                    }
                return count > 0;
            }
            return false;
        }

        @Override
        public boolean addText(CharSequence text) {
            return text != null && add(new Text.Creator(text));
        }

        @Override
        public boolean addHeading(CharSequence heading) {
            return heading != null && add(new Heading.Creator(heading));
        }

        @Override
        public boolean addList(Object... items) {
            return items != null && add(new nx.peter.java.document.core.page.body.List.Creator(items));
        }

        @Override
        public boolean removeByIndex(int index) {
            return false;
        }

        @Override
        public Element.Elements<nx.peter.java.document.reader.page.body.Text> getTexts() {
            return null;
        }

        @Override
        public Element.Elements<nx.peter.java.document.reader.page.body.List> getLists() {
            return null;
        }

        @Override
        public Element.Elements<nx.peter.java.document.reader.page.body.Heading> getHeadings() {
            return null;
        }

        @Override
        public Element.Elements<nx.peter.java.document.reader.page.body.Element> getElements() {
            return null;
        }

        @Override
        public Element getElementByPosition(int position) {
            return null;
        }

        @Override
        public Element.Elements<nx.peter.java.document.reader.page.body.Element> getElementsByType(Element.Entity entity) {
            return null;
        }

        @Override
        public <E extends Element> Element.Elements<E> getElementsByClass(Class<E> clazz) {
            return null;
        }
    }
}

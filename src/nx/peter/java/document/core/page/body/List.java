package nx.peter.java.document.core.page.body;

import nx.peter.java.document.core.Document;
import nx.peter.java.document.core.page.Body;
import nx.peter.java.document.exception.NotItemException;
import nx.peter.java.json.JsonArray;
import nx.peter.java.json.reader.JsonObject;
import nx.peter.java.pis.reader.Node;
import nx.peter.java.util.Util;
import nx.peter.java.util.data.Texts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public interface List<I, L extends List, B extends Body> extends Element<L, B> {
    String PIS_TAG = "LIST";
    String JSON_TAG = "list";

    /**
     * The [.json] {@link Document} format Key for the {@link List}'s items
     */
    String JSON_ITEMS = "items";

    /**
     * The [.pis] {@link Document} format Key for the {@link List}'s item
     */
    String PIS_ITEM = "ITEM";

    /**
     * The [.pis] {@link Document} format Key for the {@link List}'s item size
     */
    String ITEM_COUNT = "item-count";

    /**
     * The [.pis] {@link Document} format Key for the {@link List}'s items
     */
    String PIS_ITEMS = "ITEMS";


    boolean hasItems();

    boolean equals(List list);


    class Creator<I> extends
            ISource<Creator> implements
            nx.peter.java.document.reader.page.body.List<I, Creator, Body.Creator>,
            nx.peter.java.document.writer.page.body.List<I, Creator, Body.Creator> {

        /**
         * List of items
         */
        java.util.List<I> items;

        public Creator() {
            this(new ArrayList<>());
        }

        @SafeVarargs
        public Creator(I... items) {
            this(items != null ? Arrays.asList(items) : null);
        }

        public Creator(java.util.List<I> items) {
            super();
            set(items);
        }

        @Override
        public boolean isEmpty() {
            return !isNotEmpty();
        }

        @Override
        public boolean isNotEmpty() {
            return hasItems() || (body != null && body.isNotEmpty());
        }

        @Override
        public boolean equals(Creator source) {
            return source != null && Objects.equals(items, source.items) && Objects.equals(body, source.body);
        }

        @Override
        public boolean hasItems() {
            return !items.isEmpty();
        }

        @Override
        public boolean equals(List list) {
            return equals((Creator) list);
        }

        @Override
        public JsonObject toJson() {
            nx.peter.java.json.JsonObject json = new nx.peter.java.json.JsonObject();
            json.add(Document.JSON_STEP, step);
            json.add(ENCODE, getEncoding());
            json.add(ITEM_COUNT, items.size());
            json.add(TAG, JSON_TAG);

            JsonArray array = new JsonArray();
            for (I item : items)
                if (item instanceof Element)
                    array.add(((nx.peter.java.document.reader.page.body.Element<?, ?>) item).toJson());
                else array.add(item);

            json.add(JSON_ITEMS, items);
            json.add(JSON_BODY, hasBody() ? body.toJson() : new nx.peter.java.json.JsonObject());
            return json;
        }

        @Override
        public Node toPis() {
            nx.peter.java.pis.Node node = new nx.peter.java.pis.Node(PIS_TAG);
            node.addAttr(Document.PIS_STEP, step);
            node.addAttr(ENCODE, getEncoding());
            node.addAttr(ITEM_COUNT, items.size());

            nx.peter.java.pis.Node iPis = new nx.peter.java.pis.Node(PIS_ITEMS);
            for (I item : items)
                if (item instanceof Element)
                    iPis.addNode(((nx.peter.java.document.reader.page.body.Element<?, ?>) item).toPis());
                else iPis.addNode(PIS_ITEM, item);
            node.addNode(iPis);

            node.addNode(PIS_BODY, hasBody() ? body.toPis() : new nx.peter.java.pis.Node());
            return node;
        }

        @Override
        public Texts getData(int lineLength) {
            Texts texts = new Texts();
            if (hasItems()) {
                int count = 0;
                for (I item : items) {
                    count++;
                    texts.append((item instanceof Element ? ((nx.peter.java.document.reader.page.body.Element<?, ?>) item).getData(lineLength) : Util.formatLine(item.toString(), lineLength)))
                            .append((count < items.size() ? "\n" : ""));
                }
            }
            if (hasBody())
                texts.append(body.getData(lineLength));
            // System.out.println("List: " + items.size() + " " + texts.toString());
            return texts;
        }

        @Override
        public Entity getEntity() {
            return Entity.List;
        }

        @Override
        public Creator<I> set(Creator source) throws NotItemException {
            if (source != null) {
                try {
                    set(source.items);
                } catch (ClassCastException e) {
                    throw new NotItemException(e.getMessage());
                }
                setBody(source.body);
            }
            return this;
        }

        @Override
        public Creator<I> fromJson(JsonObject json) {
            if (json != null) {
                setStep(json.getInt(Document.JSON_STEP, 0));
                items = new ArrayList<>();

                if (json.containsKey(JSON_ITEMS)) {
                    JsonArray arr = (JsonArray) json.getArray(JSON_ITEMS);
                    for (int n = 0; n < arr.size(); n++) {
                        Object o = arr.get(n);
                        if (o instanceof Map) {
                            JsonObject obj = arr.getObject(n);
                            String encoding = obj.getString(ENCODE);
                            if (encoding != null)
                                add((I) switch (encoding) {
                                    case "Heading" -> new Heading.Creator().fromJson(obj);
                                    case "Image" -> new Image.Creator().fromJson(obj);
                                    case "List" -> new Creator<>().fromJson(obj);
                                    case "Text" -> new Text.Creator().fromJson(obj);
                                    default -> null;
                                });
                        } else add((I) o);
                    }
                }
            }
            if (json.containsKey(JSON_BODY))
                setBody(new Body.Creator().fromJson(json.getObject(JSON_BODY)));
            return this;
        }

        @Override
        public Creator<I> fromPis(Node node) {
            if (node != null) {
                setStep(node.getAttribute(Document.PIS_STEP).getInt(0));
                items = new ArrayList<>();
                Node.Nodes nItems = node.getChildByTag(PIS_ITEMS);
                if (nItems.isNotEmpty()) {
                    Node pis = (Node) nItems.get(0);
                    for (int i = 0; i < pis.size(); i++) {
                        Node item = pis.getChildAt(i);
                        if (item.isSingleValue())
                            add((I) item.get());
                        else if (item.containsAttr(ENCODE)) {
                                String encoding = item.getAttribute(ENCODE).getString();
                                if (encoding != null)
                                    add((I) switch (encoding) {
                                        case "Heading" -> new Heading.Creator().fromPis(item);
                                        case "Image" -> new Image.Creator().fromPis(item);
                                        case "List" -> new Creator<>().fromPis(item);
                                        case "Text" -> new Text.Creator().fromPis(item);
                                        default -> null;
                                    });
                            }
                    }
                }
                if (node.containsChild(PIS_BODY))
                    setBody(new Body.Creator().fromPis((Node) node.getChildByTag(PIS_BODY).get(0)));
            }
            return this;
        }

        @Override
        public Creator set(List list) throws NotItemException {
            return set((Creator) list);
        }

        @Override
        public Creator<I> set(java.util.List<I> items) {
            this.items = new ArrayList<>();
            add(items);
            return this;
        }

        @SafeVarargs
        @Override
        public final Creator<I> set(I... items) {
            return set(items != null ? Arrays.asList(items) : null);
        }

        @SafeVarargs
        @Override
        public final boolean add(I... items) {
            return add(items != null ? Arrays.asList(items) : null);
        }

        @Override
        public boolean add(java.util.List<I> items) {
            if (items != null) {
                int count = 0;
                for (I item : items)
                    if (item != null) {
                        this.items.add(item);
                        count++;
                    }
                return count > 0;
            }
            return false;
        }

        @Override
        public boolean remove(I item) {
            return items.remove(item);
        }

        @Override
        public boolean removeAt(int index) {
            return items.remove(index) != null;
        }
    }
}

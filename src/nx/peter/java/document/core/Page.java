package nx.peter.java.document.core;

import nx.peter.java.document.core.page.Body;
import nx.peter.java.document.core.page.body.Element;
import nx.peter.java.document.core.page.body.Heading;
import nx.peter.java.document.core.page.body.Text;
import nx.peter.java.json.reader.JsonObject;
import nx.peter.java.pis.reader.Node;
import nx.peter.java.util.Util;
import nx.peter.java.util.data.Texts;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public interface Page<P extends Page, B extends Body, H extends Heading> extends
        Document.Source<P> {
    String JSON_TAG = "page";
    String PIS_TAG = "PAGE";

    /**
     * The [.json] {@link Document} format Key for the {@link Page}'s body
     */
    String JSON_BODY = "body";

    /**
     * The [.json] {@link Document} format Key for the {@link Page}'s number
     */
    String JSON_NUMBER = "number";

    /**
     * The [.json] {@link Document} format Key for the {@link Page}'s <b>topHeading</b>
     */
    String JSON_HEADING = "top-heading";

    /**
     * The [.pis] {@link Document} format end Tag for the {@link Page}'s <b>body</b><br>
     * Like a Mark-up Language.
     */
    String PIS_BODY = "BODY";

    /**
     * The [.pis] {@link Document} format for the {@link Page}'s <b>heading</b><br>
     * Like a Mark-up Language.
     */
    String PIS_HEADING = "TOP-HEADING";

    /**
     * The [.pis] {@link Document} format for the {@link Page}'s number<br>
     * Like a Mark-up Language.
     */
    String PIS_NUMBER = "number";


    /**
     * Checks if page has heading
     * @return true if page has heading
     */
    boolean hasHeading();

    /**
     * Checks if body exist
     * @return true if exist
     */
    boolean hasBody();

    /**
     * Checks if page has a valid number<pre>number > 0</pre>
     * @return true if page has number
     */
    boolean hasNumber();

    /**
     * Checks if given page equals this page
     * @param page given page
     * @return true if they are equal
     */
    boolean equals(Page page);

    /**
     * Page Creator
     */
    class Creator extends ISource<Creator> implements
            nx.peter.java.document.reader.Page<Creator, Body.Creator, Heading.Creator>,
            nx.peter.java.document.writer.Page<Creator, Body.Creator, Heading.Creator> {
        protected Heading.Creator heading;
        protected int number;

        public Creator() {
            this(0);
        }

        public Creator(int number) {
            this(number, null, null);
        }

        public Creator(int number, Heading heading) {
            this(number, heading, null);
        }

        public Creator(int number, Body body) {
            this(number, null, body);
        }

        public Creator(int number, Heading heading, Body body) {
            super();
            setNumber(number);
            setHeading(heading);
            setBody(body);
        }

        @Override
        public Creator reset() {
            setHeading((Heading) null);
            setNumber(0);
            return super.reset();
        }

        @Override
        public boolean isEmpty() {
            return (hasHeading());
        }

        @Override
        public boolean equals(Creator source) {
            return source != null &&
                    Objects.equals(number, source.number) &&
                    Objects.equals(heading, source.heading) &&
                    Objects.equals(body, source.body);
        }

        @Override
        public boolean hasHeading() {
            return heading != null;
        }

        @Override
        public boolean hasNumber() {
            return number > 0;
        }

        @Override
        public boolean equals(Page page) {
            return equals((Creator) page);
        }

        @Override
        public JsonObject toJson() {
            nx.peter.java.json.JsonObject json = new nx.peter.java.json.JsonObject();
            json.add(Document.JSON_STEP, step);
            json.add(ENCODE, getEncoding());
            json.add(JSON_NUMBER, number);
            json.add(JSON_HEADING, hasHeading() ? heading.toJson() : new nx.peter.java.json.JsonObject());
            json.add(JSON_BODY, hasBody() ? body.toJson() : new nx.peter.java.json.JsonObject());
            return json;
        }

        @Override
        public Node toPis() {
            nx.peter.java.pis.Node node = new nx.peter.java.pis.Node(PIS_TAG);
            node.addAttr(Document.PIS_STEP, step);
            node.addAttr(ENCODE, getEncoding());
            node.addAttr(PIS_NUMBER, number);
            node.addNode(PIS_HEADING, hasHeading() ? heading.toPis() : new nx.peter.java.pis.Node());
            node.addNode(PIS_BODY, hasBody() ? body.toPis() : new nx.peter.java.pis.Node());
            return node;
        }

        @Override
        public Texts getData(int lineLength) {
            Texts texts = new Texts();
            if (hasHeading())
                texts.append(heading.getData(lineLength))
                        .append("\n");
            if (hasBody())
                texts.append(body.getData(lineLength));
            String line = "*".repeat(lineLength);
            if (number > 0)
                texts.append(line + "\n")
                        .append(Util.tab(5) + number)
                        .append("\n");
            return texts;
        }

        @Override
        public Creator autoStep() {
            if (hasHeading()) {
                heading.setStep(step + 1);
                heading.autoStep();
            }
            return super.autoStep();
        }

        @Override
        public Creator set(Creator source) {
            if (source != null) {
                setNumber(source.number);
                setHeading(source.heading);
                setBody(source.body);
            }
            return this;
        }

        @Override
        public Creator fromJson(JsonObject json) {
            if (json != null) {
                setStep(json.getInt(Document.JSON_STEP, 0));
                setNumber(json.getInt(JSON_NUMBER, 0));
                JsonObject obj = json.getObject(JSON_BODY);
                if (obj.isNotEmpty()) setBody(new Body.Creator().fromJson(obj));
                obj = json.getObject(JSON_HEADING);
                if (obj.isNotEmpty()) setHeading(new Heading.Creator().fromJson(obj));
            }
            return this;
        }

        @Override
        public Creator fromPis(Node node) {
            if (node != null) {
                setStep(node.getInt(Document.PIS_STEP, 0));
                setNumber(node.getInt(PIS_NUMBER, 0));
                Node pis = (Node) node.getChildByTag(PIS_BODY).get(0);
                if (pis != null && pis.isNotEmpty()) setBody(new Body.Creator().fromPis(pis));
                pis = (Node) node.getChildByTag(PIS_HEADING).get(0);
                if (pis != null && pis.isNotEmpty()) setHeading(new Heading.Creator().fromPis(pis));
            }
            return this;
        }

        @Override
        public boolean isValid() {
            return number > 0 && super.isValid();
        }

        @Override
        public Creator set(Page page) {
            return set((Creator) page);
        }

        @Override
        public Creator set(Heading heading, Body body) {
            return set(new Creator(number, heading, body));
        }

        @Override
        public Creator setBody(Body body) {
            this.body = (Body.Creator) body;
            return this;
        }

        @Override
        public Creator setNumber(int number) {
            this.number = number;
            return this;
        }

        @Override
        public Creator setBody(CharSequence body) {
            return setBody(new Text.Creator(body));
        }

        @Override
        public Creator setBody(Element... elements) {
            return setBody(elements != null ? Arrays.asList(elements) : null);
        }

        @Override
        public Creator setBody(List<Element> elements) {
            return setBody(elements != null ? new Body.Creator(elements) : null);
        }

        @Override
        public Creator setHeading(CharSequence heading) {
            return setHeading(heading != null ? new Heading.Creator(heading) : null);
        }

        @Override
        public Creator setHeading(Heading heading) {
            this.heading = (Heading.Creator) heading;
            return this;
        }

        @Override
        public boolean removeHeading() {
            if (hasHeading()) {
                heading = null;
                return true;
            }
            return false;
        }

        @Override
        public boolean removeBody() {
            if (hasBody()) {
                body = null;
                return true;
            }
            return false;
        }

        @Override
        public int getNumber() {
            return number;
        }
    }
}

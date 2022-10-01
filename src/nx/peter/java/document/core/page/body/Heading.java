package nx.peter.java.document.core.page.body;

import nx.peter.java.document.core.Document;
import nx.peter.java.document.core.page.Body;
import nx.peter.java.json.reader.JsonObject;
import nx.peter.java.pis.reader.Node;
import nx.peter.java.util.Util;
import nx.peter.java.util.data.Texts;

import java.util.Objects;

public interface Heading<H extends Heading, B extends Body> extends
        Element<H, B> {
    String JSON_TAG = "element-heading";
    String PIS_TAG = "ELEMENT-HEADING";
    String PIS_HEADING = "HEADING";

    String JSON_HEADING = "heading";

    /**
     * Checks if it has a String heading
     * @return true if heading is not empty
     */
    boolean hasHeading();

    boolean equals(Heading heading);

    class Creator extends
            ISource<Creator> implements
            nx.peter.java.document.writer.page.body.Heading<Creator, Body.Creator>,
            nx.peter.java.document.reader.page.body.Heading<Creator, Body.Creator> {
        protected String heading;

        public Creator() {
            this("");
        }

        public Creator(CharSequence heading) {
            this(heading, null);
        }

        public Creator(Body body) {
            this("", body);
        }

        public Creator(CharSequence heading, Body body) {
            super();
            setHeading(heading);
            setBody(body);
        }

        @Override
        public Creator reset() {
            super.reset();
            return setHeading("");
        }

        @Override
        public boolean isEmpty() {
            return (heading != null && heading.isEmpty()) && (body != null && body.isEmpty());
        }

        @Override
        public boolean equals(Creator source) {
            return source != null && Objects.equals(body, source.body) && Objects.equals(heading, source.heading);
        }

        @Override
        public JsonObject toJson() {
            nx.peter.java.json.JsonObject json = new nx.peter.java.json.JsonObject();
            json.add(Document.JSON_STEP, step);
            json.add(ENCODE, getEncoding());
            json.add(TAG, JSON_TAG);
            json.add(JSON_HEADING, heading);
            json.add(JSON_BODY, hasBody() ? body.toJson() : new nx.peter.java.json.JsonObject());
            return json;
        }

        @Override
        public Node toPis() {
            nx.peter.java.pis.Node pis = new nx.peter.java.pis.Node(PIS_TAG);
            pis.addAttr(Document.PIS_STEP, step);
            pis.addAttr(ENCODE, getEncoding());
            pis.addNode(PIS_HEADING, heading);
            pis.addNode(PIS_BODY, hasBody() ? body.toPis() : new nx.peter.java.pis.Node());
            return pis;
        }

        @Override
        public Texts getData(int lineLength) {
            Texts data = new Texts();
            if (hasHeading())
                data.append(Util.centerLine(heading, lineLength)).append("\n");
            if (hasBody())
                data.append(body.getData(lineLength));
            return data;
        }

        @Override
        public Creator autoStep() {
            if (hasBody()) {
                body.setStep(step + 1);
                body.autoStep();
            }
            return this;
        }

        @Override
        public Creator set(Creator source) {
            if (source != null) {
                setHeading(source.heading);
                setBody(source.body);
            }
            return this;
        }

        @Override
        public Creator fromJson(JsonObject json) {
            if (json != null) {

            }
            return this;
        }

        @Override
        public Creator fromPis(Node node) {

            return this;
        }

        @Override
        public boolean hasHeading() {
            return !heading.isEmpty();
        }

        @Override
        public boolean equals(Heading heading) {
            return equals((Creator) heading);
        }

        @Override
        public String getHeading() {
            return heading;
        }

        @Override
        public Entity getEntity() {
            return Entity.Heading;
        }

        @Override
        public Creator setHeading(CharSequence heading) {
            this.heading = heading != null ? heading.toString() : "";
            return this;
        }
    }
}

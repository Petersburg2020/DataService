package nx.peter.java.document.core.page.body;

import nx.peter.java.document.core.Document;
import nx.peter.java.document.core.page.Body;
import nx.peter.java.json.reader.JsonObject;
import nx.peter.java.pis.reader.Node;
import nx.peter.java.util.Util;
import nx.peter.java.util.data.IData;
import nx.peter.java.util.data.Texts;

import java.util.Objects;

public interface Text<T extends Text, B extends Body> extends
        Element<T, B> {
    String PIS_TEXT = "TEXT";
    String JSON_TEXT = "text";
    String JSON_TAG = "element-text";
    String PIS_TAG = "ELEMENT-TEXT";

    /**
     * Checks if String text exists
     * @return true if text exist
     */
    boolean hasText();

    boolean equals(Text text);

    class Creator extends ISource<Creator> implements
            nx.peter.java.document.writer.page.body.Text<Creator, Body.Creator>,
            nx.peter.java.document.reader.page.body.Text<Creator, Body.Creator> {
        protected String text;
        protected Body.Creator body;

        public Creator() {
            this("");
        }

        public Creator(CharSequence text) {
            this(text, null);
        }

        public Creator(Body body) {
            this("", body);
        }

        public Creator(CharSequence text, Body body) {
            setText(text);
            setBody(body);
        }

        @Override
        public Creator reset() {
            setText("");
            return super.reset();
        }

        @Override
        public boolean isEmpty() {
            return (text != null && text.isEmpty()) && (body != null && body.isEmpty());
        }

        @Override
        public boolean equals(Creator source) {
            return source != null && Objects.equals(text, source.text) && Objects.equals(body, source.body);
        }

        @Override
        public JsonObject toJson() {
            nx.peter.java.json.JsonObject json = new nx.peter.java.json.JsonObject();
            json.add(Document.JSON_STEP, step);
            json.add(ENCODE, getEncoding());
            json.add(TAG, JSON_TAG);
            json.add(JSON_TEXT, text);
            json.add(JSON_BODY, hasBody() ? body.toJson() : new nx.peter.java.json.JsonObject());
            return json;
        }

        @Override
        public Node toPis() {
            nx.peter.java.pis.Node pis = new nx.peter.java.pis.Node(PIS_TAG);
            pis.addAttr(Document.PIS_STEP, step);
            pis.addAttr(ENCODE, getEncoding());
            pis.addNode(PIS_TEXT, text);
            pis.addNode(PIS_BODY, hasBody() ? body.toPis() : new nx.peter.java.pis.Node());
            return pis;
        }

        @Override
        public Texts getData(int lineLength) {
            Texts data = new Texts();
            if (hasText())
                data.append(Util.formatLine(text, lineLength)).append("\n");
            if (hasBody())
                data.append(body.getData(lineLength));
            return data;
        }

        public Creator set(Text text) {
            return set((Creator) text);
        }

        @Override
        public Creator set(Creator source) {
            if (source != null) {
                setBody(source.body);
                setText(source.text);
            }
            return this;
        }

        @Override
        public Creator fromJson(JsonObject json) {
            if (json != null) {
                setStep(json.getInt(Document.JSON_STEP, 0));
                setText(json.getString(JSON_TEXT));
                JsonObject obj = json.getObject(JSON_BODY);
                if (obj.isNotEmpty()) setBody(new Body.Creator().fromJson(obj));
            }
            return this;
        }

        @Override
        public Creator fromPis(Node node) {

            return this;
        }

        @Override
        public boolean hasBody() {
            return body != null && body.isNotEmpty();
        }

        @Override
        public boolean hasText() {
            return !text.isEmpty();
        }

        @Override
        public boolean equals(Text text) {
            return equals((Creator) text);
        }

        @Override
        public Creator setBody(Body body) {
            this.body = (Body.Creator) body;
            return this;
        }

        @Override
        public Creator setText(CharSequence text) {
            this.text = text != null ? text.toString() : "";
            return this;
        }

        @Override
        public Creator setText(IData text) {
            return setText(text != null ? text.get() : "");
        }

        @Override
        public Creator setText(Object text) {
            return setText(String.valueOf(text));
        }

        @Override
        public Body.Creator getBody() {
            return body;
        }

        @Override
        public Entity getEntity() {
            return Entity.Text;
        }

        @Override
        public String getText() {
            return text;
        }
    }
}

package nx.peter.java.document.core.page.body;

import nx.peter.java.document.core.Document;
import nx.peter.java.document.core.page.Body;
import nx.peter.java.document.exception.NotItemException;
import nx.peter.java.json.reader.JsonObject;
import nx.peter.java.pis.reader.Node;
import nx.peter.java.util.data.Texts;

public interface Image<I extends Image, B extends Body> extends Element<I, B> {
    boolean hasImage();

    boolean equals(Image image);

    class Creator extends ISource<Creator> implements
            nx.peter.java.document.writer.page.body.Image<Creator, Body.Creator>,
            nx.peter.java.document.reader.page.body.Image<Creator, Body.Creator> {
        protected String image;
        protected boolean isUrl;

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean equals(Creator source) {
            return false;
        }

        @Override
        public boolean hasImage() {
            return !image.isEmpty();
        }

        @Override
        public boolean equals(Image image) {
            return equals((Creator) image);
        }

        @Override
        public JsonObject toJson() {

            return null;
        }

        @Override
        public Node toPis() {
            nx.peter.java.pis.Node pis = new nx.peter.java.pis.Node();

            return pis;
        }

        @Override
        public Texts getData(int lineLength) {
            return null;
        }

        @Override
        public Entity getEntity() {
            return null;
        }

        @Override
        public Creator set(Creator source) throws NotItemException {
            return null;
        }

        @Override
        public Creator fromJson(JsonObject json) {
            return null;
        }

        @Override
        public Creator fromPis(Node node) {
            return null;
        }
    }
}

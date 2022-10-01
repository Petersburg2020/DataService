package nx.peter.java.pis.writer;

import nx.peter.java.context.Writer;
import nx.peter.java.storage.FileManager;
import nx.peter.java.pis.core.PisCore;
import nx.peter.java.pis.core.Source;

public class PisWriter extends PisCore<Node, Node.Attr> implements
        Writer<PisWriter, Source<? extends Source, Node, Node.Attr>> {

    public PisWriter(CharSequence path) {
        super(path);
    }

    public PisWriter(CharSequence source, boolean isPath) {
        super(source, isPath);
    }

    public PisWriter createNode(CharSequence tag, nx.peter.java.pis.core.Node node) {
        root.set(tag, node);
        return this;
    }

    public PisWriter createNode(CharSequence tag, CharSequence childTag, Object... values) {
        root.set(tag, new nx.peter.java.pis.Node());
        if (values != null)
            for (Object value : values)
                root.add(childTag, value);
        return this;
    }

    public PisWriter createObject(CharSequence tag, Object value) {
        root.set(tag, value);
        return this;
    }

    public PisWriter setRoot(nx.peter.java.pis.core.Node node) {
        if (node != null)
            root.set(node.getTag(), node);
        return this;
    }

    public boolean addNode(CharSequence tag, nx.peter.java.pis.core.Node node) {
        return root.add(tag, node);
    }

    public boolean addObject(CharSequence tag, Object value) {
        return root.add(tag, value);
    }

    public boolean addAttr(CharSequence name, Object value) {
        return root.addAttr(name, value);
    }

    public boolean addAttr(nx.peter.java.pis.core.Node.Attr attr) {
        return root.getNode().addAttr(attr);
    }

    public boolean removeByTag(CharSequence tag) {
        return root.removeTag(tag);
    }

    public boolean removeValue(Object value) {
        return root.getNode().removeValue(value);
    }

    public boolean removeNode(nx.peter.java.pis.core.Node node) {
        return root.getNode().removeNode(node);
    }

    public PisWriter setPath(CharSequence path) {
        this.path = path != null ? path.toString() : "";
        return this;
    }

    public boolean store() {
        return FileManager.writeFile(path, root.prettyPrint(), false);
    }

    public boolean store(CharSequence path) {
        return setPath(path).store();
    }

}

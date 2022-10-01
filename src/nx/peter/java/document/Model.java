package nx.peter.java.document;

public abstract class Model extends nx.peter.java.context.model.Model {
    public String content;

    public Model(String modelName, long modelId, String content) {
        super(modelName, modelId);
        this.content = content;
    }

    @Override
    public String toString() {
        return super.toString() + ", content='" + content + "'";
    }
}

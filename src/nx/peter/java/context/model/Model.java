package nx.peter.java.context.model;

import java.io.Serializable;

public abstract class Model implements Serializable {
    public final String modelName;
    public final long modelId;

    public Model(String modelName, long modelId) {
        this.modelName = modelName != null ? modelName : "un-named";
        this.modelId = modelId > 0 ? modelId : 0;
    }

    public final boolean isValid() {
        return modelId > 0 && !modelName.contentEquals("un-named");
    }

    public boolean equals(Model other) {
        return other != null && other.modelName.contentEquals(modelName) && other.modelId == modelId;
    }

    @Override
    public String toString() {
        return "modelName='" + modelName + '\'' + ", modelId=" + modelId;
    }
}

package nx.peter.java.math.shape.solid;

import nx.peter.java.math.shape.AbstractShape;

public abstract class Solid<S extends Solid> extends AbstractShape<S> {
	public Solid() {
		super();
	}

	@Override
	public Form getForm() {
		return Form.Solid;
	}
	
}

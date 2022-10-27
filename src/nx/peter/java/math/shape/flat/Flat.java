package nx.peter.java.math.shape.flat;

import nx.peter.java.math.shape.AbstractShape;

public abstract class Flat<F extends Flat> extends AbstractShape<F> {
	public Flat() {
		super();
	}

	@Override
	public Form getForm() {
		return Form.Flat;
	}

}

package nx.peter.java.math.shape.solid;

import nx.peter.java.math.shape.Shape;
import nx.peter.java.math.value.Area;
import nx.peter.java.math.value.Perimeter;
import nx.peter.java.math.value.Volume;

public class Hole extends Solid<Hole> implements nx.peter.java.math.shape.Hole<Hole> {
	protected Solid solid;
	
	public Hole() {
		super();
	}

	public Hole(Solid solid) {
		set(solid);
	}

	public void set(Solid solid) {
		this.solid = solid;
	}

	@Override
	public void reset() {
		solid = null;
	}

	public boolean isNotNull() {
		return solid != null;
	}

	@Override
	public Area getArea() {
		return isNotNull() ? solid.getArea() : new Area();
	}

	@Override
	public Volume getVolume() {
		return isNotNull() ? solid.getVolume() : new Volume();
	}

	@Override
	public Perimeter getPerimeter() {
		return isNotNull() ? solid.getPerimeter() : new Perimeter();
	}

	@Override
	public boolean equals(Shape shape) {
		return isNotNull() ? solid.equals(shape) : false;
	}

	@Override
	public Hole multiply(double value) {
		if (isNotNull())
			solid.multiply(value);
		return this;
	}

	@Override
	public Hole divide(double value) {
		if (isNotNull())
			solid.divide(value);
		return this;
	}

	@Override
	public Hole getPercent(double percent) {
		solid.getPercent(percent);
		return this;
	}

	@Override
	public String getShape() {
		return solid.getShape() + "-" + super.getShape();
	}

	@Override
	public boolean isHole() {
		return true;
	}
	
}

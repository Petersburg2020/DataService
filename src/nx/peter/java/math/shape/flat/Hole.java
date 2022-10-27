package nx.peter.java.math.shape.flat;

import nx.peter.java.math.shape.Shape;
import nx.peter.java.math.value.Area;
import nx.peter.java.math.value.Perimeter;
import nx.peter.java.math.value.Volume;

public class Hole extends Flat<Hole> implements nx.peter.java.math.shape.Hole<Hole> {
	protected Flat flat;
	
	public Hole(Flat flat) {
		set(flat);
	}

	public void set(Flat flat) {
		this.flat = flat;
	}

	@Override
	public void reset() {
		flat = null;
	}
	
	protected boolean isNotNull() {
		return flat != null;
	}

	@Override
	public Area getArea() {
		return isNotNull() ? flat.getArea() : new Area();
	}

	@Override
	public Volume getVolume() {
		return isNotNull() ? flat.getVolume() : new Volume();
	}

	@Override
	public Perimeter getPerimeter() {
		return isNotNull() ? flat.getPerimeter() : new Perimeter();
	}

	@Override
	public boolean equals(Shape shape) {
		return isNotNull() && flat.equals(shape);
	}

	@Override
	public Hole multiply(double value) {
		if (isNotNull())
			flat.multiply(value);
		return this;
	}

	@Override
	public Hole divide(double value) {
		if (isNotNull())
			flat.divide(value);
		return this;
	}

	@Override
	public Hole getPercent(double percent) {
		flat.getPercent(percent);
		return this;
	}

	@Override
	public boolean isHole() {
		return true;
	}
	
}

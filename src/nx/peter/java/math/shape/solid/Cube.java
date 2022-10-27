package nx.peter.java.math.shape.solid;

import nx.peter.java.math.value.Linear;
import nx.peter.java.math.value.Linear.LinearUnit;

public class Cube extends Cuboid {
	public Cube() {
		super();
	}
	
	public Cube(Linear length) {
		super(length, length, length);
	}
	
	public Cube(double length, LinearUnit unit) {
		super(length, length, length, unit);
	}
	
	public void set(Linear length) {
		set(length, length, length);
	}

	@Override
	public void setWidth(Linear width) {
		super.setWidth(width);
		super.setLength(width);
		super.setDepth(width);
	}

	@Override
	public void setDepth(Linear depth) {
		super.setWidth(depth);
		super.setLength(depth);
		super.setDepth(depth);
	}

	@Override
	public void setLength(Linear length) {
		super.setWidth(length);
		super.setLength(length);
		super.setDepth(length);
	}

	@Override
	public Cube getPercent(double percent) {
		return (Cube) super.getPercent(percent);
	}

	public Cuboid toCuboid() {
		return new Cuboid(length, width, depth);
	}
	
}

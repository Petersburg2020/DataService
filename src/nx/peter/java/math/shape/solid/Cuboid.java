package nx.peter.java.math.shape.solid;

import nx.peter.java.math.shape.Shape;
import nx.peter.java.math.value.Area;
import nx.peter.java.math.value.Linear;
import nx.peter.java.math.value.Linear.LinearUnit;
import nx.peter.java.math.value.Perimeter;
import nx.peter.java.math.value.Volume;

public class Cuboid extends Solid<Cuboid> {
	protected Linear length, depth, width;
	
	public Cuboid() {
		super();
	}
	
	public Cuboid(Linear length, Linear width, Linear depth) {
		set(length, width, depth);
	}

	public Cuboid(double length, double width, double depth, LinearUnit unit) {
		set(length, width, depth, unit);
	}
	

	@Override
	public void reset() {
		length = new Linear();
		depth = new Linear();
		width = new Linear();
	}


	public void set(double length, double width, double depth, LinearUnit unit) {
		set(new Linear(length, unit), new Linear(width, unit), new Linear(depth, unit));
	}

	public void set(Linear length, Linear width, Linear depth) {
		setLength(length);
		setWidth(width);
		setDepth(depth);
	}
	
	public void setLength(Linear length) {
		this.length = length != null ? length : new Linear();
	}
	
	public void setLength(double length, LinearUnit unit) {
		setLength(new Linear(length, unit));
	}

	public Linear getLength() {
		return new Linear(length);
	}

	public void setDepth(Linear depth) {
		this.depth = depth != null ? depth : new Linear();
	}

	public void setDepth(double depth, LinearUnit unit) {
		setDepth(new Linear(depth, unit));
	}
	
	public Linear getDepth() {
		return new Linear(depth);
	}

	public void setWidth(Linear width) {
		this.width = width != null ? width : new Linear();
	}

	public void setWidth(double width, LinearUnit unit) {
		setWidth(new Linear(width, unit));
	}
	
	public Linear getWidth() {
		return new Linear(width);
	}

	@Override
	public Cuboid getPercent(double percent) {
		length.getPercent(percent);
		width.getPercent(percent);
		depth.getPercent(percent);
		return this;
	}

	
	@Override
	public Area getArea() {
		return Area.fromCuboid(length, width, depth);
	}

	@Override
	public Volume getVolume() {
		return Volume.fromCuboid(length, width, depth);
	}

	@Override
	public Perimeter getPerimeter() {
		return Perimeter.fromCuboid(length, width, depth);
	}

	@Override
	public boolean equals(Shape shape) {
		return shape instanceof Cuboid && super.equals(shape);
	}

	@Override
	public Cuboid multiply(double value) {
		length.multiply(value);
		width.multiply(value);
		depth.multiply(value);
		return this;
	}

	@Override
	public Cuboid divide(double value) {
		length.divide(value);
		width.divide(value);
		depth.divide(value);
		return this;
	}
	
	
}

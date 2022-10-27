package nx.peter.java.math.shape.flat;

import nx.peter.java.math.shape.Shape;
import nx.peter.java.math.value.Area;
import nx.peter.java.math.value.Linear;
import nx.peter.java.math.value.Linear.LinearUnit;
import nx.peter.java.math.value.Perimeter;
import nx.peter.java.math.value.Volume;

public class Circle extends Flat<Circle> {
	protected Linear radius;
	
	
	public Circle() {
		super();
	}
	
	public Circle(Linear radius) {
		super();
		set(radius);
	}

	public Circle(double radius) {
		this(radius, LinearUnit.Metre);
	}
	
	public Circle(double radius, LinearUnit unit) {
		super();
		set(radius, unit);
	}

	@Override
	public void reset() {
		set(new Linear());
	}


	public void set(Linear radius) {
		this.radius = radius != null ? radius : new Linear();
	}

	public void set(double radius, LinearUnit unit) {
		set(new Linear(radius, unit));
	}

	public void setRadius(double radius) {
		this.radius.setValue(radius);
	}

	public void setUnit(LinearUnit unit) {
		radius.setUnit(unit);
	}

	@Override
	public Circle multiply(double value) {
		radius.multiply(value);
		return this;
	}

	@Override
	public Circle divide(double value) {
		radius.divide(value);
		return this;
	}

	public Linear getRadius() {
		return radius;
	}

	@Override
	public Circle getPercent(double percent) {
		radius.getPercent(percent);
		return this;
	}

	@Override
	public Perimeter getPerimeter() {
		return Perimeter.fromCircle(radius);
	}

	@Override
	public Area getArea() {
		return Area.fromCircle(radius);
	}

	@Override
	public Volume getVolume() {
		return new Volume();
	}

	@Override
	public boolean equals(Shape shape) {
		return shape instanceof Circle && ((Circle) shape).getRadius().equals(radius);
	}

	

	@Override
	public String toString() {
		return super.toString() + ";\n\t Radius=" + getRadius();
	}
	
}

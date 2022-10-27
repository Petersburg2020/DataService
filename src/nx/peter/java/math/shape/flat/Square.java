package nx.peter.java.math.shape.flat;

import nx.peter.java.math.shape.Shape;
import nx.peter.java.math.value.Area;
import nx.peter.java.math.value.Linear;
import nx.peter.java.math.value.Linear.LinearUnit;
import nx.peter.java.math.value.Perimeter;
import nx.peter.java.math.value.Volume;


public class Square extends Flat<Square> {
	private Linear length;
	
	
	public Square() {
		super();
	}
	
	public Square(Linear length) {
		super();
		set(length);
	}
	
	public Square(double length, LinearUnit unit) {
		super();
		set(length, unit);
	}

	@Override
	public void reset() {
		set(new Linear());
	}
	
	

	public void set(Linear length) {
		this.length = length != null ? length : new Linear();
	}
	
	public void set(double length, LinearUnit unit) {
		set(new Linear(length, unit));
	}
	
	public void setLength(double length) {
		this.length.setValue(length);
	}
	
	public void setUnit(LinearUnit unit) {
		length.setUnit(unit);
	}
	
	public Linear getLength() {
		return length;
	}

	@Override
	public Square multiply(double value) {
		length.multiply(value);
		return this;
	}

	@Override
	public Square divide(double value) {
		length.divide(value);
		return this;
	}

	@Override
	public Square getPercent(double percent) {
		set(length.getPercent(percent));
		return this;
	}
	
	@Override
	public Perimeter getPerimeter() {
		return new Perimeter(length);
	}

	@Override
	public Area getArea() {
		return Area.fromSquare(length);
	}

	@Override
	public Volume getVolume() {
		return new Volume();
	}

	@Override
	public boolean equals(Shape shape) {
		return shape instanceof Square && ((Square) shape).getLength().equals(length);
	}


	@Override
	public String toString() {
		return super.toString() + "; Length=" + getLength();
	}
}

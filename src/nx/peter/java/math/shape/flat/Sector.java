package nx.peter.java.math.shape.flat;

import nx.peter.java.math.shape.Shape;
import nx.peter.java.math.value.*;
import nx.peter.java.math.value.Linear.LinearUnit;
import nx.peter.java.math.value.Angle.AngleUnit;

public class Sector extends Flat<Sector> {
	protected Linear radius;
	protected Angle angle;
	
	public Sector() {
		super();
	}

	public Sector(double radius, double angle) {
		this(radius, LinearUnit.Metre, angle, AngleUnit.Degree);
	}
	
	public Sector(Linear radius, Angle angle) {
		set(radius, angle);
	}

	public Sector(double radius, LinearUnit rUnit, double angle, AngleUnit aUnit) {
		this(new Linear(radius, rUnit), new Angle(angle, aUnit));
	}

	
	@Override
	public void reset() {
		radius = new Linear();
		angle = new Angle();
	}

	public void set(Linear radius, Angle angle) {
		setRadius(radius);
		setAngle(angle);
	}
	
	public void setRadius(Linear radius) {
		this.radius = radius != null ? radius : new Linear();
	}

	public Linear getRadius() {
		return new Linear(radius);
	}

	public void setAngle(Angle angle) {
		this.angle = angle != null ? angle : new Angle();
	}

	public Angle getAngle() {
		return new Angle(angle);
	}
	
	@Override
	public Area getArea() {
		return Area.fromSector(radius, new Angle(angle).convertTo(AngleUnit.Degree).getValue());
	}

	@Override
	public Volume getVolume() {
		return Volume.fromCube(radius).setValue(0);
	}

	@Override
	public Perimeter getPerimeter() {
		return Perimeter.fromSector(radius, new Angle(angle).convertTo(AngleUnit.Degree).getValue());
	}

	@Override
	public Sector getPercent(double percent) {
		radius.getPercent(percent);
		angle.getPercent(percent);
		return this;
	}

	@Override
	public boolean equals(Shape shape) {
		return shape instanceof Sector && shape.getArea().equals(getArea());
	}

	@Override
	public Sector multiply(double value) {
		radius.multiply(value);
		angle.multiply(value);
		return this;
	}

	@Override
	public Sector divide(double value) {
		radius.divide(value);
		angle.divide(value);
		return this;
	}
	
}

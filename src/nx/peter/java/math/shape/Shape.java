package nx.peter.java.math.shape;

import nx.peter.java.math.value.Area;
import nx.peter.java.math.value.Perimeter;
import nx.peter.java.math.value.Volume;

import java.util.List;

public interface Shape<S extends Shape> {
	enum Form {
		Solid, Flat, Mixed
	}
	
	void reset();
	
	Area getArea();
	Volume getVolume();
	Perimeter getPerimeter();
	Form getForm();
	boolean isSolid();
	boolean isFlat();
	boolean isMixed();
	boolean isHole();
	boolean isTruncated();
	boolean isBigger(Shape another);
	boolean isSmaller(Shape another);
	boolean equals(Shape shape);
	S multiply(double value);
	S divide(double value);
	S getPercent(double percent);
	Perimeter subtract(Perimeter perimeter);
	Perimeter add(Perimeter perimeter);
	Area subtract(Area area);
	Area add(Area area);
	Volume subtract(Volume volume);
	Volume add(Volume volume);
	<S extends Shape> ComplexShape add(S... shapes);
	<S extends Shape> ComplexShape add(List<S> shape);
	<S extends Shape> ComplexShape remove(S... shapes);
	<S extends Shape> ComplexShape remove(List<S> shapes);
	ComplexShape toComplex();
	String getShape();
}

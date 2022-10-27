package nx.peter.java.math.shape;

import nx.peter.java.math.value.Area;
import nx.peter.java.math.value.Perimeter;
import nx.peter.java.math.value.Volume;

import java.util.List;

public abstract class AbstractShape<A extends AbstractShape> implements Shape<A> {
	
	public AbstractShape() {
		reset();
	}

	@Override
	public boolean isFlat() {
		return getForm().equals(Form.Flat) || isMixed();
	}

	@Override
	public boolean isSolid() {
		return getForm().equals(Form.Solid) || isMixed();
	}

	@Override
	public boolean isMixed() {
		return getForm().equals(Form.Mixed);
	}

	@Override
	public boolean isHole() {
		return false;
	}

	@Override
	public boolean isTruncated() {
		return false;
	}

	@Override
	public boolean isBigger(Shape another) {
		return getArea().isGreaterThan(another.getArea()) && getVolume().isGreaterThan(another.getVolume());
	}

	@Override
	public boolean isSmaller(Shape another) {
		return getArea().isLessThan(another.getArea()) && getVolume().isLessThan(another.getVolume());
	}

	@Override
	public Volume add(Volume volume) {
		return getVolume().add(volume);
	}

	@Override
	public Area add(Area area) {
		return getArea().add(area);
	}

	@Override
	public Perimeter add(Perimeter perimeter) {
		return getPerimeter().add(perimeter);
	}

	@Override
	public Area subtract(Area area) {
		return getArea().subtract(area);
	}

	@Override
	public Volume subtract(Volume volume) {
		return getVolume().subtract(volume);
	}

	@Override
	public Perimeter subtract(Perimeter perimeter) {
		return getPerimeter().subtract(perimeter);
	}

	@Override
	public <S extends Shape> ComplexShape add(S... shapes) {
		return toComplex().add(shapes);
	}

	@Override
	public ComplexShape toComplex() {
		return new nx.peter.java.math.shape.mixed.ComplexShape(this);
	}

	@Override
	public <S extends Shape> ComplexShape add(List<S> shapes) {
		return toComplex().add(shapes);
	}

	@Override
	public <S extends Shape> ComplexShape remove(List<S> shapes) {
		return toComplex().remove(shapes);
	}

	@Override
	public <S extends Shape> ComplexShape remove(S... shapes) {
		return toComplex().remove(shapes);
	}

	@Override
	public String getShape() {
		return getClass().getName().substring(getClass().getName().lastIndexOf(".") + 1);
	}

	@Override
	public boolean equals(Shape shape) {
		return getArea().isEquivalentTo(shape.getArea()) && getVolume().isEquivalentTo(shape.getVolume());
	}




	@Override
	public String toString() {
		return "SHAPE: " + (getShape().equals("ComplexShape") ? getForm() : "") + getShape() + 
			"\n\t Volume: " + getVolume() + 
			"\n\t Area: " + getArea() +
			"\n\t Perimeter: " + getPerimeter() +
			"\n\t Form: " + getForm() +
			"\n\t Is Hollow: " + toComplex().isHollow();
	}
	
}

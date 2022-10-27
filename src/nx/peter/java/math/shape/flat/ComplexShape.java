package nx.peter.java.math.shape.flat;

import nx.peter.java.math.shape.Shape;
import nx.peter.java.math.value.Area;
import nx.peter.java.math.value.Perimeter;
import nx.peter.java.math.value.Volume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComplexShape extends Flat<ComplexShape> implements nx.peter.java.math.shape.ComplexShape<ComplexShape> {
	
	protected List<Shape> shapes;

	public ComplexShape() {
		super();
	}

	public ComplexShape(Shape... shapes) {
		super();
		set(shapes);
	}

	public ComplexShape(List<Shape> shapes) {
		super();
		set(shapes);
	}

	@Override
	public void reset() {
		shapes = new ArrayList<>();
	}

	@Override
	public <S extends Shape> void set(List<S> shapes) {
		reset();
		add(shapes);
	}

	@Override
	public <S extends Shape> void set(S... shapes) {
		set(Arrays.asList(shapes));
	}

	@Override
	public ComplexShape multiply(double value) {
		for (Shape s : shapes)
			s.multiply(value);
		return this;
	}

	@Override
	public ComplexShape divide(double value) {
		for (Shape s : shapes)
			s.divide(value);
		return this;
	}

	@Override
	public <S extends Shape> ComplexShape add(S... shapes) {
		return add(Arrays.asList(shapes));
	}

	@Override
	public <S extends Shape> ComplexShape add(List<S> shapes) {
		for (S s : shapes)
			if (s != null && s.isFlat())
				this.shapes.add(s);
		return this;
	}

	@SafeVarargs
	@Override
	public final <S extends Shape> ComplexShape remove(S... shapes) {
		return remove(Arrays.asList(shapes));
	}

	@Override
	public <S extends Shape> ComplexShape remove(List<S> shapes) {
		for (S s : shapes)
			if (contains(s))
				this.shapes.remove(s);
		return this;
	}

	@Override
	public boolean isHollow() {
		for (Shape s : shapes)
			if (s.isHole())
				return true;
		return false;
	}

	@Override
	public boolean contains(Shape shape) {
		if (shape != null)
			for (Shape s : shapes)
				if (s.equals(shape))
					return true;
		return false;
	}

	@Override
	public List<Shape> getShapes() {
		return shapes;
	}

	@Override
	public ComplexShape toComplex() {
		return this;
	}

	@Override
	public ComplexShape getPercent(double percent) {
		for (Shape s : getShapes())
			s.getPercent(percent);
		return this;
	}
	
	@Override
	public Perimeter getPerimeter() {
		Perimeter p = new Perimeter();
		if (!isEmpty())
			p = new Perimeter(0, shapes.get(0).getPerimeter().getUnit());
		for (Shape s : shapes)
			if (s.isHole())
				p.subtract(s.getPerimeter());
			else
				p.add(s.getPerimeter());
		return p;
	}

	@Override
	public Area getArea() {
		Area a = new Area();
		if (!isEmpty())
			a = new Area(0, shapes.get(0).getArea().getUnit());
		for (Shape s : shapes)
			if (s.isHole())
				a.subtract(s.getArea());
			else
				a.add(s.getArea());
		return a;
	}

	@Override
	public Area getAreaOfHoles() {
		Area area = new Area(getArea());
		area.setValue(0);
		for (Hole hole : toComplex().getHoles())
			area.add(hole.getArea());
		return area;
	}
	
	@Override
	public Volume getVolume() {
		Volume v = new Volume();
		if (!isEmpty())
			v = new Volume(0, shapes.get(0).getVolume().getUnit());
		for (Shape s : shapes)
			if (s.isHole())
				v.subtract(s.getVolume());
			else
				v.add(s.getVolume());
		return v;
	}

	@Override
	public int shapeCount() {
		return shapes.size();
	}

	@Override
	public boolean isEmpty() {
		return shapes.isEmpty();
	}

	@Override
	public boolean equals(Shape shape) {
		return shape instanceof nx.peter.java.math.shape.ComplexShape && ((nx.peter.java.math.shape.ComplexShape<?>) shape).getShapes().equals(shapes);
	}

	@Override
	public boolean isHole() {
		return shapeCount() == 1 && isHollow();
	}
	
	@Override
	public List<Hole> getHoles() {
		List<Hole> holes = new ArrayList<>();
		for (Shape s : shapes)
			if (s.isHole())
				holes.add((Hole) s);
		return holes;
	}

	
	@Override
	public List<String> getShapesNames() {
		List<String> names = new ArrayList<>();
		for(Shape s : shapes)
			names.add(s.getShape());
		return names;
	}

	@Override
	public List<Area> getShapesAreas() {
		List<Area> areas = new ArrayList<>();
		for (Shape s : shapes)
			areas.add(s.getArea());
		return areas;
	}

	@Override
	public List<Volume> getShapesVolumes() {
		List<Volume> volumes = new ArrayList<>();
		for (Shape s : shapes)
			volumes.add(s.getVolume());
		return volumes;
	}

	@Override
	public List<Perimeter> getShapesPerimeters() {
		List<Perimeter> perimeters = new ArrayList<>();
		for (Shape s : shapes)
			perimeters.add(s.getPerimeter());
		return perimeters;
	}
	
	@Override
	public Shape toShape() {
		return shapeCount() == 1 ? shapes.get(0) : this;
	}

	@Override
	public String getShape() {
		return shapeCount() == 1 ? toShape().getShape() : "Complex";
	}

	@Override
	public String toString() {
		return (shapeCount() == 1 ? shapes.get(0).toString() : super.toString() +
			"\n\t Shapes: " + getShapesNames().toString().replace("[", "").replace("]", "") +
			";\n\t Total Shapes: " + shapeCount());
	}
	
}

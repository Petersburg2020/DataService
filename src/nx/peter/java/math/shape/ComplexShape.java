package nx.peter.java.math.shape;

import nx.peter.java.math.value.Area;
import nx.peter.java.math.value.Perimeter;
import nx.peter.java.math.value.Volume;

import java.util.List;

public interface ComplexShape<C extends ComplexShape> extends Shape<C> {
	<S extends Shape> void set(List<S> shapes);
	<S extends Shape> void set(S... shapes);
	List<Shape> getShapes();
	<H extends Hole> List<H> getHoles();
	Area getAreaOfHoles();
	int shapeCount();
	boolean isEmpty();
	boolean isHollow();
	<S extends Shape> boolean contains(S shape);
	List<String> getShapesNames();
	List<Area> getShapesAreas();
	List<Volume> getShapesVolumes();
	List<Perimeter> getShapesPerimeters();
	Shape toShape();
}

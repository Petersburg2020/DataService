package nx.peter.java.math.shape.solid;

import nx.peter.java.math.value.Area;
import nx.peter.java.math.value.Linear;
import nx.peter.java.math.value.Linear.LinearUnit;
import nx.peter.java.math.value.Perimeter;
import nx.peter.java.math.value.Volume;

public class Cylinder extends Solid<Cylinder> {
    public enum Openings {
        Both, None, Single
    }

    protected Linear radius, depth;
    protected Openings openings;

    public Cylinder() {
        super();
    }

    public Cylinder(double radius, double depth, LinearUnit unit) {
        this(new Linear(radius, unit), new Linear(depth, unit));
    }


    public Cylinder(double radius, double depth, LinearUnit unit, Openings openings) {
        this(new Linear(radius, unit), new Linear(depth, unit), openings);
    }

    public Cylinder(Linear radius, Linear depth) {
        this(radius, depth, null);
    }

    public Cylinder(Linear radius, Linear depth, Openings openings) {
        this();
        set(radius, depth, openings);
    }

    @Override
    public void reset() {
        radius = new Linear();
        depth = new Linear();
    }

    public Cylinder set(double radius, double depth, LinearUnit unit) {
        return set(new Linear(radius, unit), new Linear(depth, unit));
    }

    public Cylinder set(Linear radius, Linear depth) {
        return set(radius, depth, null);
    }

    public Cylinder set(double radius, double depth, LinearUnit unit, Openings openings) {
        return set(new Linear(radius, unit), new Linear(depth, unit), openings);
    }

    public Cylinder set(Linear radius, Linear depth, Openings openings) {
        return setDepth(depth).setRadius(radius).setOpenings(openings);
    }

    public Linear getRadius() {
        return radius;
    }

    public Cylinder setRadius(Linear radius) {
        this.radius = radius != null ? radius : new Linear();
        return this;
    }

    public Openings getOpenings() {
        return openings;
    }

    public int getOpeningCount() {
        return switch (openings) {
            case Both -> 2;
            case Single -> 1;
            default -> 0;
        };
    }

    public Cylinder setOpenings(Openings openings) {
        this.openings = openings != null ? openings : Openings.None;
        return this;
    }

    public Linear getDepth() {
        return depth;
    }

    public Cylinder setDepth(Linear depth) {
        this.depth = depth != null ? depth : new Linear();
        return this;
    }

    @Override
    public Area getArea() {
        return Area.fromCylinder(radius, depth);
    }

    @Override
    public Volume getVolume() {
        return Volume.fromCylinder(radius, depth);
    }

    @Override
    public Perimeter getPerimeter() {
        Perimeter circle = Perimeter.fromCircle(radius);
        return circle.multiply(2 - getOpeningCount()).add(new Perimeter(circle, depth));
    }

    @Override
    public Cylinder multiply(double value) {
        depth.multiply(value);
        return this;
    }

    @Override
    public Cylinder divide(double value) {
        depth.divide(value);
        return this;
    }

    @Override
    public Cylinder getPercent(double percent) {
        return multiply(percent/100);
    }
}

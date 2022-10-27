package nx.peter.java.math.shape.solid;

import nx.peter.java.math.shape.Shape;
import nx.peter.java.math.value.*;
import nx.peter.java.math.value.Angle.AngleUnit;
import nx.peter.java.math.value.Linear.LinearUnit;

public class Cone extends Solid<Cone> {
    public enum ConeState {
        Opened, Closed
    }

    protected Linear radius, height;
    protected ConeState state;

    public Cone() {
        super();
    }

    public Cone(double radius, double height) {
        this(radius, height, (LinearUnit) null);
    }

    public Cone(double radius, double height, LinearUnit unit) {
        this(new Linear(radius, unit), new Linear(height, unit));
    }

    public Cone(Linear radius, Linear height) {
        this(radius, height, null);
    }

    public Cone(double radius, double height, ConeState state) {
        this(radius, height, null, state);
    }

    public Cone(double radius, double height, LinearUnit unit, ConeState state) {
        this(new Linear(radius, unit), new Linear(height, unit), state);
    }

    public Cone(Linear radius, Linear height, ConeState state) {
        super();
        set(radius, height, state);
    }

    @Override
    public void reset() {
        radius = new Linear();
        height = new Linear();
        state = ConeState.Closed;
    }

    public void set(Linear radius, Linear height) {
        set(radius, height, null);
    }

    public void set(Linear radius, Linear height, ConeState state) {
        setRadius(radius);
        setHeight(height);
        setState(state);
    }

    public void setHeight(Linear height) {
        this.height = height != null ? height : new Linear();
    }

    public void setRadius(Linear radius) {
        this.radius = radius != null ? radius : new Linear();
    }

    public void setSlantHeight(Linear height) {
        height = height != null ? new Linear(height) : new Linear();
        setHeight(height.pow(2).subtract(getRadius().pow(2)).sqrt()); // Pythagoras h = √(l² - r²)
    }

    public void setUnit(LinearUnit unit) {
        radius.setUnit(unit);
        height.setUnit(unit);
    }

    public void setState(ConeState state) {
        this.state = state != null ? state : ConeState.Closed;
    }

    public ConeState getState() {
        return state;
    }

    public Linear getRadius() {
        return new Linear(radius);
    }

    public Linear getHeight() {
        return new Linear(height);
    }

    public Linear getSlantHeight() {
        return getHeight().pow(2).add(getRadius().multiply(2)).sqrt(); // Pythagoras l = √(h² + r²)
    }

    @Override
    public Cone getPercent(double percent) {
        set(radius.getPercent(percent), height.getPercent(percent), state);
        return this;
    }


    /**
     * angle = (1/tan) * (2 * radius/height)
     */
    public Angle getSlantAngle() {
        return new Angle(Math.atan(getRadius().getValue() == 0 && getHeight().getValue() == 0 ? 0 : getRadius().divide(getHeight()).getValue()), AngleUnit.Radian).convertTo(AngleUnit.Degree);
    }

    @Override
    public Area getArea() {
        return Area.fromCone(getRadius(), getHeight(), state);
    }

    @Override
    public Volume getVolume() {
        return Volume.fromCone(getRadius(), getHeight());
    }

    @Override
    public Perimeter getPerimeter() {
        return new Perimeter();
    }

    @Override
    public boolean equals(Shape shape) {
        return shape instanceof Cone && ((Cone) shape).getRadius().isEquivalentTo(getRadius()) && ((Cone) shape).getHeight().isEquivalentTo(getHeight());
    }

    @Override
    public Cone multiply(double value) {
        radius.multiply(value);
        height.multiply(value);
        return this;
    }

    @Override
    public Cone divide(double value) {
        return this;
    }

    public boolean isOpen() {
        return state.equals(ConeState.Opened);
    }

    public boolean isClosed() {
        return state.equals(ConeState.Closed);
    }

    @Override
    public String toString() {
        return super.toString() +
                "\n\t Radius: " + getRadius() +
                "\n\t Height: " + getHeight() +
                "\n\t Slant Height: " + getSlantHeight() +
                "\n\t Slant Angle: " + getSlantAngle();
    }

}

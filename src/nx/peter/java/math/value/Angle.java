package nx.peter.java.math.value;

import nx.peter.java.math.value.IValue.Value;
import nx.peter.java.util.Util;

public class Angle extends Value<Angle.AngleUnit, Angle> {

	public enum AngleUnit {
		Degree, Seconds, Minute, Radian
	}

	public Angle() {
		super();
	}
	
	public Angle(Value<AngleUnit, ? extends Angle> another) {
		super(another);
	}

	public Angle(double value) {
		this(value, AngleUnit.Degree);
	}
	
	public Angle(double value, AngleUnit unit) {
		super(value, unit);
	}

	@Override
	protected AngleUnit[] values() {
		return AngleUnit.values();
	}

	public Angle setSine(double sine) {
		return setValue(new Angle(Math.asin(sine), AngleUnit.Radian).convertTo(unit).getValue());
	}

	public Angle setCosine(double cosine) {
		return setValue(new Angle(Math.acos(cosine), AngleUnit.Radian).convertTo(unit).getValue());
	}

	public Angle setTangent(double tangent) {
		return setValue(new Angle(Math.atan(tangent), AngleUnit.Radian).convertTo(unit).getValue());
	}
	
	@Override
	public void reset() {
		value = 0;
		unit = AngleUnit.Degree;
	}

	@Override
	public String getUnitPrint() {
		return switch (unit) {
			case Radian -> "rad";
			case Seconds -> "\"";
			case Minute -> "'";
			default -> "°";
		};
	}

	@Override
	protected double fromBaseValueTo(AngleUnit unit, double value) {
		return switch (unit) {
			case Radian -> value * Math.PI / 180;
			case Seconds -> value * 3600;
			case Minute -> value * 60;
			default -> value;
		};
	}

	@Override
	protected double toBaseValue() {
		return switch (unit) {
			case Radian -> value / Math.PI * 180;
			case Seconds -> value / 3600;
			case Minute -> value / 60;
			default -> value;
		};
	}
	
	public double sine() {
		return Math.sin(Util.toNDecimalPlace(new Angle(this).convertTo(AngleUnit.Radian).getValue(), 4));
	}

	public double cosine() {
		return Math.cos((new Angle(this).convertTo(AngleUnit.Radian).getValue()));
	}

	public double tangent() {
		return Math.tan((new Angle(this).convertTo(AngleUnit.Radian).getValue()));
	}
	
}

package nx.peter.java.math.value;

import nx.peter.java.math.value.IValue.Value;
import nx.peter.java.math.value.Linear.LinearUnit;

public class Volume extends Value<Volume.VolumeUnit, Volume> {
	public enum VolumeUnit {
		CubicMeter,
		CubicMillimeter,
		CubicCentimeter,
		CubicKilometer,
		Litre,
		CentiLitre,
		MilliLitre
	}

	public Volume() {
		this(0, VolumeUnit.CubicMeter);
	}
	
	public Volume(Volume another) {
		super(another);
	}
	
	// Volume of cuboid
	public Volume(Linear length, Linear width, Linear depth) {
		this(new Linear(length).multiply(width).multiply(depth).convertTo(LinearUnit.Metre).getValue(), VolumeUnit.CubicMeter);
	}
	
	// Volume of cylinder
	public Volume(Linear radius, Linear depth) {
		this(new Linear(radius).pow(2).multiply(Math.PI).multiply(depth).convertTo(LinearUnit.Metre).getValue(), VolumeUnit.CubicMeter);
	}
	
	// Volume of sphere
	public Volume(Linear radius) {
		this(new Linear(radius).pow(3).multiply(Math.PI).convertTo(LinearUnit.Metre).getValue(), VolumeUnit.CubicMeter);
	}
	
	public Volume(double value, VolumeUnit unit) {
		super(value, unit);
	}

	@Override
	protected VolumeUnit[] values() {
		return VolumeUnit.values();
	}

	@Override
	public void reset() {
		value = 0;
		unit = VolumeUnit.CubicMeter;
	}

	// Volume of cuboid
	public void set(Linear length, Linear width, Linear depth) {
		set(length.multiply(width).multiply(depth).convertTo(LinearUnit.Metre).getValue(), VolumeUnit.CubicMeter);
	}

	// Volume of cylinder
	public void set(Linear radius, Linear depth) {
		set(radius.pow(2).multiply(Math.PI).multiply(depth).convertTo(LinearUnit.Metre).getValue(), VolumeUnit.CubicMeter);
	}

	// Volume of sphere
	public void set(Linear radius) {
		set(radius.pow(3).multiply(Math.PI).convertTo(LinearUnit.Metre).getValue(), VolumeUnit.CubicMeter);
	}
	

	@Override
	public String getUnitPrint() {
		return switch (getUnit()) {
			case CubicMillimeter -> "mm³";
			case CubicCentimeter -> "cm³";
			case CubicKilometer -> "km³";
			case Litre -> "L";
			case CentiLitre -> "cL";
			case MilliLitre -> "mL";
			default -> "m³";
		};
	}
	
	@Override
	protected double fromBaseValueTo(VolumeUnit unit, double value) {
		return switch (unit) {
			case CubicMillimeter -> value * Math.pow(1000, 3);
			case CubicCentimeter -> value * Math.pow(100, 3);
			case CubicKilometer -> value / Math.pow(1000, 3);
			case Litre -> value * Math.pow(10, 3);
			case CentiLitre -> value * Math.pow(10, 3 + 2);
			case MilliLitre -> value * Math.pow(10, 3 + 3);
			default -> value;
		};
	}

	// Use CubicMeter (m³) as base
	@Override
	protected double toBaseValue() {
		return switch (unit) {
			case CubicMillimeter -> value / Math.pow(1000, 3);
			case CubicCentimeter -> value / Math.pow(100, 3);
			case CubicKilometer -> value * Math.pow(1000, 3);
			case Litre -> value / Math.pow(10, 3);
			case CentiLitre -> value / Math.pow(10, 3 + 2);
			case MilliLitre -> value / Math.pow(10, 3 + 3);
			default -> value;
		};
	}
	
	
	

	public static Volume fromCube(Linear length) {
		return fromCuboid(length, length, length);
	}
	
	public static Volume fromCuboid(Linear length, Linear width, Linear depth) {
		return convertTo(new Volume(length, width, depth), length);
	}

	public static Volume fromSphere(Linear radius) {
		return convertTo(new Volume(radius), radius);
	}

	public static Volume fromCylinder(Linear radius, Linear depth) {
		return convertTo(new Volume(radius, depth), radius);
	}

	public static Volume fromCone(Linear radius, Linear depth) {
		return fromCylinder(radius, depth).divide(3);
	}
	
	private static Volume convertTo(Volume v, Linear linear) {
		double value = v.getValue();
		// System.out.println(value);
		switch(linear.getUnit()) {
			case Yard:
				v.convertTo(VolumeUnit.CubicMeter);
				break;
			case Inches:
				v.convertTo(VolumeUnit.CubicCentimeter);
				break;
			case MilliMetre:
				v.convertTo(VolumeUnit.CubicMillimeter);
				break;
			case CentiMetre:
				v.convertTo(VolumeUnit.CubicCentimeter);
				break;
			case KiloMetre:
				v.convertTo(VolumeUnit.CubicKilometer);
				break;
			case Feet:
				v.convertTo(VolumeUnit.CubicMeter);
				break;
			case Mile:
				v.convertTo(VolumeUnit.CubicKilometer);
				break;
		}
		v.setValue(value);
		// System.out.println(v);
		return v;
	}
	
}

package nx.peter.java.math.value;

import nx.peter.java.math.value.Linear.LinearUnit;
import nx.peter.java.math.value.IValue.Value;

public class Perimeter extends Value<LinearUnit, Perimeter> {
	
	public Perimeter() {
		this(0, LinearUnit.Metre);
	}
	
	// Perimeter of square
	public Perimeter(IValue<LinearUnit, ? extends IValue> length) {
		this(length, length);
	}
	
	// Perimeter of rectangle
	public Perimeter(IValue<LinearUnit, ? extends IValue> length, IValue<LinearUnit, ? extends IValue> width) {
		this(new Linear(length.getValue(), length.getUnit()).add(width).multiply(2).getValue(), length.getUnit());
	}
	
	public Perimeter(double value, LinearUnit unit) {
		super(value, unit);
	}

	@Override
	protected LinearUnit[] values() {
		return LinearUnit.values();
	}

	@Override
	public void reset() {
		value = 0;
		unit = LinearUnit.Metre;
	}



	@Override
	public String getUnitPrint() {
		return switch (getUnit()) {
			case MilliMetre -> "mm";
			case CentiMetre -> "cm";
			case KiloMetre -> "km";
			case Inches -> "in";
			case Mile -> "mi";
			case Feet -> "ft";
			case Yard -> "yd";
			default -> "m";
		};
	}

	@Override
	protected double fromBaseValueTo(LinearUnit unit, double value) {
		return switch (unit) {
			case MilliMetre -> value * 1000;
			case CentiMetre -> value * 100;
			case KiloMetre -> value / 1000;
			case Inches -> value * 39.3701;
			case Mile -> value / 1609.344;
			case Feet -> value * 3.28084;
			case Yard -> value * 1.09361;
			default -> value;
		};
	}

	// Use Metre (m) as base
	@Override
	protected double toBaseValue() {
		return switch (unit) {
			case MilliMetre -> value / 1000;
			case CentiMetre -> value / 100;
			case KiloMetre -> value * 1000;
			case Inches -> value / 39.3701;
			case Mile -> value * 1609.344;
			case Feet -> value / 3.28084;
			case Yard -> value / 1.09361;
			default -> value;
		};
	}
	
	
	
	
	public static Perimeter fromCircle(Linear radius) {
		return new Perimeter(radius.multiply(Math.PI).multiply(2).getValue(), radius.getUnit()); // 2πr
	}
	
	public static Perimeter fromSphere(Linear radius) {
		return fromCircle(radius).multiply(8); // 8πr => 4 Area of Circle
	}
	
	public static Perimeter fromArc(Linear radius, double angleInDegree) {
		return fromCircle(radius).multiply(angleInDegree).divide(360);
	}

	public static Perimeter fromSector(Linear radius, double angleInDegree) {
		return fromArc(radius, angleInDegree).add(new Linear(radius.getValue(), radius.getUnit()).multiply(2).getValue()); // 2r(π + 1) => Perimeter of Arc + 2 radius
	}
	
	public static Perimeter fromCube(Linear length) {
		return fromCuboid(length, length, length);
	}
	
	public static Perimeter fromCuboid(Linear length, Linear width, Linear depth) {
		return new Perimeter(length, width).add(new Perimeter(length, depth)).add(new Perimeter(depth, depth));
	}
	
}

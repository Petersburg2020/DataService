package nx.peter.java.math.value;

import nx.peter.java.math.value.IValue.Value;

public class Linear extends Value<Linear.LinearUnit, Linear> {

	public enum LinearUnit {
		Metre, // m
		CentiMetre, // cm
		DeciMetre, // dm
		MilliMetre, // mm
		KiloMetre, // km
		Mile, // mile
		Inches, // inch
		Feet, // ft
		Yard // yard
	}

	public Linear() {
		this(0, LinearUnit.Metre);
	}
	
	public Linear(IValue<LinearUnit, ? extends IValue> another) {
		this(another.getValue(), another.getUnit());
	}

	public Linear(double value, LinearUnit unit) {
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
	
}

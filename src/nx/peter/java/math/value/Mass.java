package nx.peter.java.math.value;

import nx.peter.java.math.value.IValue.Value;

public class Mass extends Value<Mass.MassUnit, Mass> {

	public enum MassUnit {
		Milligramme,
		Kilogramme,
		Gramme,
		Tonne,
		Pounds
	}

	public Mass() {
		this(0, MassUnit.Kilogramme);
	}
	
	public Mass(IValue<MassUnit, ? extends IValue> another) {
		super(another);
	}
	
	public Mass(double value, MassUnit unit) {
		super(value, unit);
	}

	@Override
	protected MassUnit[] values() {
		return MassUnit.values();
	}

	@Override
	public void reset() {
		value = 0;
		unit = MassUnit.Kilogramme;
	}


	@Override
	public String getUnitPrint() {
		return switch (getUnit()) {
			case Milligramme -> "mg";
			case Gramme -> "g";
			case Tonne -> "tons";
			case Pounds -> "lb";
			default -> "kg";
		};
	}

	@Override
	protected double fromBaseValueTo(MassUnit unit, double value) {
		return switch (unit) {
			case Kilogramme -> value / Math.pow(10, 3);
			case Milligramme -> value * Math.pow(10, 3);
			case Tonne -> value / Math.pow(10, 6);
			case Pounds -> value / 454;
			default -> value;
		};
	}

	// Use Gramme as the base value
	@Override
	protected double toBaseValue() {
		return switch (getUnit()) {
			case Kilogramme -> value * Math.pow(10, 3); // 1kg = 1000g
			case Milligramme -> value / Math.pow(10, 3); // 1g = 1000000mg
			case Tonne -> value * Math.pow(10, 6); // 1ton = 1000000g
			case Pounds -> value * 454; // 1pound = 0.454kg = 454g
			default -> value;
		};
	}
	
}

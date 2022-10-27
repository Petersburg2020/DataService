package nx.peter.java.math.value;

import nx.peter.java.math.value.IValue.Value;

public class Force extends Value<Force.ForceUnit, Force> implements UnitLoad<Force.ForceUnit, Force> {

	public enum ForceUnit {
		Newton, KiloNewton
	}
	public Force() {
		this(0, ForceUnit.Newton);
	}
	
	public Force(IValue<ForceUnit, ? extends IValue> another) {
		super(another);
	}
	
	public Force(double value, ForceUnit unit) {
		super(value, unit);
	}

	@Override
	protected ForceUnit[] values() {
		return ForceUnit.values();
	}

	@Override
	public void reset() {
		unit = ForceUnit.Newton;
		value = 0;
	}

	@Override
	public String getUnitPrint() {
		return unit == ForceUnit.KiloNewton ? "kN" : "N";
	}

	@Override
	protected double fromBaseValueTo(ForceUnit unit, double value) {
		return unit == ForceUnit.KiloNewton ? value / 1000 : value;
	}

	@Override
	protected double toBaseValue() {
		return getUnit() == ForceUnit.KiloNewton ? value * 1000 : value;
	}

	@Override
	public Load getPointLoad() {
		return toLoad();
	}

	@Override
	public Force getUnitLoad() {
		return this;
	}

	@Override
	public boolean equals(UnitLoad<? extends Enum, ? extends UnitLoad> another) {
		return getPointLoad().isEquivalentTo(another.getPointLoad());
	}
	
	public Weight toWeight() {
		return new Weight(value, unit);
	}

	public Load toLoad() {
		return new Load(value, unit);
	}
	
}

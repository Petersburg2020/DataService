package nx.peter.java.math.value;

import nx.peter.java.math.value.Force.ForceUnit;
import nx.peter.java.math.value.IValue.Value;
import nx.peter.java.util.Random;

public class Load extends Value<ForceUnit, Load> implements UnitLoad<ForceUnit, Load> {
	public Load(IValue<ForceUnit, ? extends IValue> another) {
		super(another);
	}

	public Load(double value, ForceUnit unit) {
		super(value, unit);
	}

	@Override
	protected ForceUnit[] values() {
		return ForceUnit.values();
	}

	@Override
	protected ForceUnit randomUnit() {
		return ForceUnit.values()[Random.nextInt(0, ForceUnit.values().length)];
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
		return this;
	}

	@Override
	public Load getUnitLoad() {
		return this;
	}

	@Override
	public boolean equals(UnitLoad<? extends Enum, ? extends UnitLoad> another) {
		return getPointLoad().isEquivalentTo(another.getPointLoad());
	}
	
	
	public Force toForce() {
		return new Force(value, unit);
	}
	
	public Weight toWeight() {
		return new Weight(value, unit);
	}
}

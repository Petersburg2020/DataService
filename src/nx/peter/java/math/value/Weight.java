package nx.peter.java.math.value;

import nx.peter.java.math.value.Force.ForceUnit;
import nx.peter.java.math.value.IValue.Value;
import nx.peter.java.math.value.Mass.MassUnit;

public class Weight extends Value<ForceUnit, Weight> implements UnitLoad<ForceUnit, Weight> {
	
	public Weight() {
		this(0, ForceUnit.Newton);
	}
	
	public Weight(Mass mass) {
		this(new Mass(mass.getValue(), mass.getUnit()).convertTo(MassUnit.Kilogramme).getValue() * 9.8, ForceUnit.Newton);
	}

	public Weight(IValue<ForceUnit, ? extends IValue> another) {
		super(another);
	}

	public Weight(double value, ForceUnit unit) {
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
	public Weight getUnitLoad() {
		return this;
	}

	@Override
	public boolean equals(UnitLoad<? extends Enum, ? extends UnitLoad> another) {
		return getPointLoad().isEquivalentTo(another.getPointLoad());
	}

	public Force toForce() {
		return new Force(value, unit);
	}
	
	public Load toLoad() {
		return new Load(value, unit);
	}
	
}

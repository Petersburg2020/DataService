package nx.peter.java.math.value;

import nx.peter.java.math.value.IValue.Value;
import nx.peter.java.util.Random;

public class Energy extends Value<Energy.EnergyUnit, Energy> {

	public enum EnergyUnit {
		Joule, KiloJoule
	}

	public Energy() {
		this(0, EnergyUnit.Joule);
	}
	
	public Energy(Energy energy) {
		this(energy.getValue(), energy.getUnit());
	}
	
	public Energy(double value, EnergyUnit unit) {
		super(value, unit);
	}

	@Override
	protected EnergyUnit[] values() {
		return EnergyUnit.values();
	}

	@Override
	protected EnergyUnit randomUnit() {
		return EnergyUnit.values()[Random.nextInt(0, EnergyUnit.values().length)];
	}

	@Override
	public void reset() {
		unit = EnergyUnit.Joule;
		value = 0;
	}

	@Override
	public String getUnitPrint() {
		switch(unit) {
			case KiloJoule: return "kJ";
			default: return "J";
		}
	}

	@Override
	protected double fromBaseValueTo(EnergyUnit unit, double value) {
		return 0;
	}

	@Override
	protected double toBaseValue() {
		return 0;
	}
	
	
}

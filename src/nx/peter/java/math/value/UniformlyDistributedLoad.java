package nx.peter.java.math.value;

import nx.peter.java.math.value.Force.ForceUnit;
import nx.peter.java.math.value.Linear.LinearUnit;
import nx.peter.java.math.value.IValue.Value;


public class UniformlyDistributedLoad extends Value<UniformlyDistributedLoad.UDLUnit, UniformlyDistributedLoad> implements UnitLoad<UniformlyDistributedLoad.UDLUnit, UniformlyDistributedLoad> {
	public enum UDLUnit {
		KNPerM,
		KNPerKM,
		NPerM,
		NPerMM
	}

	protected Linear length;
	
	
	public UniformlyDistributedLoad() {
		super();
	}
	
	public UniformlyDistributedLoad(Value<ForceUnit, ? extends Value> point, Linear length) {
		this(from(point, length));
		setLength(length);
	}
	
	public UniformlyDistributedLoad(Value<UDLUnit, ? extends Value> another) {
		super(another);
	}

	public UniformlyDistributedLoad(double value, UDLUnit unit) {
		super(value, unit);
	}

	@Override
	public void reset() {
		unit = UDLUnit.NPerM;
		value = 0;
		length = new Linear();
	}

	@Override
	protected UDLUnit[] values() {
		return UDLUnit.values();
	}

	public UniformlyDistributedLoad set(IValue<ForceUnit, ? extends IValue> point, Linear length) {
		set(from(point, length));
		this.length = length;
		return this;
	}

	public UniformlyDistributedLoad setLength(Linear length) {
		this.length = length;
		return this;
	}

	public Linear getLength() {
		return new Linear(length);
	}
	
	public UniformlyDistributedLoad setPointLoad(Value<ForceUnit, ? extends Value> load) {
		set(load, length);
		return this;
	}
	
	public VaryingDistributedLoad getVDL() {
		return new VaryingDistributedLoad(this);
	}
	
	@Override
	public String getUnitPrint() {
		return switch (unit) {
			case NPerMM -> "N/mm";
			case KNPerM -> "kN/m";
			case KNPerKM -> "kN/km";
			default -> "N/m";
		};
	}

	@Override
	protected double fromBaseValueTo(UDLUnit unit, double value) {
		return switch (unit) {
			case NPerMM, KNPerM -> value / 1000;
			case KNPerKM, NPerM -> value;
		};
	}

	@Override
	protected double toBaseValue() {
		return switch (unit) {
			case NPerMM, KNPerM -> value / 1000;
			case KNPerKM, NPerM -> value;
		};
	}

	public Force toForce() {
		return new Force(getPointLoad());
	}

	public Weight toWeight() {
		return new Weight(getPointLoad());
	}
	
	public Load getPointLoad() {
		Linear len = new Linear(length);
		switch (unit) {
			case KNPerKM: 
				len.convertTo(LinearUnit.KiloMetre);
			case KNPerM: 
				if (!unit.equals(UDLUnit.KNPerKM))
					len.convertTo(LinearUnit.Metre);
				return new Load(len.multiply(value).getValue(), ForceUnit.KiloNewton);
			case NPerMM: 
				len.convertTo(LinearUnit.MilliMetre);
			default:
				if (!unit.equals(UDLUnit.NPerMM))
					len.convertTo(LinearUnit.Metre);
				return new Load(len.multiply(value).getValue(), ForceUnit.Newton);
		}
	}

	@Override
	public UniformlyDistributedLoad getUnitLoad() {
		return this;
	}

	@Override
	public boolean equals(UnitLoad<? extends Enum, ? extends UnitLoad> another) {
		return getPointLoad().isEquivalentTo(another.getPointLoad());
	}
	
	
	
	public static UniformlyDistributedLoad from(IValue<ForceUnit, ? extends IValue> load, Linear length) {
		load = new Load(load.getValue(), load.getUnit());
		Linear len = new Linear(length);

		if (load.getUnit() == ForceUnit.KiloNewton) {
			switch (len.getUnit()) {
				case Feet:
				case Inches:
				case Yard:
				case MilliMetre:
				case CentiMetre:
					len.convertTo(LinearUnit.Metre);
				case Metre:
					return new UniformlyDistributedLoad(load.divide(len.getValue()).getValue(), UDLUnit.KNPerM).setLength(length);
				case Mile:
					len.convertTo(LinearUnit.KiloMetre);
				default:
					return new UniformlyDistributedLoad(load.divide(len.getValue()).getValue(), UDLUnit.KNPerKM).setLength(length);
			}
		}
		switch (len.getUnit()) {
			case Feet:
			case Inches:
			case Yard:
			case CentiMetre:
				len.convertTo(LinearUnit.MilliMetre);
			case MilliMetre:
				return new UniformlyDistributedLoad(load.divide(len.getValue()).getValue(), UDLUnit.NPerMM).setLength(length);
			case Mile:
			case KiloMetre:
				len.convertTo(LinearUnit.Metre);
			default:
				return new UniformlyDistributedLoad(load.divide(len.getValue()).getValue(), UDLUnit.NPerM).setLength(length);
		}
	}

}

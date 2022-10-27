package nx.peter.java.math.value;

import nx.peter.java.math.value.Force.ForceUnit;
import nx.peter.java.math.value.IValue.Value;
import nx.peter.java.math.value.UniformlyDistributedLoad.UDLUnit;


public class VaryingDistributedLoad extends Value<UDLUnit, VaryingDistributedLoad> implements UnitLoad<UDLUnit, VaryingDistributedLoad> {
	protected Linear length;
	
	public VaryingDistributedLoad() {
		super();
	}
	
	public VaryingDistributedLoad(Value<UDLUnit, ? extends Value> another) {
		super(another);
	}
	
	public VaryingDistributedLoad(Value<ForceUnit, ? extends Value> point1, Value<ForceUnit, ? extends Value> point2, Linear length) {
		set(point1, point2, length);
	}
	
	public VaryingDistributedLoad(double value, UDLUnit unit) {
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

	public void set(Value<ForceUnit, ? extends Value> point1, Value<ForceUnit, ? extends Value> point2, Linear length) {
		set(from(point1, point2, length));
		setLength(length);
	}
	
	public VaryingDistributedLoad setLength(Linear length) {
		this.length = length;
		return this;
	}
	
	public Linear getLength() {
		return new Linear(length);
	}
	
	@Override
	public Load getPointLoad() {
		return new UniformlyDistributedLoad(this).setLength(length).getPointLoad();
	}
	
	public UniformlyDistributedLoad toUDL() {
		return new UniformlyDistributedLoad(this);
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

	@Override
	public VaryingDistributedLoad getUnitLoad() {
		return this;
	}

	@Override
	public boolean equals(UnitLoad<? extends Enum, ? extends UnitLoad> another) {
		return getPointLoad().isEquivalentTo(another.getPointLoad());
	}
	
	
	
	
	public static VaryingDistributedLoad from(IValue<ForceUnit, ? extends IValue> point1, Value<ForceUnit, ? extends Value> point2, Linear length) {
		return new VaryingDistributedLoad(UniformlyDistributedLoad.from(new Load(point1).average(point2), length));
	}
	
}

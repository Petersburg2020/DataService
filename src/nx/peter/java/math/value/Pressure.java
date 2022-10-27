package nx.peter.java.math.value;

import nx.peter.java.math.value.Area.AreaUnit;
import nx.peter.java.math.value.Force.ForceUnit;
import nx.peter.java.math.value.IValue.Value;

public class Pressure extends Value<Pressure.PressureUnit, Pressure> {
	public enum PressureUnit {
		Pascal,
		KiloPascal,
		MegaPascal,
		AtmosphericPressure,
		MillimetersOfMercury,
		NPerSqMM,
		kNPerSqM,
		kNPerSqkM
	}
	public Pressure() {
		super();
	}
	
	public Pressure(Value<ForceUnit, ? extends Value> force, Area area) {
		super(from(force, area));
	}
	
	public Pressure(Value<PressureUnit, ? extends Value> another) {
		super(another);
	}

	public Pressure(double value, PressureUnit unit) {
		super(value, unit);
	}

	@Override
	protected PressureUnit[] values() {
		return PressureUnit.values();
	}

	@Override
	public void reset() {
		value = 0;
		unit = PressureUnit.Pascal;
	}

	@Override
	public String getUnitPrint() {
		return switch (unit) {
			case AtmosphericPressure -> "atm";
			case MillimetersOfMercury -> "mmHg";
			case NPerSqMM -> "N/mm²";
			case kNPerSqM -> "kN/m²";
			case kNPerSqkM -> "kN/km²";
			case MegaPascal -> "MPa";
			case KiloPascal -> "kPa";
			default -> "Pa";
		};
	}

	@Override
	protected double fromBaseValueTo(PressureUnit unit, double value) {
		return switch (unit) {
			case AtmosphericPressure -> value / 101325;
			case MillimetersOfMercury -> (value / 101325) * 760;
			case NPerSqMM, kNPerSqM, MegaPascal -> value / Math.pow(1000, 2);
			case KiloPascal -> value / 1000;
			default -> value;
		};
	}

	@Override
	protected double toBaseValue() {
		return switch (unit) {
			case AtmosphericPressure -> value * 101325;
			case MillimetersOfMercury -> (value * 101325) / 760;
			case NPerSqMM, kNPerSqM, MegaPascal -> value * Math.pow(1000, 2);
			case KiloPascal -> value * 1000;
			default -> value;
		};
	}

	
	public static Pressure from(Value<ForceUnit, ? extends Value> force, Area area) {
		Force f = new Force(force);
		Area a = new Area(area);

		if (force.getUnit() == ForceUnit.KiloNewton) {
			switch (area.getUnit()) {
				case SquareFoot:
				case SquareInches:
				case SquareYard:
				case SquareMillimeter:
				case SquareCentimeter:
					a.convertTo(AreaUnit.SquareMeter);
				case SquareMeter:
					return new Pressure(f.getValue() / a.getValue(), PressureUnit.kNPerSqM);
				case SquareMile:
				case Hectare:
				case Acre:
					a.convertTo(AreaUnit.SquareKilometer);
				default:
					return new Pressure(f.getValue() / a.getValue(), PressureUnit.kNPerSqkM);
			}
		} else {
			switch (area.getUnit()) {
				case SquareFoot:
				case SquareInches:
				case SquareCentimeter:
					a.convertTo(AreaUnit.SquareMillimeter);
				case SquareMillimeter:
					return new Pressure(f.getValue() / a.getValue(), PressureUnit.NPerSqMM);
				case SquareMile:
				case Hectare:
				case Acre:
				case SquareYard:
				case SquareKilometer:
					a.convertTo(AreaUnit.SquareMeter);
				default:
					return new Pressure(f.getValue() / a.getValue(), PressureUnit.Pascal);
			}
		}
	}
	
	
}

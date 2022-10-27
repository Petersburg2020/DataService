package nx.peter.java.math.value;

import nx.peter.java.math.value.Force.ForceUnit;
import nx.peter.java.math.value.Pressure.PressureUnit;
import nx.peter.java.math.value.IValue.Value;

public class Strength extends Value<PressureUnit, Strength> {
	public enum Form {
		Compresive, Tensile, Shear, None
	}
	
	protected Form form;
	
	public Strength() {
		super();
	}
	
	public Strength(Value<ForceUnit, ? extends Value> force, Area area) {
		super(from(force, area));
	}
	
	public Strength(Value<PressureUnit, ? extends Value> another) {
		super(another);
	}

	public Strength(double value, PressureUnit unit) {
		super(value, unit);
	}

	@Override
	public void reset() {
		value = 0;
		unit = PressureUnit.Pascal;
		form = Form.None;
	}

	@Override
	protected PressureUnit[] values() {
		return PressureUnit.values();
	}

	public Strength setForm(Form form) {
		this.form = form != null ? form : Form.None;
		return this;
	}

	public Form getForm() {
		return form;
	}
	
	@Override
	public String getUnitPrint() {
		switch(unit) {
			case AtmosphericPressure: return "atm";
			case MillimetersOfMercury: return "mmHg";
			case NPerSqMM: return "N/mm²";
			case kNPerSqM: return "kN/m²";
			case kNPerSqkM: return "kN/km²";
			case MegaPascal: return "MPa";
			case KiloPascal: return "kPa";
			default: return "Pa";
		}
	}

	@Override
	protected double fromBaseValueTo(PressureUnit unit, double value) {
		switch(unit) {
			case AtmosphericPressure: return value / 101325;
			case MillimetersOfMercury: return (value / 101325) * 760;
			case NPerSqMM: return value / Math.pow(1000, 2);
			case kNPerSqM: return value / Math.pow(1000, 2);
			case kNPerSqkM: return value;
			case MegaPascal: return value / Math.pow(1000, 2);
			case KiloPascal: return value / 1000;
			default: return value;
		}
	}

	@Override
	protected double toBaseValue() {
		switch(unit) {
			case AtmosphericPressure: return value * 101325;
			case MillimetersOfMercury: return (value * 101325) / 760;
			case NPerSqMM: return value * Math.pow(1000, 2);
			case kNPerSqM: return value * Math.pow(1000, 2);
			case kNPerSqkM: return value;
			case MegaPascal: return value * Math.pow(1000, 2);
			case KiloPascal: return value * 1000;
			default: return value;
		}
	}


	public static Strength from(Value<ForceUnit, ? extends Value> force, Area area) {
		return new Strength(Pressure.from(force, area));
	}
	
	
}

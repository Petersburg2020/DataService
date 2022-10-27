package nx.peter.java.math.value;

import nx.peter.java.math.value.IValue.Value;
import nx.peter.java.math.value.Mass.MassUnit;
import nx.peter.java.math.value.Volume.VolumeUnit;

public class Density extends Value<Density.DensityUnit, Density> {
	public enum DensityUnit {
		KgPerCubM, // Kilogram per cubic meter (kg/m³)
		KgPerCubMM, // Kilogram per cubic millimeter (kg/mm³)
		TonPerCubKM, // Tonne per cubic kilometer (Tonne/km³)
		GmPerCubMM, // Gram per cubic millimeter (g/mm³)
		TonPerCubM, // Tonne per cubic meter (Tonne/m³)
		GmPerCubM // Gram per cubic meter (g/m³)
	}

	public Density() {
		super(0, DensityUnit.KgPerCubM);
	}
	
	public Density(Mass mass, Volume volume) {
		this(from(mass, volume));
	}
	
	public Density(Density another) {
		this(another.getValue(), another.getUnit());
	}
	
	public Density(double value, DensityUnit unit) {
		super(value, unit);
	}

	@Override
	protected DensityUnit[] values() {
		return DensityUnit.values();
	}

	@Override
	public void reset() {
		unit = DensityUnit.KgPerCubM;
		value = 0;
	}

	@Override
	public String getUnitPrint() {
		return getMassUnitPrint() + "/" + getVolumeUnitPrint();
	}
	
	public MassUnit getMassUnit() {
		return switch (unit) {
			case TonPerCubM, TonPerCubKM -> MassUnit.Tonne;
			case GmPerCubMM, GmPerCubM -> MassUnit.Gramme;
			case KgPerCubMM, KgPerCubM -> MassUnit.Kilogramme;
		};
	}

	public VolumeUnit getVolumeUnit() {
		return switch (unit) {
			case KgPerCubMM, GmPerCubMM -> VolumeUnit.CubicMillimeter;
			case TonPerCubKM -> VolumeUnit.CubicKilometer;
			case GmPerCubM, KgPerCubM, TonPerCubM -> VolumeUnit.CubicMeter;
		};
	}
	
	public String getVolumeUnitPrint() {
		return switch (unit) {
			case KgPerCubMM, GmPerCubMM -> "mm³";
			case TonPerCubKM -> "km³";
			case GmPerCubM, KgPerCubM, TonPerCubM -> "m³";
		};
	}
	
	public String getMassUnitPrint() {
		return switch (unit) {
			case GmPerCubM, GmPerCubMM -> "g";
			case TonPerCubM, TonPerCubKM -> "tons";
			case KgPerCubMM, KgPerCubM -> "kg";
		};
	}
	
	public Mass getMass(double volume, VolumeUnit unit) {
		return getMass(new Volume(volume, unit));
	}
	
	public Mass getMass(Volume volume) {
		if (new Mass(value * new Volume(volume).convertTo(getVolumeUnit()).getValue(), getMassUnit()).isZero() && !volume.isZero()) {
			Density density = new Density(this).convertTo(DensityUnit.GmPerCubMM);
			return new Mass(value * new Volume(volume).convertTo(density.getVolumeUnit()).getValue(), density.getMassUnit());
		}
		return new Mass(value * new Volume(volume).convertTo(getVolumeUnit()).getValue(), getMassUnit());
	}
	
	public Volume getVolume(double mass, MassUnit unit) {
		return getVolume(new Mass(mass, unit));
	}
	
	public Volume getVolume(Mass mass) {
		return new Volume(mass.convertTo(getMassUnit()).getValue() / value, getVolumeUnit());
	}

	@Override
	protected double fromBaseValueTo(DensityUnit unit, double value) {
		return switch (unit) { // Converts cubic meter to cubic millimeter only
			case KgPerCubMM, TonPerCubM -> // Converts kilogramme to tonne only
					value / Math.pow(1000, 3); // divide original value by 1000
			case TonPerCubKM -> // Converts cubic meter to cubic kilometer and kilogramme to tonne
					value; // multiply original value by 1000 and divide by 1000
			case GmPerCubM -> // Converts kilogramme to gramme only
					value * Math.pow(1000, 3); // multiply original value by 1000
			case GmPerCubMM -> // Converts kilogramme to gramme and cubic meter to cubic millimeter
					value; // multiply original value by 1000 and divide by 1000
			// multiply original value by 1000
			default -> value;
		};
	}
	
	// Using Kg/m³ as base
	@Override
	protected double toBaseValue() {
		return switch (getUnit()) { // Converts cubic millimeter to cubic meter only
			case KgPerCubMM, TonPerCubM -> // Converts tonne to kilogramme only
					value * Math.pow(1000, 3); // multiply original value by 1000
			case TonPerCubKM -> // Converts cubic kilometer to cubic meter and tonne to kilogramme
					value; // multiply original value by 1000 and divide by 1000
			case GmPerCubM -> // Converts gramme to kilogramme only
					value / Math.pow(1000, 3); // divide original value by 1000
			case GmPerCubMM -> // Converts gramme to kilogramme and cubic millimeter to cubic meter
					value; // multiply original value by 1000 and divide by 1000
			default -> value;
		};
	}

	
	
	public static Density from(Mass mass, Volume volume) {
		double value = mass.getValue()/volume.getValue();
		DensityUnit unit = DensityUnit.KgPerCubM;
		
		switch(mass.getUnit()) {
			case Kilogramme:
				switch(volume.getUnit()) {
					case CubicMeter:
						break;
					case CubicKilometer:
					case Litre:
						value = mass.getValue()/volume.convertTo(VolumeUnit.CubicMeter).getValue();
						break;
					case CubicMillimeter:
						unit = DensityUnit.KgPerCubMM;
						break;
					case CubicCentimeter:
					case MilliLitre:
						unit = DensityUnit.KgPerCubMM;
						value = mass.getValue()/volume.convertTo(VolumeUnit.CubicMillimeter).getValue();
						break;
				}
				break;
			case Gramme:
				switch (volume.getUnit()) {
					case CubicMeter -> unit = DensityUnit.GmPerCubM;
					case CubicKilometer, Litre -> {
						unit = DensityUnit.GmPerCubM;
						value = mass.getValue() / volume.convertTo(VolumeUnit.CubicMeter).getValue();
					}
					case CubicMillimeter -> unit = DensityUnit.GmPerCubMM;
					case CubicCentimeter -> {
						unit = DensityUnit.GmPerCubMM;
						value = mass.getValue() / volume.convertTo(VolumeUnit.CubicMillimeter).getValue();
					}
					case MilliLitre -> {
						unit = DensityUnit.KgPerCubMM;
						value = mass.getValue() / volume.convertTo(VolumeUnit.CubicMillimeter).getValue();
					}
				}
				break;
			case Milligramme:
				switch (volume.getUnit()) {
					case CubicMeter -> {
						unit = DensityUnit.GmPerCubM;
						value = mass.convertTo(MassUnit.Gramme).getValue() / volume.getValue();
					}
					case CubicKilometer, Litre -> {
						unit = DensityUnit.GmPerCubM;
						value = mass.convertTo(MassUnit.Gramme).getValue() / volume.convertTo(VolumeUnit.CubicMeter).getValue();
					}
					case CubicMillimeter -> {
						unit = DensityUnit.GmPerCubMM;
						value = mass.convertTo(MassUnit.Gramme).getValue() / volume.getValue();
					}
					case CubicCentimeter -> {
						unit = DensityUnit.GmPerCubMM;
						value = mass.convertTo(MassUnit.Gramme).getValue() / volume.convertTo(VolumeUnit.CubicMeter).getValue();
					}
					case MilliLitre -> {
						unit = DensityUnit.GmPerCubMM;
						value = mass.convertTo(MassUnit.Gramme).getValue() / volume.convertTo(VolumeUnit.CubicMillimeter).getValue();
					}
				}
				break;
			case Tonne:
				switch (volume.getUnit()) {
					case CubicMeter -> unit = DensityUnit.TonPerCubM;
					case CubicKilometer, CubicCentimeter, MilliLitre, Litre -> {
						unit = DensityUnit.TonPerCubM;
						value = mass.getValue() / volume.convertTo(VolumeUnit.CubicMeter).getValue();
					}
					case CubicMillimeter -> {
						unit = DensityUnit.TonPerCubM;
						value = mass.getValue() / volume.convertTo(VolumeUnit.CubicKilometer).getValue();
					}
				}
				break;
			case Pounds:
				switch (volume.getUnit()) {
					case CubicMeter -> value = mass.convertTo(MassUnit.Kilogramme).getValue() / volume.getValue();
					case CubicKilometer, Litre -> value = mass.convertTo(MassUnit.Kilogramme).getValue() / volume.convertTo(VolumeUnit.CubicMeter).getValue();
					case CubicMillimeter -> {
						unit = DensityUnit.KgPerCubMM;
						value = mass.convertTo(MassUnit.Kilogramme).getValue() / volume.getValue();
					}
					case CubicCentimeter -> {
						unit = DensityUnit.KgPerCubMM;
						value = mass.convertTo(MassUnit.Kilogramme).getValue() / volume.convertTo(VolumeUnit.CubicMeter).getValue();
					}
					case MilliLitre -> {
						unit = DensityUnit.KgPerCubMM;
						value = mass.convertTo(MassUnit.Kilogramme).getValue() / volume.convertTo(VolumeUnit.CubicMillimeter).getValue();
					}
				}
				break;
		}
		return new Density(value, unit);
	}
}



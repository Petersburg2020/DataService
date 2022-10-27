package nx.peter.java.math.value;

import nx.peter.java.math.shape.solid.Cone.ConeState;
import nx.peter.java.math.value.IValue.Value;
import nx.peter.java.math.value.Linear.LinearUnit;

public class Area extends Value<Area.AreaUnit, Area> {

	public enum AreaUnit {
		SquareMeter, // m²
		SquareCentimeter, // cm²
		SquareMillimeter, // mm²
		SquareKilometer, // km²
		SquareMile, // mile²
		SquareFoot, // ft²
		SquareInches, // inch²
		SquareYard, // yrd²
		Acre, // acre
		Hectare // htrs
	}

	public Area() {
		this(0, AreaUnit.SquareMeter);
	}
	
	public Area(Area area) {
		this(area.getValue(), area.getUnit());
	}

	// Area of rectangle
	public Area(Linear length, Linear width) {
		this(new Linear(length).multiply(new Linear(width.getValue(), width.getUnit()).convertTo(length.getUnit())).getValue(), getAreaUnit(length.getUnit()));
	}

	// Area of cuboid
	public Area(Linear length, Linear width, Linear depth) {
		this(new Linear(length.getValue(), length.getUnit()).multiply(width).add(new Linear(length.getValue(), length.getUnit()).multiply(depth)).add(new Linear(width.getValue(), width.getUnit()).multiply(depth)).getValue(), getAreaUnit(length.getUnit()));
	}
	
	// Area of circle
	public Area(Linear radius) {
		this(new Linear(radius).pow(2).multiply(Math.PI).getValue(), getAreaUnit(radius.getUnit()));
	}

	public Area(double value, AreaUnit unit) {
		super(value, unit);
	}

	@Override
	protected AreaUnit[] values() {
		return new AreaUnit[0];
	}

	@Override
	public void reset() {
		value = 0;
		unit = AreaUnit.SquareMeter;
	}

	// Volume of cuboid
	public void set(Linear length, Linear width, Linear depth) {
		set(length.multiply(width).multiply(depth).convertTo(LinearUnit.Metre).getValue(), AreaUnit.SquareMeter);
	}

	// Volume of cylinder
	public void set(Linear radius, Linear depth) {
		set(radius.pow(2).multiply(Math.PI).multiply(depth).convertTo(LinearUnit.Metre).getValue(), AreaUnit.SquareMeter);
	}

	// Volume of sphere
	public void set(Linear radius) {
		set(radius.pow(3).multiply(Math.PI).convertTo(LinearUnit.Metre).getValue(), AreaUnit.SquareMeter);
	}


	@Override
	public String getUnitPrint() {
		return switch (getUnit()) {
			case SquareYard -> "yd²";
			case SquareMillimeter -> "mm²";
			case SquareCentimeter -> "cm²";
			case SquareKilometer -> "km²";
			case SquareInches -> "in²";
			case SquareFoot -> "ft²";
			case SquareMile -> "mi²";
			case Hectare -> "ha";
			case Acre -> "ac";
			default -> "m²";
		};
	}

	@Override
	protected double fromBaseValueTo(AreaUnit unit, double value) {
		return switch (unit) {
			case SquareMillimeter -> value * Math.pow(1000, 2);
			case SquareCentimeter -> value * Math.pow(100, 2);
			case SquareKilometer -> value / Math.pow(1000, 2);
			case SquareMile -> value / 2589988.11;
			case SquareInches -> value * 1550;
			case SquareYard -> value * 1.19599;
			case SquareFoot -> value * 10.7639;
			case Acre -> value / 4046.856;
			case Hectare -> value / Math.pow(10, 4);
			default -> value;
		};
	}

	// Use SquareMeter (m²) as base
	@Override
	protected double toBaseValue() {
		return switch (unit) {
			case SquareMillimeter -> value / Math.pow(1000, 2);
			case SquareCentimeter -> value / Math.pow(100, 2);
			case SquareKilometer -> value * Math.pow(1000, 2);
			case SquareMile -> value * 2589988.11;
			case SquareInches -> value / 1550;
			case SquareYard -> value / 1.19599;
			case SquareFoot -> value / 10.7639;
			case Acre -> value * 4046.856;
			case Hectare -> value * Math.pow(10, 4);
			default -> value;
		};
	}
	
	
	
	
	
	public static Area fromSquare(Linear length) {
		return fromRectangle(length, length); // l²
	}

	public static Area fromRectangle(Linear length, Linear width) {
		return new Area(length, width); // lw
	}

	public static Area fromCircle(Linear radius) {
		return convertTo(new Area(radius), radius); // πr²
	}
	
	public static Area fromCylinder(Linear radius, Linear depth) {
		Area a = fromCircle(radius).multiply(2).add(fromRectangle(new Linear(Perimeter.fromCircle(radius).getValue(), radius.getUnit()), depth));
		return convertTo(a, radius); // 2πr² + 2πrh => Area of circle + (Perimeter of Circle * Depth)
	}

	public static Area fromCone(Linear radius, Linear depth, ConeState state) {
		state = state != null ? state : ConeState.Closed;
		// 2πr² + πrl OR {2πr² + πr√(h² + r²)} => Area of circle + (Perimeter of Circle * Slant Height)/2  ---> Closed
		Area a = fromCircle(radius).multiply(2).add(new Linear(radius).multiply(new Linear(depth)).multiply(Math.PI).getValue());
		if (state.equals(ConeState.Opened))
		// πrl OR {πr√(h² + r²)} => (Perimeter of Circle * Slant Height)/2  ---> Opened
			a = new Area(new Linear(radius).multiply(new Linear(depth).pow(2).add(new Linear(radius).pow(2))).sqrt().multiply(Math.PI).getValue(), getAreaUnit(radius.getUnit()));
		return convertTo(a, radius); 
	}
	
	public static Area fromSphere(Linear radius) {
		return fromCircle(radius).multiply(4).divide(3); // (4/3)πr² => (4/3) Area of circle
	}
	
	public static Area fromCuboid(Linear length, Linear width, Linear depth) {
		return convertTo(new Area(length, width, depth), length); // lwd
	}
	
	public static Area fromSector(Linear radius, double angleInDegrees) {
		Area a = new Area(radius).multiply(angleInDegrees).divide(360);
		return convertTo(a, radius); // (∆/360)πr² = (∆/360) * Area of Circle
	}
	
	private static Area convertTo(Area a, Linear linear) {
		return a.convertTo(getAreaUnit(linear.getUnit()));
	}
	
	public static AreaUnit getAreaUnit(LinearUnit unit) {
		return switch (unit) {
			case Yard -> AreaUnit.SquareYard;
			case Inches -> AreaUnit.SquareInches;
			case MilliMetre -> AreaUnit.SquareMillimeter;
			case CentiMetre -> AreaUnit.SquareCentimeter;
			case KiloMetre -> AreaUnit.SquareKilometer;
			case Feet -> AreaUnit.SquareFoot;
			case Mile -> AreaUnit.SquareMile;
			default -> AreaUnit.SquareMeter;
		};
	}
	
}

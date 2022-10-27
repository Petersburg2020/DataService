package nx.peter.java.math.value;

public interface UnitLoad<U extends Enum, L extends UnitLoad> extends IValue<U, L> {
	Load getPointLoad();
	L getUnitLoad();
	boolean equals(UnitLoad<? extends Enum, ? extends UnitLoad> another);
}

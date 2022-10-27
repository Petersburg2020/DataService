package nx.peter.java.math.value;

import nx.peter.java.math.value.IValue.Value;
import nx.peter.java.util.Util;

public class Money extends Value<Money.Currency, Money> {
	public enum Currency {
		USD,
		Euro,
		Pounds,
		Naira,
		Yen
	}

	public Money() {
		super();
	}
	
	public Money(IValue<Currency, ? extends IValue> another) {
		super(another);
	}
	
	public Money(double value, Currency unit) {
		super(value, unit);
	}

	@Override
	public void reset() {
		value = 0;
		unit = Currency.USD;
	}

	@Override
	public double getValue() {
		return Util.toNDecimalPlace(super.getValue(), 2);
	}

	@Override
	protected Currency[] values() {
		return Currency.values();
	}

	@Override
	public String getUnitPrint() {
		switch (unit) {
			case Pounds: return "£";
			case Euro: return "€";
			case Yen: return "¥";
			case Naira: return "₦";
			default: return "$";
		}
	}

	@Override
	protected double fromBaseValueTo(Currency unit, double value) {
		switch (unit) {
			case Pounds: return value / 1.36;
			case Euro: return value / 1.14;
			case Yen: return value * 114.98;
			case Naira: return value * 416.04;
			default: return value;
		}
	}

	// Use USD as base unit
	@Override
	protected double toBaseValue() {
		switch (unit) {
			case Pounds: return value * 1.36;
			case Euro: return value * 1.14;
			case Yen: return value / 114.98;
			case Naira: return value / 416.04;
			default: return value;
		}
	}

	@Override
	public String toString() {
		return getUnitPrint() + getValue();
	}
	
	public String getFormattedValue() {
		return getUnitPrint() + format();
	}
	
	protected String format() {
		return "";
	}
}

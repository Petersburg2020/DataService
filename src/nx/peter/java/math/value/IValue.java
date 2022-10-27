package nx.peter.java.math.value;

import nx.peter.java.util.Fraction;
import nx.peter.java.util.Percent;
import nx.peter.java.util.Random;
import nx.peter.java.util.Util;
import nx.peter.java.math.value.Angle.AngleUnit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface IValue<U extends Enum, I extends IValue> {
    void reset();
    I set(double value, U unit);
    I set(IValue<U, ? extends IValue> another);
    I setUnit(U unit);
    I setValue(double value);
    I sin(Angle angle);
    I sin(double value, AngleUnit unit);
    I cos(Angle angle);
    I cos(double value, AngleUnit unit);
    I tan(Angle angle);
    I tan(double value, AngleUnit unit);
    I add(double value);
    I add(IValue<U, ? extends IValue> another);
    I subtract(double value);
    I subtract(IValue<U, ? extends IValue> another);
    I multiply(double value);
    I multiply(IValue<U, ? extends IValue> another);
    I divide(double value);
    I divide(IValue<U, ? extends IValue> another);
    I convertTo(U unit);
    I pow(double power);
    I log();
    I sqrt();
    I round();
    I random();
    I random(U unit);
    I average(IValue<U, ? extends IValue> another);
    I getPercent(double percent);
    Percent getPercent(IValue<U, ? extends IValue> another);
    I getRatio(double ratio);
    Fraction getRatio(IValue<U, ? extends IValue> another);
    IValue<U, ? extends IValue> max(IValue<U, ? extends IValue> another);
    IValue<U, ? extends IValue> min(IValue<U, ? extends IValue> another);
    IValue<U, ? extends IValue> mean(IValue<U, ? extends IValue>... others);
    IValue<U, ? extends IValue> mean(List<IValue<U, ? extends IValue>> others);
    IValue<U, ? extends IValue> median(IValue<U, ? extends IValue>... others);
    IValue<U, ? extends IValue> median(List<IValue<U, ? extends IValue>> others);
    IValue<U, ? extends IValue> mode(IValue<U, ? extends IValue>... others);
    IValue<U, ? extends IValue> mode(List<IValue<U, ? extends IValue>> others);
    List<IValue<U, ? extends IValue>> sort(List<IValue<U, ? extends IValue>> values);
    boolean isZero();
    boolean isInfinity();
    double getValue();
    double getWholeValue();
    String getName();
    U getUnit();
    String getUnitPrint();
    I getRatio(Fraction ratio);
    U getInitialUnit();
    boolean isGreaterThan(IValue<U, ? extends IValue> another);
    boolean isLessThan(IValue<U, ? extends IValue> another);
    boolean isLessThanOrEqualTo(IValue<U, ? extends IValue> another);
    boolean isGreaterThanOrEqualTo(IValue<U, ? extends IValue> another);
    boolean isEquivalentTo(IValue<U, ? extends IValue> another);
    boolean isSameType(IValue<? extends Enum, ? extends IValue> another);
    boolean equals(IValue<? extends Enum, ? extends IValue> another);


    abstract class Value<U extends Enum, V extends Value> implements IValue<U, V> {
        protected double value;
        protected U unit, initial;

        public Value() {
            reset();
        }

        public Value(IValue<U, ? extends IValue> another) {
            set(another);
        }

        public Value(double value, U unit) {
            set(value, unit);
        }

        @Override
        public V set(double value, U unit) {
            reset();
            if (unit != null)
                this.unit = unit;
            return setValue(value);
        }

        @Override
        public V set(IValue<U, ? extends IValue> another) {
            return another != null ? set(another.getValue(), another.getUnit()) : (V) this;
        }

        @Override
        public V setUnit(U unit) {
            if (unit != null)
                this.unit = unit;
            return convertTo(unit);
        }

        @Override
        public double getValue() {
            return Util.toNDecimalPlace(value, 4);
        }

        @Override
        public double getWholeValue() {
            return value;
        }

        @Override
        public U getUnit() {
            return unit;
        }

        @Override
        public boolean isZero() {
            return value == 0;
        }

        @Override
        public boolean isInfinity() {
            return Double.isInfinite(value);
        }

        @Override
        public V setValue(double value) {
            this.value = value;
            return (V) this;
        }

        @Override
        public V convertTo(U unit) {
            if (unit != null) {
                try {
                    value = fromBaseValueTo(unit, toBaseValue());
                } catch (NumberFormatException e) {
                    value = 0;
                }
                this.unit = unit;
            }
            return (V) this;
        }

        @Override
        public U getInitialUnit() {
            return initial;
        }


        // Arithmetic Operations
        @Override
        public V add(double value) {
            return setValue(this.value + value);
        }

        @Override
        public V add(IValue<U, ? extends IValue> another) {
            return another != null ? add(another.convertTo(unit).getValue()) : (V) this;
        }

        @Override
        public V subtract(double value) {
            setValue(this.value - value);
            return (V) this;
        }

        @Override
        public V subtract(IValue<U, ? extends IValue> another) {
            return another != null ? subtract(another.convertTo(unit).getValue()) : (V) this;
        }

        @Override
        public V multiply(double value) {
            return setValue(this.value * value);
        }

        @Override
        public V multiply(IValue<U, ? extends IValue> another) {
            return another != null ? multiply(another.convertTo(unit).getValue()) : (V) this;
        }

        @Override
        public V divide(double value) {
            return setValue(this.value / value);
        }

        @Override
        public V divide(IValue<U, ? extends IValue> another) {
            return another != null ? divide(another.convertTo(unit).getValue()) : (V) this;
        }

        @Override
        public V sqrt() {
            return setValue(Math.sqrt(value));
        }

        @Override
        public V pow(double power) {
            return setValue(Math.pow(value, power));
        }

        @Override
        public V log() {
            return setValue(Math.log(value));
        }

        @Override
        public IValue<U, ? extends IValue> min(IValue<U, ? extends IValue> another) {
            IValue<U, ? extends IValue> temp = another;
            if (isLessThanOrEqualTo(temp))
                return this;
            else return another;
        }

        @Override
        public IValue<U, ? extends IValue> max(IValue<U, ? extends IValue> another) {
            IValue<U, ? extends IValue> temp = another;
            if (isGreaterThanOrEqualTo(temp))
                return this;
            else return another;
        }

        @Override
        public V round() {
            return setValue(Math.round(value));
        }

        @Override
        public Percent getPercent(IValue<U, ? extends IValue> another) {
            IValue<U, ? extends IValue> temp = another;
            return new Percent(temp != null ? temp.convertTo(unit).getValue() : 0, value);
        }

        @Override
        public V getPercent(double percent) {
            return setValue(new Percent(percent).getPercentageOf(value));
        }

        @Override
        public V getRatio(double ratio) {
            IValue<U, V> temp = this;
            return temp.multiply(ratio);
        }

        @Override
        public V getRatio(Fraction ratio) {
            return getRatio(ratio.getDecimal());
        }

        @Override
        public Fraction getRatio(IValue<U, ? extends IValue> another) {
            if (another != null)
                return new Fraction(getWholeValue() / another.convertTo(unit).getWholeValue());
            return new Fraction(getWholeValue(), getWholeValue());
        }

        @Override
        public V random() {
            return random(randomUnit());
        }

        public V random(double min, double max) {
            return (V) setValue(Random.nextDouble(min, max)).setUnit(randomUnit());
        }

        protected abstract U[] values();

        protected U randomUnit() {
            return values()[Random.nextInt(values().length)];
        }

        @Override
        public V random(U unit) {
            setValue((value > 0 ? value : 1) * Random.nextDouble());
            setUnit(unit);
            return (V) this;
        }


        // Trigonometry
        @Override
        public V sin(Angle angle) {
            return setValue(value * angle.sine());
        }

        @Override
        public V cos(Angle angle) {
            return setValue(value * angle.cosine());
        }

        @Override
        public V sin(double value, AngleUnit unit) {
            return sin(new Angle(value, unit));
        }

        @Override
        public V cos(double value, AngleUnit unit) {
            return cos(new Angle(value, unit));
        }

        @Override
        public V tan(double value, AngleUnit unit) {
            return tan(new Angle(value, unit));
        }

        @Override
        public V tan(Angle angle) {
            return setValue(value * angle.tangent());
        }


        // Statistics
        @Override
        public V average(IValue<U, ? extends IValue> another) {
            if (another != null)
                add(another).divide(2);
            return (V) this;
        }

        @SafeVarargs
        public final IValue<U, ? extends IValue> mean(IValue<U, ? extends IValue>... others) {
            return mean(Arrays.asList(others));
        }

        @Override
        public IValue<U, ? extends IValue> mean(List<IValue<U, ? extends IValue>> others) {
            if (others != null && !others.isEmpty()) {
                Value<U, ? extends Value> total = this;
                for (IValue<U, ? extends IValue> other : others)
                    total.add(other);
                total.divide(others.size() + 1);
            }
            return this;
        }

        @SafeVarargs
        @Override
        public final IValue<U, ? extends IValue> mode(IValue<U, ? extends IValue>... others) {
            return mode(Arrays.asList(others));
        }

        @Override
        public IValue<U, ? extends IValue> mode(List<IValue<U, ? extends IValue>> others) {
            if (others != null && !others.isEmpty()) {
                IValue<U, ? extends IValue> greatest = this;

                for (IValue<U, ? extends IValue> other : others)
                    greatest = greatest.max(other);
                return greatest;
            }
            return this;
        }

        @SafeVarargs
        @Override
        public final IValue<U, ? extends IValue> median(IValue<U, ? extends IValue>... others) {
            return median(Arrays.asList(others));
        }

        @Override
        public IValue<U, ? extends IValue> median(List<IValue<U, ? extends IValue>> others) {
            others = sort(others);
            if (Util.isOdd(others.size())) {
                int middle = Math.round(others.size()/2.0f);
                return others.get(middle);
            } else {
                int first = others.size()/2;
                int second = first + 1;
                return others.get(first).average(others.get(second));
            }
        }

        @Override
        public List<IValue<U, ? extends IValue>> sort(List<IValue<U, ? extends IValue>> values) {
            if (values != null && !values.isEmpty()) {
                values.sort((first, second) -> first.isLessThan(second) ? -1 : (first.isGreaterThan(second)) ? 1 : 0);
                return values;
            }
            return new ArrayList<>();
        }


        // Conditional Operations
        @Override
        public boolean isGreaterThan(IValue<U, ? extends IValue> another) {
            IValue<U, ? extends IValue> temp = another;
            return temp != null && value > temp.convertTo(unit).getWholeValue();
        }

        @Override
        public boolean isGreaterThanOrEqualTo(IValue<U, ? extends IValue> another) {
            IValue<U, ? extends IValue> temp = another;
            return temp != null && value >= temp.convertTo(unit).getWholeValue();
        }

        @Override
        public boolean isLessThan(IValue<U, ? extends IValue> another) {
            IValue<U, ? extends IValue> temp = another;
            return temp != null && value < temp.convertTo(unit).getWholeValue();
        }

        @Override
        public boolean isLessThanOrEqualTo(IValue<U, ? extends IValue> another) {
            IValue<U, ? extends IValue> temp = another;
            return temp != null && value <= temp.convertTo(unit).getWholeValue();
        }

        @Override
        public boolean equals(IValue<? extends Enum, ? extends IValue> another) {
            IValue<? extends Enum, ? extends IValue> temp = another;
            return temp != null && unit.equals(temp.getUnit()) && value == temp.getWholeValue();
        }

        @Override
        public boolean isEquivalentTo(IValue<U, ? extends IValue> another) {
            IValue<U, ? extends IValue> temp = another;
            return temp != null && temp.convertTo(unit).getWholeValue() == value;
        }

        @Override
        public boolean isSameType(IValue<? extends Enum, ? extends IValue> another) {
            return another != null && another.getUnit().getClass().equals(unit.getClass());
        }


        protected abstract double fromBaseValueTo(U unit, double value) throws NumberFormatException;

        protected abstract double toBaseValue() throws NumberFormatException;

        @Override
        public String getName() {
            return getClass().getName().substring(getClass().getName().lastIndexOf(".") + 1);
        }

        @Override
        public String toString() {
            return (getValue() == 0.0 && !isInfinity() ? getWholeValue() : getValue()) + getUnitPrint();
        }

    }
}

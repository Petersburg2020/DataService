package nx.peter.java.util.data.encryption;

import java.util.Objects;

public class Pin {
    protected Password pin;

    public Pin(int pin) throws Password.InvalidPasswordException {
        this(pin, 4);
    }

    public Pin(int pin, int length) throws Password.InvalidPasswordException {
        setPin(pin, length);
    }

    public void setPin(int pin) throws Password.InvalidPasswordException {
        setPin(pin, this.pin.length());
    }

    public void setPin(int pin, int length) throws Password.InvalidPasswordException {
        this.pin = new Password(String.valueOf(pin), length);
    }

    public void setLength(int length) throws Password.InvalidPasswordException {
        pin.setLengthRestriction(length, pin.getLengthRestriction().getVariant());
    }

    public int getPin() {
        return Integer.parseInt(pin.get());
    }

    @Override
    public String toString() {
        return pin.toString();
    }

    public static Pin generate(int length) {
            return Pin.getInstance(Integer.parseInt(Objects.requireNonNull(Password.generate(length, Password.Restriction.Number)).get()), length);
    }

    public static Pin getInstance(int pin) {
        return getInstance(pin, 4);
    }

    public static Pin getInstance(int pin, int length) {
        try {
            return new Pin(pin, length);
        } catch (Password.InvalidPasswordException e) {
            return null;
        }
    }

}

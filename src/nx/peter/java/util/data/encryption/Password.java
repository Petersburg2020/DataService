package nx.peter.java.util.data.encryption;

import nx.peter.java.util.Random;
import nx.peter.java.util.data.DataManager;

import java.util.Objects;

public class Password {
    private String password;
    private Restriction restriction;
    private LengthVariant variant;
    private int length;


    public Password(CharSequence password) throws InvalidPasswordException{
        this(password, 0);
    }

    public Password(CharSequence password, int length) throws InvalidPasswordException{
        this(password, length, LengthVariant.Greater, Restriction.None);
    }

    public Password(CharSequence password, int length, LengthVariant variant, Restriction restriction) throws InvalidPasswordException{
        this.length = length;
        this.variant = variant;
        this.password = "";
        this.restriction = restriction;
        set(password);
    }

    public void reset(){
        password = "";
        length = 0;
        variant = LengthVariant.Greater;
        restriction = Restriction.None;
    }

    public Password getInstance() {
        return getInstance("");
    }

    public static Password getInstance(CharSequence password) {
        try {
            return new Password(password);
        } catch (InvalidPasswordException e) {
            return null;
        }
    }

    public static Password getInstance(CharSequence password, int length) {
        try {
            return new Password(password, length);
        } catch (InvalidPasswordException e) {
            return null;
        }
    }

    public void set(CharSequence password) throws InvalidPasswordException {
        if (password == null)
            throw new InvalidPasswordException("Null password was passed! It is invalid!!!");
        boolean isLess = variant.equals(LengthVariant.Less) && password.length() > length;
        boolean isEqual = variant.equals(LengthVariant.Equal) && password.length() != length;
        boolean isGreater = variant.equals(LengthVariant.Greater) && password.length() < length;

        if(!followsRestriction(password, restriction))
            throw new InvalidPasswordException("\"" + password + "\" " + getMessage(restriction, password));
        else if(isLess || isEqual || isGreater)
            throw new InvalidPasswordException("The length of \"" + password +  "\" is " + getMessage(variant) + " " + length + "!");
        this.password = password.toString();
    }

    public String get(){
        return password;
    }

    public int length(){
        return password.length();
    }

    public void setRestriction(Restriction restriction) throws InvalidPasswordException {
        if(restriction != null){
            this.restriction = restriction;
            if(!followsRestriction(password, restriction)){
                String temp = password;
                password = "";
                throw new InvalidPasswordException("\"" + temp + "\" " + getMessage(this.restriction, temp));
            }
        }
    }

    public Restriction getRestriction(){
        return restriction;
    }

    public LengthRestriction getLengthRestriction(){
        return new LengthRestriction(){
            @Override
            public int getLength(){
                return length;
            }

            @Override
            public LengthVariant getVariant(){
                return variant;
            }
        };
    }

    public void setLengthRestriction(int length, LengthVariant variant) throws InvalidPasswordException{
        if(variant != null){
            this.variant = variant;
            this.length = length;

            boolean isLess = variant.equals(LengthVariant.Less) && length() > length;
            boolean isEqual = variant.equals(LengthVariant.Equal) && length() != length;
            boolean isGreater = variant.equals(LengthVariant.Greater) && length() < length;

            if(isLess || isEqual || isGreater){
                String temp = password;
                password = "";
                throw new InvalidPasswordException(temp + "'s length is " + getMessage(variant) + " " + length + "!");
            }
        }
    }

    @Override
    public String toString(){
        return password;
    }

    public void generate(){
        password = Objects.requireNonNull(generate(length, restriction)).get();
    }

    public static Password generate(int length, Restriction restriction){
        String all = "";
        StringBuilder password = new StringBuilder();

        if(restriction == null)
            restriction = Restriction.None;

        if(length < 4)
            length = 4;

        switch(restriction){
            case AlphaNumeric:
                while(password.length() < length){
                    int type = Random.nextInt(2);
                    switch (type) {
                        case 0 -> all = DataManager.ALPHABETS;
                        case 1 -> all = DataManager.NUMBERS;
                    }
                    password.append(all.charAt(Random.nextInt(all.length() - 1)));
                    if(password.length() == length && !followsRestriction(password.toString(), Restriction.AlphaNumeric))
                        password = new StringBuilder();
                }
                break;

            case Alphabet:
                all = DataManager.ALPHABETS;
                while(password.length() < length)
                    password.append(all.charAt(Random.nextInt(all.length() - 1)));

                break;

            case AlphaCharacter:
                while(password.length() < length){
                    int type = Random.nextInt(2);
                    switch (type) {
                        case 0:
                            all = DataManager.SPECIAL_CHARACTERS;
                            break;
                        case 1:
                            all = DataManager.ALPHABETS;
                    }
                    password.append(all.charAt(Random.nextInt(all.length() - 1)));
                    if(password.length() == length && !followsRestriction(password.toString(), Restriction.AlphaCharacter))
                        password = new StringBuilder();
                }
                break;

            case CharNumeric:
                while(password.length() < length){
                    int type = Random.nextInt(2);
                    all = switch (type) {
                        case 0 -> DataManager.SPECIAL_CHARACTERS;
                        case 1 -> DataManager.NUMBERS;
                        default -> all;
                    };
                    password.append(all.charAt(Random.nextInt(all.length() - 1)));
                    if(password.length() == length && !followsRestriction(password.toString(), Restriction.CharNumeric))
                        password = new StringBuilder();
                }
                break;

            case Number:
                all = DataManager.NUMBERS;
                while(password.length() < length) {
                    password.append(all.charAt(Random.nextInt(all.length() - 1)));
                    if(password.length() == length && !followsRestriction(password.toString(), Restriction.Number))
                        password = new StringBuilder();
                }
                break;

            case All:
                while(password.length() < length){
                    int type = Random.nextInt(3);
                    all = switch (type) {
                        case 0 -> DataManager.ALPHABETS;
                        case 1 -> DataManager.SPECIAL_CHARACTERS;
                        case 2 -> DataManager.NUMBERS;
                        default -> all;
                    };
                    password.append(all.charAt(Random.nextInt(all.length() - 1)));
                    if(password.length() == length && !followsRestriction(password.toString(), Restriction.All))
                        password = new StringBuilder();
                }
                break;

            default:
                all = DataManager.SPECIAL_CHARACTERS + DataManager.ALPHABETS + DataManager.NUMBERS;

                while(password.length() < length)
                    password.append(all.charAt(Random.nextInt(all.length() - 1)));
        }

        // Main.println("Follows: " + followsRestriction(password, restriction));

        try{
            return new Password(password.toString(), length, LengthVariant.Equal, restriction);
        } catch (InvalidPasswordException e){
            // Main.println(e.getMessage());
            return null;
        }
    }

    public static boolean followsRestriction(CharSequence password, Restriction restriction){
        switch (restriction) {
            case Alphabet:
                return !hasNumber(password) && hasAlphabet(password) && !hasCharacter(password);
            case AlphaCharacter:
                return !hasNumber(password) && hasAlphabet(password) && hasCharacter(password);
            case AlphaNumeric:
                return hasNumber(password) && hasAlphabet(password) && !hasCharacter(password);
            case All:
                return hasNumber(password) && hasAlphabet(password) && hasCharacter(password);
            case CharNumeric:
                return hasNumber(password) && !hasAlphabet(password) && hasCharacter(password);
            case Number:
                return hasNumber(password) && !hasAlphabet(password) && !hasCharacter(password);
            default:
                return hasAlphabet(password) || hasCharacter(password) || hasNumber(password);
        }
    }

    private String getMessage(Restriction restriction, CharSequence password){
        switch(restriction){
            case Alphabet:
                if(!hasAlphabet(password))
                    return "has no letter!";
                else
                    return "has no Alphabet!";

            case AlphaCharacter:
                if(hasCharacter(password) && hasNumber(password) && hasAlphabet(password))
                    return "does not contain Alphabets and Special Characters only!";
                else if(!hasCharacter(password) && hasNumber(password) && hasAlphabet(password))
                    return "does not contain Special Characters, and has some invalid character(s)";
                else if(hasCharacter(password) && hasNumber(password) && !hasAlphabet(password))
                    return "does not contain Alphabet, and has some invalid character(s)";
                else if(!hasCharacter(password) && hasNumber(password) && !hasAlphabet(password))
                    return "does not contain Alphabet";
                else if(!hasCharacter(password) && !hasNumber(password) && hasAlphabet(password))
                    return "does not contain Special Characters";
                else if(!hasCharacter(password) && hasNumber(password) && !hasAlphabet(password))
                    return "has no valid character!";
                else
                    return "has no letter!";


            case AlphaNumeric:
                if(hasCharacter(password) && hasNumber(password) && hasAlphabet(password))
                    return "does not contain Alphabets and Numbers only!";
                else if(hasCharacter(password) && !hasNumber(password) && hasAlphabet(password))
                    return "does not contain Number, and has some invalid character(s)";
                else if(hasCharacter(password) && hasNumber(password) && !hasAlphabet(password))
                    return "does not contain Alphabet, and has some invalid character(s)";
                else if(!hasCharacter(password) && hasNumber(password) && !hasAlphabet(password))
                    return "does not contain Alphabet";
                else if(!hasCharacter(password) && !hasNumber(password) && hasAlphabet(password))
                    return "does not contain Number";
                else if(hasCharacter(password) && !hasNumber(password) && !hasAlphabet(password))
                    return "has no valid character!";
                else
                    return "has no letter!";

            case All:
                if(hasCharacter(password) && !hasNumber(password) && hasAlphabet(password))
                    return "does not contain Number";
                else if(hasCharacter(password) && hasNumber(password) && !hasAlphabet(password))
                    return "does not contain Alphabet";
                else if(!hasCharacter(password) && hasNumber(password) && !hasAlphabet(password))
                    return "does not contain Alphabet and Special Characters";
                else if(!hasCharacter(password) && !hasNumber(password) && hasAlphabet(password))
                    return "does not contain Number and Special Characters";
                else if(hasCharacter(password) && !hasNumber(password) && !hasAlphabet(password))
                    return "does not contain Numbers and Alphabets";
                else if(hasCharacter(password) && !hasNumber(password) && !hasAlphabet(password))
                    return "has no valid character!";
                else
                    return "has no letter!";


            case CharNumeric:
                if(hasCharacter(password) && hasNumber(password) && hasAlphabet(password))
                    return "does not contain Special Characters and Numbers only!";
                else if(hasCharacter(password) && !hasNumber(password) && hasAlphabet(password))
                    return "does not contain Number, and has some invalid character(s)";
                else if(hasCharacter(password) && hasNumber(password) && !hasAlphabet(password))
                    return "does not contain Special Characters, and has some invalid character(s)";
                else if(!hasCharacter(password) && hasNumber(password) && !hasAlphabet(password))
                    return "does not contain Special Characters";
                else if(!hasCharacter(password) && !hasNumber(password) && hasAlphabet(password))
                    return "does not contain Number";
                else if(hasCharacter(password) && !hasNumber(password) && !hasAlphabet(password))
                    return "has no valid character!";
                else
                    return "has no letter!";


            case Number:
                if(!hasNumber(password))
                    return "has no letter!";
                else
                    return "has no Alphabet!";

            default:
                return "has no letter!";
        }
    }

    private String getMessage(LengthVariant variant){
        return switch (variant) {
            case Greater -> "not greater than/equal to";
            case Less -> "not less than/equal to";
            default -> "not equal to";
        };
    }

    private static boolean hasCharacter(CharSequence letters){
        for(char c : letters.toString().toCharArray())
            if(DataManager.isSpecialCharacter(c + ""))
                return true;
        return false;
    }

    private static boolean hasNumber(CharSequence letters){
        for(char c : letters.toString().toCharArray())
            if(DataManager.isNumber(c))
                return true;
        return false;
    }

    private static boolean hasAlphabet(CharSequence letters){
        for(char c : letters.toString().toCharArray())
            if(DataManager.isAlphabet(c + ""))
                return true;
        return false;
    }


    public static class InvalidPasswordException extends Exception {
        public InvalidPasswordException(CharSequence message) {
            super(message.toString());
        }
    }

    public enum Restriction {
        All,
        None,
        AlphaCharacter,
        AlphaNumeric,
        Alphabet,
        CharNumeric,
        Number
    }

    public enum LengthVariant {
        Equal,
        Less,
        Greater
    }

    public interface LengthRestriction {
        int getLength();
        LengthVariant getVariant();
    }

}

package nx.peter.java.util.data;

import nx.peter.java.json.Json;
import nx.peter.java.json.core.JsonElement;
import nx.peter.java.pis.Pis;
import nx.peter.java.pis.core.Node;
import nx.peter.java.util.data.comparator.ComparedLetters;
import nx.peter.java.util.param.IntString;

import java.util.*;

public class DataManager {
    public static final String ALPHABETS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    public static final String NUMBERS = "1234567890";
    public static final String CHARACTERS = "{}();.,-∅_+!?/#$@*~`|•√π÷×¶∆=°^¥€¢£%©®™✓[]><\"⁻ᵐ’‘‹›«»„“”¡¿\\←↑↓→ΩΠμ§ⁿ':";

    public static final String SPECIAL_CHARACTERS = "@$&_©®™£€¥¢#";
    public static final String FRACTIONS = "⅙⅐⅛⅑⅒½⅓¼⅕⅔⅖⅗¾⅜⅘⅝⅚⅞";
    public static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    public static final String VOWELS = "AEIOUaeiou";
    public static final String CONSONANTS = "BCDFGHJKLMNPQRSTVWXYZbcdfghjklmnpqrstvwxyz";

    public static final String OPERATORS = "-+*÷×/";

    public static final String OPERATOR_PLUS = "+";
    public static final String OPERATOR_MINUS = "-";
    public static final String OPERATOR_TIMES = "×";
    public static final String OPERATOR_DIVIDE = "÷";

    public static final String EQUALITY_NOT_EQUAL_TO = "≠";
    public static final String EQUALITY_EQUAL_TO = "=";
    public static final String EWUALITY_ALMOST_EQUAL_TO = "≈";
    public static final String EQUALITY_IDENTICAL_TO = "≡";
    public static final String EQUALITY_NOT_IDENTICAL_TO = "≢";
    public static final String EQUALITY_GREATER_EQUAL_TO = "≥";
    public static final String EQUALITY_LESS_EQUAL_TO = "≤";
    public static final String EQUALITY_LESS_THAN = "<";
    public static final String EQUALITY_GREATER_THAN = ">";
    public static final String EQUALITY_NOT_LESS_THAN = "≮";
    public static final String EQUALITY_NOT_GREATER_THAN = "≯";
    public static final String EQUALITY_NOT_GREATER_EQUAL_TO = "≱";
    public static final String EQUALITY_NOT_LESS_EQUAL_TO = "≰";

    public static final String INTEGRAL = "∫";
    public static final String MATH_EPSILON = "∑";

    public static final String ROOT = "√";
    public static final String ROOT_4 = "∜";
    public static final String ROOT_3 = "∛";
    public static final String INDEXES = "¹²³⁴⁵⁶⁷⁸⁹⁰";
    public static final String INDEX_CHARACTERS = "⁾⁺⁽⁻⁼";
    public static final String INDEX_OPERATORS = "⁺⁻";
    public static final String INDEX_VARIABLES = "ᵃᵇᶜᵈᵉᶠᵍʰⁱʲᵏˡᵐⁿᵒᵖʳˢᵗᵘᵛʷˣʸᶻᵂⱽᵁᵀᴿᴾᴼᴺᴹᴸᴷᴶᴵᴴᴳᴱᴰᴮᴬ";

    public static final String SUPERSCRIPT_SPECIALS = "ᵝᶷᶿᶲᶮᶯᶞᵟᵑᵆ";
    public static final String SUPERSCRIPTS = INDEXES + INDEX_CHARACTERS + INDEX_VARIABLES + SUPERSCRIPT_SPECIALS;

    public static final String SUBSCRIPTS = "₀₁₂₃₄₅₆₇₈₉";
    public static final String SUBSCRIPT_VARIABLES = "ₐₑₒₓₔᵣᵤᵥᵦᵨᵩ";
    public static final String SUBSCRIPT_CHARACTERS = "₎₍₊₌₋";
    public static final String SUBSCRIPT_OPERATORS = "₊₋";
    public static final String SUBSCRIPTS_ALL = SUBSCRIPTS + SUBSCRIPT_CHARACTERS + SUBSCRIPT_VARIABLES;

    public static final String SET_NULL_SET = "∅";
    public static final String SET_ELEMENT_OF = "∈";
    public static final String SET_NOT_ELEMENT_OF = "∉";
    public static final String SET_MEMBER_OF = "∋";
    public static final String SET_NOT_MEMBER_OF = "∌";
    public static final String SET_INTERSECTION = "∩";
    public static final String SET_UNION = "∪";
    public static final String SET_NOT_INTERSECTION = "∩";
    public static final String SET_NOT_UNION = "∪";
    public static final String SET_SUBSET_OF = "⊂";
    public static final String SET_SUPERSET_OF = "⊃";
    public static final String SET_NOT_SUBSET_OF = "⊄";
    public static final String SET_NOT_SUPERSET_OF = "⊅";
    public static final String SET_SUBSET_OF_EQUAL_TO = "⊆";
    public static final String SET_SUPERSET_OF_EQUAL_TO = "⊇";
    public static final String SET_NOT_SUBSET_OF_EQUAL_TO = "⊈";
    public static final String SET_NOT_SUPERSET_OF_EQUAL_TO = "⊉";
    public static final String SET_MINUS = "∖";
    public static final String SET_COMPLEMENT = "∁";

    public static final String SET_SYMBOLS = SET_MINUS + SET_COMPLEMENT + SET_UNION + SET_NOT_UNION
            + SET_INTERSECTION + SET_ELEMENT_OF + SET_NOT_ELEMENT_OF + SET_NOT_INTERSECTION
            + SET_MEMBER_OF + SET_NOT_MEMBER_OF + SET_NOT_SUBSET_OF + SET_SUBSET_OF + SET_SUPERSET_OF
            + SET_NOT_SUPERSET_OF + SET_SUPERSET_OF_EQUAL_TO + SET_NOT_SUPERSET_OF_EQUAL_TO
            + SET_SUBSET_OF_EQUAL_TO + SET_NOT_SUBSET_OF_EQUAL_TO + SET_NULL_SET;

    public static final String ANGLE_DIAMETER = "⌀";
    public static final String ANGLE_ANGLE = "∠";
    public static final String ANGLE_MEASURED_ANGLE = "∡";
    public static final String ANGLE_SPHERICAL_ANGLE = "∢";
    public static final String ANGLE_RIGHT_ANGLE = "⦜";
    public static final String ANGLE_ACUTE_ANGLE = "⦟";
    public static final String ANGLE_DEGREE = "°";
    public static final String ANGLE_PARALLEL_TO = "∥";
    public static final String ANGLE_NOT_PARALLEL_TO = "∦";
    public static final String ANGLE_PERPENDICULAR = "⟂";

    public static final String ANGLE_SYMBOLS = ANGLE_ANGLE + ANGLE_ACUTE_ANGLE + ANGLE_SPHERICAL_ANGLE
            + ANGLE_DEGREE + ANGLE_DIAMETER + ANGLE_MEASURED_ANGLE + ANGLE_NOT_PARALLEL_TO
            + ANGLE_RIGHT_ANGLE + ANGLE_PARALLEL_TO + ANGLE_PERPENDICULAR;


    public static boolean isSubscript(char letter) {
        return isSubscript(letter + "");
    }

    public static boolean isSubscript(CharSequence letter) {
        if (letter != null && letter.length() == 1)
            return SUBSCRIPTS_ALL.contains(letter);
        return false;
    }


    public static boolean isSubscriptCharacter(char letter) {
        return isSubscriptCharacter(letter + "");
    }

    public static boolean isSubscriptCharacter(CharSequence letter) {
        if (letter != null && letter.length() == 1)
            return SUBSCRIPT_CHARACTERS.contains(letter);
        return false;
    }


    public static boolean isSubscriptOperator(char letter) {
        return isSubscriptOperator(letter + "");
    }

    public static boolean isSubscriptOperator(CharSequence letter) {
        if (letter != null && letter.length() == 1)
            return SUBSCRIPT_OPERATORS.contains(letter);
        return false;
    }


    public static boolean isSubscriptVariable(char letter) {
        return isSubscriptVariable(letter + "");
    }

    public static boolean isSubscriptVariable(CharSequence letter) {
        if (letter != null && letter.length() == 1)
            return SUBSCRIPT_VARIABLES.contains(letter);
        return false;
    }


    public static boolean isSuperscript(char letter) {
        return isSuperscript(letter + "");
    }

    public static boolean isSuperscript(CharSequence letter) {
        if (letter != null && letter.length() == 1)
            return SUPERSCRIPTS.contains(letter);
        return false;
    }


    public static boolean isSuperscriptCharacter(char letter) {
        return isSuperscriptCharacter(letter + "");
    }

    public static boolean isSuperscriptCharacter(CharSequence letter) {
        if (letter != null && letter.length() == 1)
            return INDEX_CHARACTERS.contains(letter);
        return false;
    }


    public static boolean isSuperscriptOperator(char letter) {
        return isSuperscriptOperator(letter + "");
    }

    public static boolean isSuperscriptOperator(CharSequence letter) {
        if (letter != null && letter.length() == 1)
            return INDEX_OPERATORS.contains(letter);
        return false;
    }


    public static boolean isSuperscriptVariable(char letter) {
        return isSuperscriptVariable(letter + "");
    }

    public static boolean isSuperscriptVariable(CharSequence letter) {
        if (letter != null && letter.length() == 1)
            return INDEX_VARIABLES.contains(letter);
        return false;
    }


    public static boolean isAlphabet(char letter) {
        return isAlphabet(letter + "");
    }

    public static boolean isAlphabet(CharSequence letter) {
        if (letter != null && letter.length() == 1)
            return ALPHABETS.contains(letter);
        return false;
    }

    public static boolean isConsonant(char letter) {
        return isConsonant(letter + "");
    }

    public static boolean isConsonant(CharSequence letter) {
        if (letter != null && letter.length() == 1)
            return CONSONANTS.contains(letter);
        return false;
    }

    public static boolean isOperator(char letter) {
        return isOperator(letter + "");
    }

    public static boolean isOperator(CharSequence letter) {
        if (letter != null && letter.length() == 1)
            return OPERATORS.contains(letter);
        return false;
    }

    public static boolean isVowel(char letter) {
        return isVowel(letter + "");
    }

    public static boolean isVowel(CharSequence letter) {
        if (letter != null && letter.length() == 1)
            return VOWELS.contains(letter);
        return false;
    }

    public static boolean isLetter(char letter) {
        return isLetter(letter + "");
    }

    public static boolean isLetter(CharSequence letter) {
        return isIndex(letter) || isAlphabet(letter) || isCharacter(letter) || isSpecialCharacter(letter)
                || isFraction(letter) || isOperator(letter) || isNumber(letter);
    }

    public static boolean isNonLetter(char letter) {
        return !isNonLetter(letter + "");
    }

    public static boolean isNonLetter(CharSequence letter) {
        return letter.length() == 1 && !isLetter(letter);
    }

    public static boolean isUpperCase(char letter) {
        return isUpperCase(letter + "");
    }

    public static boolean isUpperCase(CharSequence letter) {
        if (letter.length() == 1)
            return UPPER_CASE.contains(letter);
        return false;
    }

    public static boolean isLowerCase(char letter) {
        return isLowerCase(letter + "");
    }

    public static boolean isLowerCase(CharSequence letter) {
        if (letter.length() == 1)
            return LOWER_CASE.contains(letter);
        return false;
    }


    public static boolean isCharacter(char letter) {
        return isCharacter(letter + "");
    }

    public static boolean isCharacter(CharSequence letter) {
        if (letter.length() == 1)
            return CHARACTERS.contains(letter);
        return false;
    }


    public static boolean isSpecialCharacter(char letter) {
        return isSpecialCharacter(letter + "");
    }

    public static boolean isSpecialCharacter(CharSequence letter) {
        if (letter.length() == 1)
            return SPECIAL_CHARACTERS.contains(letter);
        return false;
    }


    public static boolean isIndex(CharSequence letter) {
        if (letter != null && letter.length() == 1)
            return INDEXES.contains(letter);
        return false;
    }

    public static boolean isIndex(char letter) {
        return isIndex(letter + "");
    }

    public static boolean isFraction(char letter) {
        return isFraction(letter + "");
    }

    public static boolean isFraction(CharSequence letter) {
        if (letter.length() == 1)
            return FRACTIONS.contains(letter);
        return false;
    }

    public static boolean isNumber(char letter) {
        return isNumber(letter + "");
    }

    public static boolean isBool(CharSequence letter) {
        return letter.equals("true") || letter.equals("false");
    }

    public static String tab(int indents) {
        return "\t".repeat(Math.max(0, indents));
    }

    public static boolean isInteger(char letter) {
        return isInteger(letter + "");
    }

    public static boolean isLong(CharSequence letters) {
        if (extractDoubles(letters).size() == 1) {
            double num = extractDoubles(letters).get(0);
            return ((long) num) == num;
        }
        return false;
    }

    public static boolean isFloat(CharSequence letters) {
        if (extractDoubles(letters).size() == 1) {
            double num = extractDoubles(letters).get(0);
            return ((float) num) == num;
        }
        return false;
    }

    public static boolean isIndexes(CharSequence letters) {
        if (new Word(letters).count('.') > 1)
            return false;
        for (char c : letters.toString().toCharArray())
            if (c != '.' && !isIndex(c))
                return false;
        return true;
    }

    public static boolean isNumber(CharSequence letter) {
        if (letter.length() == 1)
            return NUMBERS.contains(letter);
        return false;
    }

    public static boolean isNumberOnly(CharSequence letters) {
        List<Double> doubles = extractDoubles(letters);
        return doubles.size() == 1 && (isInteger(letters) ?
                String.valueOf((int) (double) doubles.get(0)).contentEquals(letters) : String.valueOf((double) doubles.get(0)).contentEquals(letters));
    }

    public static boolean isInteger(CharSequence letters) {
        List<Double> doubles = extractDoubles(letters);
        return doubles.size() == 1 && doubles.get(0) == (int) (double) doubles.get(0);
    }

    public static String toAlphabet(int number) {
        return number > 0 && number <= UPPER_CASE.length() ? UPPER_CASE.charAt(number - 1) + "" : "";
    }

    public static int toNumber(char alphabet) {
        return isAlphabet(alphabet) ? UPPER_CASE.indexOf(alphabet + "") : 0;
    }

    public static int toNumber(CharSequence alphabet) {
        return isAlphabet(alphabet) ? UPPER_CASE.indexOf(alphabet.toString()) : 0;
    }

    public static int alphabetCount(CharSequence letters) {
        int count = 0;
        if (letters.length() == 1)
            for (char letter : letters.toString().toCharArray())
                if (isAlphabet(letter))
                    count++;
        return count;
    }

    public static int getNumberOfAlmostEqualLetters(CharSequence letters1, CharSequence letters2) {
        return getAlmostEqualLetters(letters1, letters2).size();
    }

    public static ComparedLetters getAlmostEqualLetters(CharSequence letters1, CharSequence letters2) {
        return ComparedLetters.getAlmostEquals(letters1, letters2);
    }

    public static int getNumberOfEqualLetters(CharSequence letters1, CharSequence letters2) {
        return getEqualLetters(letters1, letters2).size();
    }

    public static ComparedLetters getEqualLetters(CharSequence letters1, CharSequence letters2) {
        return ComparedLetters.getEquals(letters1, letters2);
    }


    public static List<Alphabet> extractAlphabets(CharSequence letters) {
        List<Alphabet> alphabets = new ArrayList<>();
        for (IntString property : getProperties(letters))
            if (isAlphabet(property.letter))
                alphabets.add(new Alphabet(property.letter));
        return alphabets;
    }

    public static List<Character> extractCharacters(CharSequence letters) {
        List<Character> characters = new ArrayList<>();
        for (IntString property : getProperties(letters))
            if (isCharacter(property.letter))
                characters.add(new Character(property.letter));
        return characters;
    }

    public static List<Boolean> extractBools(CharSequence letters) {
        List<Boolean> bools = new ArrayList<>();
        for (Word word : extractWords(letters))
            if (isBool(word.get()))
                bools.add(word.contentEquals("true"));
        return bools;
    }

    public static List<FractionData> extractFractions(CharSequence letters) {
        List<FractionData> characters = new ArrayList<>();
        for (IntString property : getProperties(letters))
            if (isFraction(property.letter))
                characters.add(new FractionData(property.letter));
        return characters;
    }

    public static List<Word> getWordsOnly(CharSequence letters) {
        List<Word> words = new ArrayList<>();
        for (String word : letters.toString().split(" ")) {
            String w = word;
            if (w.length() > 1 && isCharacter(word.charAt(word.length() - 1)))
                w = w.substring(0, word.length() - 1);
            if (w.length() == 1 && !isAlphabet(w))
                continue;
            words.add(new Word(w));
        }
        return words;
    }

    public static List<Word> getWords(CharSequence letters) {
        List<Word> words = new ArrayList<>();
        for (String word : letters.toString().split(" "))
            words.add(new Word(word));
        return words;
    }

    public static List<Word> extractWords(CharSequence letters) {
        List<Word> words = new ArrayList<>();
        String word = "";
        for (IntString property : getProperties(letters)) {
            if (isAlphabet(property.letter))
                word += property.letter;
            else {
                if (word.length() > 0)
                    words.add(new Word(word));
                word = "";
            }
            if (property.index + 1 == letters.length())
                if (word.length() > 0)
                    words.add(new Word(word));
        }
        return words;
    }

    public static List<Word> getWords(Sentence sentence) {
        return getWords(sentence.get());
    }

    public static List<Word> getWords(Paragraph paragraph) {
        return getWords(paragraph.get());
    }

    public static List<Word> getWords(Texts texts) {
        return getWords(texts.get());
    }

    public static List<Paragraph> extractParagraphs(CharSequence letters) {
        List<Paragraph> paragraphs = new ArrayList<>();
        String texts = letters.toString();
        int start = 0, end;
        if (letters.length() > 0)
            while (start < letters.length() - 1) {
                end = texts.indexOf(".\n", start);
                String paragraph = "";
                if (end >= 0) {
                    paragraph = texts.substring(start, end + 1);
                    if (paragraph.contains("?\n")) {
                        end = paragraph.indexOf("?\n");
                        paragraph = paragraph.substring(0, end + 1);
                    }
                    if (paragraph.contains("!\n")) {
                        end = paragraph.indexOf("!\n");
                        paragraph = paragraph.substring(0, end + 1);
                    }
                } else {
                    if (texts.contains("?\n")) {
                        end = texts.indexOf("?\n");
                        paragraph = texts.substring(0, end + 1);
                        if (paragraph.contains("!\n")) {
                            end = paragraph.indexOf("!\n");
                            paragraph = paragraph.substring(0, end + 1);
                        }
                    } else if (texts.contains("!\n")) {
                        end = texts.indexOf("!\n");
                        paragraph = texts.substring(0, end + 1);
                    } else paragraph = texts.substring(start);
                }
                start = end + 1;
                paragraphs.add(new Paragraph(paragraph.trim()));
                if (start <= 0)
                    break;
            }
        return paragraphs;
    }

    public static boolean isIndent(char indent) {
        return indent == '\t';
    }

    public static <S extends ISentence> List<Paragraph> extractParagraphs(S texts) {
        return extractParagraphs(texts.get());
    }

    public static List<Sentence> extractSentences(CharSequence letters) {
        List<Sentence> sentences = new ArrayList<>();
        String texts = letters.toString();
        int start = 0, end;
        if (letters.length() > 0)
            while (start < letters.length() - 1) {
                end = texts.indexOf(". ", start);
                String sentence = "";
                if (end >= 0) {
                    // Main.println("We are here for (.) cos end=" + end);
                    sentence = texts.substring(start, end + 1);
                    if (sentence.contains("? ")) {
                        // Main.println("We are here for (?) cos end=" + end);
                        end = sentence.indexOf("? ");
                        sentence = sentence.substring(0, end + 1);
                    }
                    if (sentence.contains("! ")) {
                        // Main.println("We are here for (!) cos end=" + end);
                        end = sentence.indexOf("! ");
                        sentence = sentence.substring(0, end + 1);
                    }
                } else {
                    if (texts.contains("? ")) {
                        // Main.println("We are here for (?) cos end=" + end);
                        end = texts.indexOf("? ");
                        sentence = texts.substring(0, end + 1);
                        if (sentence.contains("! ")) {
                            // Main.println("We are here for (!) cos end=" + end);
                            end = sentence.indexOf("! ");
                            sentence = sentence.substring(0, end + 1);
                        }
                    } else if (texts.contains("! ")) {
                        end = texts.indexOf("! ");
                        sentence = texts.substring(0, end + 1);
                    } else {
                        // Main.println("We are here for (None) cos end=" + end);
                        sentence = texts.substring(start);
                    }
                }
                start = end + 1;
                sentences.add(new Sentence(sentence.trim()));
                if (start <= 0)
                    break;
            }
        return sentences;
    }

    public static <S extends ISentence> List<Sentence> extractSentences(S sentence) {
        return extractSentences(sentence.get());
    }

    public static List<Sentence> extractSentences(Texts texts) {
        List<Sentence> sentences = new ArrayList<>();
        for (Paragraph paragraph : texts.extractParagraphs())
            sentences.addAll(paragraph.extractSentences().getSentences());
        return sentences;
    }


    public static List<Integer> extractIntegers(CharSequence letters) {
        List<Integer> integers = new ArrayList<>();
        for (double num : extractDoubles(letters))
            if (isInteger(String.valueOf(num)))
                integers.add((int) num);
        return integers;
    }

    public static List<Double> extractDecimals(CharSequence letters) {
        List<Double> decimals = new ArrayList<Double>();
        for (double num : extractDoubles(letters))
            if (!isInteger(String.valueOf(num)))
                decimals.add(num);
        return decimals;
    }

    public static List<Long> extractLongs(CharSequence letters) {
        List<Long> longs = new LinkedList<>();
        for (double num : extractDoubles(letters))
            if (isLong(num + ""))
                longs.add((long) num);
        return longs;
    }

    public static List<Number> extractNumbers(CharSequence letters) {
        List<Number> numbers = new ArrayList<>();
        for (double num : extractDoubles(letters))
            if (isInteger(num + ""))
                numbers.add(new Number(extractIntegers(num + "").get(0)));
            else
                numbers.add(new Number(num));
        return numbers;
    }

    public static List<Operator> extractOperators(CharSequence letters) {
        List<Operator> operators = new LinkedList<>();
        for (IntString prop : getProperties(letters))
            if (isOperator(prop.letter))
                operators.add(new Operator(prop.letter));
        return operators;
    }

    public static List<Letter> extractLetters(CharSequence letters) {
        List<Letter> letterList = new ArrayList<>();
        for (IntString prop : getProperties(letters))
            if (isLetter(prop.letter))
                letterList.add(new Letter(prop.letter));
        return letterList;
    }

    public static List<Double> extractDoubles(CharSequence letters) {
        List<Double> doubles = new ArrayList<>();
        String number = "";
        for (IntString property : getProperties(letters)) {
            if (isNumber(property.letter) || property.letter.contentEquals(".")) {
                if (property.letter.contentEquals(".") && number.contains(".")) {
                    if (number.startsWith(".") && number.length() > 1) {
                        number = 0 + number;
                        doubles.add(Double.parseDouble(number));
                    }
                    number = property.letter;
                } else
                    number += property.letter;
                if (property.index + 1 == letters.length())
                    if (number.length() > 0) {
                        if (number.contains(".") && number.length() > 1) {
                            if (number.startsWith("."))
                                number = 0 + number;
                            else if (number.endsWith("."))
                                number += 0;
                            doubles.add(Double.parseDouble(number));
                        } else if (!number.contains("."))
                            doubles.add(Double.parseDouble(number));
                        number = "";
                    }
            } else if (number.length() > 0) {
                if (number.contains(".") && number.length() > 1) {
                    if (number.startsWith("."))
                        number = 0 + number;
                    else if (number.endsWith("."))
                        number += 0;
                    doubles.add(Double.parseDouble(number));
                } else {
                    if (!number.contains("."))
                        doubles.add(Double.parseDouble(number));
                }
                number = "";
            }
        }
        return doubles;
    }

    public static List<Float> extractFloats(CharSequence letters) {
        List<Float> floats = new LinkedList<>();
        for (double num : extractDoubles(letters))
            if (isFloat(num + ""))
                floats.add((float) num);
        return floats;
    }

    public static List<Index> extractIndexes(CharSequence letters) {
        List<Index> indices = new ArrayList<>();
        StringBuilder index = new StringBuilder();
        for (IntString property : getProperties(letters)) {
            if (isIndex(property.letter) || (property.letter.contentEquals("°") && !index.toString().contains("°"))) {
                if (property.letter.contentEquals("°") && index.isEmpty())
                    index.append("⁰");
                index.append(property.letter);
            } else {
                if (index.length() > 0)
                    indices.add(new Index(index.toString()));
                index = new StringBuilder();
            }
        }
        return indices;
    }

    public static <JA extends JsonElement, JO extends JsonElement, JE extends JsonElement> Json<JA, JO, JE> extractJson(CharSequence json) {
        if (json.toString().trim().startsWith("[") && json.toString().trim().endsWith("]"))
            return new Json<>(extractArray(json));
        else if (json.toString().trim().startsWith("{") && json.toString().trim().endsWith("}"))
            return new Json<>(extractObject(json));
        return new Json<>();
    }

    public static List<Object> extractArray(CharSequence letters) {
        List<Object> array = new ArrayList<>();
        Word data = new Word(letters.toString().substring(letters.toString().indexOf("[") + 1, letters.toString().lastIndexOf("]")).trim());
        int start = 0, end = 0;

        while (start > -1 && start < data.length() - 1) {
            start = data.subLetters(end).getFirstLetterIndex();
            char firstLetter = data.subLetters(end).getFirstLetter();
            if (firstLetter == ',' || firstLetter == '}') {
                end += data.subLetters(end).indexOf(firstLetter) + 1;
                firstLetter = data.subLetters(end).getFirstLetter();
            }

            if (start <= -1)
                break;
            start += end;

            end = getEnd(data, firstLetter, start);

            if (end < 0 || start >= data.length())
                break;

            String value = data.substring(start, end).trim();
            if (end >= data.length() - 1)
                value = data.substring(start).trim();

            if (value.startsWith("[") && !value.endsWith("]")) {
                end = value.lastIndexOf("]");
                value = value.substring(0, end + 1);
            } else if (value.startsWith("{") && !value.endsWith("}")) {
                end = value.lastIndexOf("}");
                value = value.substring(0, end + 1);
            }

            String type = getType(value);
            if (type.contentEquals(NULL)) break;

            Object val = switch (type) {
                case OBJECT -> extractObject(value);
                case ARRAY -> extractArray(value);
                default -> toObject(value);
            };

            array.add(val);

            if (type.contentEquals(OBJECT) || type.contentEquals(ARRAY) || type.contentEquals(STRING))
                end++;

            if (end >= data.length() - 1 || start >= data.length() - 1)
                break;
        }
        return array;
    }

    public static Map<String, Object> extractObject(CharSequence letters) {
        Map<String, Object> object = new HashMap<>();
        Word data = new Word(letters.toString().substring(letters.toString().indexOf("{") + 1, letters.toString().lastIndexOf("}")).replaceAll("\t", "").replaceAll("\n", "").trim());
        int start = 0, end = 0, lastIndex;

        while (start < data.length() - 1 && end < data.length() - 1) {
            start = data.indexOf("\"", end) + 1;
            end = data.indexOf("\":", start);
            if (end < 0 || end > data.length() - 1 || start > data.length() - 1)
                break;

            String key = data.substring(start, end);
            start = data.indexOf(":", end);
            if (start > -1) start++;
            else break;

            lastIndex = start;
            start = data.subLetters(lastIndex).getFirstLetterIndex();
            char firstLetter = data.subLetters(lastIndex + 1).getFirstLetter();
            if (firstLetter == ',' || firstLetter == '}') {
                end += data.subLetters(end).indexOf(firstLetter) + 1;
                firstLetter = data.subLetters(end).getFirstLetter();
            }

            if (start <= -1) break;
            else start += lastIndex;

            end = getEnd(data, firstLetter, start);

            if (end < 0 || start > data.length() - 1)
                break;

            String value = data.substring(start, end).trim();
            if (end >= data.length() - 1 && start < data.length() - 1)
                value = data.substring(start).trim();

            if (value.startsWith("[") && !value.endsWith("]"))
                value = value.substring(0, value.lastIndexOf("]") + 1);
            else if (value.startsWith("{") && !value.endsWith("}"))
                value = value.substring(0, value.lastIndexOf("}") + 1);

            String type = getType(value);
            if (type.contentEquals(NULL)) break;

            Object val = switch (type) {
                case OBJECT -> extractObject(value);
                case ARRAY -> extractArray(value);
                default -> toObject(value);
            };
            object.put(key, val);

            if (type.contentEquals(OBJECT) || type.contentEquals(ARRAY) || type.contentEquals(STRING))
                end++;

            if (end > data.length() - 1 || start > data.length() - 1 || end <= -1 || start <= -1)
                break;
        }
        return object;
    }

    protected static int getEnd(Word data, char firstLetter, int start) {
        int end;
        if (start <= -1 || start > data.length() - 1) return -1;
        if (firstLetter == '\"') {
            end = data.subLetters(start).contains("\",") ? data.indexOf("\",", start) : data.indexOf("\" ", start);
            if (end > -1)
                end++;
            else
                end = data.length();
        } else if (firstLetter == '[') {
            end = getCoverIndex(data, '[', ']', start);
            if (end > -1)
                end++;
        } else if (firstLetter == '{') {
            end = getCoverIndex(data, '{', '}', start);
            if (end > -1)
                end++;
        } else
            end = data.subLetters(start).contains(",") ? data.indexOf(",", start) : data.length() - 1;
        return end;
    }

    public static String getType(String value) {
        return value.startsWith("[") && value.endsWith("]") ? ARRAY :
                value.startsWith("{") && value.endsWith("}") ? OBJECT :
                        value.startsWith("\"") && value.endsWith("\"") ? STRING :
                                isBool(value) ? BOOLEAN : isInteger(value) ? INTEGER : isLong(value) ? LONG :
                                        isFloat(value) ? FLOAT : isNumberOnly(value) ? DOUBLE : NULL;
    }

    public static final String ARRAY = "array";
    public static final String OBJECT = "object";
    public static final String STRING = "string";
    public static final String INTEGER = "int";
    public static final String FLOAT = "float";
    public static final String DOUBLE = "double";
    public static final String LONG = "long";
    public static final String BOOLEAN = "boolean";
    public static final String NULL = "null";

    public static int getCoverIndex(Word source, char open, char close, int index) {
        if (source == null || index > source.length() || index < 0 && !source.subLetters(index).contains(open))
            return -1;
        int start, end = index, openCount = 0, closeCount = 0, lastIndex = -1;
        start = source.indexOf(open, end);
        end = start;
        lastIndex = end + 1;
        while (start > -1 && start < source.length() - 1) {
            start = source.indexOf(close, lastIndex);
            if (start <= -1 || start < end) return openCount == closeCount ? lastIndex : -1;
            start++;
            if (start >= source.length() - 1) {
                openCount = source.subLetters(end).getLetterCount(open);
                closeCount = source.subLetters(end).getLetterCount(close);
            } else {
                openCount = source.subLetters(end, start).getLetterCount(open);
                closeCount = source.subLetters(end, start).getLetterCount(close);
            }
            lastIndex = start;
            if (openCount == closeCount) return lastIndex;
        }
        return -1;
    }

    public static boolean isValid(Word source, char open, char close, int start) {
        return getCoverIndex(source, open, close, start) > -1;
    }


    public static <N extends Node, A extends Node.Attr> Pis<N, A> extractPis(CharSequence pis) {
        NodeMap node = extractNode(pis);
        return new Pis<>(node.tag, node.node);
    }

    public static NodeMap extractNode(CharSequence letters) {
        String tag = "";
        Word pis = new Word(letters != null ? letters.toString().trim() : "");
        List<Pis.TagValue> node = new ArrayList<>();
        int start = 0, end = 0;

        // System.out.println("Extract: \n" + pis.get());
        if (pis.startsWith("<") && pis.contains("</") && pis.getLetterCount('>') > 1 && pis.endsWith(">")) {
            tag = pis.extractWords().isNotEmpty() ? pis.extractWords().get(0).get() : "";
            Word wrap = new Word();

            // System.out.println("Tag: " + tag);

            wrap.set(pis.subLetters("<", start, ">"))
                    .removeAll("\t")
                    .replaceAll("\n", ", ")
                    .remove("<" + tag + ", ")
                    .trim()
					/*.append("\" ", 0)
					.append(" \"")*/;
            System.out.println("Wrap0: " + wrap.get());
            // System.out.println("Wrap Words: " + wrap.getWords());

            // Check if attribute exists
            Letters.Split split = wrap.split(", ", "=");
            System.out.println("Split0: " + split);
            if (split.isNotEmpty()) {
                Map<String, Object> attrs = new LinkedHashMap<>();

                for (String w : split) {
                    Letters.Split attr = new Word(w).split("=");
                    if (attr.size() == 2) {
                        String name = new Word(attr.get(0)).trim().removeAll("\"").get();
                        String val0 = attr.get(1).trim();
                        System.out.println("Type: " + getType(val0));
                        // val0 = val0.contains("\"") ? new Word(val0).remove("\"", val0.length() - 2).remove("\"").get() : val0;
                        Object val = attr.size() > 1 ? toObject(val0) : null;
                        if (val != null) {
                            /*if (val0.contains("\""))
                                val = new Word(val0).remove("\"", val0.length() - 2).remove("\"").get();*/
                            System.out.println("Val: " + val);
                            attrs.put(name, val);
                        }
                    }
                }
                // System.out.println("Attrs: " + attrs);
                // Add all attributes
                node.add(new Pis.TagValue(Pis.ATTRS_NAME, attrs));
            }
            // System.out.println("PIS: " + pis.subLetters(">\n", start, pis.lastIndexOf("</" + tag)));

            wrap.set(pis.subLetters(">\n", start, pis.lastIndexOf("</")));
            if (!pis.contains(">\n"))
                wrap.set(pis.subLetters("> ", start, pis.lastIndexOf("</")));

            wrap.removeAll("\t")
                    .replaceAll("\n", " ")
                    .remove(">")
                    .trim();
            System.out.println("Start1: " + start + ", Wrap1: " + wrap.get());


            if (wrap.contains(">") && wrap.contains("</") && wrap.startsWith("<") && wrap.endsWith(">")) {
                List<String> nodeList = new ArrayList<>();
                int startNode = 0;
                while (wrap.substring(startNode) != null) {
                    String dTag = wrap.subLetters("<", startNode) != null ? wrap.subLetters("<", startNode).get() : null;

                    // System.out.println("DTag: " + dTag);
                    if (dTag == null)
                        break;
                    Letters.Words words = new Word(dTag).getWords();
                    if (words.isNotEmpty()) {
                        Word word = words.get(0).remove("<");
                        dTag = word.contains(">") ? word.subLetters(0, word.indexOf(">")).get() : word.get();
                    } else dTag = null;
                    // dTag = words.isNotEmpty() ? words.get(0).remove("<").subLetters(0, words.get(0).indexOf(">")).get() : null;
                    // System.out.println("DTag: " + dTag);
                    if (dTag == null)
                        break;

                    System.out.println("DTag: " + dTag);
                    startNode += dTag.length();
                    String dNode = wrap.subLetters(">", startNode, wrap.indexOf("</" + dTag, startNode)) != null ? wrap.subLetters(">", startNode, wrap.indexOf("</" + dTag, startNode)).remove("> ").get() : null;
                    if (dNode == null)
                        break;
                    // System.out.println("DNode: " + dNode);
                    startNode = wrap.indexOf(dNode + "</" + dTag + ">", startNode) + (dNode + "</" + dTag + ">").length();
                    nodeList.add("<" + dTag + "> " + dNode + "</" + dTag + ">");

                    // System.out.println("Start node: " + startNode + ", Length: " + wrap.length());
                    if (startNode >= wrap.length() || startNode < 0)
                        break;

                    // Update Child Node
                    /*Pis.TagValue tagValue = extractChildNode("<" + dTag + "> " + dNode + "</" + dTag + ">");
                    System.out.println("Node: " + tagValue.value);
                    node.add(tagValue);*/
                }
                // System.out.println("Node List: " + nodeList);

                // Update Child Nodes
                for (String dNode : nodeList) {
                    NodeMap map = extractChildNode(dNode);
                    // System.out.println("Node: " + map.node);
                    node.add(new Pis.TagValue(map.tag, map.node));
                }
            } else {
                // System.out.println("Value: " + toObject(wrap.remove(">").get()));
                node.add(new Pis.TagValue(Pis.SINGLE_CHILD_TAG, toObject(wrap.get())));
            }
        }
        // System.out.println("Node: " + node);
        return new NodeMap(tag, new Pis.TagValueList(node));
    }

    protected static NodeMap extractChildNode(CharSequence letters) {
        String tag = "";
        Word pis = new Word(letters != null ? letters.toString().trim() : "");
        List<Pis.TagValue> node = new ArrayList<>();
        int start = 0, end = 0;

        // System.out.println("Extract: \n" + pis.get());
        if (pis.startsWith("<") && pis.contains("</") && pis.getLetterCount('>') > 1 && pis.endsWith(">")) {
            tag = pis.extractWords().isNotEmpty() ? pis.extractWords().get(0).get() : "";
            Word wrap = new Word();

            // System.out.println("Tag: " + tag);

            wrap.set(pis.subLetters("<", start, ">"))
                    .removeAll("\t")
                    .replaceAll("\n", ", ")
                    .remove("<" + tag + ", ")
                    .trim()
					/*.append("\" ", 0)
					.append(" \"")*/;
            System.out.println("Wrap01: " + wrap.get());
            // System.out.println("Wrap Words: " + wrap.getWords());

            // Check if attribute exists
            Letters.Split split = wrap.split(", ", "=");
            System.out.println("Split: " + split);
            if (split.isNotEmpty()) {
                Map<String, Object> attrs = new LinkedHashMap<>();

                for (String w : split) {
                    Letters.Split attr = new Word(w).split("=");
                    if (attr.size() == 2) {
                        String name = new Word(attr.get(0)).trim().removeAll("\"").get();
                        String val0 = attr.get(1).trim();
                        System.out.println("Type: " + getType(val0));
                        // val0 = val0.contains("\"") ? new Word(val0).remove("\"", val0.length() - 2).remove("\"").get() : val0;
                        Object val = attr.size() > 1 ? toObject(val0) : null;
                        if (val != null) {
                            /*if (val0.contains("\""))
                                val = new Word(val0).remove("\"", val0.length() - 2).remove("\"").get();*/
                            System.out.println("Val: " + val);
                            attrs.put(name, val);
                        }
                    }
                }
                // System.out.println("Attrs: " + attrs);
                // Add all attributes
                node.add(new Pis.TagValue(Pis.ATTRS_NAME, attrs));
            }
            // System.out.println("PIS: " + pis.subLetters(">\n", start, pis.lastIndexOf("</" + tag)));

            wrap.set(pis.subLetters(">\n", start, pis.lastIndexOf("</")));
            if (!pis.contains(">\n"))
                wrap.set(pis.subLetters("> ", start, pis.lastIndexOf("</")));

            wrap.removeAll("\t")
                    .replaceAll("\n", " ")
                    .remove(">")
                    .trim();
            System.out.println("Start11: " + start + ", Wrap11: " + wrap.get());


            if (wrap.contains(">") && wrap.contains("</") && wrap.startsWith("<") && wrap.endsWith(">")) {
                List<String> nodeList = new ArrayList<>();
                int startNode = 0;
                while (wrap.substring(startNode) != null) {
                    String dTag = wrap.subLetters("<", startNode) != null ? wrap.subLetters("<", startNode).get() : null;

                    // System.out.println("DTag: " + dTag);
                    if (dTag == null)
                        break;
                    Letters.Words words = new Word(dTag).getWords();
                    if (words.isNotEmpty()) {
                        Word word = words.get(0).remove("<");
                        dTag = word.contains(">") ? word.subLetters(0, word.indexOf(">")).get() : word.get();
                    } else dTag = null;
                    // dTag = words.isNotEmpty() ? words.get(0).remove("<").subLetters(0, words.get(0).indexOf(">")).get() : null;
                    // System.out.println("DTag: " + dTag);
                    if (dTag == null)
                        break;

                    System.out.println("DTag: " + dTag);
                    startNode += dTag.length();
                    String dNode = wrap.subLetters(">", startNode, wrap.indexOf("</" + dTag, startNode)) != null ? wrap.subLetters(">", startNode, wrap.indexOf("</" + dTag, startNode)).remove("> ").get() : null;
                    if (dNode == null)
                        break;
                    // System.out.println("DNode: " + dNode);
                    startNode = wrap.indexOf(dNode + "</" + dTag + ">", startNode) + (dNode + "</" + dTag + ">").length();
                    nodeList.add("<" + dTag + "> " + dNode + "</" + dTag + ">");

                    // System.out.println("Start node: " + startNode + ", Length: " + wrap.length());
                    if (startNode >= wrap.length() || startNode < 0)
                        break;

                    // Update Child Node
                    /*Pis.TagValue tagValue = extractChildNode("<" + dTag + "> " + dNode + "</" + dTag + ">");
                    System.out.println("Node: " + tagValue.value);
                    node.add(tagValue);*/
                }
                // System.out.println("Node List: " + nodeList);

                // Update Child Nodes
                for (String dNode : nodeList) {
                    NodeMap map = extractNode(dNode);
                    // System.out.println("Node: " + map.node);
                    node.add(new Pis.TagValue(map.tag, map.node));
                }
            } else {
                // System.out.println("Value: " + toObject(wrap.remove(">").get()));
                node.add(new Pis.TagValue(Pis.SINGLE_CHILD_TAG, toObject(wrap.get())));
            }
        }
        System.out.println("Node: " + node);
        return new NodeMap(tag, new Pis.TagValueList(node));
    }

    public static Object toObject(String value) {
        return switch (getType(value)) {
            case BOOLEAN -> value.contentEquals("true");
            case INTEGER -> extractIntegers(value).get(0);
            case LONG -> !extractLongs(value).isEmpty() ? extractLongs(value).get(0) : Long.MIN_VALUE;
            case FLOAT -> !extractFloats(value).isEmpty() ? extractFloats(value).get(0) : Float.MIN_VALUE;
            case DOUBLE -> !extractDecimals(value).isEmpty() ? extractDecimals(value).get(0) : Double.MIN_VALUE;
            case STRING ->
                    value.length() > 1 ? new Word(value).remove("\"", value.length() - 2).remove("\"").replaceAll("@10", "\n").replaceAll("@09", "\t").get() : value;
            default -> null;
        };
    }


    public record NodeMap(CharSequence tag, Pis.TagValueList node) {
    }

    public static List<IntString> getProperties(CharSequence letters) {
        List<IntString> properties = new ArrayList<>();
        for (int n = 0; n < letters.length(); n++)
            properties.add(new IntString(letters.toString().charAt(n), n));
        return properties;
    }

}


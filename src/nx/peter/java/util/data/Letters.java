package nx.peter.java.util.data;

import nx.peter.java.dictionary.WordDictionary;
import nx.peter.java.util.Fraction;
import nx.peter.java.util.Percent;
import nx.peter.java.util.Random;
import nx.peter.java.util.Util;
import nx.peter.java.util.data.comparator.ComparedLetters;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Stream;


public abstract class Letters<L extends Letters> extends Data<L> {
    public Letters() {
        super();
    }

    public Letters(char... letters) {
        super(letters);
    }

    public Letters(CharSequence letters) {
        super(letters);
    }


    public Alphabet getAlphabetAt(int index) {
        return index > -1 && index < length() && DataManager.isAlphabet(charAt(index)) ? new Alphabet(charAt(index)) : null;
    }

    public Alphabet getNextAlphabet(int start) {
        return getAlphabetAt(getNextAlphabetIndex(start));
    }

    public int getNextAlphabetIndex(int from) {
        if (from >= 0 && from < length() - 2) {
            int index = from;
            for (char c : data.substring(from + 1).toCharArray()) {
                index++;
                if (DataManager.isAlphabet(c))
                    return index;
            }
        }
        return -1;
    }

    public Number getNextNumber(int from) {
        return getNumberAt(getNextNumberIndex(from));
    }

    public int getNextNumberIndex(int from) {
        if (from >= 0 && from < length() - 2) {
            int index = from;
            for (char c : data.substring(from + 1).toCharArray()) {
                index++;
                if (DataManager.isNumber(c))
                    return index;
            }
        }
        return -1;
    }

    public Number getNumberAt(int index) {
        return index > -1 && index < length() && DataManager.isNumber(charAt(index)) ? subLetters(index).extractNumbers().get(0) : null;
    }

    public Alphabet getPreviousAlphabet(int start) {
        return getAlphabetAt(getPreviousAlphabetIndex(start));
    }

    public int getPreviousAlphabetIndex(int start) {
        if (start > 0 && start <= length() - 1)
            for (int index = start + 1; index >= 0; index--)
                if (DataManager.isAlphabet(data.substring(0, start).charAt(index)))
                    return index;
        return -1;
    }

    public Alphabets extractAlphabets() {
        return new Alphabets(this, DataManager.extractAlphabets(data));
    }

    public List<Operator> extractOperators() {
        return DataManager.extractOperators(data);
    }

    public List<Character> extractCharacters() {
        return DataManager.extractCharacters(data);
    }

    public Numbers extractIntegers() {
        return new Numbers(this, Util.toNumbers(DataManager.extractIntegers(data)));
    }

    public List<Double> extractDoubles() {
        return DataManager.extractDoubles(data);
    }

    public Numbers extractNumbers() {
        return new Numbers(this, DataManager.extractNumbers(data));
    }

    public LetterList extractLetters() {
        return new LetterList(this, DataManager.extractLetters(data));
    }


    public ComparedLetters getEquals(Letters<?> another) {
        return ComparedLetters.getEquals(another, this);
    }

    public ComparedLetters getAlmostEquals(Letters<?> another) {
        return ComparedLetters.getAlmostEquals(another, this);
    }


    public boolean isEqualTo(Letters<?> another) {
        return equals(another);
    }

    // if both letters have at least
    public boolean isAlmostEqualTo(Letters<?> another) {
        ComparedLetters letters = getAlmostEquals(another);
        return another != null && ((letters.size() >= new Percent(75).getPercentageOf(length()) && letters.size() > new Percent(50).getPercentageOf(another.length())) || (letters.size() > new Percent(50).getPercentageOf(length())) && letters.size() >= new Percent(75).getPercentageOf(another.length()));
    }


    public char getFirstLetter() {
        int index = getFirstLetterIndex();
        return index > -1 ? data.charAt(index) : 0;
    }

    public int getFirstLetterIndex() {
        int index = 0;
        for (char c : data.toCharArray()) {
            if (DataManager.isNonLetter(c))
                return index;
            index++;
        }
        return -1;
    }


    public L replace(CharSequence old, Object value) {
        return replace(old, String.valueOf(value));
    }

    public L replace(CharSequence old, IData<?> value) {
        return replace(old, value != null ? value.get() : (String) null);
    }

    public L replace(CharSequence old, CharSequence text) {
        if (old != null && text != null && contains(old)) {
            int index = indexOf(old);
            String before = index > 0 ? substring(0, index) : "";
            String after = index + old.length() < length() - 1 ?
                    substring(index + old.length()) :
                    index + old.length() == length() - 1 ?
                            charAt(length() - 1) + "" : "";
            // System.out.println("Data: " + data + "Before: " + before + ", After: " + after);
            data = before + text + after;
        }
        return (L) this;
    }


    public L remove(Object data) {
        return remove(String.valueOf(data));
    }

    public L remove(IData<?> data) {
        return remove(data != null ? data.get() : "");
    }

    public L remove(CharSequence data) {
        return replace(data, "");
    }


    public L remove(boolean data, int origin) {
        return remove(data + "", origin);
    }

    public L remove(char data, int origin) {
        return remove(data + "", origin);
    }

    public L remove(double data, int origin) {
        return remove(data + "", origin);
    }

    public L remove(int data, int origin) {
        return remove(data + "", origin);
    }

    public L remove(IData<?> data, int origin) {
        return remove(data != null ? data.get() : "", origin);
    }

    public L remove(CharSequence data, int origin) {
        if (data != null && !isEmpty() && origin >= 0 && origin < length()) {
            String s = substring(origin);
            String i = new Word(s).replace(data, "").get();
            this.data = (origin > 0 ? substring(0, origin) : "") + i;
            // replace(s, i);
            // System.out.println("Origin: " + origin + ", R: '" + i + "', I: '" + data + "', S: '" + s + "', Data: '" + this.data + "'");
        }
        return (L) this;
    }


    public L removeLast(Object data) {
        return removeLast(data != null ? data.toString() : null);
    }

    public L removeLast(IData<?> data) {
        return removeLast(data != null ? data.get() : null);
    }

    public L removeLast(CharSequence data) {
        return remove(data, lastIndexOf(data));
    }


    public L removeAll(boolean data) {
        return removeAll(data, 0);
    }

    public L removeAll(char data) {
        return removeAll(data, 0);
    }

    public L removeAll(double data) {
        return removeAll(data, 0);
    }

    public L removeAll(int data) {
        return removeAll(data, 0);
    }

    public L removeAll(IData<?> data) {
        return removeAll(data, 0);
    }

    public L removeAll(CharSequence data) {
        return removeAll(data, 0);
    }


    public L removeAll(boolean data, int origin) {
        return removeAll(data + "", origin);
    }

    public L removeAll(char data, int origin) {
        return removeAll(data + "", origin);
    }

    public L removeAll(double data, int origin) {
        return removeAll(data + "", origin);
    }

    public L removeAll(int data, int origin) {
        return removeAll(data + "", origin);
    }

    public L removeAll(IData<?> data, int origin) {
        return removeAll(data != null ? data.get() : null, origin);
    }

    public L removeAll(CharSequence data, int origin) {
        if (data != null && !isEmpty() && origin >= 0 && origin < length()) {
            String s = substring(origin);
            String i = new Word(s).replaceAll(data, "").get();
            replace(s, i);
        }
        return (L) this;
    }


    public L replaceAll(CharSequence old, boolean value) {
        return replaceAll(old, String.valueOf(value));
    }

    public L replaceAll(CharSequence old, double value) {
        return replaceAll(old, String.valueOf(value));
    }

    public L replaceAll(CharSequence old, int value) {
        return replaceAll(old, String.valueOf(value));
    }

    public L replaceAll(CharSequence old, IData<?> value) {
        return replaceAll(old, value != null ? value.get() : null);
    }

    public L replaceAll(CharSequence old, CharSequence text) {
        if (old != null && text != null)
            data = data.replaceAll(old.toString(), text.toString());
        return (L) this;
    }


    public char[] toCharArray() {
        return data.toCharArray();
    }

    public char charAt(int index) {
        return data.charAt(index);
    }

    public String substring(int start) {
        return data.substring(start);
    }

    public String substring(int start, int end) {
        return start < end && start >= 0 && start < data.length() && end < data.length() ? data.substring(start, end) : "";
    }


    public int getAlphabetCount(char letter) {
        return getAlphabetCount(letter + "");
    }

    public int getAlphabetCount(Alphabet letter) {
        return getAlphabetCount(letter.data);
    }

    public int getAlphabetCount(CharSequence letter) {
        int count = 0;
        for (char l : toCharArray())
            if (DataManager.isAlphabet(l) && DataManager.isAlphabet(letter) && letter.equals(l + ""))
                count++;
        return count;
    }

    public int getLetterCount(char letter) {
        return getLetterCount(letter + "");
    }

    public int getLetterCount(Letter<?> letter) {
        return getLetterCount(letter.data);
    }

    public int getLetterCount(CharSequence letter) {
        int count = 0;
        for (char l : toCharArray())
            if (letter.equals(l + ""))
                count++;
        return count;
    }

    protected List<Integer> getLetterIndexes(char letter) {
        return getLetterIndexes(letter + "");
    }

    protected List<Integer> getLetterIndexes(CharSequence letter) {
        List<Integer> indexes = new ArrayList<>();
        if (getLetterCount(letter) > 0)
            for (int index = 0; index < length(); index++)
                if (new Alphabet(letter).equals(new Alphabet(charAt(index))))
                    indexes.add(index);
        return indexes;
    }

    protected List<Integer> getNumberIndexes(Number number) {
        List<Integer> indexes = new ArrayList<>();
        if (getNumberCount(number) > 0)
            for (int index = 0; index < length(); index++)
                if (subLetters(index).startsWith(number))
                    indexes.add(index);
        return indexes;
    }

    public int getNumberCount(double number) {
        return getNumberCount(new Number(number));
    }

    public int getNumberCount(int number) {
        return getNumberCount(new Number(number));
    }

    public int getNumberCount(CharSequence number) {
        return getNumberCount(new Number(number));
    }

    public int getNumberCount(Number number) {
        int count = 0;
        for (Number num : extractNumbers())
            if (number.equals(num))
                count++;
        return count;
    }

    public NumbersCount getNumbersCount() {
        List<NumberCount> counts = new LinkedList<>();
        for (Number number : extractNumbers())
            if (!counts.contains(new NumberCount(number, getNumberIndexes(number))))
                counts.add(new NumberCount(number, getNumberIndexes(number)));
        // System.out.println("Inner: " + number + " Index: " + getNumberIndexes(number));
        return new NumbersCount(counts);
    }

    public int getOperatorCount(char operator) {
        return getOperatorCount(new Operator(operator));
    }

    public int getOperatorCount(Operator operator) {
        int count = 0;
        for (Operator op : extractOperators())
            if (op.equals(operator))
                count++;
        return count;
    }

    protected List<Integer> getOperatorIndexes(Operator operator) {
        List<Integer> indexes = new LinkedList<>();
        if (getOperatorCount(operator) > 0)
            for (int index = 0; index < length(); index++)
                if (subLetters(index).startsWith(operator))
                    indexes.add(index);
        return indexes;
    }

    public AlphabetsCount getAlphabetsCount() {
        List<AlphabetCount> counts = new LinkedList<>();
        for (char l : DataManager.ALPHABETS.toCharArray())
            if (contains(l) && !counts.contains(new AlphabetCount(new Alphabet(l), getLetterCount(l), getLetterIndexes(l))))
                counts.add(new AlphabetCount(new Alphabet(l), getLetterCount(l), getLetterIndexes(l)));
        return new AlphabetsCount(counts);
    }

    public LettersCount getLettersCount() {
        List<LetterCount> counts = new LinkedList<>();
        for (char l : data.toCharArray())
            if (contains(l) && !counts.contains(new LetterCount(l, getLetterCount(l), getLetterIndexes(l))))
                counts.add(new LetterCount(l, getLetterCount(l), getLetterIndexes(l)));
        return new LettersCount(counts);
    }

    public OperatorsCount getOperatorsCount() {
        List<OperatorCount> counts = new LinkedList<>();
        for (Operator op : extractOperators())
            if (!counts.contains(new OperatorCount(op, getOperatorCount(op), getOperatorIndexes(op))))
                counts.add(new OperatorCount(op, getOperatorCount(op), getOperatorIndexes(op)));
        return new OperatorsCount(counts);
    }

    public LettersCount getCharactersCount() {
        List<LetterCount> counts = new LinkedList<>();
        for (char l : DataManager.CHARACTERS.toCharArray())
            if (contains(l))
                counts.add(new LetterCount(l, getLetterIndexes(l)));
        return new LettersCount(counts);
    }

    public AlphabetsCount getConsonantsCount() {
        List<AlphabetCount> counts = new LinkedList<>();
        for (char l : DataManager.CONSONANTS.toCharArray())
            if (contains(l))
                counts.add(new AlphabetCount(new Alphabet(l), getLetterIndexes(l)));
        return new AlphabetsCount(counts);
    }

    public LettersCount getFractionsCount() {
        List<LetterCount> counts = new LinkedList<>();
        for (char l : DataManager.FRACTIONS.toCharArray())
            if (contains(l))
                counts.add(new LetterCount(l, getLetterIndexes(l)));
        return new LettersCount(counts);
    }

    public LettersCount getIndexesCount() {
        List<LetterCount> counts = new LinkedList<>();
        for (char l : DataManager.INDEXES.toCharArray())
            if (contains(l))
                counts.add(new LetterCount(l, getLetterIndexes(l)));
        return new LettersCount(counts);
    }

    public AlphabetsCount getVowelsCount() {
        List<AlphabetCount> counts = new LinkedList<>();
        for (char l : DataManager.VOWELS.toCharArray())
            if (contains(l))
                counts.add(new AlphabetCount(new Alphabet(l), getLetterIndexes(l)));
        return new AlphabetsCount(counts);
    }

    public boolean contentEquals(Letters<?> another) {
        return contentEquals(another != null ? another.get() : null);
    }

    public boolean contentEquals(CharSequence texts) {
        return this.data.contentEquals(texts);
    }


    public Letters<?> subLetters(int start) {
        return !isEmpty() && start >= 0 && start < length() ? new Sentence(data.substring(start)) : null;
    }

    public Letters<?> subLetters(IData<?> where) {
        return subLetters(where.get());
    }

    public Letters<?> subLetters(CharSequence where) {
        return subLetters(indexOf(where));
    }

    public Letters<?> subLetters(char letter) {
        return subLetters(letter + "");
    }

    public Letters<?> subLetters(CharSequence where, int start) {
        return subLetters(!isEmpty() && start >= 0 && start < length() ? start + subLetters(start).indexOf(where) : -1);
    }

    public Letters<?> subLetters(CharSequence from, CharSequence to) {
        return subLetters(from, 0, to);
    }

    public Letters<?> subLetters(CharSequence where, int start, int end) {
        return subLetters(!isEmpty() && start >= 0 && start < length() ? start + subLetters(start).indexOf(where) : -1, end);
    }

    public Letters<?> subLetters(CharSequence from, int start, CharSequence to) {
        return subLetters(from, start, (!isEmpty() && start >= 0 && start < length() ? start + subLetters(start).indexOf(from) : -1) < start + subLetters(start).indexOf(to) ? start + subLetters(start).indexOf(to) : -1);
    }

    public Letters<?> subLetters(int start, int end) {
        return !isEmpty() && start >= 0 && start < length() && start < end ? new Sentence(data.substring(start, end)) : null;
    }


    public boolean startsWith(Object value) {
        return startsWith(String.valueOf(value));
    }

    public boolean startsWith(IData<?> value) {
        return value != null && startsWith(value.get());
    }

    public boolean startsWith(CharSequence letters) {
        return letters != null && data.startsWith(letters.toString());
    }


    public boolean endsWith(Object value) {
        return endsWith(String.valueOf(value));
    }

    public boolean endsWith(IData<?> value) {
        return value != null && endsWith(value.get());
    }

    public boolean endsWith(CharSequence letters) {
        return letters != null && data.endsWith(letters.toString());
    }


    public boolean contains(Alphabet letter) {
        for (Alphabet a : extractAlphabets())
            if (a.equals(letter))
                return true;
        return false;
    }

    public boolean containsIgnoreCase(CharSequence letters) {
        return new Word(data).toLowerCase().contains(new Word(letters).toLowerCase());
    }

    public boolean containsIgnoreCase(IData<?> data) {
        return containsIgnoreCase(data != null ? data.get() : null);
    }

    public boolean containsIgnoreCase(Object data) {
        return containsIgnoreCase(data != null ? data.toString() : null);
    }

    public boolean hasAlphabet() {
        for (char c : toCharArray())
            if (DataManager.isAlphabet(c))
                return true;
        return false;
    }

    public boolean hasNumber() {
        for (char c : toCharArray())
            if (DataManager.isNumber(c))
                return true;
        return false;
    }

    public boolean hasFraction() {
        for (char c : toCharArray())
            if (DataManager.isFraction(c))
                return true;
        return false;
    }

    public L reverseLetters() {
        StringBuilder reverse = new StringBuilder();
        if (!isEmpty())
            for (int n = length() - 1; n >= 0; n--)
                reverse.append(charAt(n));
        data = reverse.toString();
        return (L) this;
    }

    public L shuffleLetters() {
        Word word = new Word(data),
                shuffled = new Word();
        if (word.isNotEmpty()) {
            int length = word.length();
            while (shuffled.length() < length && word.isNotEmpty()) {
                int index = word.length() > 1 ? Random.nextInt(word.length() - 1) : 0;
                shuffled.append(word.charAt(index));
                word.remove(word.charAt(index), index);
            }
            data = shuffled.get();
        }
        return (L) this;
    }

    public Letters<?> shuffleLetters(int length) {
        return length < length() && length > 0 ? shuffleLetters().subLetters(0, length) : shuffleLetters().subLetters(0);
    }

    public void extractPossibleWords(int length, OnWordsExtractedListener listener) {
        final List<Word> words = new ArrayList<>();
        Thread extractor = new Thread(() -> extractPossibleWords(length, words));
        extractor.start();
        AtomicLong start = new AtomicLong(System.currentTimeMillis());
        final int[] counter = {0};

        new Thread(() -> {
            while (extractor.getState().equals(Thread.State.RUNNABLE)) {
                long current = System.currentTimeMillis();

                // Check if it's still extracting
                if ((current - start.get()) % 1000 == 0 && !extractor.getState().equals(Thread.State.TERMINATED)) {
                    int duration = (int) ((current - start.get()) / 1000);
                    if (duration - counter[0] >= 1) {
                        listener.onExtracting(new Words(words, this), duration);
                        counter[0] = duration;
                    }
                }

                if (extractor.getState().equals(Thread.State.TERMINATED)) {
                    List<Word> temp = new ArrayList<>();
                    words.sort(Comparator.comparing(Word::get));
                    for (Word word : words)
                        if (!new Words(temp, this).contains(word)) temp.add(word);
                    listener.onExtracted(new Words(temp, this), System.currentTimeMillis() - start.get());
                    break;
                }
            }
        }).start();
    }

    private void extractPossibleWords(int length, List<Word> words) {
        if (length > length() || length == 0) return;
        Words dWords = new WordDictionary().getWords();
        for (int i = 1; i <= length; i++) {
            Word word = new Word(data);
            List<Word> wTemp = new ArrayList<>();
            long possibleOutcome = Util.getCombination(length(), i);
            for (int n = 1; n <= possibleOutcome; n++) {
                Letters<?> letters = new Word(word.get()).shuffleLetters(i);
                while (new Words(wTemp, word).contains(letters) && new Words(wTemp, word).getCount(letters) >= countLetters(letters))
                    letters = word.shuffleLetters(i);
                wTemp.add(new Word(letters.get()));
                if (dWords.contains(letters.get()) && !new Words(words, word).contains(letters))
                    words.add(new Word(letters.toLowerCase().get()));
            }
        }

        words.sort(Comparator.comparing(Word::get));
    }

    // get the number of ways the letters can be rearranged not ignoring repetitions
    private int countLetters(Letters<?> letters) {
        int count = 1;
        List<String> temp = new ArrayList<>();
        for (char c : letters.toCharArray())
            if (!temp.contains(c + "")) {
                count *= letters.count(c);
                temp.add(c + "");
            }
        return count;
    }

    public boolean hasIndex() {
        for (char c : toCharArray())
            if (DataManager.isIndex(c))
                return true;
        return false;
    }

    public boolean hasOperator() {
        for (char c : toCharArray())
            if (DataManager.isOperator(c))
                return true;
        return false;
    }

    public boolean isUpperCase() {
        for (char letter : toCharArray())
            if (DataManager.isAlphabet(letter) && !DataManager.isUpperCase(letter))
                return false;
        return true;
    }

    public boolean isLowerCase() {
        for (char letter : toCharArray())
            if (DataManager.isAlphabet(letter) && !DataManager.isLowerCase(letter))
                return false;
        return true;
    }

    public int getConsonantCount() {
        int count = 0;
        for (char letter : toCharArray())
            if (DataManager.isConsonant(letter))
                count++;
        return count;
    }

    public int getVowelCount() {
        int count = 0;
        for (char letter : toCharArray())
            if (DataManager.isVowel(letter))
                count++;
        return count;
    }

    public boolean hasConsonant() {
        for (char letter : toCharArray())
            if (DataManager.isConsonant(letter))
                return true;
        return false;
    }

    public boolean hasVowel() {
        for (char letter : toCharArray())
            if (DataManager.isVowel(letter))
                return true;
        return false;
    }

    public L toUpperCase() {
        data = data.toUpperCase();
        return (L) this;
    }

    public L toLowerCase() {
        data = data.toLowerCase();
        return (L) this;
    }

    public L trim() {
        data = data.trim();
        return (L) this;
    }

    public L toSentenceCase() {
        if (getLetterCount(".") <= 1) {
            if (!isEmpty() && DataManager.isAlphabet(data.charAt(0)))
                data = new Alphabet(data.charAt(0)).toUpperCase().get() + (length() >= 2 ? data.substring(1) : "");
        } else {
            Split split = split(". ");
            if (!split.isEmpty())
                data = "";
            for (String sentence : split)
                if (!sentence.isEmpty()) {
                    if (!data.isEmpty())
                        data += ".";
                    Word word = new Word(sentence);
                    int index = word.getNextValidLetterIndex(0);
                    if (DataManager.isAlphabet(word.charAt(index))) {
                        data += word.replace(word.charAt(index) + "", new Alphabet(word.charAt(index)).toUpperCase()).get();
                    } else
                        data += sentence;
                }
        }
        return (L) this;
    }

    public int getNextValidLetterIndex(int start) {
        if (start < 0 || start >= length())
            return -1;
        for (int index = 0; index < substring(start).length(); index++) {
            if (!"\n\t ".contains(charAt(index) + ""))
                return index;
        }
        return -1;
    }

    public boolean hasNextValidLetterIndex(int start) {
        return getNextValidLetterIndex(start) > -1;
    }

    @SafeVarargs
    public final <O> Split split(CharSequence delimiter, O... mustContain) {
        return split(delimiter, "", "", mustContain);
    }

    @SafeVarargs
    public final <O> Split split(CharSequence delimiter, CharSequence ifContain, CharSequence mustEnd, O... mustContain) {
        return split(delimiter, ifContain, mustEnd, Arrays.asList(mustContain), new ArrayList<>());
    }

    /**
     * Split data with a delimiter and each split element must contain some elements
     * @param delimiter the element for splitting the data
     * @param ifContain if the element of split contains this
     * @param mustEnd element of split must end with this if it contains <b>"ifContain"</b>
     * @param mustContain list of what must be included in each split content
     * @param canContain list of what can be included in each split content
     * @return a list of strings in a split object
     */
    public <O> Split split(CharSequence delimiter, CharSequence ifContain, CharSequence mustEnd, List<O> mustContain, List<O> canContain) {
        List<String> split = new ArrayList<>();
        splitData(delimiter != null ? delimiter : "", ifContain != null ? ifContain : "", mustEnd != null ? mustEnd : "", mustContain != null ? Util.toList(mustContain) : new ArrayList<>(), canContain != null ? Util.toList(canContain) : new ArrayList<>(), split);
        return new Split() {
            @Override
            public int size() {
                return split.size();
            }

            @Override
            public boolean isEmpty() {
                return split.isEmpty();
            }

            @Override
            public boolean isNotEmpty() {
                return !isEmpty();
            }

            @Override
            public <O> boolean contains(O data) {
                return contains(data != null ? data.toString() : null);
            }

            @Override
            public boolean contains(IData data) {
                return contains(data != null ? data.get() : null);
            }

            @Override
            public boolean contains(CharSequence data) {
                return data != null && split.contains(data.toString());
            }

            @Override
            public boolean equals(Split another) {
                return another != null && another.getAll().equals(split);
            }

            @Override
            public Letters getData() {
                return Letters.this;
            }

            @Override
            public List<String> getAll() {
                return new ArrayList<>(split);
            }

            @Override
            public String get(int index) {
                return index < size() && index >= 0 ? split.get(index) : null;
            }

            @Override
            public Stream<String> stream() {
                return split.stream();
            }

            @Override
            public String toString() {
                return new Word(split.toString().trim()).remove("[").remove("]", split.toString().length() - 2).get();
            }

            @Override
            public Iterator<String> iterator() {
                return split.iterator();
            }
        };
    }

    protected void splitData(CharSequence delimiter, CharSequence ifContain, CharSequence mustEnd, List<String> mustContain, List<String> canContain, List<String> split) {
        int start, end = -1;
        /*if (delimiter.toString().contentEquals("%#0f")) {
            Numbers numbers = extractNumbers();
            String last = "";
            for (int n = 0; n < numbers.size(); n++) {
                start = end + 1 + last.length();
                end = n < numbers.size() ? indexOf(numbers.get(n), start) : length();
                last = numbers.get(n + 1).get();
                String s;
                if (end >= length()) {
                    s = substring(start);
                    split.add(s);
                    break;
                }
                s = substring(start, end);
                split.add(s);
            }
        } else if (delimiter.toString().contains("%#0f")) {
            if (containsData(delimiter)) {
                for (String d : splitDelimiter(delimiter))
                    splitData(d, mustContain, split);
            }
        } else*/
        // System.out.println("Delimiters: " + splitDelimiter(delimiter));
        if (containsData(delimiter)) {
            while (end < length() - 1) {
                start = end > -1 ? end + delimiter.length() : 0;
                end = endIndexOf(delimiter, start, ifContain, mustEnd, mustContain);
                // System.out.println("Start: " + start + ", End: " + end);
                String s;
                if (end >= length() - 1 || end < 0 || end < start) {
                    s = substring(start);
                    if (startsWithDelimiter(s, delimiter))
                        s = s.substring(0, s.length() - Objects.requireNonNull(getStartDelimiter(s, delimiter)).length());
                    else if (endsWithDelimiter(s, delimiter))
                        s = s.substring(0, s.length() - Objects.requireNonNull(getEndDelimiter(s, delimiter)).length());
                    // System.out.println("Line0: " + s + "\n");
                    split.add(s);
                    break;
                }
                s = substring(start, end);
                // System.out.println("Line: " + s + "\n");
                split.add(s);
            }
        } else
            split.add(data);
        // System.out.println("Splitter: " + split);
    }

    private String getDelimiterAt(int index) {
        return index > -1 && index < length() ? substring(index) : null;
    }

    private String getEndDelimiter(String source, CharSequence delimiter) {
        for (String d : splitDelimiter(delimiter))
            if (source.endsWith(d)) return d;
        return null;
    }

    private String getStartDelimiter(String source, CharSequence delimiter) {
        for (String d : splitDelimiter(delimiter))
            if (source.startsWith(d)) return d;
        return null;
    }

    private boolean endsWithDelimiter(String source, CharSequence delimiter) {
        return getEndDelimiter(source, delimiter) != null;
    }

    private boolean startsWithDelimiter(String source, CharSequence delimiter) {
        return getStartDelimiter(source, delimiter) != null;
    }

    private int endIndexOf(CharSequence delimiter, int start, CharSequence ifContain, CharSequence mustEnd, List<String> mustContain) {
        int end = -1, temp = start;
        while (temp < length() - 1) {
            if (temp > length() -1) return -1;
            end = getNextIndex(temp, splitDelimiter(delimiter));
            // System.out.println("Starter: " + start + ", Ender: " + end);
            temp = end + 1;
            if (end == -1) return -1;
            // System.out.println(substring(start, end));
            Word sub = new Word(substring(start, end));
            if (containsAll(sub.get(), mustContain))
                if (!ifContain.isEmpty() && sub.contains(ifContain)) {
                    String ender = getEndDelimiter(sub.get(), delimiter);
                    if (mustEnd.isEmpty() || (ender != null ? sub.subLetters(0, sub.lastIndexOf(ender)) : sub.trim()).endsWith(mustEnd.toString()))
                        return end;
                } else return end;
        }
        return end;
    }

    private int startIndexOf(CharSequence delimiter, int end) {
        return end + Objects.requireNonNull(getDelimiterAt(end)).length();
    }

    private boolean containsAll(String source, List<String> list) {
        for (String s : list)
            if (!source.contains(s)) return false;
        return true;
    }

    private List<String> splitDelimiter(CharSequence delimiter) {
        List<String> result = new ArrayList<>();
        Word w = new Word(delimiter);
        if (w.endsWith("%&#")) result.add(w.substring(0, w.indexOf("%&#")));
        else if (w.startsWith("%&#")) result.add(w.substring("%&#".length()));
        else if (w.contains("%&#")){
            System.out.println("Word: " + w.get());
            result.add(w.substring(0, w.indexOf("%&#")));
            result.add(w.substring(w.indexOf("%&#") + "%&#".length()));
        } else result.add(delimiter.toString());
        return result;
    }

    private int getNextIndex(int start, List<String> delimiters) {
        int min = -1;
        for (String delimiter : delimiters) {
            Util.MinMax<Integer> temp;
            if (delimiter.contentEquals("%&#")) temp = new Util.MinMax<>(min, getNextNumberIndex(start) > -1 ? getNextNumberIndex(start) + getNextNumber(start).get().length() : -1);
            else temp = new Util.MinMax<>(min, indexOf(delimiter, start));
            min = temp.min() > -1 ? temp.min() : temp.max();
        }
        return min;
    }

    private boolean containsData(CharSequence delimiter) {
        for (String d : splitDelimiter(delimiter))
            if (!contains(delimiter)) return false;
        return true;
    }


    public Words extractWords() {
        return new Words(DataManager.extractWords(data), this);
    }

    public Words getWords() {
        return new Words(DataManager.getWords(data), this);
    }

    public Words getWordsOnly() {
        return new Words(DataManager.getWordsOnly(data), this);
    }

    public int lastIndexOf(CharSequence letters) {
        return data != null ? data.lastIndexOf(letters.toString()) : -1;
    }


    public interface OnWordsExtractedListener {
        void onExtracted(Words words, long durationInMillis);

        void onExtracting(Words words, int durationSecs);
    }


    public interface Split extends Iterable<String> {
        int size();

        boolean isEmpty();

        boolean isNotEmpty();

        <O> boolean contains(O data);

        boolean contains(IData data);

        boolean contains(CharSequence data);

        boolean equals(Split another);

        Letters getData();

        List<String> getAll();

        String get(int index);

        Stream<String> stream();
    }

    public static class Words extends DataList<Words, Word> {

        public Words(List<Word> words, Letters<? extends Letters> parent) {
            super(parent, words);
        }

        public Word get(CharSequence word) {
            return get(indexOf(word));
        }

        public boolean contains(CharSequence word) {
            return contains(new Word(word));
        }

        public int indexOf(CharSequence letters) {
            return indexOf(new Word(letters));
        }

        public Word getFirst() {
            return isNotEmpty() ? get(0) : new Word("");
        }

        public Word getLast() {
            return isNotEmpty() ? get(size() - 1) : new Word("");
        }

    }

    public static class Alphabets extends DataList<Alphabets, Alphabet> {
        public Alphabets(Letters parent, List<Alphabet> items) {
            super(parent, items);
        }

        public boolean contains(char alphabet) {
            return contains(new Alphabet(alphabet));
        }

        public boolean contains(CharSequence alphabet) {
            return contains(new Alphabet(alphabet));
        }

        public int indexOf(char alphabet) {
            return indexOf(new Alphabet(alphabet));
        }

        public int indexOf(CharSequence alphabet) {
            return indexOf(new Alphabet(alphabet));
        }
    }

    public static class Numbers extends DataList<Numbers, Number> {
        public Numbers(Letters parent, List<Number> items) {
            super(parent, items);
        }

        public boolean contains(CharSequence number) {
            return contains(new Number(number));
        }

        public boolean contains(char number) {
            return contains(new Number(number));
        }

        public boolean contains(double number) {
            return contains(new Number(number));
        }

        public int indexOf(CharSequence number) {
            return indexOf(new Number(number));
        }

        public int indexOf(double number) {
            return indexOf(new Number(number));
        }

        public int indexOf(char number) {
            return indexOf(new Number(number));
        }
    }

    public static class LetterList extends DataList<LetterList, Letter> {

        public LetterList(Letters parent, List<Letter> items) {
            super(parent, items);
        }

        public boolean contains(IData data) {
            return contains(new Letter<>(data.get()));
        }

        public boolean contains(CharSequence letter) {
            return contains(new Letter<>(letter));
        }

        public boolean contains(char letter) {
            return contains(new Letter<>(letter));
        }

        public int indexOf(CharSequence letter) {
            return indexOf(new Letter<>(letter));
        }

        public int indexOf(char number) {
            return indexOf(new Letter<>(number));
        }

    }


    public static abstract class DataList<I extends DataList, D extends IData> implements Iterable<D> {
        protected List<D> items;
        protected Letters parent;

        public DataList(Letters parent, List<D> items) {
            this.items = items != null ? items : new ArrayList<>();
            this.parent = parent != null ? parent : new Sentence();
        }

        public Letters getParent() {
            return parent;
        }

        public List<D> toList() {
            return stream().toList();
        }

        public List<String> toStringList() {
            return Util.toStringList(items);
        }

        public int size() {
            return items.size();
        }

        public boolean isEmpty() {
            return items.isEmpty();
        }

        public boolean isNotEmpty() {
            return !isEmpty();
        }

        public boolean contains(IData data) {
            if (data != null)
                for (D d : items)
                    if (d.equalsIgnoreType(data))
                        return true;
            return false;
        }

        public boolean hasIndex(int index) {
            return get(index) != null;
        }

        public int indexOf(IData data) {
            int index = -1;
            for (D d : items) {
                index++;
                if (d.equalsIgnoreType(data))
                    return index;
            }
            return -1;
        }

        public int getCount(IData data) {
            int count = 0;
            for (D d : items)
                if (d.equals(data))
                    count++;
            return count;
        }

        public D get(int index) {
            return index >= 0 && index < size() ? items.get(index) : null;
        }

        public D getFirst() {
            return get(0);
        }

        public D getLast() {
            return get(size() - 1);
        }

        public Stream<D> stream() {
            return items.stream();
        }

        @Override
        public String toString() {
            return new Word(Util.toStringList(items).toString()).remove("[").remove("]").get();
        }

        @Override
        public Iterator<D> iterator() {
            return items.iterator();
        }
    }


    public static abstract class Count<C extends Count, D> {
        public final int count;
        protected int index;
        protected final List<Integer> indexes;

        public Count(int count, List<Integer> indexes) {
            this.count = Math.abs(count);
            this.indexes = indexes;
            index = 0;
        }

        public int nextIndex() {
            return index < indexes.size() - 2 ? get(++index) : indexes.isEmpty() ? get(index) : -1;
        }

        public int get(int index) {
            return !indexes.isEmpty() && index >= 0 && index < indexes.size() ? indexes.get(index) : -1;
        }

        public abstract D getData();

        public int getCount() {
            return count;
        }

        public int previousIndex() {
            return index > 0 ? get(--index) : !indexes.isEmpty() ? get(index) : -1;
        }

        public boolean hasIndex(int index) {
            return indexes.contains(index);
        }

        public boolean equalsData(D data) {
            return data.equals(getData());
        }

        public boolean equals(C count) {
            return count.getData().equals(getData());
        }
    }


    public static abstract class DataCount<I extends DataCount<I, C, D>, C extends Count<C, D>, D extends IData> implements Iterable<C> {
        protected final List<C> counts;

        public DataCount(List<C> counts) {
            this.counts = counts;
        }

        public C get(int index) {
            return hasIndex(index) ? counts.get(index - 1) : null;
        }

        public C getCount(D data) {
            for (C count : counts)
                if (count.getData().equals(data))
                    return count;
            return null;
        }

        public D getData(int index) {
            return hasIndex(index) ? get(index).getData() : null;
        }

        public C getMostOccurredCount() {
            C data = null;
            if (isNotEmpty()) {
                int max = 0;
                for (C count : counts)
                    if (count.count > max) {
                        max = count.count;
                        data = count;
                    }
            }
            return data;
        }

        public D getMostOccurredData() {
            D data = null;
            if (isNotEmpty()) {
                int max = 0;
                for (C count : counts)
                    if (count.count > max) {
                        max = count.count;
                        data = count.getData();
                    }
            }
            return data;
        }

        @Override
        public Iterator<C> iterator() {
            return counts.iterator();
        }

        public int size() {
            return counts.size();
        }

        public boolean contains(D data) {
            return getCount(data) != null;
        }

        public boolean contains(C count) {
            return counts.contains(count);
        }

        public boolean hasIndex(int index) {
            return isNotEmpty() && index > 0 && index <= size();
        }

        public boolean isEmpty() {
            return counts.isEmpty();
        }

        public boolean isNotEmpty() {
            return !isEmpty();
        }

        public boolean equals(DataCount counts) {
            return counts.counts.equals(this.counts);
        }

        @Override
        public String toString() {
            return new Word(counts.toString()).remove("[").remove("]").get();
        }
    }

    public static class LettersCount extends DataCount<LettersCount, LetterCount, Letter> {

        public LettersCount(List<LetterCount> counts) {
            super(counts);
            this.counts.sort(Comparator.comparing(l -> String.valueOf(l.index)));
        }

        public LetterCount getCount(char letter) {
            return getCount(new Letter(letter));
        }

        public LetterCount getCount(int letter) {
            return getCount(new Letter(letter + ""));
        }

        public boolean contains(char letter) {
            return contains(new Letter(letter));
        }

    }

    public static class OperatorsCount extends DataCount<OperatorsCount, OperatorCount, Operator> {

        public OperatorsCount(List<OperatorCount> counts) {
            super(counts);
        }

        public OperatorCount getCount(char operator) {
            return getCount(new Operator(operator));
        }

        @Override
        public OperatorCount getCount(Operator data) {
            OperatorCount count = super.getCount(data);
            return count != null ? count : new OperatorCount(data, 0);
        }

        public boolean contains(char operator) {
            return contains(new Operator(operator));
        }

        public boolean contains(CharSequence operator) {
            return contains(new Operator(operator));
        }

    }

    public static class NumbersCount extends DataCount<NumbersCount, NumberCount, Number> {
        public NumbersCount(List<NumberCount> counts) {
            super(counts);
        }

        public NumberCount getCount(double number) {
            return getCount(new Number(number));
        }

        public NumberCount getCount(int number) {
            return getCount(new Number(number));
        }

        @Override
        public NumberCount getCount(Number data) {
            NumberCount count = super.getCount(data);
            return count != null ? count : new NumberCount(data, 0);
        }

        public boolean contains(double number) {
            return contains(new Number(number));
        }

        public boolean contains(int number) {
            return contains(new Number(number));
        }

    }

    public static class AlphabetsCount extends DataCount<AlphabetsCount, AlphabetCount, Alphabet> {
        public AlphabetsCount(List<AlphabetCount> counts) {
            super(counts);
        }

        public AlphabetCount getCount(char alphabet) {
            return getCount(new Alphabet(alphabet));
        }

        @Override
        public AlphabetCount getCount(Alphabet data) {
            AlphabetCount count = super.getCount(data);
            return count != null ? count : new AlphabetCount(data, 0);
        }

        public boolean contains(char alphabet) {
            return contains(new Alphabet(alphabet));
        }
    }


    public static class AlphabetCount extends Count<AlphabetCount, Alphabet> {
        public final Alphabet alphabet;

        public AlphabetCount(Alphabet data, List<Integer> indexes) {
            this(data, indexes.size(), indexes);
        }

        public AlphabetCount(Alphabet data, int count, List<Integer> indexes) {
            super(count, indexes);
            this.alphabet = data;
        }

        public AlphabetCount(Alphabet data, int count, Integer... indexes) {
            this(data, count, Arrays.asList(indexes));
        }

        @Override
        public Alphabet getData() {
            return alphabet;
        }

        @Override
        public String toString() {
            return "\"" + alphabet + "\": " + count;
        }
    }

    public static class LetterCount extends Count<LetterCount, Letter> {
        public final char letter;

        public LetterCount(char letter, List<Integer> indexes) {
            this(letter, indexes.size(), indexes);
        }

        public LetterCount(char letter, int count, List<Integer> indexes) {
            super(count, indexes);
            this.letter = letter;
        }

        public LetterCount(char letter, int count, Integer... indexes) {
            this(letter, count, Arrays.asList(indexes));
        }

        public boolean isAlphabet() {
            return DataManager.isAlphabet(letter);
        }

        public boolean isCharacter() {
            return DataManager.isCharacter(letter);
        }

        public boolean isFraction() {
            return DataManager.isFraction(letter);
        }

        public boolean isIndex() {
            return DataManager.isIndex(letter);
        }

        public boolean isNumber() {
            return DataManager.isNumber(letter);
        }

        public Letter getData() {
            return new Letter(letter);
        }

        public Alphabet getAlphabet() {
            return new Alphabet(letter);
        }

        @Override
        public String toString() {
            return "\"" + letter + "\": " + count;
        }
    }

    public static class OperatorCount extends Count<OperatorCount, Operator> {
        public final Operator operator;

        public OperatorCount(Operator operator, int count, List<Integer> indexes) {
            super(count, indexes);
            this.operator = operator;
        }

        public OperatorCount(Operator operator, int count, Integer... indexes) {
            this(operator, count, Arrays.asList(indexes));
        }

        public boolean equals(char operator) {
            return equalsData(new Operator(operator));
        }

        public boolean equals(CharSequence operator) {
            return equalsData(new Operator(operator));
        }

        @Override
        public Operator getData() {
            return operator;
        }

        @Override
        public String toString() {
            return "\"" + operator + "\": " + count;
        }
    }

    public static class NumberCount extends Count<NumberCount, Number> {
        public final Number number;

        public NumberCount(Number number, List<Integer> indexes) {
            this(number, indexes.size(), indexes);
        }

        public NumberCount(Number number, int count, List<Integer> indexes) {
            super(count, indexes);
            this.number = number;
        }

        public NumberCount(Number number, int count, Integer... indexes) {
            this(number, count, Arrays.asList(indexes));
        }

        public boolean isInteger() {
            return number.isInteger();
        }

        public boolean isDecimal() {
            return number.isDecimal();
        }

        public Fraction getFraction() {
            return number.getFraction();
        }

        public int getInteger() {
            return number.getInteger();
        }

        public double getDouble() {
            return number.getDouble();
        }

        public String getNumber() {
            return number.get();
        }

        @Override
        public Number getData() {
            return number;
        }

        @Override
        public String toString() {
            return "\"" + number + "\": " + count;
        }
    }
}

package nx.peter.java.dictionary;

import nx.peter.java.storage.FileManager;
import nx.peter.java.util.Random;
import nx.peter.java.util.Util;
import nx.peter.java.util.data.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface Dictionary {
    Word generateWord();
    Word getFirstWord();
    Word getLastWord();
    Word nextWord(IData<?> word);
    Word nextWord(CharSequence word);
    Word previousWord(IData<?> word);
    Word previousWord(CharSequence word);
    boolean isLastWord(IData<?> word);
    boolean isFirstWord(IData<?> word);
    Word getWord(CharSequence word);
    boolean containsWord(IData<?> word);
    boolean containsWord(CharSequence word);
    int getWordCount(CharSequence word);
    int getWordCount(IData<?> word);
    int getWordCount();



    abstract class Builder implements Dictionary {
        protected List<WordMeaning> dictionary;
        private Type type;
        private Texts texts;

        public static final String RAW_DICTIONARY = "dictionary.txt";
        public static final String RAW_WORD = "words.txt";
        public static final String RAW_NAME = "names.txt";
        public static final String RAW_BANK = "banks.txt";
        public static final String RAW_ADJECTIVE = "adjectives.txt";
        public static final String RAW_ADVERB = "adverbs.txt";
        public static final String RAW_NOUN  = "nouns.txt";
        public static final String RAW_VERB = "verbs.txt";
        private static final String RAW_COUNTRY = "countries.txt";

        public static final String ROOT_PATH = "src/nx/peter/store/dictionary/";

        public Builder(Type type) {
            reset();
            setFilePath(type);
            format();
        }


        protected void reset() {
            type = null;
            texts = new Texts();
            dictionary = new ArrayList<>();
        }

        protected void format() {
            if (type != null) {
                List<String> lines = FileManager.readLines(getFilePath());
                texts = new Texts(Util.toString(lines));
                for (String line : lines) {
                    if (type.equals(Type.Dictionary)) {
                        String[] split = line.split(" - ");
                        Word word = new Word(split[0].trim());
                        word.setPartOfSpeech(getPartOfSpeech(split[1].charAt(0)));
                        Sentence meaning = new Sentence(split[1].substring(2).trim());
                        add(new IWordMeaning(word, meaning));
                    } else
                        add(new IWordMeaning(new Word(line)));
                }
            }
        }

        private Word.PartOfSpeech getPartOfSpeech(char letter) {
            return switch (letter) {
                case 'n' -> Word.PartOfSpeech.Noun;
                case 'v' -> Word.PartOfSpeech.Verb;
                case 'j' -> Word.PartOfSpeech.Adjective;
                case 'a' -> Word.PartOfSpeech.Adverb;
                case 'p' -> Word.PartOfSpeech.Pronoun;
                default -> Word.PartOfSpeech.Unknown;
            };
        }

        protected void set(List<WordMeaning> dictionary) {
            this.dictionary = dictionary;
        }

        protected void set(WordMeaning... dictionary) {
            set(Arrays.asList(dictionary));
        }

        protected void add(List<WordMeaning> dictionary) {
            this.dictionary.addAll(dictionary);
        }

        protected void add(WordMeaning... dictionary) {
            add(Arrays.asList(dictionary));
        }

        protected void setFilePath(Type type) {
            this.type = type;
        }

        protected String getFilePath() {
            return switch (type) {
                case Adjective -> ROOT_PATH + RAW_ADJECTIVE;
                case Adverb -> ROOT_PATH + RAW_ADVERB;
                case Bank -> ROOT_PATH + RAW_BANK;
                case Dictionary -> ROOT_PATH + RAW_DICTIONARY;
                case Name -> ROOT_PATH + RAW_NAME;
                case Noun -> ROOT_PATH + RAW_NOUN;
                case Verb -> ROOT_PATH + RAW_VERB;
                case Word -> ROOT_PATH + RAW_WORD;
                case Country -> ROOT_PATH + RAW_COUNTRY;
            };
        }


        public Word getFirstWord() {
            return isNotEmpty() ? dictionary.get(0).getWord() : null;
        }

        public Word getLastWord() {
            return isNotEmpty() ? dictionary.get(getWordCount() - 1).getWord() : null;
        }

        public Word nextWord(CharSequence after) {
            if (containsWord(after))
                if (indexOf(after) + 1 < getWordCount())
                    return getWords().get(indexOf(after) + 1);
            return null;
        }

        public boolean isLastWord(IData<?> word) {
            return getLastWord().equalsIgnoreCase(word);
        }

        public boolean isFirstWord(IData<?> word) {
            return getFirstWord().equalsIgnoreCase(word);
        }

        public Word nextWord(IData<?> after) {
            return nextWord(after != null ? after.get() : null);
        }

        public Word previousWord(CharSequence before) {
            if (containsWord(before))
                if (indexOf(before) - 1 >= 0)
                    return getWords().get(indexOf(before) + 1);
            return null;
        }

        public boolean isFirstWord(CharSequence word) {
            return word != null && isFirstWord(new Word(word));
        }

        public boolean isLastWord(CharSequence word) {
            return word != null && isLastWord(new Word(word));
        }

        public Word previousWord(IData<?> before) {
            return nextWord(before != null ? before.get() : null);
        }

        public Letters.Words getWords() {
            List<Word> words = new ArrayList<>();
            for (WordMeaning detail : dictionary)
                words.add(detail.getWord());
            return new Letters.Words(words, texts);
        }

        public List<String> getStringWords() {
            return Util.toStringList(getWords().toList());
        }

        public String[] getStringArrayWords() {
            return Util.toArray(getStringWords());
        }

        public boolean containsWord(CharSequence word) {
            return word != null && containsWord(new Word(word));
        }

        public boolean containsWord(IData<?> word) {
            if (word == null) return false;
            for (WordMeaning w : dictionary)
                if (w.getWord().equalsIgnoreCase(word))
                    return true;
            return false;
        }

        public int getWordCount(CharSequence word) {
            int count = containsWord(word) ? 1 : 0;
            for (WordMeaning m : dictionary)
                count += m.getMeaning().wordCount(word);
            return count;
        }

        public int getWordCount(IData<?> word) {
            return getWordCount(word != null ? word.get() : null);
        }

        protected int indexOf(Word word) {
            for (int n = 0; n < getWords().size(); n++)
                if (getWords().get(n).equalsIgnoreCase(word))
                    return n;
            return -1;
        }

        protected int indexOf(CharSequence word) {
            return indexOf(new Word(word));
        }

        public Word getWord(CharSequence word) {
            for (WordMeaning w : dictionary)
                if (w.equalsWord(new Word(word)))
                    return w.getWord();
            return null;
        }

        public Word getWord(IData<?> word) {
            return word != null ? getWord(word.get()) : null;
        }

        public Word generateWord() {
            return dictionary.get(Random.nextInt(getWordCount() - 1)).getWord();
        }

        public int getWordCount() {
            return dictionary.size();
        }

        public boolean isEmpty() {
            return dictionary.isEmpty();
        }

        public boolean isNotEmpty() {
            return !isEmpty();
        }


        protected static class IWordMeaning implements WordMeaning {
            protected Word word;
            protected Sentence meaning;

            public IWordMeaning(Word word) {
                this(word, new Sentence());
            }

            public IWordMeaning(Word word, Sentence meaning) {
                this.word = word;
                this.meaning = meaning;
            }

            @Override
            public Word getWord() {
                return word;
            }

            @Override
            public Sentence getMeaning() {
                return meaning;
            }

            @Override
            public boolean isValid() {
                return word != null && word.isValid();
            }

            @Override
            public boolean equalsWord(IData<?> word) {
                return this.word.equalsIgnoreCase(word);
            }

            @Override
            public boolean equalsMeaning(IData<?> meaning) {
                return this.meaning.equalsIgnoreCase(meaning);
            }

            @Override
            public boolean equals(WordMeaning another) {
                return another != null && equalsMeaning(another.getMeaning()) && equalsWord(another.getWord());
            }
        }
    }

    abstract class FullBuilder extends Builder {
        public FullBuilder() {
            super(Type.Dictionary);
        }


        public Sentence getMeaning(IData<?> word) {
            return getWordMeaning(word).getMeaning().toSentenceCase();
        }

        public WordMeaning getWordMeaning(IData<?> word) {
            return containsWord(word) ? dictionary.get(indexOf(word)) : new IWordMeaning(new Word(word.get()));
        }

        public boolean containsMeaning(CharSequence meaning) {
            return containsMeaning(new Sentence(meaning));
        }

        public boolean containsMeaning(IData<?> meaning) {
            boolean isTrue = false;
            for (WordMeaning w : dictionary)
                if (w.getMeaning().equalsIgnoreCase(meaning)) {
                    isTrue = true;
                    break;
                }
            return isTrue;
        }

        public ISentence.Sentences getMeanings() {
            List<Sentence> meanings = new ArrayList<>();
            for (WordMeaning detail : dictionary)
                meanings.add(detail.getMeaning());
            return new ISentence.Sentences(meanings);
        }

        public List<String> getStringMeanings() {
            return Util.toStringList(getMeanings().getSentences());
        }

        public String[] getStringArrayMeanings() {
            return Util.toStringArray(getStringMeanings());
        }

    }

    enum Type {
        Adjective,
        Adverb,
        Bank,
        Dictionary,
        Name,
        Noun,
        Verb,
        Word,
        Country
    }

    interface WordMeaning {
        Word getWord();
        Sentence getMeaning();
        boolean isValid();
        boolean equalsWord(IData<?> word);
        boolean equals(WordMeaning another);
        boolean equalsMeaning(IData<?> meaning);
    }
}
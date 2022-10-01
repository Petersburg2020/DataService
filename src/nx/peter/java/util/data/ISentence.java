package nx.peter.java.util.data;

import nx.peter.java.dictionary.WordDictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public abstract class ISentence<S extends ISentence> extends Letters<S> {
    public enum SentenceType {
        Simple,
        Phrase,
        Multiple,
        Question,
        Exclamation
    }

    public enum SentenceError {
        SyntaxError, GrammaticalError, None
    }


    protected SentenceType sentenceType;

    public ISentence() {
        super();
    }

    public ISentence(CharSequence sentence) {
        super(sentence);
    }

    @Override
    public S reset() {
        super.reset();
        return setSentenceType(SentenceType.Simple);
    }

    public S setSentenceType(SentenceType sentenceType) {
        this.sentenceType = sentenceType;
        return (S) this;
    }

    public SentenceType getSentenceType() {
        return sentenceType;
    }

    public boolean isQuestionSentence() {
        return data.endsWith("?");
    }

    public boolean isExclamatorySentence() {
        return data.endsWith("!");
    }

    public boolean isNormalSentence() {
        return data.endsWith(".");
    }

    public boolean isCompoundSentence() {
        return (isNormalSentence() && wordCount(". ") > 1) || (isNormalSentence() && contains("? ")) || (isNormalSentence() && contains("! ")) ||
                (isExclamatorySentence() && contains(". ")) || (isExclamatorySentence() && contains("? ")) || (isExclamatorySentence() && wordCount("! ") > 1) ||
                (isQuestionSentence() && contains(". ")) || (isQuestionSentence() && wordCount("? ") > 1) || (isQuestionSentence() && contains("! "));
    }

    public boolean isUnclassifiedSentence() {
        return !isExclamatorySentence() && !isNormalSentence() && !isQuestionSentence();
    }


    public boolean containsWord(Word word) {
        return getWords().contains(word);
    }

    public int wordCount(CharSequence word) {
        int noOfOccurrence = 0;
        String strWord = word.toString();
        int index = data.indexOf(strWord);
        if (contains(word))
            while (index > -1) {
                index = data.indexOf(strWord, index);
                if (index <= -1)
                    break;
                index += strWord.length();
                noOfOccurrence++;
            }
        return noOfOccurrence;
    }

    public int wordCount(Word word) {
        return wordCount(word.get());
    }

    public int wordCountIgnoreCase(CharSequence word) {
        return new Sentence(data).toLowerCase().wordCount(word.toString().toLowerCase());
    }

    public int wordCountIgnoreCase(Word word) {
        return wordCountIgnoreCase(word.get());
    }

    public void hasError(SentenceError error, OnCheckErrorListener listener) {
        AtomicBoolean exists = new AtomicBoolean(false);
        Thread checker = new Thread(() -> {
            switch (error) {
                case SyntaxError -> {
                    if (data.contains("  "))
                        exists.set(true);
                    else
                        for (Word w : getWords())
                            if (w.get().contains(",") || w.get().contains(".")) {
                                exists.set(true);
                                break;
                            }
                }
                case GrammaticalError -> {
                    String OTHERS = "the he she a her him they them we to though thorough our ours you yours their theirs there over above";
                    for (Word w : getWords())
                        if (!w.isEmpty() && !new WordDictionary().containsWord(w) && !OTHERS.contains(new Word(w.get()).toLowerCase().get())) {
                            exists.set(true);
                            break;
                        }
                }
            }
        });
        checker.start();

        AtomicLong start = new AtomicLong(System.currentTimeMillis());
        final int[] counter = {0};

        Thread runner = new Thread(() -> {
            while (checker.getState().equals(Thread.State.RUNNABLE)) {
                long current = System.currentTimeMillis();

                if (current - start.get() >= 1000) {
                    counter[0]++;
                    start.set(System.currentTimeMillis());
                    //Main.println("Duration: " + counter[0]);
                    listener.onStillChecking(error, counter[0]);
                }

                if (checker.getState().equals(Thread.State.TERMINATED)) {
                    listener.onFinishedCheckError(error, exists.get());
                    break;
                }
            }
        });
        runner.start();
    }

    public void getErrors(OnGetErrorsListener listener) {
        List<ErrorDefinition> errors = new ArrayList<>();

        AtomicLong start = new AtomicLong(System.currentTimeMillis());
        AtomicLong end = new AtomicLong(start.get());
        final int[] counter = {0};

        Thread thread1 = new Thread(() -> scanForError(SentenceError.GrammaticalError, new OnScanErrorListener() {
            @Override
            public void onScanStarted(SentenceError error, long startTimeInMillis) {
                if (start.get() <= 0)
                    start.set(startTimeInMillis);
            }

            @Override
            public void onScanRunning(SentenceError error, List<ErrorDefinition> errorsDetected, int durationInSeconds) {

            }

            @Override
            public void onScanFinished(SentenceError error, List<ErrorDefinition> errorsDetected, long endTimeInMillis, int durationInSeconds) {
                errors.addAll(errorsDetected);
                end.set(endTimeInMillis);
            }
        }));

        Thread thread2 = new Thread(() -> scanForError(SentenceError.SyntaxError, new OnScanErrorListener() {
            @Override
            public void onScanStarted(SentenceError error, long startTimeInMillis) {
                if (start.get() <= 0)
                    start.set(startTimeInMillis);
            }

            @Override
            public void onScanRunning(SentenceError error, List<ErrorDefinition> errorsDetected, int durationInSeconds) {

            }

            @Override
            public void onScanFinished(SentenceError error, List<ErrorDefinition> errorsDetected, long endTimeInMillis, int durationInSeconds) {
                errors.addAll(errorsDetected);
            }
        }));

        hasError(SentenceError.GrammaticalError, new OnCheckErrorListener() {
            @Override
            public void onFinishedCheckError(SentenceError error, boolean exists) {
                if (exists)
                    thread1.start();
            }

            @Override
            public void onStillChecking(SentenceError error, int duration) {

            }
        });

        hasError(SentenceError.SyntaxError, new OnCheckErrorListener() {
            @Override
            public void onFinishedCheckError(SentenceError error, boolean exists) {
                if (exists)
                    thread2.start();
            }

            @Override
            public void onStillChecking(SentenceError error, int duration) {

            }
        });


        Thread runner = new Thread(() -> {
            while (thread1.getState().equals(Thread.State.RUNNABLE) || thread2.getState().equals(Thread.State.RUNNABLE)) {
                long current = System.currentTimeMillis();

                if (current - start.get() >= 1000) {
                    counter[0]++;
                    start.set(System.currentTimeMillis());
                    // System.out.println("Duration: " + counter[0]);
                    listener.onStillGetting(Arrays.asList(SentenceError.SyntaxError, SentenceError.GrammaticalError), counter[0]);
                }

                if (thread1.getState().equals(Thread.State.TERMINATED) && thread2.getState().equals(Thread.State.TERMINATED))
                    listener.onGetErrors(errors, start.get(), end.get(), (int) (end.get() - start.get()));
            }
        });
        runner.start();
    }

    public void scanForError(SentenceError error, OnScanErrorListener listener) {
        List<ErrorDefinition> errors = new ArrayList<>();

        Thread scanner = new Thread(() -> {
            switch (error) {
                case SyntaxError -> {
                    if (data.contains("  ")) {
                        int start, end = 0;
                        while (true) {
                            start = data.indexOf("  ", end);
                            end = start + 2;
                            if (start < 0 || end > length())
                                break;

                            ErrorDefinition err = new ErrorDefinition(
                                    SentenceError.SyntaxError,
                                    "Contains '  ' between words at index: " + start,
                                    start
                            );

                            // Check if it does not contain this error before
                            // If it does not contain it, add the error!
                            if (notContain(errors, err))
                                errors.add(err);
                        }
                    }
                    for (Word w : getWords()) {
                        if (w.contains(",")) {
                            int start, end = 0;
                            while (true) {
                                // Start index of word
                                start = data.indexOf(w.get(), end);
                                end = start + w.length();
                                if (start < 0 || end > length())
                                    break;

                                ErrorDefinition err = new ErrorDefinition(
                                        SentenceError.SyntaxError,
                                        "Contains ',' between '" + w.get() + "' at index: " + start,
                                        start
                                );

                                // Check if it does not contain this error before
                                // If it does not contain it, add the error!
                                if (notContain(errors, err))
                                    errors.add(err);
                            }
                        }
                        if (w.contains(".")) {
                            int start, end = 0;
                            while (true) {
                                // Start index of word
                                start = data.indexOf(w.get(), end);
                                end = start + w.length();
                                if (start < 0 || end > length())
                                    break;

                                ErrorDefinition err = new ErrorDefinition(
                                        SentenceError.SyntaxError,
                                        "Contains '.' between '" + w.get() + "' at index: " + start,
                                        start
                                );

                                // Check if it does not contain this error before
                                // If it does not contain it, add the error!
                                if (notContain(errors, err))
                                    errors.add(err);
                            }
                        }
                    }
                }
                case GrammaticalError -> {
                    String OTHERS = "the he she a her him they them we to though thorough our ours you yours their theirs there over above";
                    for (Word w : getWords())
                        if (!w.isEmpty() && !w.contains('-') && !new WordDictionary().containsWord(w) && !OTHERS.contains(new Word(w.get()).toLowerCase().get())) {
                            int start, end = 0;
                            while (true) {
                                // Start index of word
                                start = data.indexOf(w.get(), end);
                                end = start + w.length();
                                if (start < 0 || end > length())
                                    break;

                                ErrorDefinition err = new ErrorDefinition(
                                        SentenceError.SyntaxError,
                                        "'" + w.get() + "' at index: " + start + " does not exist in dictionary!",
                                        start
                                );

                                // Check if it does not contain this error before
                                // If it does not contain it, add the error!
                                if (notContain(errors, err))
                                    errors.add(err);
                            }
                        } else if (w.contains('-')) {
                            for (String s : w.get().split("-")) {
                                Word a = new Word(s);
                                if (!a.isEmpty() && !new WordDictionary().containsWord(a) && !OTHERS.contains(new Word(a.get()).toLowerCase().get())) {
                                    int start, end = 0;
                                    while (true) {
                                        // Start index of word
                                        start = data.indexOf(a.get(), end);
                                        end = start + a.length();
                                        if (start < 0 || end > length())
                                            break;

                                        ErrorDefinition err = new ErrorDefinition(
                                                SentenceError.SyntaxError,
                                                "'" + a.get() + "' at index: " + start + " does not exist in dictionary!",
                                                start
                                        );

                                        // Check if it does not contain this error before
                                        // If it does not contain it, add the error!
                                        if (notContain(errors, err))
                                            errors.add(err);
                                    }
                                }
                            }
                        }
                }
            }
        });

        scanner.start();

        AtomicLong start = new AtomicLong(System.currentTimeMillis());
        final int[] counter = {0};

        listener.onScanStarted(error, start.get());

        Thread runner = new Thread(() -> {
            while (scanner.getState().equals(Thread.State.RUNNABLE)) {
                long current = System.currentTimeMillis();

                if (current - start.get() >= 1000) {
                    counter[0]++;
                    listener.onScanRunning(error, errors, counter[0]);
                    start.set(System.currentTimeMillis());
                }

                if (scanner.getState().equals(Thread.State.TERMINATED)) {
                    if ((current - (counter[0] * 1000L) + start.get()) % 1000 < 1)
                        counter[0]++;
                    listener.onScanFinished(error, errors, current, counter[0]);
                    break;
                }
            }
        });

        runner.start();
    }

    private boolean notContain(List<ErrorDefinition> errors, ErrorDefinition err) {
        if (errors.size() > 0)
            for (ErrorDefinition e : errors)
                if (e.equals(err))
                    return false;
        return true;
    }

    public void totalErrors(OnGetTotalErrorsListener listener) {
        getErrors(new OnGetErrorsListener() {
            @Override
            public void onGetErrors(List<ErrorDefinition> errors, long startTimeInMillis, long endTimeInMillis, int durationInSeconds) {
                listener.onGetTotalErrors(errors.size(), startTimeInMillis, endTimeInMillis, durationInSeconds);
            }

            @Override
            public void onStillGetting(List<SentenceError> asList, int duration) {
                listener.onStillGetting(asList.size(), duration);
            }
        });
    }

    public Sentences extractSentences() {
        return new Sentences(DataManager.extractSentences(this));
    }

    public Paragraphs extractParagraphs() {
        return new Paragraphs(DataManager.extractParagraphs(this));
    }



    public static class ErrorDefinition {
        public final SentenceError error;
        public final String message;
        public final int index;

        public ErrorDefinition() {
            this(SentenceError.None, "Nothing found", -1);
        }

        public ErrorDefinition(SentenceError error, String message, int index) {
            this.error = error;
            this.message = message;
            this.index = index;
        }

        public boolean equals(ErrorDefinition another) {
            return error.equals(another.error) && another.message.equals(message) && another.index == index;
        }

        @Override
        public String toString() {
            return message;
        }
    }

    public static class Sentences implements Iterable<Sentence> {
        protected List<Sentence> sentences;

        public Sentences(List<Sentence> sentences) {
            this.sentences = sentences;
        }

        public int size() {
            return sentences.size();
        }

        public boolean contains(ISentence sentence) {
            for (Sentence s : sentences)
                if (s.equals(sentence))
                    return true;
            return false;
        }

        public boolean isEmpty() {
            return sentences.isEmpty();
        }

        public boolean isNotEmpty() {
            return !isEmpty();
        }

        public List<Sentence> getSentences() {
            return new ArrayList<>(sentences);
        }

        public ISentence get(int index) {
            return index > 0 && index <= size() ? sentences.get(index - 1) : null;
        }

        @Override
        public Iterator<Sentence> iterator() {
            return sentences.iterator();
        }

        @Override
        public String toString() {
            String toString = "";
            int count = 0;
            for (Sentence sentence : sentences)
                toString += sentence.get() + (++count < size() ? "\n" : "");
            return toString;
        }

        public boolean equals(Sentences another) {
            return another != null && another.sentences.equals(sentences);
        }
    }

    public static class Paragraphs implements IParagraph {
        protected List<Paragraph> paragraphs;

        public Paragraphs(Paragraph... paragraphs) {
            this(Arrays.asList(paragraphs));
        }

        public Paragraphs(List<Paragraph> paragraphs) {
            this.paragraphs = paragraphs;
        }

        public Paragraph getParagraph(int index) {
            return index >= -1 && index <= size() ? paragraphs.get(index) : null;
        }

        public int size() {
            return paragraphs.size();
        }

        public boolean isEmpty() {
            return paragraphs.isEmpty();
        }

        public boolean isNotEmpty() {
            return !isEmpty();
        }

        public boolean contains(ISentence sentence) {
            for (Paragraph p : paragraphs)
                if (p.equals(sentence))
                    return true;
            return false;
        }

        public List<Paragraph> getParagraphs() {
            return new ArrayList<>(paragraphs);
        }

        @Override
        public Iterator<Paragraph> iterator() {
            return paragraphs.iterator();
        }

        @Override
        public String toString() {
            String toString = "";
            int count = 0;
            for (Paragraph paragraph : paragraphs)
                toString += paragraph.get() + (++count < size() ? "\n" : "");
            return toString;
        }

        public boolean equals(Paragraphs another) {
            return another != null && paragraphs.equals(another.paragraphs);
        }

        @Override
        public Sentences getSentences() {
            List<Sentence> sentences = new ArrayList<>();
            for (Paragraph paragraph : paragraphs)
                sentences.addAll(paragraph.extractSentences().getSentences());
            return new Sentences(sentences);
        }
    }

    public interface OnScanErrorListener {
        void onScanStarted(SentenceError error, long startTimeInMillis);

        void onScanRunning(SentenceError error, List<ErrorDefinition> errorsDetected, int durationInSeconds);

        void onScanFinished(SentenceError error, List<ErrorDefinition> errorsDetected, long endTimeInMillis, int durationInSeconds);
    }

    public interface OnGetErrorsListener {
        void onGetErrors(List<ErrorDefinition> errors, long startTimeInMillis, long endTimeInMillis, int durationInSeconds);
        void onStillGetting(List<SentenceError> asList, int duration);
    }

    public interface OnGetTotalErrorsListener {
        void onGetTotalErrors(int totalErrors, long startTimeInMillis, long endTimeInMillis, int durationInSeconds);
        void onStillGetting(int errorCount, int duration);
    }

    public interface OnCheckErrorListener {
        void onFinishedCheckError(SentenceError error, boolean exists);
        void onStillChecking(SentenceError error, int duration);
    }

    protected interface IParagraph extends Iterable<Paragraph> {
        Sentences getSentences();
    }

}

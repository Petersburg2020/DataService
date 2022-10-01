package nx.peter.java.util.lorem;

import nx.peter.java.storage.File;
import nx.peter.java.storage.FileManager;
import nx.peter.java.util.Util;
import nx.peter.java.util.data.*;

import java.util.ArrayList;
import java.util.List;

public class LoremIpsumDictionary {
    protected List<Paragraph> paragraphs;
    protected String lorem;

    public LoremIpsumDictionary() {
        initParagraphs();
    }

    private void initParagraphs() {
        paragraphs = new ArrayList<>();
        List<String> lines = FileManager.readLines(File.FILES_FOLDER + "lorem_texts.txt");
        for (int line = 0; line < lines.size()/2; line += 2)
            paragraphs.add(new Paragraph(lines.get(line)));
        lorem = Util.toString(lines);
    }

    public Paragraph getParagraph(int paragraph) {
        return paragraph > 0 && paragraph <= getParagraphCount() ? paragraphs.get(paragraph - 1) : new Paragraph();
    }

    public Paragraph.Paragraphs getParagraphs() {
        return new Paragraph.Paragraphs(paragraphs);
    }

    public Paragraph.Paragraphs getParagraphs(int start, int end) {
        Util.MinMax<Integer> m = new Util.MinMax<>(start, end);
        List<Paragraph> paragraphs = new ArrayList<>();
        if (m.min() > 0 && m.isUnequal() && m.max() <= getParagraphCount())
            for (int i = m.min(); i <= m.max(); i++)
                paragraphs.add(getParagraph(i));
        return new Paragraph.Paragraphs(paragraphs);
    }

    public int getParagraphCount() {
        return paragraphs.size();
    }


    public Paragraph.Sentences getSentences() {
        return getParagraphs().getSentences();
    }

    public Paragraph.Sentences getSentences(int start, int end) {
        Util.MinMax<Integer> m = new Util.MinMax<>(start, end);
        List<Sentence> sentences = new ArrayList<>();
        if (m.min() > 0 && m.isUnequal() && m.max() <= getSentenceCount())
            for (int i = m.min(); i <= m.max(); i++)
                sentences.add(getSentence(i));
        return new Paragraph.Sentences(sentences);
    }

    public int getSentenceCount() {
        return getSentences().size();
    }

    public Sentence getSentence(int sentence) {
        return sentence > 0 && sentence < getSentenceCount() ? (Sentence) getSentences().get(sentence - 1) : new Sentence();
    }


    public Paragraph.Words getWords() {
        List<Word> words = new ArrayList<>();
        for (Paragraph p : paragraphs)
            words.addAll(p.getWords().toList());
        return new Paragraph.Words(words, getTexts());
    }

    public Paragraph.Words getWords(int start, int end) {
        Util.MinMax<Integer> m = new Util.MinMax<>(start, end);
        List<Word> words = new ArrayList<>();
        if (m.min() > 0 && m.isUnequal() && m.max() <= getSentenceCount())
            for (int i = m.min(); i <= m.max(); i++)
                words.add(getWord(i));
        return new Letters.Words(words, getTexts());
    }

    public int getWordCount() {
        return getWords().size();
    }

    public Word getWord(int position) {
        return getWords().get(position);
    }


    public Texts getTexts() {
        return new Texts(lorem);
    }

    public boolean contains(IData data) {
        return contains(data != null ? data.get() : null);
    }

    public boolean contains(CharSequence data) {
        if (data != null)
            for (Paragraph p : paragraphs)
                if (p.contains(data))
                    return true;
        return false;
    }

    @Override
    public String toString() {
        return getParagraphs().toString();
    }
}

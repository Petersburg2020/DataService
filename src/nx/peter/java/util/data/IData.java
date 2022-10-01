package nx.peter.java.util.data;

import java.io.Serializable;

public interface IData<D extends IData> extends CharSequence, Comparable<D>, Serializable {
	D reset();
	D set(D data);
	D set(char... data);
	D set(CharSequence data);
	D set(int index, char... data);
	D set(int index, CharSequence data);
	int count(char data);
	int count(long data);
	int count(int data);
	int count(boolean data);
	int count(double data);
	int count(IData data);
	int count(CharSequence data);
	String get();
	D getData();
	int length();
	D append(int data);
	D append(char data);
	D append(long data);
	D append(double data);
	D append(boolean word);
	D append(IData data);
	D append(CharSequence data);
	D append(int data, int index);
	D append(char data, int index);
	D append(long data, int index);
	D append(double data, int index);
	D append(boolean word, int index);
	D append(IData data, int index);
	D append(CharSequence data, int index);
	int indexOf(int data);
	int indexOf(double data);
	int indexOf(long data);
	int indexOf(boolean data);
	int indexOf(char data);
	int indexOf(IData data);
	int indexOf(CharSequence data);
	int indexOf(int data, int start);
	int indexOf(char data, int start);
	int indexOf(long data, int start);
	int indexOf(boolean data, int start);
	int indexOf(double data, int start);
	int indexOf(IData data, int start);
	int indexOf(CharSequence data, int start);
	int indexBefore(int data);
	int indexBefore(char data);
	int indexBefore(long data);
	int indexBefore(double data);
	int indexBefore(boolean data);
	int indexBefore(IData data);
	int indexBefore(CharSequence data);
	int indexAfter(int data);
	int indexAfter(char data);
	int indexAfter(long data);
	int indexAfter(boolean data);
	int indexAfter(double data);
	int indexAfter(IData data);
	int indexAfter(CharSequence data);
	DataType getType();
	boolean isEmpty();
	boolean isNotEmpty();
	boolean isWord();
	boolean isIndex();
	boolean isAlphabet();
	boolean isCharacter();
	boolean isFraction();
	boolean isLetter();
	boolean isLetters();
	boolean isNumber();
	boolean isSentence();
	boolean isValid();
	boolean contains(boolean data);
	boolean contains(char data);
	boolean contains(double data);
	boolean contains(int data);
	boolean contains(long data);
	<O> boolean contains(O data);
	boolean contains(IData data);
	boolean contains(CharSequence data);
	int comparesTo(boolean data);
	int comparesTo(char data);
	int comparesTo(double data);
	int comparesTo(int data);
	int comparesTo(long data);
	int comparesTo(IData data);
	int comparesTo(CharSequence data);
	boolean equals(IData another);
	boolean equalsIgnoreCase(IData another);
	boolean equalsIgnoreType(IData another);

	enum DataType {
		Alphabet, 
		Character, 
		Fraction,
		Index,
		Letter,
		Number,
		Operator,
		Others,
		Sentence,
		Subscript,
		Superscript,
        Texts,
        Paragraph, Word
	}

	interface OnFinishedLoadingListener<P> {
		void onFinishedLoading(P data);
	}

}

package nx.peter.java.util.data;

public abstract class Data<D extends Data> implements IData<D> {
	protected String data;
	protected int index;
	
	public Data() {
		reset();
	}
	
	public Data(char... data) {
		this(new String(data));
	}

	public Data(int index, char... data) {
		this(index, new String(data));
	}

	public Data(int index, CharSequence data) {
		reset();
		set(index, data);
	}

	public Data(D data) {
		reset();
		set(data);
	}
	
	public Data(CharSequence data) {
		reset();
		set(data);
	}

	@Override
	public D reset() {
		data = "";
		index = 0;
		return (D) this;
	}

	@Override
	public D set(D data) {
		if (data != null)
			set(data.get());
		return (D) this;
	}

	@Override
	public D set(char... data) {
		return set(new String(data));
	}

	@Override
	public D set(CharSequence data) {
		if (data != null)
			this.data = data.toString();
		return (D) this;
	}

	@Override
	public D set(int index, char... data) {
		return set(index, new String(data));
	}

	@Override
	public D set(int index, CharSequence data) {
		this.index = index;
		if (data != null)
			this.data = data.toString();
		return (D) this;
	}

	@Override
	public char charAt(int index) {
		return data.charAt(index);
	}

	@Override
	public CharSequence subSequence(int start, int end) {
		return data.subSequence(start, end);
	}

	@Override
	public D append(double word, int index) {
		return append(word + "", index);
	}

	@Override
	public D append(int word, int index) {
		return append(word + "", index);
	}

	@Override
	public D append(long word, int index) {
		return append(word + "", index);
	}

	@Override
	public int count(int data) {
		return count(data + "");
	}

	@Override
	public int count(long data) {
		return count(data + "");
	}

	@Override
	public int count(char data) {
		return count(data + "");
	}

	@Override
	public int count(boolean data) {
		return count(data + "");
	}

	@Override
	public int count(double data) {
		return count(data + "");
	}

	@Override
	public int count(IData data) {
		return count(data != null ? data.get() : (String) null);
	}
	
	@Override
	public int count(CharSequence data) {
		if (data == null)
			return 0;
		int count = 0, index = 0;
		String temp = this.data;
		while(index > -1){
			index = temp.indexOf(data.toString());
			if(index <= -1)
				break;
			count++;
			temp = temp.substring(index + data.length());
		}
		return count;
	}

	@Override
	public int compareTo(D data) {
		return this.data.compareTo(data != null ? data.data : "");
	}

	@Override
	public D append(char word, int index) {
		return append(word + "", index);
	}

	@Override
	public D append(boolean word, int index) {
		return append(word + "", index);
	}

	@Override
	public D append(IData data, int index) {
		return append(data != null ? data.get() : (CharSequence) null, index);
	}

	@Override
	public D append(CharSequence word, int index) {
		if(length() > index && index > 0){
			String start = data.substring(0, index);
			String end = data.substring(index);
			set(start + word.toString() + end);
		}else if(index == 0)
			set(word.toString() + data);
		else if(index == length())
			set(data + word.toString());
		return (D) this;
	}

	@Override
	public D append(double word) {
		return append(word + "");
	}

	@Override
	public D append(long word) {
		return append(word + "");
	}

	@Override
	public D append(int word) {
		return append(word + "");
	}

	@Override
	public D append(char word) {
		return append(word + "");
	}

	@Override
	public D append(boolean word) {
		return append(word + "");
	}

	@Override
	public D append(IData data) {
		return append(data != null ? data.get() : null);
	}

	@Override
	public D append(CharSequence word) {
		return append(word, length());
	}
	
	@Override
	public int length() {
		return data.length();
	}

	@Override
	public String get() {
		return data;
	}

	@Override
	public boolean isValid() {
		return !data.isEmpty();
	}

	@Override
	public boolean isEmpty() {
		return data.isEmpty();
	}

	@Override
	public boolean isNotEmpty() {
		return !isEmpty();
	}

	@Override
	public boolean isLetter() {
		return isAlphabet() || isCharacter();
	}

	@Override
	public boolean isLetters() {
		return isWord() || isSentence();
	}

	@Override
	public boolean isWord() {
		return getType().equals(DataType.Word);
	}

	@Override
	public boolean isIndex() {
		return getType().equals(DataType.Index);
	}

	@Override
	public boolean isFraction() {
		return getType().equals(DataType.Fraction);
	}

	@Override
	public boolean isAlphabet() {
		return getType().equals(DataType.Alphabet);
	}

	@Override
	public boolean isNumber() {
		return getType().equals(DataType.Number);
	}

	@Override
	public boolean isCharacter() {
		return getType().equals(DataType.Character);
	}

	@Override
	public boolean isSentence() {
		return getType().equals(DataType.Sentence);
	}

	@Override
	public int indexOf(int data) {
		return indexOf(data + "");
	}

	@Override
	public int indexOf(char data) {
		return indexOf(data + "");
	}

	@Override
	public int indexOf(long data) {
		return indexOf(data + "");
	}

	@Override
	public int indexOf(boolean data) {
		return indexOf(data + "");
	}

	@Override
	public int indexOf(double data) {
		return indexOf(data + "");
	}

	@Override
	public int indexOf(IData data) {
		return indexOf(data != null ? data.get() : null);
	}

	@Override
	public int indexOf(CharSequence data) {
		return data != null ? this.data.indexOf(data.toString()) : -1;
	}

	@Override
	public int indexOf(int data, int start) {
		return indexOf(data + "", start);
	}

	@Override
	public int indexOf(char data, int start) {
		return indexOf(data + "", start);
	}
	
	@Override
	public int indexOf(long data, int start) {
		return indexOf(data + "", start);
	}

	@Override
	public int indexOf(boolean data, int start) {
		return indexOf(data + "", start);
	}

	@Override
	public int indexOf(double data, int start) {
		return indexOf(data + "", start);
	}

	@Override
	public int indexOf(IData data, int start) {
		return indexOf(data != null ? data.toString() : (CharSequence) null, start);
	}
	
	@Override
	public int indexOf(CharSequence data, int start) {
		return data != null && start >= 0 && start < length() ? this.data.indexOf(data.toString(), start) : -1;
	}


	@Override
	public int indexBefore(int data) {
		return indexBefore(data + "");
	}

	@Override
	public int indexBefore(char data) {
		return indexBefore(data + "");
	}

	@Override
	public int indexBefore(long data) {
		return indexBefore(data + "");
	}

	@Override
	public int indexBefore(boolean data) {
		return indexBefore(data + "");
	}

	@Override
	public int indexBefore(double data) {
		return indexBefore(data + "");
	}

	@Override
	public int indexBefore(IData data) {
		return indexBefore(data != null ? data.get() : (String) null);
	}

	@Override
	public int indexBefore(CharSequence data) {
		return data != null && contains(data) ? this.data.indexOf(data.toString()) + data.length() : -1;
	}


	@Override
	public int indexAfter(int data) {
		return indexAfter(data + "");
	}

	@Override
	public int indexAfter(char data) {
		return indexAfter(data + "");
	}

	@Override
	public int indexAfter(long data) {
		return indexAfter(data + "");
	}

	@Override
	public int indexAfter(boolean data) {
		return indexAfter(data + "");
	}

	@Override
	public int indexAfter(double data) {
		return indexAfter(data + "");
	}

	@Override
	public int indexAfter(IData data) {
		return indexAfter(data != null ? data.get() : (String) null);
	}

	@Override
	public int indexAfter(CharSequence data) {
		return data != null && contains(data) ? this.data.indexOf(data.toString()) + data.length() : -1;
	}


	@Override
	public D getData() {
		return (D) this;
	}

	@Override
	public boolean contains(int data) {
		return contains(data + "");
	}

	@Override
	public boolean contains(char data) {
		return contains(data + "");
	}

	@Override
	public boolean contains(boolean data) {
		return contains(data + "");
	}

	@Override
	public boolean contains(long data) {
		return contains(data + "");
	}

	@Override
	public <O> boolean contains(O data) {
		return contains(data != null ? data.toString() : "");
	}

	@Override
	public boolean contains(IData data) {
		return contains(data + "");
	}

	@Override
	public boolean contains(double data) {
		return contains(data + "");
	}

	@Override
	public boolean contains(CharSequence data) {
		return data != null && this.data.contains(data);
	}

	@Override
	public int comparesTo(int data) {
		return comparesTo(String.valueOf(data));
	}

	@Override
	public int comparesTo(char data) {
		return comparesTo(String.valueOf(data));
	}

	@Override
	public int comparesTo(long data) {
		return comparesTo(String.valueOf(data));
	}

	@Override
	public int comparesTo(double data) {
		return comparesTo(String.valueOf(data));
	}

	@Override
	public int comparesTo(boolean data) {
		return comparesTo(String.valueOf(data));
	}

	@Override
	public int comparesTo(IData data) {
		return comparesTo(String.valueOf(data));
	}

	@Override
	public int comparesTo(CharSequence data) {
		return this.data.compareTo(data.toString());
	}

	@Override
	public boolean equals(IData another) {
		return another != null && another.getClass().equals(getClass()) && equalsIgnoreType(another) && another.getType().equals(getType());
	}

	@Override
	public boolean equalsIgnoreCase(IData another) {
		return another != null && another.get().equalsIgnoreCase(get());
	}

	@Override
	public boolean equalsIgnoreType(IData another) {
		return another != null && another.get().equals(get());
	}

	@Override
	public String toString() {
		return data;
	}

}

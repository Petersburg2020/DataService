package nx.peter.java.util.data;

public class Letter<L extends Letter> extends Data<L> {
	protected int index;
	
	public Letter() {
		super();
	}
	
	public Letter(char letter) {
		super(letter);
	}
	
	public Letter(CharSequence letter) {
		super(letter);
	}
	
	public Letter(char letter, int index) {
		super(letter);
		this.index = index;
	}
	
	public Letter(Letter another) {
		this(another != null ? another.get() : "", another != null ? another.getIndex() : -1);
	}
	
	public Letter(CharSequence letter, int index) {
		super(letter);
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	@Override
	public L reset() {
		super.reset();
		index = -1;
		return (L) this;
	}
	
	public L set(char data) {
		return super.set(data);
	}

	@Override
	public L set(CharSequence data) {
		if (data != null && data.length() == 1)
			super.set(data);
		return (L) this;
	}
	
	public char[] toCharArray() {
		return data.toCharArray();
	}
	
	public L toUpperCase() {
		data = data.toUpperCase();
		return (L) this;
	}
	
	public L toLowerCase() {
		data = data.toLowerCase();
		return (L) this;
	}
	
	public boolean isUpperCase() {
		return DataManager.isUpperCase(data);
	}
	
	public boolean isLowerCase() {
		return DataManager.isLowerCase(data);
	}
	
	public boolean isConsonant() {
		return DataManager.isConsonant(data);
	}
	
	public boolean isVowel() {
		return DataManager.isVowel(data);
	}

	@Override
	public boolean isValid() {
		return super.isValid() && index > -1;
	}

	@Override
	public DataType getType() {
		return DataType.Letter;
	}
	
	public boolean equalsIgnoreCase(Letter another) {
		return new Letter(this).toLowerCase().equals(new Letter(another).toLowerCase());
	}

	@Override
	public boolean equals(IData another) {
		return super.equals(another) && (another instanceof Letter && ((Letter) another).getIndex() == index);
	}
	
	
	
}

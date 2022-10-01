package nx.peter.java.util.data.comparator;


import nx.peter.java.util.data.Letter;

public class ComparedLetter extends ComparedData<Letter> {
	public enum Order {
		Ascending, 
		Descending
	}
	
	public ComparedLetter(char letter1, int index1, char letter2, int index2) {
		super(new Letter(letter1, index1), new Letter(letter2, index2));
	}
	
	public ComparedLetter(char letter1, int index1, CharSequence letter2, int index2) {
		super(new Letter(letter1, index1), new Letter(letter2, index2));
	}
	
	public ComparedLetter(CharSequence letter1, int index1, char letter2, int index2) {
		super(new Letter(letter1, index1), new Letter(letter2, index2));
	}
	
	public ComparedLetter(CharSequence letter1, int index1, CharSequence letter2, int index2) {
		super(new Letter(letter1, index1), new Letter(letter2, index2));
	}
	
	public ComparedLetter(Letter letter1, Letter letter2) {
		super(letter1, letter2);
	}
	
	public ComparedLetter(Letter letter1, char letter2, int index2) {
		super(letter1, new Letter(letter2, index2));
	}
	
	public ComparedLetter(char letter1, int index1, Letter letter2) {
		super(new Letter(letter1, index1), letter2);
	}
	
	
	
	
	public boolean isAlmostEqual() {
		return Math.abs(data1.getIndex() - data2.getIndex()) <= 1 && data1.equalsIgnoreCase(data2);
	}
	
	public boolean equals(ComparedLetter another) {
		return another != null && ((another.getData1().equals(data1) && another.getData2().equals(data2)) || (another.getData1().equals(data2) && another.getData2().equals(data1)));
	}
	
	public boolean equalsIgnoreCase(ComparedLetter another) {
		return another != null && ((another.getData1().equalsIgnoreCase(data1) && another.getData2().equalsIgnoreCase(data2)) || (another.getData1().equalsIgnoreCase(data2) && another.getData2().equalsIgnoreCase(data1)));
	}
	
}

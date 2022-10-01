package nx.peter.java.util.data.comparator;

import nx.peter.java.util.data.Letter;
import nx.peter.java.util.data.Letters;

import java.util.ArrayList;
import java.util.List;

public class ComparedLetters {
	protected List<ComparedLetter> comparedLetters;
	protected boolean isAlmostEqual;
	
	public ComparedLetters() {
		this("", "", true);
	}
	
	public ComparedLetters(CharSequence letter1, CharSequence letter2, boolean isAlmostEqual) {
		this(isAlmostEqual ? getAlmostEquals(letter1, letter2).getComparedLetters() : getEquals(letter1, letter2).getComparedLetters(), isAlmostEqual);
	}

	public ComparedLetters(Letters letters1, Letters letters2, boolean isAlmostEqual) {
		this(letters1 != null ? letters1.get() : "", letters2 != null ? letters2.get() : "", isAlmostEqual);
	}
	
	public ComparedLetters(List<ComparedLetter> letters, boolean isAlmostEqual) {
		comparedLetters = letters != null ? letters : new ArrayList<>();
		this.isAlmostEqual = isAlmostEqual;
	}

	public boolean contains(Letter letter) {
		for (ComparedLetter cLetter : comparedLetters)
			if (cLetter.contains(letter))
				return true;
		return false;
	}
	
	public boolean contains(ComparedLetter letter) {
		for (ComparedLetter cLetter : comparedLetters)
			if (cLetter.equals(letter))
				return true;
		return false;
	}
	
	public int size() {
		return comparedLetters.size();
	}
	
	public ComparedLetter get(int index) {
		return index >= 0 && index < size() ? comparedLetters.get(index) : null;
	}
	
	public int indexOf(Letter letter) {
		for (int index = 0; index < size(); index++)
			if (get(index).contains(letter))
				return index;
		return -1;
	}
	
	public List<ComparedLetter> getComparedLetters() {
		List<ComparedLetter> letters = new ArrayList<>();
		for (ComparedLetter c : comparedLetters)
			letters.add(c);
		return letters;
	}
	
	
	
	public static ComparedLetters getEquals(CharSequence letters1, CharSequence letters2) {
		if (letters1 == null || letters2 == null)
			return new ComparedLetters();
		List<ComparedLetter> cLetters = new ArrayList<>();
		for (int index1 = 0; index1 < letters1.length(); index1++)
			for (int index2 = 0; index2 < letters2.length(); index2++) {
				ComparedLetter compared = new ComparedLetter(letters1.toString().charAt(index1), index1, letters2.toString().charAt(index2), index2);
				if (compared.isEqual())
					cLetters.add(compared);
			}
		return new ComparedLetters(cLetters, false);
	}

	public static ComparedLetters getEquals(Letters letters1, Letters letters2) {
		return getEquals(letters1 != null ? letters1.get() : null, letters2 != null ? letters2.get() : null);
	}
	
	public static ComparedLetters getAlmostEquals(CharSequence letters1, CharSequence letters2) {
		if (letters1 == null || letters2 == null)
			return new ComparedLetters();
		List<ComparedLetter> cLetters = new ArrayList<>();
		for (int index1 = 0; index1 < letters1.length(); index1++)
			for (int index2 = 0; index2 < letters2.length(); index2++) {
				ComparedLetter compared = new ComparedLetter(letters1.toString().charAt(index1), index1, letters2.toString().charAt(index2), index2);
				if (compared.isAlmostEqual())
					cLetters.add(compared);
			}
		return new ComparedLetters(cLetters, true);
	}
	
	public static ComparedLetters getAlmostEquals(Letters letters1, Letters letters2) {
		return getAlmostEquals(letters1 != null ? letters1.get() : null, letters2 != null ? letters2.get() : null);
	}
	
}

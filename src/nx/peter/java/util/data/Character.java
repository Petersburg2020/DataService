package nx.peter.java.util.data;

public class Character extends Letter<Character> {
	public Character(){
		super();
	}

	public Character(char character){
		super(character);
	}

	public Character(CharSequence character){
		super(character);
	}

	public Character(CharSequence letter, int index) {
		super(letter, index);
	}

	@Override
	public Character set(CharSequence character){
		if(character != null && DataManager.isCharacter(character))
			super.set(character);
		return this;
	}

	@Override
	public DataType getType(){
		return DataType.Character;
	}


}


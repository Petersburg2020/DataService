package nx.peter.java.util.data.word;

import nx.peter.java.util.data.Word;

public class Name extends Noun {
	private String firstName, middleName, lastName, nickName;
	
	public Name(){
		super();
	}
	
	public Name(CharSequence firstName){
		super(firstName);
		set(firstName);
	}

	public Name(Word name){
		this(name.get());
	}

	public Name(CharSequence firstName, CharSequence lastName){
		this(firstName, "", lastName);
	}
	
	public Name(CharSequence firstName, CharSequence middleName, CharSequence lastName){
		this(firstName, middleName, lastName, "");
	}

	public Name(CharSequence firstName, CharSequence middleName, CharSequence lastName, CharSequence nickName){
		this(firstName);
		set(firstName, middleName, lastName, nickName);
	}


	public Word reset() {
		super.reset();
		set("", "", "");
		setNickName("");
		return this;
	}

	public Name set(CharSequence firstName){
		this.firstName = firstName.toString();
		this.middleName = "";
		this.lastName = "";
		super.set(getFullName());
		return this;
	}
	
	public Name set(CharSequence firstName, CharSequence lastName){
		this.firstName = firstName.toString();
		this.middleName = "";
		this.lastName = lastName.toString();
		return (Name) super.set(getFullName());
	}

	public Name set(CharSequence firstName, CharSequence middleName, CharSequence lastName){
		this.firstName = firstName.toString();
		this.middleName = middleName.toString();
		this.lastName = lastName.toString();
		return (Name) super.set(getFullName());
	}

	public Name set(CharSequence firstName, CharSequence middleName, CharSequence lastName, CharSequence nickName) {
		set(firstName, middleName, lastName);
		return setNickName(nickName);
	}


	public Name setFirstName(CharSequence firstName) {
		this.firstName = firstName.toString();
		return this;
	}

	public Name setLastName(CharSequence lastName) {
		this.lastName = lastName.toString();
		return this;
	}

	public Name setMiddleName(CharSequence middleName) {
		this.middleName = middleName.toString();
		return this;
	}

	public Name setNickName(CharSequence nickName) {
		this.nickName = nickName.toString();
		return this;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getNickName() {
		return nickName;
	}

	public String getFullName(){
		if(hasFirstName() && hasMiddleName() && hasLastName())
			return firstName + " " + middleName + " " + lastName;
		else if(hasFirstName() && hasMiddleName() && !hasLastName())
			return firstName + " " + middleName;
		else if(hasFirstName() && !hasMiddleName() && hasLastName())
			return firstName + " " + lastName;
		else if(hasFirstName() && !hasMiddleName() && !hasLastName())
			return firstName;
		else
			return "";
	}
	
	public boolean hasFirstName(){
		return !firstName.isEmpty();
	}
	
	public boolean hasMiddleName(){
		return !middleName.isEmpty();
	}
	
	public boolean hasLastName(){
		return !lastName.isEmpty();
	}
	
	public int lastNameLength(){
		return lastName.length();
	}
	
	public boolean equals(Name name){
		return name.firstName.equalsIgnoreCase(firstName) && name.lastName.equalsIgnoreCase(lastName) && name.middleName.equalsIgnoreCase(middleName);
	}

	@Override
	public PartOfSpeech getPartOfSpeech() {
		return PartOfSpeech.Noun;
	}

	public int middleNameLength(){
		return middleName.length();
	}

	public int firstNameLength(){
		return firstName.length();
	}

	public int totalLength(){
		return lastNameLength() + middleNameLength() + firstNameLength();
	}
	
}

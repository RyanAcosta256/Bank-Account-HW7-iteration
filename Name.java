
public class Name {
	private String fname;
	private String lname;

//No Arg constructors 	
	public Name() {
		fname = "";
		lname = "";
	}
	public Name(String f, String l) {
		fname = f;
		lname = l;
	}
	
	//copy constructor
	public Name(Name name) {
		this.fname = name.getFirst();
		this.lname = name.getLast();
	}

	// setters
	public void setFirst(String str) {
		fname = str;
	}

	public void setLast(String str) {
		lname = str;
	}

	// getters
	public String getFirst() {
		return fname;
	}

	public String getLast() {
		return lname;
	}
	
	
	
	//.toString() method
	public String toString() { //Override  toString()
		return fname + " "+ lname;
	}
	//.equals() method
	public boolean equals(Name name) {
		if(fname.equals(name.getFirst()) && lname.equals(name.getLast())){
			return true;
		}
		else {
			return false;
		}
	}

	
	

}

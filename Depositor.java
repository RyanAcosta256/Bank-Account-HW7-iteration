//Aggregation occurs in this class 
public class Depositor {
	private Name name;
	private int SSN;

//No Arg constructor
	public Depositor() {
		SSN = 00000000;
		name = new Name();
	}
	
	public Depositor(Name n, int s) {
		SSN = s;
		name = n;
	}
	//toString(), equals(), and deep copy lines
	
	//copy constructor
	public Depositor(Depositor dep) {
		name = new Name(dep.name);
		SSN = dep.getSSN(); //this.VarName is just another way of saying var = blank; but is commonly used for attributes of the same value
		//this. example this.SSN = dep.getSSN();
	}
	
	//aggregation lines

	
	public Depositor getDepositor () {
		Depositor dep = new Depositor(name, SSN);
		return dep;
	}
	
	//.equals() operator
	public boolean equals(Depositor dep) {
		if(SSN == dep.getSSN() && name.equals(dep.getName())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	//.toString()
	public String toString() {
		return String.format("%-27s %-27s", name.toString(), SSN);
	}
	
	
	// setter
	public void setSSN(int social) {
		SSN = social;
	}

	public void setName(Name n) {
		name = n;
	}

	// getters

	public int getSSN() {
		return SSN;
	}

	public Name getName() {
		return name;
	}
}

import java.util.Calendar;

public class Check {
	private int acctNumber;
	private double checkAmnt;
	private Calendar dateOfCheck;

	public Check(int num, double amnt, String date) {
		acctNumber = num;
		checkAmnt = amnt;
		dateOfCheck = Calendar.getInstance();
		dateOfCheck.clear();
		String[] dateArray = date.split("/");
		dateOfCheck.set(Integer.parseInt(dateArray[2]), Integer.parseInt(dateArray[0]) - 1,
				Integer.parseInt(dateArray[1]));
	}

//copy constructor 
	public Check(Check check) {
		acctNumber = check.getAcctNumber();
		checkAmnt = check.getCheckAmnt();
		dateOfCheck = check.getDateOfCheck();
	}

	
	public String toString() {
		String str =  String.format("Account Number: %d,Check Amount: $%.2f\n", acctNumber, checkAmnt);
		return str;
	}
	
	public boolean equals(Check check) {
		if(acctNumber == check.getAcctNumber() && checkAmnt == check.getCheckAmnt()) {
			return true;
		}
		else {
			return false;
		}
	}
	
//getters
	public int getAcctNumber() {
		return acctNumber;
	}

	public double getCheckAmnt() {
		return checkAmnt;
	}

	public Calendar getDateOfCheck() { // the dateOfCheck() was changed to getDateOfCheck() for the sake of coder
										// convenience
		return dateOfCheck;
	}

}

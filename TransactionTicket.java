import java.util.Calendar;

public class TransactionTicket {
	private int acctNum;
	private Calendar dateOfTransaction;
	private String typeOfTransaction;
	private double amountOfTransaction;
	private int termOfCD;
	private Calendar CDDate;//used for newAccts method
	public int constructorCount; //a flag used to determine which constructor is used
	/*
	 * A flag is basically a homemade condition that can be used to see if certain functions of a program are being used or not
	 * A flag was used in this case to determine which constructor was being used, hence why it's public
	 */
	public TransactionTicket(int num, Calendar date, String type, double amnt, int term) {
		acctNum = num;
		dateOfTransaction = date;
		typeOfTransaction = type;
		amountOfTransaction = amnt;
		termOfCD = term;
		constructorCount = 5;

	}
	public TransactionTicket(int num, Calendar date, String type, double amnt, int term, Calendar CDDate) {
		acctNum = num;
		dateOfTransaction = date;
		typeOfTransaction = type;
		amountOfTransaction = amnt;
		termOfCD = term;
		this.CDDate = CDDate;
		constructorCount = 6;

	}
	//copy constructor 
	public TransactionTicket(TransactionTicket ticket) {
		acctNum = ticket.getAcctNumber();
		dateOfTransaction = ticket.getDateOfTransaction();
		typeOfTransaction = ticket.getTransactionType();
		amountOfTransaction = ticket.getTransactionAmount();
		termOfCD = ticket.getTermOfCD();
	}
	
	public String toString() {
		String str = String.format("Transaction Requested: %s\nAccount number: %d\n", typeOfTransaction, acctNum);
		return str;
	}
	
	
	
	
	public Calendar setCDDate(Calendar c) {
		CDDate = c;
		return CDDate;
	}
	
	public int getAcctNumber() {
		return acctNum;
	}
	public Calendar getCDDate() {
		return CDDate;
	}
	public Calendar getDateOfTransaction() {
		return dateOfTransaction;
	}

	public String getTransactionType() {
		return typeOfTransaction;
	}

	public double getTransactionAmount() {
		return amountOfTransaction;
	}

	public int getTermOfCD() {
		return termOfCD;
	}

}

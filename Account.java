import java.util.Calendar;
import java.util.ArrayList;

public class Account {
	protected Depositor dep;
	protected int acctNum;
	protected String acctType;
	protected double balance;
	// protected Calendar maturityDate;
	protected ArrayList<TransactionReceipt> arrayOfReceipts; // arraylist of TransactionReceipts
	protected String accountStatus;
//the makeDeposit/withdrawal has to be null   so it can be overridden in the subclasses

	public Account(Depositor d, int num, String type, double bal, String status) {// the String naming is used
		dep = d;
		acctNum = num;
		acctType = type;
		balance = bal;
		// maturityDate = null;
		accountStatus = status;
		// contestant = new ArrayList<>();
		arrayOfReceipts = new ArrayList<>();
		// arrayReceipts = new ArrayList<>();
	}

	// Copy Constructor
	public Account(Account acct) {
		dep = new Depositor(acct.getDepositor());
		acctNum = acct.getAcctNumber();
		acctType = acct.getAcctType();
		balance = acct.getAcctBalance();
		// maturityDate = acct.getMaturityDate();
		accountStatus = acct.getAccountStatus();
		arrayOfReceipts = new ArrayList<>(acct.arrayOfReceipts);
	}
	
	
	public String toString() { // prints account information
		String str1 = dep.getDepositor().toString();
		String str2 = String.format("%s %-24s %-24s ", str1, acctNum, // getAcctNum(),
				acctType);/*
							 * accountStatus, balance, //getAcctBalance(), getMaturityDateString(),
							 * maturityDate);
							 */

		return str2;
	}


	public boolean equals(Account acct) {
		if (dep == acct.getDepositor() && acctNum == acct.getAcctNumber() && acctType.equals(acct.getAcctType())
				&& balance == acct.getAcctBalance() && accountStatus.equals(acct.getAccountStatus())) {
			return true;
		} else {
			return false;
		}
	}

	public TransactionReceipt closeAcct(TransactionTicket ticket) {
		setAccountStatus("Closed");
		TransactionReceipt result = new TransactionReceipt(ticket, true,
				("Account number " + acctNum + " is now closed\n"), getAcctBalance(), // pre
				getAcctBalance(), // post
				null);
		addTransaction(result);

		return result;
	}

	public TransactionReceipt reOpenAcct(TransactionTicket ticket) {
		setAccountStatus("Open");
//public TransactionReceipt(TransactionTicket t, boolean T, String reason, double pre, double post, Calendar matDate) {
		TransactionReceipt result = new TransactionReceipt(ticket, true,
				("Account number " + acctNum + " is now open\n"), getAcctBalance(), // pre
				getAcctBalance(), // post
				null);
		addTransaction(result);

		return result;
	}

//next to each other for the sake of ease of reference 
	public void addTransaction(TransactionReceipt Receipt) {
		arrayOfReceipts.add(Receipt);
	}

	public ArrayList<TransactionReceipt> getTransactionHistory(TransactionTicket ticket) {
		return arrayOfReceipts;
	}

	public TransactionReceipt getBalance(TransactionTicket ticket) {
		TransactionReceipt result;

			result = new TransactionReceipt(ticket, true,
					String.format("Account Balance: $%.2f ", getAcctBalance()),
					getAcctBalance(), getAcctBalance(), null);
			addTransaction(result);
			return result;
		//}
	}

	public TransactionReceipt makeDeposit(TransactionTicket ticket) {
		TransactionReceipt result;
		return result = null;
	}

	public TransactionReceipt makeWithdrawal(TransactionTicket ticket) {
		TransactionReceipt result;
		return result = null;
	}

	public TransactionReceipt clearCheck(Check check) {
		Calendar today = Calendar.getInstance();
		TransactionReceipt result;
		return result = null;
	}

//private setters
	protected void setAcctBalance(double b) { // a work around of adding the two accounts

		balance = b;
	}

	private void setAcctNum(int an) {
		acctNum = an;
	}

	private void setAcctType(String type) {
		acctType = type;
	}

	private void setDepositor(Depositor d) {
		dep = d;
	}

	private void setAccountStatus(String stat) {
		// accountStatus.replaceAll(accountStatus, stat);
		accountStatus = stat;
	}

	// getters

	public String getAccountStatus() {
		return accountStatus;
	}

	public Depositor getDepositor() {
		return new Depositor(dep);
	}

	public int getAcctNumber() {
		return acctNum;
	}

	public String getAcctType() {
		return acctType;
	}

	public double getAcctBalance() {
		// returns the balance
		return balance;
	}

	/*
	 * outFile.printf("%-27s %-27s %-24s %-24s %-14s %14s %20s\n", "Name", "SSN",
	 * "Account Number", "Account Type","Account Status", "Balance",
	 * "Maturity Date");
	 */

}

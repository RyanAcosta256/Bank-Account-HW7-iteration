import java.util.ArrayList;
import java.util.Calendar;

public class CDAccount extends SavingsAccount {
	private Calendar maturityDate;

//public Account(Depositor d, int num, String type, double bal, String matDate, String status)
	public CDAccount(Depositor d, int num, String type, double bal, String matDate, String status) {
		super(d, num, type, bal, status);
		maturityDate = Calendar.getInstance();
		maturityDate.clear();
		String[] dateArray = matDate.split("/");
		maturityDate.set(Integer.parseInt(dateArray[2]), Integer.parseInt(dateArray[0]) - 1,
				Integer.parseInt(dateArray[1]));
	}

	public CDAccount(CDAccount CD) {
		super(CD.dep, CD.acctNum, CD.acctType, CD.balance, CD.accountStatus);
		maturityDate = CD.maturityDate;
		super.arrayOfReceipts = new ArrayList<>(CD.arrayOfReceipts);
	}

	

	public TransactionReceipt makeDeposit(TransactionTicket ticket) {
		TransactionReceipt result;
		if (ticket.getTransactionAmount() <= 0.0) {// invalid
			
			result = new TransactionReceipt(ticket, false,
					String.format("Error: $%.2f is an invalid amount", ticket.getTransactionAmount()), getAcctBalance(),
					getAcctBalance(), null);
			return result;
		}

		else { // valid and differentiates acctTypes
			System.out.println("THE TICKET DATE: "+ setMaturityDateString(ticket.getDateOfTransaction())) ;
			double updtedAmnt = getAcctBalance() + ticket.getTransactionAmount();
			setMatDate(ticket.getDateOfTransaction(), ticket.getTermOfCD()); // sets a new maturity date using the
																				// transaction date and term of CD

			result = new TransactionReceipt(ticket, true,
					String.format("TransactionAmount: $%.2f\nOld Account Balance: $%.2f\nNew Account Balance: $%.2f\nNew Maturity date: %s\n",
							ticket.getTransactionAmount(), getAcctBalance(), updtedAmnt, getMaturityDateString()),
					getAcctBalance(), // pre
					ticket.getTransactionAmount() + getAcctBalance(), // post
					null);
			addTransaction(result);
			//setAcctBalance(ticket.getTransactionAmount() + getAcctBalance());
			balance = ticket.getTransactionAmount() + balance;
			

			return result;

		}
	}

	public TransactionReceipt makeWithdrawal(TransactionTicket ticket) {
		TransactionReceipt result;
		if (ticket.getTransactionAmount() <= 0.0) {// invalid
			result = new TransactionReceipt(ticket, false,
					String.format("Error: $%.2f is an invalid amount", ticket.getTransactionAmount()), getAcctBalance(),
					getAcctBalance(), null);
			return result;
		} else if (ticket.getTransactionAmount() > getAcctBalance()) {// invalid

			result = new TransactionReceipt(ticket, false,
					String.format("Error: Insufficient Funds", ticket.getTransactionAmount()), getAcctBalance(),
					getAcctBalance(), null);
			return result;
		} else {// valid

			double updtedAmnt = getAcctBalance() - ticket.getTransactionAmount();

			setMatDate(ticket.getDateOfTransaction(), ticket.getTermOfCD()); // sets a new maturity date using the

			result = new TransactionReceipt(ticket, true,
					String.format("TransactionAmount: $%.2f\nOld Account Balance: $%.2f\nNew Account Balance: $%.2f\nNew Maturity date: %s\\n",
							ticket.getTransactionAmount(), getAcctBalance(), updtedAmnt, getMaturityDateString()),
					getAcctBalance(), // pre
					getAcctBalance() - ticket.getTransactionAmount(), // post
					null);
			addTransaction(result);
			balance = balance - ticket.getTransactionAmount();
			return result;
		}
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
	
	
	private void setMatDate(Calendar d, int choice) {
		d.add(Calendar.MONTH, choice);
		maturityDate = d;
	}
	//used for testing purposes 
	public String setMaturityDateString(Calendar cal) {
		String str;
		if(cal== null) {
			str = " ";
		}
		else {
			str = String.format("%02d/%02d/%4d",
					cal.get(Calendar.MONTH) + 1,
					cal.get(Calendar.DAY_OF_MONTH),
					cal.get(Calendar.YEAR)
								);
		}
		return str;
		
	}
	
	
	
	
	
	public String getMaturityDateString() {
		String str;
		if (maturityDate == null) {
			str = " ";
		} else {
			str = String.format("%02d/%02d/%4d", maturityDate.get(Calendar.MONTH) + 1,
					maturityDate.get(Calendar.DAY_OF_MONTH), maturityDate.get(Calendar.YEAR));
		}
		return str;
	}
	
	public Calendar getMaturityDate() {
		return maturityDate;
	}
}

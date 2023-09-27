import java.util.ArrayList;
import java.util.Calendar;

public class CheckingAccount  extends Account {
	//private ArrayList<TransactionReceipt> arrayOfReceipts;
	public CheckingAccount(Depositor d, int num, String type, double bal, String status) {
		super( d,  num, type, bal,  status);
		//super.arrayOfReceipts = arrayOfReceipts;// ArrayList<>();
	}
	public CheckingAccount(CheckingAccount checkingAccount) {
		super(checkingAccount.dep, checkingAccount.acctNum, checkingAccount.acctType, checkingAccount.balance, checkingAccount.accountStatus);
		//maturityDate = CD.maturityDate;
		super.arrayOfReceipts = new ArrayList<>(checkingAccount.arrayOfReceipts);
		//super.arrayOfReceipts;
	}
	/*What was working before: the array size was being filled
	 * Problem: in the main method when it received a copy of checking account, the transHistory was blank
	 * To fix: had to access the arrayOfReceipts from Account(thus the need for super.) and had it equal to a new arrayList obj that took in arrayOfReceipts from checking
	 * 
	 * 
	 */
	//ticket added for easier access and why not
	public TransactionReceipt clearCheck( Check check) {
		Calendar today = Calendar.getInstance();
		TransactionReceipt result;
		
		//public TransactionTicket(int num, Calendar date, String type, double amnt, int term) {

		
		
			TransactionTicket ticket = new TransactionTicket(check.getAcctNumber(),today, "Checking", check.getCheckAmnt(),0 );
		if ((getAcctType().equals("Checking"))) {//valid account type
			//validates whether or not the date is valid
			if ((check.getDateOfCheck().get(Calendar.YEAR) < today.get(Calendar.YEAR)
					|| ((check.getDateOfCheck().get(Calendar.MONTH) <= today.get(Calendar.MONTH) - 6)
					&& check.getDateOfCheck().get(Calendar.DAY_OF_MONTH) < today.get(Calendar.DAY_OF_MONTH))
					&& (check.getDateOfCheck().get(Calendar.YEAR) == today.get(Calendar.YEAR)))) {
				
				result = new TransactionReceipt(ticket, false, "Error: This check is older than 6 months.",getAcctBalance(), getAcctBalance(), null);
				return result;
				
			} 
			else if ((check.getDateOfCheck().get(Calendar.DAY_OF_YEAR) > (today.get(Calendar.DAY_OF_YEAR))
					&& (check.getDateOfCheck().get(Calendar.YEAR) == (today.get(Calendar.YEAR)))
					|| (check.getDateOfCheck().get(Calendar.YEAR) > (today.get(Calendar.YEAR))))) {
				
				result = new TransactionReceipt(ticket, false, "Error: Post dated checks are not allowed",getAcctBalance(), getAcctBalance(), null);
				return result;
			
			} 
			else {//preforms the actual withdrawl like action
			
					if(check.getCheckAmnt() > getAcctBalance()) {
							TransactionReceipt resultT = new TransactionReceipt(ticket, false, String.format(
								"Error: $%.2f is an invalid amount, now charging a $2.50 fee from your check to deposit\nOld Account Balance: $%.2f\nNew Account Balance: $%.2f\n",
								getAcctBalance(), getAcctBalance(), getAcctBalance()-2.50 ), getAcctBalance(), getAcctBalance() - 2.50, null);
						//setAcctBalance(getAcctBalance() - 2.50);
						balance = balance -2.50;
						addTransaction(resultT);
						return resultT;
					}
					else {
						
						
						double updtedAmnt = getAcctBalance() - check.getCheckAmnt();
						result = new TransactionReceipt(ticket, false, String.format(
								"Old Account Balance: $%.2f\nNew Account Balance: $%.2f\n", getAcctBalance(),
								updtedAmnt 
								), getAcctBalance(), getAcctBalance() - 2.50, null);
						
						addTransaction(result);
						//setAcctBalance(getAcctBalance() - check.getCheckAmnt());
						balance = balance -check.getCheckAmnt();
						return result;
					}
					
				}
			}//
		else {//invalid because invalid account type
			result = new TransactionReceipt(ticket, false, "Error: inccorect account type", getAcctBalance(),
					getAcctBalance(), null);
			return result;
		}

		} 

	
	
	public TransactionReceipt makeDeposit(TransactionTicket ticket) {
		TransactionReceipt result;
		System.out.println("DEP in CHECKING Trans His size" + getTransactionHistory(ticket).size()); //the array isn't getting filled
		if (ticket.getTransactionAmount() <= 0.0) {//invalid
			
			result = new TransactionReceipt(ticket, false, String.format("Error: $%.2f is an invalid amount\n", ticket.getTransactionAmount()), getAcctBalance(),
					getAcctBalance(), null);
			return result;
		} 
		else { //valid and differentiates acctTypes
			
			double updtedAmnt = getAcctBalance() + ticket.getTransactionAmount();
		//	System.out.println("Test  " +getAcctType());
				 result = new TransactionReceipt(ticket, true,
						 String.format("TransactionAmount: $%.2f\nOld Account Balance: $%.2f\nNew Account Balance: $%.2f\n",ticket.getTransactionAmount(), getAcctBalance(),
									updtedAmnt),
						getAcctBalance(), // pre
						ticket.getTransactionAmount() + getAcctBalance(), // post
						null);
				//setAcctBalance(ticket.getTransactionAmount() + getAcctBalance()); //where the account updates it's balance
				balance = ticket.getTransactionAmount()+balance;
			//	addTransactionHistoryType(ticket);
				addTransaction(result);
				return result;
				
			
		}
	}

	public TransactionReceipt makeWithdrawal(TransactionTicket ticket) {
		TransactionReceipt result;
		if (ticket.getTransactionAmount() <= 0.0) {//invalid
			
			result = new TransactionReceipt(ticket, false, String.format("Error: $%.2f is an invalid amount", ticket.getTransactionAmount()), getAcctBalance(),
					getAcctBalance(), null);
			return result;
		} 
		else if (ticket.getTransactionAmount() > getAcctBalance()) {//invalid
			
			result = new TransactionReceipt(ticket, false, String.format("Error: Insufficient Funds", ticket.getTransactionAmount()), getAcctBalance(),
					getAcctBalance(), null);
			return result;
		} 
		else {//valid
			
			double updtedAmnt = getAcctBalance() - ticket.getTransactionAmount();
			
				result = new TransactionReceipt(ticket, true,
						 String.format("TransactionAmount: $%.2f\nOld Account Balance: $%.2f\nNew Account Balance: $%.2f\n",ticket.getTransactionAmount(), getAcctBalance(),
									updtedAmnt),
						getAcctBalance(), // pre
						getAcctBalance() - ticket.getTransactionAmount(), // post
						null);
				//setAcctBalance(getAcctBalance() - ticket.getTransactionAmount()); //where the account updates it's balance	
				balance = balance - ticket.getTransactionAmount();
				addTransaction(result);
				return result;
				
			
		}
	}
	
	
	
	public String toString() {
		String str1 = dep.getDepositor().toString();
		String str2 = String.format("%s %-24s %-24s ", str1, acctNum, // getAcctNum(),
				acctType);
		
		return str2;
	}
	
	
	
}

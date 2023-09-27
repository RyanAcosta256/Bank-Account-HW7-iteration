import java.util.Calendar;

public class TransactionReceipt {
	private TransactionTicket ticket;
	private boolean successIndicateorFlag;
	private String reasonForFailureString;
	private double preTransactionBalance;
	private double postTransactionBalance;
	private Calendar postTransactionDate;

	public TransactionReceipt(TransactionTicket t, boolean T, String reason, double pre, double post,
			Calendar matDate) {
		ticket = t;
		successIndicateorFlag = T;
		reasonForFailureString = reason;
		preTransactionBalance = pre;
		postTransactionBalance = post;
		postTransactionDate = matDate;
	}
	
	//copy constructor
	public TransactionReceipt(TransactionReceipt re) {
		ticket = re.getTransactionTicket();
		successIndicateorFlag = re.getTransactionSuccessIndicatorFlag();
		reasonForFailureString = re.getTransactionFailureReason();
		preTransactionBalance = re.getPreTransactionBalance();
		postTransactionBalance = re.getPostTransactionBalance();
		postTransactionDate = re.getPostTransactionMaturityDate();
		
	}
//aggrgation occurred here
	public TransactionReceipt Reciept() {
		return new TransactionReceipt(ticket, successIndicateorFlag, 
				reasonForFailureString, preTransactionBalance, 
				postTransactionBalance, postTransactionDate);
		
	}
	//implement a toString() and equals() method
	//Error is that incorrectly passed parameters with str.replaceAll() are used for deposit
	public String toString() {
		String str = " ";
		switch(ticket.getTransactionType()) {
		case "Open Account":
		//	str = str.replaceAll(str, 
			 str = String.format("%s%s", ticket.toString(), reasonForFailureString);
			//return str;
			break;
		
		case "Close Account":
		// = str.replaceAll(str, 
			str = String.format("%s%s", ticket.toString(), reasonForFailureString);
			//return str;
			break;		
		case "new Account":
			//("Transaction Requested: %s\nAccount number: %d\n", typeOfTransaction, acctNum);
			//str = str.replaceAll(str, 
			 str = String.format("%s%s", ticket.toString(), reasonForFailureString);
			//return str;
			break;

		case "Account Deletion":
		//	str = str.replaceAll(str,
			 str = String.format("%s%s", ticket.toString(), reasonForFailureString);
			//return str;
			break;
			//checking, deposit, and withdrawl complicate this
		case "Deposit":
			//str = str.replaceAll(str,
			 str = String.format("%s%s", ticket.toString(), reasonForFailureString);
			//return str;
			break;
		
		case "withdrawal":
			//str = str.replaceAll(str,
			 str = String.format("%s%s", ticket.toString(), reasonForFailureString);
			//return str;
			break;
			
		case "Checking":
			//str = str.replaceAll(str,
			 str = String.format("%s%s", ticket.toString(), reasonForFailureString);
			//return str;
			break;
			
		case "Balance Inquiry":
			//str = str.replaceAll(str,
			 str = String.format("%s%s", ticket.toString(), reasonForFailureString);
			//return str;
			break;
			
		}
		return str;
		
	}

	
	public TransactionTicket getTransactionTicket() {
		return ticket;
	}

	public boolean getTransactionSuccessIndicatorFlag() {
		return successIndicateorFlag;
	}

	public String getTransactionFailureReason() {
		return reasonForFailureString;
	}

	public double getPreTransactionBalance() {
		return preTransactionBalance;
	}

	public double getPostTransactionBalance() {
		return postTransactionBalance;
	}

	public Calendar getPostTransactionMaturityDate() {
		return postTransactionDate;
	}

}

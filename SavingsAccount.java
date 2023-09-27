import java.util.ArrayList;

public class SavingsAccount extends Account{
	//public Account(Depositor d, int num, String type, double bal, String status)
		public SavingsAccount(Depositor d, int num, String type, double bal, String status) {
			super( d,  num, type, bal,  status);
		}
		public SavingsAccount(SavingsAccount SA) {
			super(SA.dep, SA.acctNum, SA.acctType, SA.balance, SA.accountStatus);
			super.arrayOfReceipts = new ArrayList<>(SA.arrayOfReceipts);

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
		
		
		
		public TransactionReceipt makeDeposit(TransactionTicket ticket) {
			TransactionReceipt result;
			if (ticket.getTransactionAmount() <= 0.0) {//invalid
				
				result = new TransactionReceipt(ticket, false, String.format("Error: $%.2f is an invalid amount", ticket.getTransactionAmount()), getAcctBalance(),
						getAcctBalance(), null);
				return result;
			} 
			else { //valid and differentiates acctTypes
				
				double updtedAmnt = getAcctBalance() + ticket.getTransactionAmount();
					 result = new TransactionReceipt(ticket, true,
							 String.format("TransactionAmount: $%.2f\nOld Account Balance: $%.2f\nNew Account Balance: $%.2f",ticket.getTransactionAmount(), getAcctBalance(),
										updtedAmnt),
							getAcctBalance(), // pre
							ticket.getTransactionAmount() + getAcctBalance(), // post
							null);
					//setAcctBalance(ticket.getTransactionAmount() + getAcctBalance()); //where the account updates it's balance		
					balance = ticket.getTransactionAmount()+balance;
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
							 String.format("TransactionAmount: $%.2f\nOld Account Balance: $%.2f\nNew Account Balance: $%.2f",ticket.getTransactionAmount(), getAcctBalance(),
										updtedAmnt),
							getAcctBalance(), // pre
							getAcctBalance() - ticket.getTransactionAmount(), // post
							null);
					//setAcctBalance(getAcctBalance() - ticket.getTransactionAmount()); //where the account updates it's balance
					balance =  balance -  ticket.getTransactionAmount();
					addTransaction(result);
					return result;
					
				
			}
		}
		




}
		/*
		 * Account holds all the personal information from dep - acctStatus
		 * Savings is a subclass that will perform the deposit/withdrawals EXCLUSIVE to an acctType that is SavingsAccount
		 * 
		 */
		
		
		
		
		
		
		

